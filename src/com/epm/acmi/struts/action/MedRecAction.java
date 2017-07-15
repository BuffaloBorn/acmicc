package com.epm.acmi.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.MedRecForm;
import com.epm.acmi.util.MedRecDBHelper;

/**
 * MedRecAction  handles Medical records tab actions
 * @author Dragos 
 *
 */
public class MedRecAction extends FWAction {
	
	
	private static Logger log;
	
	static {
		log = Logger.getLogger(com.epm.acmi.struts.action.MedRecAction.class);
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
    public void doExecute(ActionContext ctx)
        throws IOException, ServletException {

    	String action = ctx.request().getParameter("action");
    	String applicantID = ctx.request().getParameter("param");
    	String gfid = (String)ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
    	MedRecForm form = null;
    	MedRecDBHelper dbHelp;
    	
    	log.debug("Entering method doExecute()");
    	log.debug("In doexecute of MedRecAction. Action = " + action);
    	log.debug("Applicant ID = " + applicantID);
    	
    	
        // Display the Page with the MedRecList
    	ctx.session().setAttribute("showMedRecForm", "false");
    	
    	try {
    	
	    	if (action != null) {
	    		
	    		if (action.equals("Create")) {
	    			form = new MedRecForm();
	    			form.setApplicantType("");
	    			ctx.session().setAttribute("medRecForm", form);
	    			ctx.session().setAttribute("showMedRecForm", "true");
	    		}
	    		else if (action.equals("Edit")) {
	    			dbHelp = new MedRecDBHelper();
	    			form = dbHelp.getFormFromDB(gfid, applicantID);
	    			ctx.session().setAttribute("medRecForm", form);
	    			ctx.session().setAttribute("showMedRecForm", "true");	    			
	    		}
	    		else if (action.equals("Delete")) {
	    			dbHelp = new MedRecDBHelper();
	    			dbHelp.deleteFromDB(gfid, applicantID);
	    		}
	    		    		
	    	}
    	}
    	catch (Exception ex) {
    		ctx.addGlobalError("error.mainError", "");
    		log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage());
        	ex.printStackTrace();
    	}
    	
    	log.debug("Exiting method doExecute()");
    	
    	/*
    	 *   Added MyNewTabParam = 0  for USR 8399-1
    	 *   Modified on 02-12-2008 
    	 */
    	ctx.forwardToAction("main/iuauser/nbws?ctrl=nbwstabset&action=TabClick&param=tab112");
    }
    
    public void save_onClick(FormActionContext ctx) throws Exception {
    	
    	MedRecForm form;
    	MedRecDBHelper dbHelp;
    	String gfid = (String)ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
    	User user = (User) ctx.session().getAttribute(Constants.loggedUser);
    	String userID = null; 
    	
    	if (user != null) {
    		userID = user.getUserId();
    	}
    	
    	log.debug("Entering method save_onClick()");
    	
    	try {
	    	form = (MedRecForm) ctx.form();    	    
	    	
	    	log.debug(form.getFirstName());
	    	log.debug(form.getLastName());
	    	log.debug(form.getInitialMid());
	    	log.debug(form.getSuffix());
	    	log.debug(form.getApplicantType());
	    	log.debug(form.getRequestDate());
	    	log.debug(form.getPhysicianName());
	    	log.debug(form.getPhysicianLocated());
	    	log.debug(form.getPhiMissing());
	    	log.debug(form.getApplicantID());
	    	
	    	dbHelp = new MedRecDBHelper();
	    	dbHelp.saveToDB(gfid, form, userID);
    	}
    	catch (Exception ex) {
    		ctx.addGlobalError("error.mainError", "");
    		log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage());
        	ex.printStackTrace();
    	}
    	
    	log.debug("Exiting method save_onClick()");
    }
    
    public void cancel_onClick(FormActionContext ctx) throws Exception {
    	
    	log.debug("Entering method cancel_onClick()");
    	ctx.session().setAttribute("showMedRecForm", "false");
    	log.debug("Exiting method save_onClick()");
    	
    }
    
    
    
}