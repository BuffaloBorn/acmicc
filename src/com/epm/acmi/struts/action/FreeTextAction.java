/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.action;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.TreeMap;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.TextProcessing;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.ConcreteControlActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlRequestContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.FreeTextForm;
import com.epm.acmi.struts.form.PolicyExtendedCommentsMainForm;
import com.isdiary.entirex.WSFreeTextMaintCall;
import com.isdiary.entirex.WSMemoTextCall;
import com.softwarag.extirex.webservice.freetextmaint.client.MUEFMMWINOUT_PARMS;
import com.softwarag.extirex.webservice.freetextmaint.client.holders.MUEFMMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.freetextmaint.client.holders.MUEFMMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.freetextmaint.client.holders.MUEFMMWResponseOUT_PARMSHolder;
import com.softwarag.extirex.webservice.memotext.client.holders.MUMEMMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.memotext.client.holders.MUMEMMWResponseOUT_PARMHolder;

/** 
 * MyEclipse Struts
 * Creation date: 04-01-2008
 * 
 * XDoclet definition:
 * @struts.action path="/freeText" name="freeTextForm" input="/jsp/ias/freeText.jsp" scope="request" validate="true"
 */

public class FreeTextAction extends CCAction {

	private static Logger log = Logger.getLogger(FreeTextAction.class);
	private static String classAction = "Free Text Maintance Data";
	
	public void doExecute(ActionContext ctx) throws Exception 
	{
			
		log.debug("Begin execute doExecute");
		
		String showPopup = null; 
		showPopup = (String)ctx.request().getParameter("showPopup");
		
		String currentPageURL = ctx.request().getRequestURL()+"?"+ ctx.request().getQueryString() + "&showPopup=false" ;
		ctx.session().setAttribute("currentPageURL", currentPageURL);
		ctx.session().setAttribute("currentPageForm", "freeTextForm");
		
		FreeTextForm form =  (FreeTextForm)ctx.form();
		
		if(form.getAutoSave().trim().length() ==  0) 
		{	
			this.loadForm(ctx);	
			log.debug("End execute doExecute");
			return;
		}
		
		if (form.getAutoSave().equalsIgnoreCase("clear"))
		{
			form.setAutoSave("");
			form.save();
		}
		
		if(form.getAutoSave().equalsIgnoreCase("true")) 
		{
			form.save();
		}
		else 
		{
			ctx.forwardToInput();
		}
		
		if (showPopup != null)
		{
			if(showPopup.equalsIgnoreCase("true"))
			{
				ctx.forwardByName("showPopup");
			}
			
			if(showPopup.equalsIgnoreCase("false"))
			{
				ctx.forwardToInput();
			}
		}

		log.debug("End execute doExecute");
	}
	
	
	private void loadForm(ActionContext ctx) 
	{
		String action = (String)ctx.request().getParameter("action");
		
		String EventId = null; 
		EventId = (String)ctx.request().getParameter("eventid"); // come from Policy Diary Screen; saying this event has event id already
		
		String eventCode = (String)ctx.request().getParameter("eventcode"); // come from Standard Event Codes; saying create a new 
																			// event with this the Standard Event Code 
		
		if (action.equalsIgnoreCase("display"))
		{
			displayFreeTextMaint(ctx, EventId, "display");
			ctx.forwardToInput();
		}
		
		if (action.equalsIgnoreCase("edit"))
		{
			
			String personid = null;
			String person_name = null;
			String memoid = null;
			String back = null;
			
			personid  = (String)ctx.request().getParameter("personid"); //come from Browse Policy Certificate Person Help 
			person_name  = (String)ctx.request().getParameter("person_name");//come from Browse Policy Certificate Person Help 
			memoid = (String)ctx.request().getParameter("memoid"); //come from Memo Id Codes Help
			back = (String)ctx.request().getParameter("back"); //come from Memo Id Codes Help with back
			
			if ((personid != null) && (person_name!=null))
			{
				this.fillinPerson_id(ctx, personid, person_name);
				ctx.forwardToInput();
				return;
			}
			
			if (memoid != null)
			{
				fillinMemo_id(ctx, memoid);
				ctx.forwardToInput();
				return;
			}
			
			
			if (EventId != null) //event id is there; then edit with event id policy no 
			{
				editDisplayFreeTextMaint(ctx);
			}
			else //event id is missing; then create event 
			{
				if (back == null)
				{
					createFreeTextMaint(ctx, eventCode);
				}
				ctx.forwardToInput();
			}
		}
		
	}
	
