package com.epm.acmi.struts.action;

import javax.servlet.http.HttpSession;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.security.SecurityUtil;

/**
 * Common Controls Framework class, Extracted from the framework sample
 * 
 * @author Jay Hombal
 */
public abstract class CCAction extends FWAction {

	public CCAction() {
	}

	/**
	 * Returns true if the action is logon action
	 * 
	 * @return boolean
	 */
	public boolean isLogonAction() {
		return false;
	}

	/**
	 * Returns true if the session is valid
	 * 
	 * @param ctx
	 * @return boolean
	 */
	public final boolean isValidSession(ActionContext ctx) {
		HttpSession session = ctx.session();
		if (session == null || session.isNew()) {
			return false;
		}
		com.cc.framework.security.Principal principal = SecurityUtil.getPrincipal(ctx.session());
		return principal != null;
	}
	
	/**
	 * Executes before handling the request
	 */
	public boolean doPreExecute(ActionContext ctx) throws Exception {
		if (isLogonAction()) {
			return true;
		}
		
		if (!isValidSession(ctx)) {
			ctx.addGlobalError("message.nosession");
			ctx.forwardByName("logon");
			return false;
		} else {
			return true;
		}
	}

}
