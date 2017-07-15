package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.dsp.StellentClient;

/**
 * LogoutAction - logout user
 * 
 * @author Jay Hombal
 */
public class LogoutAction extends CCAction {

	private static Logger log = Logger.getLogger(LogoutAction.class);

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {
		// Remove user login
		log.debug("Begin doExecute()");

		log.debug("Removing EPM Session");
		User loggedUser = (User) ctx.session().getAttribute(Constants.loggedUser);
		log.debug("EPM Session Removed");

		StellentClient.logoutByUserNonRedundant(loggedUser);
		log.debug("User Stellent Session Removed");
		
		ctx.session().removeAttribute(Constants.loggedUser);
		ctx.session().removeAttribute(Constants.gfid);
		
		try
		{
			ctx.session().invalidate();
		} catch (Exception e)
		{
			log.debug(e.getClass().getName() + " thrown while invalidating HTTP session: " + e.getMessage());
		}

		log.debug("Logout Successful");

		log.debug("End doExecute()");

		// Return success
		ctx.forwardToInput();

	}

}