	private void createFreeTextMaint(ActionContext ctx, String eventCode) {
		
		log.debug("Prepopulate Free Text Maint.....");
		
		FreeTextForm form = (FreeTextForm) ctx.form();
		String policyNo = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		String freeformind = (String)ctx.session().getAttribute("form_id");
		
		form.clear();
		
		form.setPolicyno(policyNo );
		form.setStd_event(eventCode);
		form.setStatus("O");
		form.setEvent_id("");
		 
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
	    form.setRequested(dateFormat.format(date).toString());
	    String keyInsuredId = (String)ctx.session().getAttribute(Constants.IASkeyInsuredId);
		form.setRecipient_id(keyInsuredId);
	    String keyinsured = (String)ctx.session().getAttribute(Constants.IASkeyInsured);
		form.setRecipient_name(keyinsured);
		form.setApplication_formid(freeformind);
		
	    log.debug("Finish .....prepopulating " + classAction +  ".....");
	}

	
	private void editFreeTextMaint(ActionContext ctx) 
	{
		String service = "Edit: " + classAction;
		
		log.debug("Editing Free Text Maint");
		
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUEFMMWINOUT_PARMS inputs = new MUEFMMWINOUT_PARMS();
		
		retrieveEditForm( inputs, PolicyNo ,  ctx);
		
		MUEFMMWResponseINOUT_PARMS1Holder inoutparms = new MUEFMMWResponseINOUT_PARMS1Holder();
		MUEFMMWResponseMSG_DATAHolder msgInfo = new MUEFMMWResponseMSG_DATAHolder();
		MUEFMMWResponseOUT_PARMSHolder outparms = new MUEFMMWResponseOUT_PARMSHolder();
		
		try {
			WSFreeTextMaintCall.edit( inputs, inoutparms, msgInfo, outparms);
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		}
		
		if( msgInfo.value.getRETURN_CODE().equalsIgnoreCase("E"))
		{
			log.debug("Mainframe Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			ctx.addGlobalError(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			ctx.forwardToInput();
			log.debug("Error occurred " + classAction);
		}
		else
		{
			log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
			ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
			ctx.forwardByName(Forwards.BACK);
			form.clear();	
			log.debug("Finish....Editing " + classAction);
		}
	}
	
	private void addFreeTextMaint(ActionContext ctx) 
	{
		String service = "Add: Free Text";
		
		log.debug("Adding Free Text Maint");
		FreeTextForm form = (FreeTextForm) ctx.form();
	
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUEFMMWINOUT_PARMS inputs = new MUEFMMWINOUT_PARMS();
		
		retrieveForm( inputs, PolicyNo ,  ctx);
		
		MUEFMMWResponseINOUT_PARMS1Holder inoutparms = new MUEFMMWResponseINOUT_PARMS1Holder();
		MUEFMMWResponseMSG_DATAHolder msgInfo = new MUEFMMWResponseMSG_DATAHolder();
		MUEFMMWResponseOUT_PARMSHolder outparms = new MUEFMMWResponseOUT_PARMSHolder();
		
		try {
			WSFreeTextMaintCall.add(inputs, inoutparms, msgInfo, outparms);
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		}
		
		if( msgInfo.value.getRETURN_CODE().equalsIgnoreCase("E"))
		{
			log.debug("Mainframe Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			ctx.addGlobalError(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			ctx.forwardToInput();
			log.debug("Error occurred " + classAction);
		}
		else
		{
			log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMSG_TEXT()));
			String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
			ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
			ctx.forwardByName(Forwards.BACK);
			form.clear();	
			log.debug("Finish....Adding " + classAction);
		}
	}
	
