package com.epm.acmi.struts.action;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.common.TextProcessing.reponseDate;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.StandardLetterForm;
import com.isdiary.entirex.WSMemoTextCall;
import com.isdiary.entirex.WSStdLetterCall;
import com.softwarag.extirex.webservice.memotext.client.holders.MUMEMMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.memotext.client.holders.MUMEMMWResponseOUT_PARMHolder;
import com.softwarag.extirex.webservice.stdletter.client.MUELTMWINOUT_PARMS;
import com.softwarag.extirex.webservice.stdletter.client.holders.MUELTMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.stdletter.client.holders.MUELTMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.stdletter.client.holders.MUELTMWResponseOUT_PARMSHolder;

public class StandardLetterAction extends CCAction {

	private static Logger log = Logger.getLogger(StandardLetterAction.class);
	private static String classAction = "Standard Letter Maintance Data";

	public void doExecute(ActionContext ctx) throws Exception 
	{
		log.debug("Begin execute doExecute");
		String action = (String)ctx.request().getParameter("action");
		
		String EventId = null;
		EventId = (String)ctx.request().getParameter("eventid"); // come from Policy Diary Screen; saying this event has event id already	
		
		String eventCode = (String)ctx.request().getParameter("eventcode"); // come from Standard Event Codes; saying create a new 
																			// event with this the Standard Event Code
			
		if (action.equalsIgnoreCase("display"))
		{
			displayStdLetterMaint(ctx, EventId, "display");
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
			
			
			if (EventId != null) //event id is there; then edit with event 
			{
				editDisplayStdLetterRequest(ctx);
				ctx.forwardToInput();
			}
			else //create
			{
				if (back == null)
				{
					createStdLetterMaint(ctx,eventCode);
				}
				ctx.forwardToInput();
			}
		}
	
		log.debug("End execute doExecute");
	}

	private void createStdLetterMaint(ActionContext ctx, String eventCode) {
		log.debug("Prepopulate Standard Letter Maint.....");
		
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		String policyNo = (String)ctx.session().getAttribute(Constants.IASpolicyNumber); 
		
		form.clear();
		
		form.setPolicyno(policyNo );
		form.setStd_event(eventCode);
		form.setStatus("O");
		form.setEvent_id("");
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
	    form.setRequested(dateFormat.format(date).toString());
	    String keyInsuredId = (String)ctx.session().getAttribute(Constants.IASkeyInsuredId);;
		form.setRecipient_id(keyInsuredId);
	    String keyinsured = (String)ctx.session().getAttribute(Constants.IASkeyInsured);;
		form.setRecipient_name(keyinsured);
		
		
		log.debug("Finish .....prepopulating " + classAction +  ".....");
	}
	
	private void editDisplayStdLetterRequest(ActionContext ctx) 
	{
		String eventid = null;
		eventid = (String)ctx.request().getParameter("eventid");
		
		displayStdLetterMaint(ctx, eventid, "edit");
	}
	
