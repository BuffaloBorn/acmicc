package com.cc.acmi.service.config.xml;

import com.cc.acmi.service.security.Grant;
import com.cc.acmi.service.security.PermissionList;
import com.cc.acmi.service.security.UserRole;

public class ConfigRole
    implements UserRole, Comparable
{

    private String code;
    private String name;
    private boolean inverse;
    private PermissionList permissions;

    public ConfigRole()
    {
        inverse = false;
        permissions = new PermissionList();
    }

    public ConfigRole(String code, String name)
    {
        inverse = false;
        permissions = new PermissionList();
        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public String getName()
    {
        return name;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setInverse(boolean inverse)
    {
        this.inverse = inverse;
    }

    public void addPermission(Grant grant)
    {
        permissions.grant(grant);
    }

    public boolean hasRight(String right)
    {
        boolean granted = permissions.isGranted(right);
        if(inverse)
        {
            granted = !granted;
        }
        return granted;
    }

    public int compareTo(Object o)
    {
        if(o instanceof ConfigRole)
        {
            return name.compareTo(((ConfigRole)o).name);
        } else
        {
            return 0;
        }
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof String)
        {
            return code.equals(obj);
        } else
        {
            return super.equals(obj);
        }
    }

    public String toString()
    {
        return "Role: " + code + " [" + name + "]";
    }
}
