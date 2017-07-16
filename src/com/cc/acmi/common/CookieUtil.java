package com.cc.acmi.common;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.cc.framework.adapter.struts.FormActionContext;

public class CookieUtil {
	
	private static Logger log = Logger.getLogger(CookieUtil.class);
	
	public static void setUpdateCloseAndIaspopupCookie(FormActionContext ctx)
	{
		Cookie[] cookies = ctx.request().getCookies();
		String cookieName = null; 
		String cookieValue = null;
		String cookiePath = null;
		
		if (cookies != null )
		{
			for (int i = 0; i < cookies.length; i++) 
		    {
	            Cookie c = cookies[i];
	            String name = c.getName();
	            String value = c.getValue();
	           
	      		int expiry = 1*24*60*60;
	      		
	            if (name.equalsIgnoreCase("updateClose"))
				{
	            	if(value.equalsIgnoreCase("close")) 
	            	{
	            		cookieName = "iaspopup";
	            		cookieValue = "close";
	            		cookiePath = "/acmicc/";
	            		
	            		Cookie iaspopup = new Cookie(cookieName, cookieValue);
	            		iaspopup.setPath(cookiePath);
	            		iaspopup.setMaxAge(expiry);
						ctx.response().addCookie(iaspopup);
						
						log.debug("Created cookie: " + cookieName + " with path of: " + cookiePath + " ,max age of: " + expiry + " and a vaule of: " + cookieValue );
						
						cookieName = "updateClose";
	            		cookieValue = "open";
	            		cookiePath = "/acmicc/";
						
	            		Cookie updateClose = new Cookie(cookieName, cookieValue);
	            		updateClose.setPath(cookiePath);
						updateClose.setMaxAge(expiry);
						ctx.response().addCookie(updateClose);
						
						log.debug("Created cookie: " + cookieName + " with path of: " + cookiePath + " ,max age of: " + expiry + " and a vaule of: " + cookieValue );
	            	}
				}
		      }				
		}	
		
		
	}

}