	private void retrieveForm(MUEFMMWINOUT_PARMS inputs, String policyNo, ActionContext ctx) 
	{
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
	 
		inputs.setATTACHMENT_IND(form.getAttachment());
		if(form.getRespn_date().length() == 0)
			inputs.setDATE_COMPLETED_YYYYMMDD( new BigDecimal("0"));
		else
			inputs.setDATE_COMPLETED_YYYYMMDD( new BigDecimal(TextProcessing.YYYYMMDDFormat(form.getRespn_date())));
		inputs.setEVENT_ID(new BigDecimal("0"));
		inputs.setEVENT_STATUS_CURRENT(form.getStatus());
		inputs.setFREE_DESCRIPTION(form.getDescription());
		inputs.setMEMO_ID(form.getMemoid());
		inputs.setPOLICY_ID(new BigDecimal(policyNo));
		inputs.setRECIPIENT_PERSON_ID(new BigDecimal(form.getRecipient_id()));
		inputs.setRECIPIENT_SEARCH_NAME(form.getRecipient_name());
		inputs.setSECOND_REQUEST_IND(form.getSecond_request());
		inputs.setSTD_EVENT_ID(form.getStd_event());
		
		String[] Text = new String[60];
		TextProcessing.storeTextData(form.getFreeTextArea(), 0, 59, Text , 76, 0, 4500);
		inputs.setTEXT(Text);
		form.setHiddenfreeTextArea("");
		
		inputs.setUSER_ID(user);
		inputs.setMEMO_IND(form.getMemoind());
		inputs.setFREE_FORM_IND(form.getFreeformind());
		
	}
	
	private void retrieveEditForm(MUEFMMWINOUT_PARMS inputs, String policyNo, ActionContext ctx) 
	{
		
		//Reason: Free Text only can edit 5 feilds and the rest separate by the space below
		
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
	 
		inputs.setUSER_ID(user);
		inputs.setEVENT_ID(new BigDecimal(form.getEvent_id()));
		inputs.setPOLICY_ID(new BigDecimal(form.getPolicyno()));
		inputs.setEVENT_STATUS_CURRENT(form.getStatus());
		inputs.setSECOND_REQUEST_IND(form.getSecond_request());
		
		inputs.setATTACHMENT_IND(form.getAttachment());
		if (form.getRespn_date().length() == 0)
			inputs.setDATE_COMPLETED_YYYYMMDD( new BigDecimal("0"));
		else
			inputs.setDATE_COMPLETED_YYYYMMDD( new BigDecimal(TextProcessing.YYYYMMDDFormat(form.getRespn_date())));
		inputs.setFREE_DESCRIPTION(form.getDescription());
		inputs.setMEMO_ID(form.getMemoid());
		inputs.setRECIPIENT_PERSON_ID(new BigDecimal(form.getRecipient_id()));
		inputs.setRECIPIENT_SEARCH_NAME(form.getRecipient_name());
		inputs.setSECOND_REQUEST_IND(form.getSecond_request());
		inputs.setSTD_EVENT_ID(form.getStd_event());
		
		String[] Text = new String[60];
		TextProcessing.storeTextData(form.getFreeTextArea(), 0, 59, Text , 76, 0, 4500);
		inputs.setTEXT(Text);
		form.setHiddenfreeTextArea("");
		inputs.setUSER_ID(user);
		inputs.setMEMO_IND(form.getMemoind());
		inputs.setFREE_FORM_IND(form.getFreeformind());
				
	}

