package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.StandardEventCodesForm;


public class StandardEventCodesAction  extends CCAction {

	private static Logger log = Logger.getLogger(StandardEventCodesAction.class);
	
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
		
		boolean flag = true;
		
		StandardEventCodesForm form = (StandardEventCodesForm) ctx.form();
		
		form.setStdEventCode(evtCode);
		
		if (evtCode.equalsIgnoreCase("FREE TX")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/freeTextCreate?eventcode=" + evtCode + "&action=edit&modify=create" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("QUOTE")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/freeTextCreate?eventcode=" + evtCode + "&action=edit&modify=create" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("PHONE")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/freeTextCreate?eventcode=" + evtCode + "&action=edit&modify=create" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("PROP")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/freeTextCreate?eventcode=" + evtCode + "&action=edit&modify=create" );
			flag = false;
		}
		
		
		
		if (evtCode.equalsIgnoreCase("LETTER")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/letterCreate?eventcode=" + evtCode +"&action=edit" );
			flag = false;
		}
		
		
		if (evtCode.equalsIgnoreCase("HIPAA")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/eventStdMemoCreate?eventcode=" + evtCode+"&action=edit" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("HIPAA-MI")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/eventStdMemoCreate?eventcode=" + evtCode+"&action=edit" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("HIPAA-OH")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/eventStdMemoCreate?eventcode=" + evtCode+"&action=edit" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("HIV-H")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/eventStdMemoCreate?eventcode=" + evtCode+"&action=edit" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("RE-OPEN")) 
		{
			log.debug("std event:" + evtCode);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/eventStdMemoCreate?eventcode=" + evtCode+"&action=edit" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("SA")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardToAction("iuauser/eventStdMemoCreate?eventcode=" + evtCode+"&action=edit" );
			flag = false;
		}
		
		if (evtCode.equalsIgnoreCase("STDLET")) 
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardByName("stdletterCreate", evtCode );
			flag = false;
		}
		
		if(evtCode.equalsIgnoreCase("BLD"))
		{
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardByName("eventportamedicCreate", evtCode);
			flag = false;
		}
		
		if(evtCode.equalsIgnoreCase("ECG"))
		{ 
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardByName("eventportamedicCreate", evtCode);
			flag = false;
		}
		
		if(evtCode.equalsIgnoreCase("EXM"))
		{ 
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardByName("eventportamedicCreate", evtCode);
			flag = false;
		}
		
		if(evtCode.equalsIgnoreCase("EXM-BLD"))
		{ 
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardByName("eventportamedicCreate", evtCode);
			flag = false;
		}
		
		if(evtCode.equalsIgnoreCase("URNSPEC"))
		{ 
			log.debug("std event:" + evtCode);
			String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
			ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
			ctx.session().setAttribute(Constants.IASModify, "create");
			ctx.forwardByName("eventportamedicCreate", evtCode);
			flag = false;
		}
	
		
		if(evtCode.equalsIgnoreCase("OFFER-IN"))
		{ 
			log.debug("std event:" + evtCode);
			String riderind = (String)ctx.session().getAttribute("riderind");
			
			if (riderind.equalsIgnoreCase("N"))
			{
				ctx.addGlobalMessage(DiaryMessages.OFFER_IN_NO_RIDER, "OFFER-IN");
				ctx.session().removeAttribute("riderind");
				ctx.forwardByName("diary");
			}
			else
			{
				String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
				ctx.session().setAttribute(Constants.IASDiaryModify,IASModify);
				ctx.session().setAttribute(Constants.IASModify, "create");
				ctx.forwardToAction("iuauser/freeTextCreate?eventcode=" + evtCode + "&action=edit" );	
			}
			flag = false;
		}
	
		
		if (flag)
		{
			log.debug("Do not have a case for:" + evtCode);
			ctx.addGlobalError(DiaryMessages.STD_EVENTS_CODES_INVALID,evtCode);
			ctx.forwardByName("stdEventCodeError");
		}
		
	}

	public void createEvent_onClick(FormActionContext ctx) throws Exception
	{
		StandardEventCodesForm form = (StandardEventCodesForm) ctx.form();
	
		forwardToEvent(ctx,form.getStdEventCode());
	}
	
	
	public void backToDiary_onClick(FormActionContext ctx) throws Exception
	{ 	
		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		ctx.forwardByName("diary");
	}
	
}
