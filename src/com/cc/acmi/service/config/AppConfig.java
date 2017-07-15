package com.cc.acmi.service.config;

import com.cc.framework.common.Singleton;

/**
 * AppConfig is Common control specific class. 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.2 $
 */
public class AppConfig
    implements Singleton
{

    private static MainConfig config = null;

    private AppConfig()
    {
    }

    public static synchronized void register(MainConfig cfg)
    {
        config = cfg;
    }

    public static DatasourceConfig getDatasource()
    {
        if(config == null)
        {
            throw new IllegalAccessError("Configuration is not set!");
        } else
        {
            return config.getDatasource();
        }
    }

    public static SecurityConfig getSecurity()
    {
        if(config == null)
        {
            throw new IllegalAccessError("Configuration is not set!");
        } else
        {
            return config.getSecurity();
        }
    }

    public static String getPath(String virtpath)
    {
        if(config == null)
        {
            throw new IllegalAccessError("Configuration is not set!");
        } else
        {
            return config.getPath().getPath(virtpath);
        }
    }

    public static String[] getPathList(String pattern)
    {
        if(config == null)
        {
            throw new IllegalAccessError("Configuration is not set!");
        } else
        {
            return config.getPath().getPathList(pattern);
        }
    }

    public static String mapFile(String virtpath, String file)
    {
        if(config == null)
        {
            throw new IllegalAccessError("Configuration is not set!");
        } else
        {
            return config.getPath().getPath(virtpath);
        }
    }

}
