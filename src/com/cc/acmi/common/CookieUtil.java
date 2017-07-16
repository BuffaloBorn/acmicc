package com.cc.acmi.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class CookieUtil {
	
	private static Logger log = Logger.getLogger(CookieUtil.class);
	
	
	public static String getCookieValue( HttpServletRequest request ,String cookieName)
	{
		Cookie[] cookies = request.getCookies();
		String  CookieValue = null;
		
		if (cookies != null )
		{
			for (int i = 0; i < cookies.length; i++) 
		    {
	            Cookie c = cookies[i];
	            CookieValue = c.getValue();
	           
	            if ( cookieName.equalsIgnoreCase(c.getName()) )
	            {
	            	return CookieValue;
	            }
		    }
		}
		return null;
	}
	
	public static void setCookie(HttpServletResponse response, String cookieName, String cookieValue, String cookiePath)
	{
		Cookie newCookie = new Cookie(cookieName, cookieValue);
		
		int expiry = 1*24*60*60;
		
		newCookie.setPath(cookiePath);
		newCookie.setMaxAge(expiry);
		response.addCookie(newCookie);
		
		log.debug("Created cookie: " + cookieName + " with path of: " + cookiePath + " ,max age of: " + expiry + " and a vaule of: " + cookieValue );
	}
	
	public static void setCookie(HttpServletResponse response, int expiry, String cookieName, String cookieValue, String cookiePath)
	{
		Cookie newCookie = new Cookie(cookieName, cookieValue);
		newCookie.setPath(cookiePath);
		newCookie.setMaxAge(expiry);
		response.addCookie(newCookie);	
		
		log.debug("Created cookie: " + cookieName + " with path of: " + cookiePath + " ,max age of: " + expiry + " and a vaule of: " + cookieValue );
	}
	
	public static void deleteCookie(HttpServletResponse response, String cookieName, String cookieValue, String cookiePath)
	{
		setCookie(response, 0, cookieName, cookieValue, cookiePath);
	}
	
	public static void setUpdateCloseAndIaspopupCookie(HttpServletRequest request, HttpServletResponse response)
	{
		String cookieName = null; 
		String cookieValue = null;
		String cookiePath = null;
			      
	    int expiry = 1*24*60*60;
	      		          
    	if(getCookieValue(request,"updateClose").equalsIgnoreCase("close")) 
    	{
    		cookieName = "iaspopup";
    		cookieValue = "close";
    		cookiePath = "/acmicc/";
    		
    		setCookie(response, expiry, cookieName, cookieValue, cookiePath);
			
			log.debug("Created cookie: " + cookieName + " with path of: " + cookiePath + " ,max age of: " + expiry + " and a vaule of: " + cookieValue );
			
			cookieName = "updateClose";
    		cookieValue = "open";
    		cookiePath = "/acmicc/";
			
    		setCookie(response, expiry, cookieName, cookieValue, cookiePath);
			
			log.debug("Created cookie: " + cookieName + " with path of: " + cookiePath + " ,max age of: " + expiry + " and a vaule of: " + cookieValue );
    	}	
	}
	
	public static void setUpHelpCookie(HttpServletResponse response, String PageUrl)
	{
		String cookieName = null; 
		String cookieValue = null;
		String cookiePath = null;
		
		int expiry = 1*24*60*60;
		
		cookieName = "prevPage";
		cookieValue = PageUrl;
		cookiePath = "/acmicc/";
		
		setCookie(response, expiry, cookieName, cookieValue, cookiePath);
		
	}


}
