package com.cc.acmi.service.config.xml;

import com.cc.acmi.service.config.DatasourceConfig;

public class DatasourceConfigImp
    implements DatasourceConfig
{

    private String driver;
    private String url;
    private String user;
    private String password;

    public DatasourceConfigImp()
    {
    }

    public String getDriver()
    {
        return driver;
    }

    public String getPassword()
    {
        return password;
    }

    public String getUrl()
    {
        return url;
    }

    public String getUser()
    {
        return user;
    }

    public void setDriver(String string)
    {
        driver = string;
    }

    public void setPassword(String string)
    {
        password = string;
    }

    public void setUrl(String string)
    {
        url = string;
    }

    public void setUser(String string)
    {
        user = string;
    }
}