	private void displayFreeTextMaint(ActionContext ctx, String EventId, String page) 
	{
		
		String service = "Display: Free Text";
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		if (EventId == null) 
		{
			log.debug("Event Id is Null");
			ctx.addGlobalError(DiaryMessages.EVENT_ID_NULL,PolicyNo,service);
			ctx.forwardByName(Forwards.BACK);
		}
		
		MUEFMMWResponseINOUT_PARMS1Holder inoutparms = new MUEFMMWResponseINOUT_PARMS1Holder();
		MUEFMMWResponseMSG_DATAHolder msgInfo = new MUEFMMWResponseMSG_DATAHolder();
		MUEFMMWResponseOUT_PARMSHolder outparms = new MUEFMMWResponseOUT_PARMSHolder();
		
		try {
			WSFreeTextMaintCall.fetch(PolicyNo,EventId, inoutparms, msgInfo, outparms);
			fillForm(inoutparms,outparms, ctx);
			
			if(page.equalsIgnoreCase("display"))
			{	
				ctx.session().setAttribute(Constants.IASModify, "display");
				
			}
			else
			{
				if (form.getStatus().equalsIgnoreCase("O"))
				{
					ctx.session().setAttribute(Constants.IASModify, "editWithStatus");
				}
				else
				{
					ctx.session().setAttribute(Constants.IASModify, "edit");
				}
					
				
			}

		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.FILL_IN_FORM_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		}

		log.debug("Status: " + msgInfo.value.getMSG_TEXT());
	}

	private void fillForm(MUEFMMWResponseINOUT_PARMS1Holder inoutparms, MUEFMMWResponseOUT_PARMSHolder outparms, ActionContext ctx) throws Exception
 	{
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		form.setAttachment(inoutparms.value.getATTACHMENT_IND1());
		form.setDescription(inoutparms.value.getFREE_DESCRIPTION1());
		form.setEvent_id(inoutparms.value.getEVENT_ID1().toString());
		
		form.setFreeTextArea(TextProcessing.formatText(inoutparms.value.getTEXT1(), 75));
		form.setFreeformind(inoutparms.value.getFREE_FORM_IND1());
		form.setApplication_formid(outparms.value.getAPPLICATION_FORM_ID());
		
		form.setMemoid(inoutparms.value.getMEMO_ID1());
		form.setMemoind(inoutparms.value.getMEMO_IND1());
		form.setRecipient_id(inoutparms.value.getRECIPIENT_PERSON_ID1().toString());
		
		form.setRecipient_name(TextProcessing.recipientFormat(inoutparms.value.getRECIPIENT_SEARCH_NAME1()));
		form.setPolicyno( inoutparms.value.getPOLICY_ID1().toString());
		
		form.setSecond_request(inoutparms.value.getSECOND_REQUEST_IND1());
		
		ctx.request().setAttribute("personid", inoutparms.value.getSECOND_REQUEST_IND1());
		form.setRequested(TextProcessing.dateFormat(outparms.value.getDATE_CREATED()));
		
		//TextProcessing textProcess = new TextProcessing();
		form.setRespn_date(TextProcessing.dateFormat(inoutparms.value.getDATE_COMPLETED_YYYYMMDD1().toString()));
		
		//form.setRespn_yyyy(respn.getRespn_yyyy());
		//form.setRespn_mm(respn.getRespn_mm());
		//form.setRespn_dd(respn.getRespn_dd());
		

		//form.setRespn_date(RespndateHolder.append(respn.getRespn_mm()).append("/").append(respn.getRespn_dd()).append("/").append(respn.getRespn_yyyy()).toString());
		
		form.setStatus(inoutparms.value.getEVENT_STATUS_CURRENT1());
		ctx.request().setAttribute("status", inoutparms.value.getEVENT_STATUS_CURRENT1());
		
		form.setStd_event(inoutparms.value.getSTD_EVENT_ID1());
		form.setSecond_request(inoutparms.value.getSECOND_REQUEST_IND1());
	}

