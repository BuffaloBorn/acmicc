package com.epm.acmi.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.PrevPolicyForm;
import com.epm.acmi.util.PrevPolicyDBHelper;

/**
 * Previous policy Action
 * 
 * @author Dragos
 */
public class PrevPolicyAction extends FWAction {

	private static Logger log;

	static {
		log = Logger.getLogger(com.epm.acmi.struts.action.PrevPolicyAction.class);
	}

	/**
	 * @see com.cc.framework.adapter.struts.FWAction#doExecute(ActionContext)
	 */

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws IOException, ServletException {

		String action = ctx.request().getParameter("action");
		String prevPolicyNum = ctx.request().getParameter("param");
		String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
		PrevPolicyForm form = null;
		PrevPolicyDBHelper dbHelp;

		log.debug("Entering method doExecute()");

		log.debug("In doexecute of PrevPolicyAction. Action = " + action);
		log.debug("prevPolicyNum = " + prevPolicyNum);

		// Display the Page with the PrevPolicyList
		ctx.session().setAttribute("showPrevPolForm", "false");

		try {

			if (action != null) {

				if (action.equals("Create")) {
					form = new PrevPolicyForm();
					ctx.session().setAttribute("prevPolicyForm", form);
					ctx.session().setAttribute("showPrevPolForm", "true");
				} else if (action.equals("Edit")) {
					dbHelp = new PrevPolicyDBHelper();
					form = dbHelp.getFormFromDB(gfid, prevPolicyNum);
					ctx.session().setAttribute("prevPolicyForm", form);
					ctx.session().setAttribute("showPrevPolForm", "true");
				} else if (action.equals("Delete")) {
					dbHelp = new PrevPolicyDBHelper();
					dbHelp.deleteFromDB(gfid, prevPolicyNum);
				}

			}
		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Error", ex);
			
		}

		log.debug("Exiting method doExecute()");
	
		ctx.forwardToAction("main/iuauser/nbws?ctrl=nbwstabset&action=TabClick&param=tab113");
	}

	/**
	 * Save button click event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void save_onClick(FormActionContext ctx) throws Exception {

		PrevPolicyForm form;
		PrevPolicyDBHelper dbHelp;
		String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
		User user = (User) ctx.session().getAttribute(Constants.loggedUser);
		String userID = null;

		log.debug("Entering method save_onClick()");

		try {
			if (user != null) {
				userID = user.getUserId();
			}

			form = (PrevPolicyForm) ctx.form();

			log.debug(form.getPreviousPolicyID());
			log.debug(form.getStatus());
			log.debug(form.getPtd());
			log.debug(form.getEffDate());
			log.debug(form.getInsuredName());
			log.debug("" + form.getShortTermPolicy());

			dbHelp = new PrevPolicyDBHelper();
			dbHelp.saveToDB(ctx, gfid, form, userID);
		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Error", ex);
		}

		log.debug("Exiting method save_onClick()");
	}

	/**
	 * Cancel Button event handler
	 * @param ctx
	 * @throws Exception
	 */
	public void cancel_onClick(FormActionContext ctx) throws Exception {

		log.debug("Entering method cancel_onClick()");
		ctx.session().setAttribute("showPrevPolForm", "false");
		log.debug("Exiting method cancel_onClick()");
	}

}