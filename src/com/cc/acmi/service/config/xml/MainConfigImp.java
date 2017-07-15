package com.cc.acmi.service.config.xml;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletContext;

import com.cc.acmi.service.config.DatasourceConfig;
import com.cc.acmi.service.config.MainConfig;
import com.cc.acmi.service.config.PathConfig;
import com.cc.acmi.service.config.SecurityConfig;

// Referenced classes of package com.cc.acmi.service.config.xml:
//            PathMapping

public final class MainConfigImp
    implements MainConfig, PathConfig
{

    private String version;
    private String configFile;
    private DatasourceConfig datasource;
    private SecurityConfig security;
    private Hashtable pathMap;

    public MainConfigImp()
    {
        version = null;
        datasource = null;
        security = null;
        pathMap = new Hashtable();
    }

    public DatasourceConfig getDatasource()
    {
        return datasource;
    }

    public void setDatasource(DatasourceConfig datasource)
    {
        this.datasource = datasource;
    }

    public SecurityConfig getSecurity()
    {
        return security;
    }

    public void setSecurity(SecurityConfig security)
    {
        this.security = security;
    }

    public void addPathMapping(PathMapping mapping)
    {
        pathMap.put(mapping.getVirtname(), mapping);
    }

    public String getPath(String virtpath)
    {
        PathMapping mapping = (PathMapping)pathMap.get(virtpath);
        return mapping != null ? mapping.getPath() : null;
    }

    public String[] getPathList(String pattern)
    {
        ArrayList list = new ArrayList();
        for(Enumeration e = pathMap.keys(); e.hasMoreElements();)
        {
            String key = (String)e.nextElement();
            if(key.startsWith(pattern))
            {
                list.add(getPath(key));
            }
        }

        String result[] = new String[list.size()];
        return (String[])list.toArray(result);
    }

    public String mapFile(String virtpath, String file)
    {
        if(file == null)
        {
            return null;
        }
        if(file.indexOf(":") != -1)
        {
            return file;
        }
        String path = getPath(virtpath);
        if(path == null)
        {
            return file;
        }
        if(path.endsWith("/") || path.endsWith("\\"))
        {
            return path + file;
        } else
        {
            return path + "/" + file;
        }
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String string)
    {
        version = string;
    }

    public void setConfigFile(String fileName)
    {
        configFile = fileName;
    }

    public String getConfigFile()
    {
        return configFile;
    }

    public void resolveFilenames(ServletContext ctx)
    {
        for(Enumeration e = pathMap.keys(); e.hasMoreElements();)
        {
            String virtpath = (String)e.nextElement();
            PathMapping pathCfg = (PathMapping)pathMap.get(virtpath);
            if(pathCfg.isTranslate())
            {
                String path = pathCfg.getPath();
                if(path.indexOf(":") == -1)
                {
                    path = ctx.getRealPath(path);
                }
                path = path.replace('\\', '/');
                pathCfg.setPath(path);
            }
        }

    }

    public PathConfig getPath()
    {
        return this;
    }
}