	private void editDisplayFreeTextMaint(ActionContext ctx) {
		

		String eventid = null;
		eventid = (String)ctx.request().getParameter("eventid");
		
		displayFreeTextMaint(ctx, eventid, "edit");
				
		ctx.forwardToInput();
	}

	
	private void fillinMemo_id(ActionContext ctx, String memoid) {
		FreeTextForm form = (FreeTextForm) ctx.form();
		StringBuffer OldMemoText = new StringBuffer();
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		String service = "Memo Text";
		
		MUMEMMWResponseOUT_PARMHolder outparms = new MUMEMMWResponseOUT_PARMHolder();
		MUMEMMWResponseMSG_DATAHolder msgInfo = new MUMEMMWResponseMSG_DATAHolder();
		
		try {
			WSMemoTextCall.fetch(memoid, msgInfo, outparms);
			OldMemoText.append(form.getFreeTextArea());
			OldMemoText.append(TextProcessing.backspace(" ", 74));
			OldMemoText.append(TextProcessing.formatText(outparms.value.getTEXT(), 75));
			form.setFreeTextArea(OldMemoText.toString());
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service  +  " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		}
		
	}

	public void fillinPerson_id(ActionContext ctx, String personid, String person_name)
	{
		FreeTextForm form = (FreeTextForm) ctx.form();
		form.setRecipient_id(personid);
		form.setRecipient_name(person_name);
	}
	
	/**
	 * This method is called if the save button is clicked
	 * @param ctx The FormActionContext
	 */
	public void save_onClick(FormActionContext ctx) throws Exception {
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		form.validateForm(ctx);
	
		if (ctx.hasErrors())
		{
		   ctx.forwardToInput();
		   return;
		}
		
		addFreeTextMaint(ctx);
	}
	
	
	/**
	 * This method is called if the save button is clicked
	 * @param ctx The FormActionContext
	 */
	public void edit_onClick(FormActionContext ctx) throws Exception {
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		form.validateForm(ctx);
	
		if (ctx.hasErrors())
		{
			ctx.forwardToInput();
			 return;
		}

		editFreeTextMaint(ctx);
	}
	
	
	
	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		FreeTextForm form = (FreeTextForm) ctx.form();
		// in this example we go back to the jsp
		form.clear();	

		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		
		ctx.forwardByName(Forwards.BACK);
	}
	
	
	public void storeCurrentFieldValues(FreeTextForm form)
	{
		form.setApplication_formid(form.getApplication_formid());
		form.setAttachment(form.getAttachment());
		form.setDescription(form.getDescription());
		form.setEvent_id(form.getEvent_id());
		form.setFreeformind(form.getFreeformind());
		form.setFreeTextArea(form.getFreeTextArea());
		form.setMemoid(form.getMemoid());
		form.setMemoind(form.getMemoind());
		form.setPolicyno(form.getPolicyno());
		form.setRecipient_id(form.getRecipient_id());
		form.setRecipient_name(form.getRecipient_name());
		form.setRequested(form.getRequested());
		form.setRespn_date(form.getRespn_date());
		form.setSecond_request(form.getSecond_request());
		form.setStatus(form.getStatus());
		form.setStd_event(form.getStd_event());
	}
	
	
	
	
	public void recipientHelp_onClick(FormActionContext ctx) throws Exception {
		
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		form.save();
		
		ctx.forwardByName("recipientHelp", "FT", form.getEvent_id());
	}
	
	public void memoIdHelp_onClick(FormActionContext ctx) throws Exception {
		FreeTextForm form = (FreeTextForm) ctx.form();
		
		form.save();
		
		ctx.forwardByName("memoIdHelp", "FT", form.getEvent_id());
		
	}
	
	public void handleControlAction(ControlRequestContext ctx, Object aobj[])
	{
		 
		TreeMap handleControlActionTMap = new TreeMap();
				
		ConcreteControlActionContext ccac = null;
		
		ccac = (ConcreteControlActionContext) aobj[0];

		FreeTextForm form = (FreeTextForm) ccac.form(); 
		
		handleControlActionTMap.put("action", ccac.action().getAction());
		
		
		for (int i = 1; i < aobj.length; i++) 
		{
			handleControlActionTMap.put("param" + i, aobj[i]);
		}

			
		if (handleControlActionTMap.get("action").toString().equalsIgnoreCase("help"))
		{
			
			if (handleControlActionTMap.get("param1").toString().equalsIgnoreCase("recipientid"))
			{
				storeCurrentFieldValues(form);
				ccac.forwardByName("recipientHelp", "FT");
			}
			
			if (handleControlActionTMap.get("param1").toString().equalsIgnoreCase("memoid"))
			{
				storeCurrentFieldValues(form);
				ccac.forwardByName("memoHelp", "FT");

			}
			
		}

	}
	
}