	private void fillinMemo_id(ActionContext ctx, String memoid) {
	
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		form.setMemoid(memoid);
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		String service = "Memo Text";
		
		MUMEMMWResponseOUT_PARMHolder outparms = new MUMEMMWResponseOUT_PARMHolder();
		MUMEMMWResponseMSG_DATAHolder msgInfo = new MUMEMMWResponseMSG_DATAHolder();
		
		try {
			WSMemoTextCall.fetch(memoid, msgInfo, outparms);
			form.setLetterTextArea(TextProcessing.formatText(outparms.value.getTEXT(), 75));
			
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
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		form.setRecipient_id(personid);
		form.setRecipient_name(person_name);
	}
	
	/**
	 * This method is called if the save button is clicked
	 * @param ctx The FormActionContext
	 */
	public void save_onClick(FormActionContext ctx) throws Exception 
	{
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		form.validateForm(ctx);
		
		if (ctx.hasErrors())
		{
			ctx.forwardToInput();
			 return;
		}
		
		addStdLetterMaint(ctx);
	}
	
	private void addStdLetterMaint(FormActionContext ctx) 
	{
		String service = "Add: Standard Letter";
		log.debug("Adding Standard Letter Maint");
		
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUELTMWINOUT_PARMS inputs = new MUELTMWINOUT_PARMS();
		
		retrieveForm( inputs, PolicyNo ,  ctx);
		
		MUELTMWResponseMSG_DATAHolder msgInfo = new MUELTMWResponseMSG_DATAHolder();
		MUELTMWResponseINOUT_PARMS1Holder inoutparms = new MUELTMWResponseINOUT_PARMS1Holder();
		MUELTMWResponseOUT_PARMSHolder outparms = new MUELTMWResponseOUT_PARMSHolder();
		
		try {
			WSStdLetterCall.add(inputs, inoutparms, msgInfo, outparms);
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
			log.debug("Finish....adding " + classAction);
		}
		
	}

	private void retrieveForm(MUELTMWINOUT_PARMS inputs, String policyNo, FormActionContext ctx) 
	{
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
		
		
		
		inputs.setUSER_ID(user);
		
		inputs.setATTACHMENT_IND(form.getAttachment());
		inputs.setDATE_COMPLETED_YYYYMMDD(new BigDecimal(TextProcessing.YYYYMMDDFormat(form.getRespn_date())));
		
		if (form.getEvent_id().trim().length() == 0)
			inputs.setEVENT_ID(new BigDecimal("0"));
		else
			inputs.setEVENT_ID(new BigDecimal(form.getEvent_id()));
		
		inputs.setEVENT_STATUS_CURRENT(form.getStatus());
		inputs.setFREE_DESCRIPTION(form.getDescription());
		inputs.setMEMO_ID(form.getMemoid());
		inputs.setPOLICY_ID(new BigDecimal(form.getPolicyno()));
		inputs.setRECIPIENT_PERSON_ID(new BigDecimal(form.getRecipient_id()));
		inputs.setRECIPIENT_SEARCH_NAME(form.getRecipient_name());
		inputs.setSECOND_REQUEST_IND(form.getSecond_request());
		inputs.setSTD_EVENT_ID(form.getStd_event());
		
		String[] Text = new String[60];
		TextProcessing.storeTextData(form.getLetterTextArea(), 0, 59, Text , 75, 0, 4500);
		inputs.setTEXT(Text);

	}

	/**
	 * This method is called if the save button is clicked
	 * @param ctx The FormActionContext
	 */
	public void edit_onClick(FormActionContext ctx) throws Exception 
	{
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		form.validateForm(ctx);
		
		if (ctx.hasErrors())
		{
			if (form.getStatus().equalsIgnoreCase("O"))
			{	
				ctx.session().setAttribute("modify", "editWithStatus");
				ctx.request().setAttribute("modify", "editWithStatus");
			}
			else
			{
				ctx.session().setAttribute("modify", "edit");
				ctx.request().setAttribute("modify", "edit");
			}
			ctx.forwardToInput();
			 return;
		}
	
		editStdLetterMaint(ctx);
		
	}
	
	private void editStdLetterMaint(FormActionContext ctx) 
	{
		String service = "Edit: " + classAction;
		
		log.debug("Editing Standard Letter Maint");
		
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUELTMWINOUT_PARMS inputs = new MUELTMWINOUT_PARMS();
		
		retrieveForm( inputs, PolicyNo ,  ctx);
		
		MUELTMWResponseMSG_DATAHolder msgInfo = new MUELTMWResponseMSG_DATAHolder();
		MUELTMWResponseINOUT_PARMS1Holder inoutparms = new MUELTMWResponseINOUT_PARMS1Holder();
		MUELTMWResponseOUT_PARMSHolder outparms = new MUELTMWResponseOUT_PARMSHolder();
		
		try {
			WSStdLetterCall.edit(inputs, inoutparms, msgInfo, outparms);
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
			log.debug("Mainframe Message: " + msgInfo.value.getMSG_TEXT());
			ctx.addGlobalError(DiaryMessages.NATUAL_BUS_MSG, msgInfo.value.getMSG_TEXT());
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

	private void displayStdLetterMaint(ActionContext ctx, String Eventid, String page) 
	{
		
		String service = "Display: Std Letter Maint " + classAction;
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		
		String EventId = (String)ctx.request().getParameter("eventid");
		
		if (EventId == null) 
		{
			log.debug("Event Id is Null");
			return;
		}
		
		MUELTMWResponseMSG_DATAHolder msgInfo = new MUELTMWResponseMSG_DATAHolder();
		MUELTMWResponseINOUT_PARMS1Holder inoutparms = new MUELTMWResponseINOUT_PARMS1Holder();
		MUELTMWResponseOUT_PARMSHolder outparms = new MUELTMWResponseOUT_PARMSHolder();
		
		try {
			WSStdLetterCall.fetch(PolicyNo,EventId, inoutparms, msgInfo, outparms);
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
		
	}

	private void fillForm(MUELTMWResponseINOUT_PARMS1Holder inoutparms, MUELTMWResponseOUT_PARMSHolder outparms, ActionContext ctx) throws Exception 
	{
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		
		form.setPolicyno(inoutparms.value.getPOLICY_ID1().toString());
		form.setDescription(inoutparms.value.getFREE_DESCRIPTION1());
		form.setEvent_id(inoutparms.value.getEVENT_ID1().toString());
		form.setLetterTextArea(TextProcessing.formatText(inoutparms.value.getTEXT1(), 75));
		form.setMemoid(inoutparms.value.getMEMO_ID1());
		form.setRecipient_id(inoutparms.value.getRECIPIENT_PERSON_ID1().toString());
		form.setRecipient_name(inoutparms.value.getRECIPIENT_SEARCH_NAME1().toString());
		form.setRequested(TextProcessing.dateFormat(outparms.value.getDATE_CREATED().toString()));
		form.setAttachment(inoutparms.value.getATTACHMENT_IND1());
		
		TextProcessing textProcess = new TextProcessing();
		reponseDate respn = textProcess.new reponseDate(inoutparms.value.getDATE_COMPLETED_YYYYMMDD1().toString());
		
		form.setRespn_yyyy(respn.getRespn_yyyy());
		form.setRespn_dd(respn.getRespn_mm());
		form.setRespn_mm(respn.getRespn_mm());
		
		form.setSecond_request(inoutparms.value.getSECOND_REQUEST_IND1());
		form.setStatus(inoutparms.value.getEVENT_STATUS_CURRENT1());
		form.setStd_event(inoutparms.value.getSTD_EVENT_ID1());
		
	}



	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		form.clear();
		
		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		
		ctx.forwardByName(Forwards.BACK);
	}
	
	public void recipientHelp_onClick(FormActionContext ctx) throws Exception {
	
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		form.save();
	
		ctx.forwardByName("recipientHelp", "SL", form.getEvent_id());
		
	}
	
	public void memoIdHelp_onClick(FormActionContext ctx) throws Exception {
		StandardLetterForm form = (StandardLetterForm) ctx.form();
		form.save();
		
		ctx.forwardByName("memoIdHelp", "SL", form.getEvent_id());
		
	}
	
}
