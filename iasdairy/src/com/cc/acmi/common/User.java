package com.cc.acmi.common;

import java.io.Serializable;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cc.framework.security.Principal;

/**
 * User class holds the inforamtion about logged user by IU&A application.
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class User  implements BusinessObject, Principal, Serializable
{

    private static final long serialVersionUID = 0x2b7e6b36e0d91e68L;
    protected transient Log log;
    private String userId;
    private String firstName;
    private String lastName;
    private String role;
    private String phone;
    private String email;
    private String password;
    private UserSettings settings;
    private String AAID;

    public User()
    {
        log = LogFactory.getLog(getClass());
        userId = "";
        firstName = "";
        lastName = "";
        role = null;
        phone = "";
        email = "";
        password = "";
        settings = new UserSettings();
    }

    public User(String userId)
    {
        log = LogFactory.getLog(getClass());
        this.userId = "";
        firstName = "";
        lastName = "";
        role = null;
        phone = "";
        email = "";
        password = "";
        settings = new UserSettings();
        this.userId = userId;
    }

    public TimeZone getTimeZone()
    {
        return TimeZone.getDefault();
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getRole()
    {
        return role;
    }

    public String getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return lastName + ", " + firstName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public static String getUserName(String lastName, String firstName)
    {
        return lastName + ", " + firstName;
    }

    public UserSettings getSettings()
    {
        return settings;
    }

    public boolean hasRight(String checkForRight)
    {
        return role != null;
    }

    public boolean isInRole(String checkForRole)
    {
        if(role == null)
        {
            return false;
        } else
        {
            return role.equals(checkForRole);
        }
    }

    public String getAAID()
    {
        return AAID;
    }

    public void setAAID(String aaid)
    {
        AAID = aaid;
    }

    public void setSettings(UserSettings settings)
    {
        this.settings = settings;
    }
}
