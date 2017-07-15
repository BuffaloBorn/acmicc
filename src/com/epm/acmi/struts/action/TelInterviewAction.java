package com.epm.acmi.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FWAction;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;
import com.epm.acmi.bd.IUAProcessBD;
import com.epm.acmi.struts.form.TelInterviewForm;
import com.epm.acmi.struts.form.dsp.TInterviewDisp;
import com.epm.acmi.struts.form.dsp.TelInterviewList;
import com.epm.acmi.util.IUAUtils;
import com.epm.acmi.util.MiscellaneousUtils;
import com.epm.acmi.util.OnTabUpdates;

public class TelInterviewAction extends FWAction {

	private static final String NULL_STRING = "";
	private static final String SPACE = " ";
	private static Logger log;
		
	static {
		log = Logger.getLogger(com.epm.acmi.struts.action.MedRecAction.class);
	}
		
	public void doExecute(ActionContext ctx)
	       throws IOException, ServletException {

	    	String action = ctx.request().getParameter("action");
	    	TelInterviewForm form = (TelInterviewForm)ctx.form();
	    		    	
	    	try {
	    	
		    	if (action != null) {
		    		
		    		if (action.equals("Create")) {
		    			form.setUwList(IUAUtils.getUsersByRole("UW"));
		    			form.setFullName(NULL_STRING);
		    			form.setFirstName(NULL_STRING);
		    			form.setLastName(NULL_STRING);
		    			form.setMi(NULL_STRING);
		    			form.setSuffix(NULL_STRING);
		    			form.setShowNewInterviewRequest(true);
		    			form.setCreateNewInterviewRequest(true);
		    			ctx.session().setAttribute("refreshList", "false");
		    		}
		    		else if (action.equals("Edit")) {
		    			String currentRequestId = ctx.request().getParameter("param");
		    			SimpleListControl control = (SimpleListControl)ctx.session().getAttribute("interviewRecs");
		    			IUAProcessBD processBD = new IUAProcessBD();
		    			TInterviewDisp intDisp = processBD.getInterviewDisp((TelInterviewList)control.getDataModel(), currentRequestId);
		    			form.setInterviewRequestId(String.valueOf(intDisp.getInterviewRequestId()));
		    			form.setFullName(intDisp.getFullName());
		    			form.setFirstName(intDisp.getFirstName());
		    			form.setLastName(intDisp.getLastName());
		    			form.setMi(intDisp.getMi());
		    			form.setSuffix(intDisp.getSuffix());
		    			form.setApplicantType(IUAUtils.getAppStatusCodeFromString(intDisp.getAppType()));
		    			form.setRequestDate(intDisp.getReqDate());
		    			form.setUwIDName(intDisp.getRequestByID() + "|" + intDisp.getRequestedBy());
		    			form.setShowNewInterviewRequest(true);
		    			form.setCreateNewInterviewRequest(false);
		    			form.setUwList(IUAUtils.getUsersByRole("UW"));
		    			ctx.session().setAttribute("refreshList", "false");
		    		}
		    		else if (action.equals("Delete")) {
		    			IUAProcessBD processBD = new IUAProcessBD();
		    			String currentRequestId = ctx.request().getParameter("param");
		    			processBD.deleteRequest(currentRequestId);
		    			ctx.session().setAttribute("refreshList", "true");
		    			OnTabUpdates.TelephoneInterviewStatus(ctx);
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

	    	ctx.forwardToAction("main/iuauser/nbws?ctrl=nbwstabset&action=TabClick&param=tab116");
	    }
	    
		public void save_onClick(FormActionContext ctx) throws Exception {
	    	
	    	TelInterviewForm form;
	  
	    	String gfid = (String)ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
	   
	    	log.debug("Entering method save_onClick()");
	    	
	    	try {
		    	form = (TelInterviewForm) ctx.form();
		    	String intRequestId = form.getInterviewRequestId();
		    	String appName = getFullName(form);
		    	String firstName = form.getFirstName();
		    	String lastName = form.getLastName();
		    	String mi = form.getMi();
		    	String suffix = form.getSuffix();
		    	
		    	String uwIDName  = form.getUwIDName();
		    	String reqDate = form.getRequestDate();
		    	String appType = form.getApplicantType();
		    	
		    	IUAProcessBD processBD = new IUAProcessBD();
		    	if (intRequestId != null && !intRequestId.equals("")) { //Interview Request id exists... it's an edit
		    		processBD.edit(Long.parseLong(intRequestId), appName, firstName, lastName, mi, suffix, uwIDName, reqDate, appType);
	    			ctx.session().setAttribute("refreshList", "true");
		    	}
		    	else { //Check if the applicant name are the same.
		    		SimpleListControl list = (SimpleListControl)ctx.session().getAttribute("interviewRecs");
		    		ListDataModel model = (ListDataModel)list.getDataModel();
		    		boolean hasRequest = processBD.checkForPendingRequests(appName, model);
		    		if (hasRequest) {
		    			ctx.addGlobalError("tInterview.error.applicant.exists", appName);
		    			form.setShowNewInterviewRequest(true);
		    			return;
		    		}
		    		else {
		    			processBD.save(gfid, appName, firstName, lastName, mi, suffix, uwIDName, reqDate, appType);
		    			ctx.session().setAttribute("refreshList", "true");
		    			OnTabUpdates.TelephoneInterviewStatus(ctx);
		    		}
		    	}
//		    	form.setShowNewInterviewRequest(false);
	    	}
	    	catch (Exception ex) {
	    		ctx.addGlobalError("error.mainError", "");
	    		log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage());
	        	ex.printStackTrace();
	    	}
	    	
	    	log.debug("Exiting method save_onClick()");
	    }
	    
	    private String getFullName(TelInterviewForm form) {
			StringBuffer buff = new StringBuffer();
			buff.append(form.getFirstName());
			buff.append(SPACE);
			buff.append(form.getMi());
			buff.append(SPACE);
			buff.append(form.getLastName());
			buff.append(SPACE);
			buff.append(form.getSuffix());
			return buff.toString();
		}

		public void cancel_onClick(FormActionContext ctx) throws Exception {
	    	
	    	log.debug("Entering method cancel_onClick()");
	    	TelInterviewForm form = (TelInterviewForm) ctx.form();
			ctx.session().setAttribute("refreshList", "false");
	    	log.debug("Exiting method cancel_onClick()");
	    	
	    }
}
