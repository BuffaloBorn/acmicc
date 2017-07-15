package com.cc.acmi.service.config.xml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import com.cc.acmi.service.config.MainConfig;

// Referenced classes of package com.cc.acmi.service.config.xml:
//            MainConfigImp

public class AppConfigDigester extends Digester
{

    private boolean configured;
    private static final String DTD[] = {
        "-//SCC//DTD Sample AppConfig 1.1//EN", "/com/cc/sample/service/config/xml/app-config_1_1.dtd"
    };

    public AppConfigDigester()
    {
        configured = false;
    }

    public static MainConfig parse(ServletContext ctx, String fileName)
        throws IOException, SAXException
    {
        Digester digester = new AppConfigDigester();
        InputStream is = ctx.getResourceAsStream(fileName);
        MainConfigImp cfg = (MainConfigImp)digester.parse(is);
        cfg.setConfigFile(fileName);
        cfg.resolveFilenames(ctx);
        return cfg;
    }

    public Object parse(InputStream is)
        throws IOException, SAXException
    {
        configure();
        return super.parse(is);
    }

    protected void configure()
    {
        if(configured)
        {
            return;
        }
        setValidating(true);
        for(int i = 0; i < DTD.length; i += 2)
        {
            URL url = getClass().getResource(DTD[i + 1]);
            if(url != null)
            {
                register(DTD[i], url.toString());
            }
        }

        addObjectCreate("application-config", com.cc.acmi.service.config.xml.MainConfigImp.class);
        addSetProperties("application-config");
        addObjectCreate("application-config/datasource", com.cc.acmi.service.config.xml.DatasourceConfigImp.class);
        addSetProperties("application-config/datasource");
        addSetNext("application-config/datasource", "setDatasource", com.cc.acmi.service.config.xml.DatasourceConfigImp.class.getName());
        addObjectCreate("application-config/pathmap/path", com.cc.acmi.service.config.xml.PathMapping.class);
        addSetProperties("application-config/pathmap/path");
        addSetNext("application-config/pathmap/path", "addPathMapping", com.cc.acmi.service.config.xml.PathMapping.class.getName());
        addObjectCreate("application-config/security", com.cc.acmi.service.config.xml.SecurityConfigImp.class);
        addSetProperties("application-config/security");
        addSetNext("application-config/security", "setSecurity", com.cc.acmi.service.config.xml.SecurityConfigImp.class.getName());
        addObjectCreate("application-config/security/role", com.cc.acmi.service.config.xml.ConfigRole.class);
        addSetProperties("application-config/security/role");
        addSetNext("application-config/security/role", "addRole", com.cc.acmi.service.config.xml.ConfigRole.class.getName());
        addObjectCreate("application-config/security/role/permission", com.cc.acmi.service.security.Grant.class);
        addSetProperties("application-config/security/role/permission");
        addSetNext("application-config/security/role/permission", "addPermission", com.cc.acmi.service.security.Grant.class.getName());
        configured = true;
    }

}
