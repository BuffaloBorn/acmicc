package com.epm.acmi.util;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Represents User Object
 * @author Jay Hombal
 *
 */
public class LDAPUser
    implements Serializable, Comparable
{

    private static final long serialVersionUID = 1L;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String cn;
    private ArrayList roles;
    private String fullName;

    public LDAPUser()
    {
        userId = "";
        email = "";
        firstName = "";
        lastName = "";
        cn = "";
        roles = new ArrayList();
    }

    public String getCn()
    {
        return cn;
    }

    public void setCn(String cn)
    {
        this.cn = cn;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFullName() {
    	fullName = getFirstName() + " " + getLastName();
    	return fullName;
    }
    
    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String toString()
    {
        return "firstname=" + firstName + ", " + "lastname=" + lastName + ", " + "userId=" + userId + ", " + "email=" + email + " Roles: " + roles;
    }

    public void setRole(String role)
    {
        roles.add(role);
    }

    public boolean hasRole(String role)
    {
        if(!roles.isEmpty())
        {
            return roles.contains(role);
        } else
        {
            return false;
        }
    }

	public ArrayList getRoles() {
		return roles;
	}

	public void setRoles(ArrayList roles) {
		this.roles = roles;
	}
	
	public int compareTo(Object obj) {
		
		if (obj == null) {
			return -1;
		}
		String firstName = ((LDAPUser)obj).getFirstName();
		String fName = getFirstName();
		
		if (firstName != null && fName != null) {
			return fName.compareTo(firstName);
		}
		
		return 0;
	}
}
