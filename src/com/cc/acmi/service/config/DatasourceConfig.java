package com.cc.acmi.service.config;


public interface DatasourceConfig
{

    public abstract String getDriver();

    public abstract String getUrl();

    public abstract String getUser();

    public abstract String getPassword();
}
