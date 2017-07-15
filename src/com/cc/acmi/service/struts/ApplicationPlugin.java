package com.cc.acmi.service.struts;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.cc.acmi.Version;
import com.cc.framework.Globals;
import com.cc.framework.http.HttpUtil;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.html.HtmlPainterFactory;

/**
 * Common Control Specific class
 * @author Jay Hombal
 *
 */
public class ApplicationPlugin
    implements PlugIn
{

    private Log log;

    public ApplicationPlugin()
    {
        log = LogFactory.getLog(getClass());
    }

    public void destroy()
    {
    }

    public void init(ActionServlet actionServlet, ModuleConfig moduleConfig)
        throws ServletException
    {
        actionServlet.getServletContext().setAttribute(Globals.LOCALENAME_KEY, "true");
        PainterFactory.registerApplicationPainter(actionServlet.getServletContext(), HtmlPainterFactory.instance());
        if(log.isInfoEnabled())
        {
            log.info(HttpUtil.createVersionInfo(Version.instance()));
            log.info(HttpUtil.createEnvironmentInfo());
            log.info(HttpUtil.createContextInfo(actionServlet.getServletContext()));
        }
    }
}
