package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.bean.StandardEventIdBean;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.StandardEventCodesForm;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.MiscellaneousUtils;


public class StandardEventCodesAction  extends CCAction {

	private static Logger log = MiscellaneousUtils.getIASLogger();
	
	public void doExecute(ActionContext ctx) throws Exception 
	{
		log.debug("Begin execute doExecute");
		displayStandardEventCodes(ctx); 
		log.debug("End execute doExecute");
		
	}

	private void displayStandardEventCodes(ActionContext ctx) {
		
		StandardEventCodesForm form = (StandardEventCodesForm) ctx.form();
		form.setStdEventCode("");
		ctx.forwardToInput();
		
	}

	private void forwardToEvent(ActionContext ctx,String evtCode) 
	{
		
		String scrName = "";
		
		StandardEventCodesForm form = (StandardEventCodesForm) ctx.form();
		
		form.setStdEventCode(evtCode);
		
		StandardEventIdBean stdEvent = (StandardEventIdBean) ACMICache.getStdEventCodes().get(evtCode);
		
		scrName = stdEvent.getSCRNAME(); 
		
		log.debug("Std Event:" + evtCode);
		
		
		if(ctx.mapping().findForward(scrName) == null)
		{
			log.debug("Do not have a case for:" + evtCode);
			ctx.addGlobalError(DiaryMessages.STD_EVENTS_CODES_INVALID,evtCode);
			ctx.forwardByName("stdEventCodeError");
		}
		else
		{
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			log.debug(Constants.IASDiaryModify.toString() + ":" + IASModify); 
			ctx.session().setAttribute(Constants.IASModify, "create");
			log.debug(Constants.IASModify.toString() + ": create" );
			ctx.forwardByName(scrName , evtCode);		
			
		}

	}

	public void createEvent_onClick(FormActionContext ctx) throws Exception
	{
		StandardEventCodesForm form = (StandardEventCodesForm) ctx.form();
		log.debug("Create Event");
		forwardToEvent(ctx,form.getStdEventCode());
	}
	
	
	public void backToDiary_onClick(FormActionContext ctx) throws Exception
	{ 	
		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		log.debug(Constants.IASModify.toString() + IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		log.debug("Redirect back previous page");
		ctx.forwardByName("diary");
	}
	
}
