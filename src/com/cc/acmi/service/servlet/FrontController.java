package com.cc.acmi.service.servlet;

import java.text.MessageFormat;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionServlet;

import com.cc.acmi.Version;
import com.cc.framework.Globals;
import com.cc.framework.adapter.FrameworkAdapterFactory;
import com.cc.framework.http.HttpUtil;
import com.cc.framework.ui.painter.PainterFactory;
import com.cc.framework.ui.painter.def2.Def2PainterFactory;
import com.cc.framework.ui.painter.html.HtmlPainterFactory;

/**
 * Controller Servlet handles all requests to the web application
 * @author Jay Hombal
 *
 */
public class FrontController extends ActionServlet
{

    private static final long serialVersionUID = 0x3f4606887e4ba28cL;
    private Log log;

    public FrontController()
    {
        log = LogFactory.getLog(getClass());
    }

    public void init()
        throws ServletException
    {
        if(log.isInfoEnabled())
        {
            log.debug("********** Beginning initialization ***********");
            log.debug(HttpUtil.createVersionInfo(Version.instance()));
            log.debug(HttpUtil.createEnvironmentInfo());
            log.debug(HttpUtil.createContextInfo(getServletContext()));
        }
        super.init();
        log.info("FrontController initialized!");
        
        // Initalize custom painters
		initCustomPainters();

		// Enable resource key translation for all elements
		getServletContext().setAttribute(com.cc.framework.Globals.LOCALENAME_KEY, "true");

        getServletContext().setAttribute(Globals.LOCALENAME_KEY, "true");
        PainterFactory.registerApplicationPainter(getServletContext(), Def2PainterFactory.instance());
        PainterFactory.registerApplicationPainter(getServletContext(), HtmlPainterFactory.instance());
        
        PainterFactory.registerApplicationPainters(getServletContext());

		FrameworkAdapterFactory.registerAdapter(new AppStrutsFrameworkAdapter());

        
    }
    
	/**
	 * Registers and initializes any custom painters
	 * the user can select
	 */
	private void initCustomPainters() {

		log.debug("entering initCustomPainters()");
		
		String pattern = "com.cc.acmi.service.servlet.ambiente.Custom{0}Ambiente";

		for (int i = 0; i < 10; i++) {
			String className = MessageFormat.format(pattern, new Integer[] { new Integer(i) });
			 
			try {
				PainterInitializer init = (PainterInitializer) Class.forName(className).newInstance();

				log.info("register custom painter " + init.getClass().getName());

				// Register ambiente painter
				init.register();

			} catch (Exception e) {
				// Initializer class is not included in this JAR file
				// -> do not register custom painters
			}
		}
	}
}
  