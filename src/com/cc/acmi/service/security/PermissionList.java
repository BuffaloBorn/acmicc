package com.cc.acmi.service.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

// Referenced classes of package com.cc.acmi.service.security:
//            Grant

public class PermissionList
{

    private Set literals;
    private List expressions;

    public PermissionList()
    {
        literals = new HashSet();
        expressions = new Vector();
    }

    public void grant(String permission)
    {
        if(permission.endsWith("*"))
        {
            expressions.add(permission.substring(0, permission.length() - 1));
        } else
        {
            literals.add(permission);
        }
    }

    public void grant(Grant grant)
    {
        if(grant.isGranted())
        {
            grant(grant.getCode());
        }
    }

    public boolean isGranted(String permission)
    {
        if(literals.contains(permission))
        {
            return true;
        }
        for(int i = 0; i < expressions.size(); i++)
        {
            String grant = (String)expressions.get(i);
            if(permission.startsWith(grant))
            {
                return true;
            }
        }

        return false;
    }
}
