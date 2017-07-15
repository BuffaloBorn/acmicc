package com.cc.acmi.service.config;


// Referenced classes of package com.cc.acmi.service.config:
//            DatasourceConfig, SecurityConfig, PathConfig

public interface MainConfig
{

    public abstract DatasourceConfig getDatasource();

    public abstract SecurityConfig getSecurity();

    public abstract PathConfig getPath();
}
