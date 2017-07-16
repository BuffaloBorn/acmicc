package com.cc.acmi.service.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epm.acmi.util.ACMICache;

public class ReloadIasCache extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(ReloadIasCache.class);
	
	public ReloadIasCache()	{
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
				log.debug("Getting ready to Refresh IAS Cache");
				ACMICache.getIasCodesTable();
				log.debug("Finished Refreshing IAS Cache");
		} catch(Exception e){
			log.debug("refersh.ias.error");
			log.error(e);
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Refreahing IAS Cache</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<CENTER><BIG>Finished Refreshing IAS Cache</BIG></CENTER>");
		out.println("</BODY></HTML>");
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		doGet(request, response);
	}
	
	
}
