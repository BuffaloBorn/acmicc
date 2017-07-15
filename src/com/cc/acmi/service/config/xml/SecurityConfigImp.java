package com.cc.acmi.service.config.xml;

import java.util.Arrays;
import java.util.Hashtable;

import com.cc.acmi.service.config.SecurityConfig;
import com.cc.acmi.service.security.UserRole;

// Referenced classes of package com.cc.acmi.service.config.xml:
//            ConfigRole

public final class SecurityConfigImp
    implements SecurityConfig
{

    private Hashtable roles;

    public SecurityConfigImp()
    {
        roles = new Hashtable();
    }

    public void addRole(ConfigRole role)
    {
        roles.put(role.getCode(), role);
    }

    public UserRole[] getRoles()
    {
        ConfigRole roleList[] = new ConfigRole[roles.size()];
        roleList = (ConfigRole[])roles.values().toArray(roleList);
        Arrays.sort(roleList);
        return roleList;
    }

    public UserRole parseRole(String code)
        throws IllegalArgumentException
    {
        if(code == null)
        {
            return getGuestRole();
        }
        String c = code.trim();
        if("".equals(c))
        {
            return getGuestRole();
        }
        UserRole role = (UserRole)roles.get(c);
        if(role == null)
        {
            throw new IllegalArgumentException("Invalid UserRole [" + c + "]");
        } else
        {
            return role;
        }
    }

    public UserRole getGuestRole()
    {
        ConfigRole guest = (ConfigRole)roles.get("guest");
        if(guest == null)
        {
            guest = new ConfigRole("guest", "Guest");
            addRole(guest);
        }
        return guest;
    }
}
