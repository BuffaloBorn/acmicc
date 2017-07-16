package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.security.SecurityUtil;
import com.cc.framework.ui.painter.PainterFactory;
import com.epm.acmi.struts.form.LogonActionForm;
import com.epm.acmi.util.*;
import java.util.ArrayList;

// Referenced classes of package com.epm.acmi.struts.action:
//            CCAction

public class LogonAction extends CCAction
{

    private static final Logger log;

    public LogonAction()
    {
    }

    public void doExecute(ActionContext ctx)
        throws Exception
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
            ACMICache acmiCache = new ACMICache();
            LDAPUser cachedUser = acmiCache.getUser(form.getUserId().toLowerCase());
            EPMHelper epmh = new EPMHelper();
            ArrayList list = new LDAPUtil().getAllEPMGroups();
            com.fujitsu.iflow.model.workflow.WFSession wfsession = null;
            String userId = form.getUserId();
            String password = form.getPassword();
                 	
            try {
	            log.debug("Authenticating user with EPM");
	            wfsession = epmh.connectAsUser( userId, password);
	            log.debug("EPM Authentication successfull");
	            user.getSettings().setLocale(form.getLocale());	       
	            
	            if(wfsession == null)
	            {
	                ctx.forwardByName("failure");
	                throw new Exception("User could not be logged in ");
	            }
            } finally
            {
            	try
            	{
            		if ((epmh != null) && (wfsession != null))
            			epmh.disconnect(wfsession);
            	} catch (Exception e)
            	{
            		log.error("Exception while disconnecting from EPM Server", e);
            	}
            }
            
            if(cachedUser != null)
            {
            	log.debug("Getting User from Cache " + userId);
            	setUserObject(user, cachedUser);
            }
            else { //then try to authenticate thru LDAP.
            	log.debug("Couldn't find user in Cache, authenticating thru LDAP " + userId);
            	String dn = LDAPUtil.authenticate(userId, password);
            	if (dn != null) {
            		LDAPUser usr1 = new LDAPUtil().getUserAttributes(dn, null);
            		if (usr1.getRoles() != null ) {
            			log.debug("User authenticated thru ldap successfully");
            			acmiCache.addUser(userId, usr1);
            			setUserObject(user, usr1);
            		}
            		else { //User doesn't exists
            			log.debug("User " + userId + "Doesn't exists");
            			throw new Exception();
            		}
            	} else { //User doesn't exists
            		log.debug("User " + userId + "Doesn't exists");
        			throw new Exception();
            	}
            }
            
            log.debug("Painter selected: " + form.getUiType());
            PainterFactory.resetSessionPainter(ctx.session());
            
            if(!user.getUserId().equalsIgnoreCase(form.getUserId()))
            {
                ctx.forwardByName("failure");
            }
            
            ctx.session().setAttribute("USER", user);
            SecurityUtil.registerPrincipal(ctx.session(), user);            
    		
            LDAPIUAUserUtil iuaUserUtil = new LDAPIUAUserUtil();
    		
    		try {
    			
    			iuaUserUtil.getLDAPIUAUserUtil(user);
    			
    		} catch (Exception e) {
    			
    			log.error("Error while trying to update ACMI User table with LDAP information", e);
    		}
    		
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

    private void setUserObject(User user, LDAPUser cachedUser) {
    	user.setFirstName(cachedUser.getFirstName());
        user.setLastName(cachedUser.getLastName());
        user.setRole(cachedUser.getRoles().toString());
        
        String[] roleStrings = MiscellaneousUtils.getRoleStrings(user.getRole());                
        user.setRoleStr1(roleStrings[0]);
        user.setRoleStr2(roleStrings[1]);
        
        log.info("Logged User: " + cachedUser.getFirstName() + " " + cachedUser.getLastName() + " Roles:" + cachedUser.getRoles().toString());
    }
    
    static 
    {
        log = Logger.getLogger(com.epm.acmi.struts.action.LogonAction.class);
    }
}