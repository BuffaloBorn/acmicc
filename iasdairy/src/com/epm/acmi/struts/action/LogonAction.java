package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.painter.PainterFactory;
import com.epm.acmi.struts.form.LogonActionForm;
//import com.epm.acmi.util.ACMICache;

public class LogonAction extends CCAction {

	private static Logger log = Logger.getLogger(LogonAction.class);
	
	
	  public LogonAction()
	  {
	  }


	  public void doExecute(ActionContext ctx)  throws Exception
	  {
		  ctx.forwardToInput();
	  }

	  public boolean isLogonAction()
	    {
	        return true;
	    }
	  
	    public void logon_onClick(FormActionContext ctx)
	    {
	        log.debug("Begin logon_onClick()");
	        LogonActionForm form = (LogonActionForm)ctx.form();
	        try
	        {
	            User user = new User();
	            user.setUserId(form.getUserId());
	            user.setPassword(form.getPassword());
//	            ACMICache acmiCache = new ACMICache();
//	            LDAPUser cachedUser = acmiCache.getUser(form.getUserId().toLowerCase());
//	            EPMHelper epmh = new EPMHelper();
//	            com.fujitsu.iflow.model.workflow.WFSession wfsession = null;
	            
	            try
	            {
		            log.debug("Authenticating user with EPM");
		            //wfsession = epmh.connectAsUser(form.getUserId(), form.getPassword());
		            log.debug("EPM Authentication successfull");
		            user.getSettings().setLocale(form.getLocale());
		            
		           /* if(wfsession == null)
		            {
		                ctx.forwardByName("failure");
		                throw new Exception("User could not be logged in ");
		            }*/
	            } finally
	            {
	            	try
	            	{
	            	/*	if ((epmh != null) && (wfsession != null))
	            			epmh.disconnect(wfsession);*/
	            	} catch (Exception e)
	            	{
	            		log.error("Exception while disconnecting from EPM Server", e);
	            	}
	            }
	            
	            /*if(cachedUser != null)
	            {
	                user.setFirstName(cachedUser.getFirstName());
	                user.setLastName(cachedUser.getLastName());
	                user.setRole(cachedUser.getRoles().toString());
	                log.info("Logged User: " + cachedUser.getFirstName() + " " + cachedUser.getLastName() + " Roles:" + cachedUser.getRoles().toString());
	            }*/
	            
	            log.debug("Painter selected: " + form.getUiType());
	            PainterFactory.resetSessionPainter(ctx.session());
	            
	            if(!user.getUserId().equalsIgnoreCase(form.getUserId()))
	            {
	                ctx.forwardByName("failure");
	            }
	            
	            ctx.session().setAttribute("USER", user);
	            SecurityUtil.registerPrincipal(ctx.session(), user);            
	    		
	          /*  LDAPIUAUserUtil iuaUserUtil = new LDAPIUAUserUtil();
	    		
	    		try {
	    			
	    			iuaUserUtil.getLDAPIUAUserUtil(user);
	    			
	    		} catch (Exception e) {
	    			
	    			log.error("Error while trying to update ACMI User table with LDAP information", e);
	    		}*/
	    		
	            ctx.forwardToAction("main/secondarytabsetBrowse");
	        }
	        catch(Exception t)
	        {
	            form.setPassword("");
	            form.setUserId("");
	            ctx.addGlobalError("user.invalid.msg");
	            log.warn("Invalid User ID/Password");
	            ctx.forwardToInput();
	        }
	        log.debug("End logon_onClick()");
	    }


}
