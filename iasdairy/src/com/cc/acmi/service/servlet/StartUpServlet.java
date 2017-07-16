package com.cc.acmi.service.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.epm.acmi.util.ACMICache;

public class StartUpServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3894055809217843941L;

	private static Logger log = Logger.getLogger(StartUpServlet.class);
	
	public StartUpServlet() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * init method build ACMI cache and starts cache refresh thread and event tracker thread
	 */
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");
		

		if (file != null) {
			PropertyConfigurator.configure(prefix + file);
		}

		log.info("ACMIIUA Initializing ....");
		

		log.info("Building Standard Event Codes Cache...");
		ACMICache.loaDStdEventCodes();
		log.info("Standard Event Codes Cache built....");
		
		log.info("Building Under Writer Status Codes Cache...");
		ACMICache.loaDUnderWriterStatusCodes();
		log.info("Under Writer Status Codes Cache built....");
		
		log.info("Building Memo Id Codes Cache...");
		ACMICache.loaDMemoIdCodes();
		log.info("Under Memo Id Codes Cache built....");

	}

	static {
		log = Logger.getLogger(com.cc.acmi.service.servlet.StartUpServlet.class);
	}

}
