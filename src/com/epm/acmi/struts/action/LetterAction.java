package com.epm.acmi.struts.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.TextProcessing;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.Constants;
import com.softwarag.extirex.webservice.decappmemomaint.client.holders.MUDECMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.decappmemomaint.client.holders.MUDECMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.decappmemomaint.client.holders.MUDECMWResponseOUT_PARMSHolder;
import com.softwarag.extirex.webservice.decappmemomaint.client.MUDECMWINOUT_PARMS;
import com.epm.acmi.struts.form.LetterForm;
import com.isdiary.entirex.WSDecAppMemoMaint;

public class LetterAction extends CCAction {

	private static Logger log = Logger.getLogger(LetterAction.class);
	private static String classAction = "Letter Data";

	public LetterAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void doExecute(ActionContext ctx) throws IOException, ServletException  
	{
		log.debug("Begin execute doExecute");
		String action = (String)ctx.request().getParameter("action");
		
		String EventId = null; 
		EventId = (String)ctx.request().getParameter("eventid"); // come from Policy Diary Screen; saying this event has event id already
		
		String eventCode = (String)ctx.request().getParameter("eventcode"); // come from Standard Event Codes; saying create a new 
																			// event with this the Standard Event Code 

		if (action.equalsIgnoreCase("display"))
		{
			displayDecAppMemoMaint(ctx, EventId, "display");
			ctx.forwardToInput();
		}
		
		if (action.equalsIgnoreCase("edit"))
		{
			String personid = null;
			String person_name = null;
			
			personid  = (String)ctx.request().getParameter("personid"); //come from Browse Policy Certificate Person Help 
			person_name  = (String)ctx.request().getParameter("person_name");//come from Browse Policy Certificate Person Help
			
			if ((personid != null) && (person_name!=null))
			{
				this.fillinPerson_id(ctx, personid, person_name);
				ctx.forwardToInput();
				return;
			}
			
			if (EventId != null) //event id is there; then edit with event 
			{
				editDecAppMemoRequest(ctx);
				ctx.forwardToInput();
			}
			else
			{
				createLetterMaint(ctx, eventCode);
				ctx.forwardToInput();
			}
		}
		
		log.debug("End execute doExecute");
	}
	
	private void createLetterMaint(ActionContext ctx, String eventCode) 
	{
		log.debug("Prepopulate Letter.....");
		
		String EventId ="0";
		String service = "Letter";
		LetterForm form = (LetterForm) ctx.form();
		
		String policyNo = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		form.setPolicyno(policyNo );
		form.setStd_event(eventCode);
		form.setEvent_id("");
		form.setAttach("Y");
		form.setStatus("O");
		form.setLog_counter("0");
		
		MUDECMWResponseMSG_DATAHolder msgInfo = new MUDECMWResponseMSG_DATAHolder();
		MUDECMWResponseOUT_PARMSHolder outparms = new MUDECMWResponseOUT_PARMSHolder();
		
		MUDECMWResponseINOUT_PARMS1Holder inoutparms = new MUDECMWResponseINOUT_PARMS1Holder();
		
		try {
			WSDecAppMemoMaint.fetch(policyNo, EventId, inoutparms, msgInfo, outparms);
			form.setSuspense_amt(outparms.value.getSUSPENSE_AMOUNT().toString());
			form.setSuspense_date(TextProcessing.plainDateFormat(outparms.value.getSUSPENSE_DT().toString()));
			
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + policyNo, e);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",policyNo);
			ctx.forwardToInput();
			return;
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + policyNo, e);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION, service + " WS",policyNo);
			ctx.forwardToInput();
			return;
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + policyNo, e);
			ctx.addGlobalError(DiaryMessages.FILL_IN_FORM_EXCEPTION, service + " WS",policyNo);
			ctx.forwardToInput();
			return;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		form.setRequested(dateFormat.format(date).toString());
		String keyInsuredId = (String)ctx.session().getAttribute(Constants.IASkeyInsuredId);;
		form.setRecipient_id(keyInsuredId);
		String keyinsured = (String)ctx.session().getAttribute(Constants.IASkeyInsured);;
		form.setRecipient_name(keyinsured);
		
		log.debug("Finish .....prepopulating " + classAction +  ".....");
	}

	public void editDecAppMemoRequest(ActionContext ctx)
	{		
		String eventid = null;
		eventid = (String)ctx.request().getParameter("eventid");

		this.displayDecAppMemoMaint(ctx, eventid, "edit");
	}
	
	private void retrieveForm(MUDECMWINOUT_PARMS inputs, String PolicyNo , ActionContext ctx) {
		
		LetterForm form = (LetterForm) ctx.form();
		String[] TEXT = new String[60];
		
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
		
		inputs.setPOLICY_ID(new BigDecimal(PolicyNo));
		inputs.setUSER_ID(user);
		inputs.setLOG_COUNTER(new BigDecimal(form.getLog_counter()));
		
		if(form.getEvent_id().length() == 0)
		{
			inputs.setEVENT_ID(new BigDecimal("0")); 
		}
		else
		{
			inputs.setEVENT_ID(new BigDecimal(form.getEvent_id()));
		}
		
		inputs.setSTD_EVENT_ID(form.getStd_event());
		inputs.setSECOND_REQUEST_IND(form.getSecond_request());
		
		inputs.setFILLER(new String(""));
	
		if ((form.getLetter_text() != null) && (form.getLetter_text().trim().length() != 0))
		{
			Integer letterText = Integer.valueOf(form.getLetter_text());
			
			switch (letterText.intValue()) 
			{
				case 0: 
					if ((form.getBy_agent_applicant() != null) && (form.getBy_agent_applicant().trim().length() != 0 ))
					{
						Integer By_agent_applicant = Integer.valueOf(form.getBy_agent_applicant());
					
						switch (By_agent_applicant.intValue()) 
						{
							case 0: inputs.setAGENT("X"); 
									inputs.setAPPLICANT(new String(""));
								break;
							case 1: inputs.setAPPLICANT("X"); 
									inputs.setAGENT(new String(""));
								break;
						}
					}
					else
					{
						inputs.setAGENT(new String(""));
						inputs.setAPPLICANT(new String(""));
					}
					
					inputs.setWITHDRAWN_IND("X");
					inputs.setINCOMPLETE_IND(new String(""));
					
					if ((form.getLetter_withdrawn() != null) && (form.getLetter_withdrawn().trim().length() != 0))
					{
						if ((form.getLetter_withdrawn() != null) && (form.getLetter_withdrawn().trim().length() != 0) )
						{
							TextProcessing.storeTextDataLetter(form.getLetter_withdrawn(), 2, 5, TEXT, 75, 0, 150);
							inputs.setTEXT(TEXT);
						}
						else
							inputs.setTEXT(TEXT);
					}
					else
					{
						inputs.setTEXT(TEXT);
					}	
					break; //withdrawn
				case 1: inputs.setINCOMPLETE_IND("X"); 
						inputs.setWITHDRAWN_IND(new String(""));
						inputs.setAGENT(new String("")); 
						inputs.setAPPLICANT(new String(""));
						if ((form.getLetter_incomplete()!= null) && (form.getLetter_incomplete().trim().length() != 0))
						{
							TextProcessing.storeTextDataLetter(form.getLetter_incomplete(), 5, 9, TEXT, 75, 0, 300);
							inputs.setTEXT(TEXT);
						}
						else
						{
							inputs.setTEXT(TEXT);
						}
					break;  //incomplete
			
			}
		}
		else
		{
			inputs.setAGENT(new String(""));
			inputs.setAPPLICANT(new String(""));
			inputs.setWITHDRAWN_IND(new String(""));
		
			inputs.setINCOMPLETE_IND(new String(""));
			inputs.setTEXT(TEXT);
		}
		
		
		if(form.isLetter_declined_value())
		{
			inputs.setDECLINED_IND("X");   
			if ((form.getLetter_declined()!= null) && (form.isLetter_declined_value()))
			{
						
				TextProcessing.storeTextDataLetter( form.getLetter_declined(), 10, 16, TEXT, 75, 0, 450);
				inputs.setTEXT(TEXT);	
			}
	
		}
		else
			inputs.setDECLINED_IND(new String(""));   

	}

	 
	
	public void displayDecAppMemoMaint(ActionContext ctx, String EventId, String page)
	{
		
		String service = "Letter";
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		LetterForm form = (LetterForm) ctx.form();
		
		if (EventId == null) 
		{
			log.debug("Event Id is Null");
			ctx.addGlobalError(DiaryMessages.EVENT_ID_NULL,PolicyNo,service);
			ctx.forwardToInput();
		}
		
		MUDECMWResponseMSG_DATAHolder msgInfo = new MUDECMWResponseMSG_DATAHolder();
		MUDECMWResponseOUT_PARMSHolder outparms = new MUDECMWResponseOUT_PARMSHolder();
		
		MUDECMWResponseINOUT_PARMS1Holder inoutparms = new MUDECMWResponseINOUT_PARMS1Holder();
		
		try {
			WSDecAppMemoMaint.fetch(PolicyNo, EventId, inoutparms, msgInfo, outparms);
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
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.FILL_IN_FORM_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		}
	}
	
	
	private void fillinPerson_id(ActionContext ctx, String personid, String person_name) {
		
		LetterForm form = (LetterForm) ctx.form();
		form.setRecipient_id(personid);
		form.setRecipient_name(person_name);
	}
	
	public void fillForm(MUDECMWResponseINOUT_PARMS1Holder inoutparms,MUDECMWResponseOUT_PARMSHolder outparms, ActionContext ctx) throws Exception
	{
		LetterForm form = (LetterForm) ctx.form();
		String item=null;
		int b = 0;
		
		form.setAttach(outparms.value.getATTACHMENT_IND());
		
		form.setEvent_id(inoutparms.value.getEVENT_ID1().toString());
		form.setLog_counter(inoutparms.value.getLOG_COUNTER1().toString());
		
		String[] outArray =  inoutparms.value.getTEXT1();
	
		if(inoutparms.value.getWITHDRAWN_IND1().equalsIgnoreCase("X"))
		{
			form.setLetter_text("0");
			item = null;
			if(inoutparms.value.getAGENT1().equalsIgnoreCase("X"))
			{
				form.setBy_agent_applicant("0");
			}
			
			if(inoutparms.value.getAPPLICANT1().equalsIgnoreCase("X"))
			{
				form.setBy_agent_applicant("1");
			}
			
			
			StringBuffer letter_withdrawn = new StringBuffer( );
			for (int i = 2; i < 4; i++)
			{
				//letter_withdrawn = letter_withdrawn + outArray[i]+ " ";
				
				item = outArray[i];
				
				StringBuffer itemTxt = new StringBuffer(item);
				
				for(b=0; b<itemTxt.length(); b++)
				{
					if(itemTxt.charAt(b)=='~')
					{
						itemTxt.replace(b, b+1, " ");
					}
					else
					{	break;
						
					}
				}
				
				if(itemTxt.toString().trim().length() == 0) 
				{
					letter_withdrawn.append(System.getProperty("line.separator"));
				}
				else
				{
					letter_withdrawn.append(itemTxt.toString()).append(System.getProperty("line.separator")); 
				}	
				
			}
			
			
			if(letter_withdrawn.length() != 0)
			{
				form.setLetter_withdrawn(letter_withdrawn.toString());
			}
		}
		
		if(inoutparms.value.getINCOMPLETE_IND1().equalsIgnoreCase("X"))
		{
			form.setLetter_text("1");
			item = null;
			
			StringBuffer letter_incomplete = new StringBuffer( );
			
			for (int i = 5; i < 9; i++)
			{
				//letter_incomplete = letter_incomplete + outArray[i]+ " ";
				item = outArray[i];
				
				StringBuffer itemTxt = new StringBuffer(item);
				
				for(b=0; b<itemTxt.length(); b++)
				{
					if(itemTxt.charAt(b)=='~')
					{
						itemTxt.replace(b, b+1, " ");
					}
					else
					{	break;
						
					}
				}
				
				
				if(itemTxt.toString().trim().length() == 0) 
				{
					
					letter_incomplete.append(System.getProperty("line.separator"));
				}
				else
				{
					letter_incomplete.append(itemTxt.toString()).append(System.getProperty("line.separator")); 
				}	
				
			}
			
			if(letter_incomplete.length() != 0)
			{
				form.setLetter_incomplete(letter_incomplete.toString());
			}
		}
		
		if(inoutparms.value.getDECLINED_IND1().equalsIgnoreCase("X"))
		{
			
			item = null;
			
			form.setLetter_declined_value(true);
			
			StringBuffer letter_declined = new StringBuffer( );
			for (int i = 10; i < 16; i++)
			{
				//letter_declined = letter_declined + outArray[i] + " ";
				item = outArray[i];
				
				StringBuffer itemTxt = new StringBuffer(item);
				
				for(b=0; b<itemTxt.length(); b++)
				{
					if(itemTxt.charAt(b)=='~')
					{
						itemTxt.replace(b, b+1, " ");
					}
					else
					{	break;
						
					}
				}
				
				if(itemTxt.toString().trim().length() == 0) 
				{
					letter_declined.append(System.getProperty("line.separator"));
				}
				else
				{
					letter_declined.append(itemTxt.toString()).append(System.getProperty("line.separator"));
				}	
				
				
			}

			if(letter_declined.length() != 0)
			{
				form.setLetter_declined(letter_declined.toString());
			}
			
			
		}
				
		form.setRecipient_id(outparms.value.getRECIPIENT_PERSON_ID().toString());
		form.setRecipient_name(TextProcessing.recipientFormat(outparms.value.getRECIPIENT_SEARCH_NAME()));
		form.setRequested(TextProcessing.dateFormat(outparms.value.getDATE_CREATED()));
		form.setSecond_request(inoutparms.value.getSECOND_REQUEST_IND1());
		form.setStatus(outparms.value.getEVENT_STATUS_CURRENT());
		form.setStd_event(inoutparms.value.getSTD_EVENT_ID1());
		form.setSuspense_amt(outparms.value.getSUSPENSE_AMOUNT().toString());
		form.setSuspense_date(TextProcessing.plainDateFormat(outparms.value.getSUSPENSE_DT().toString()));
		String policyNo = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		form.setPolicyno(policyNo );
		form.setLog_counter(inoutparms.value.getLOG_COUNTER1().toString());
	}

	/**
	 * This method is called if the save button is clicked
	 * @param ctx The FormActionContext
	 */
	public void save_onClick(FormActionContext ctx) throws Exception {
		LetterForm form = (LetterForm) ctx.form();
		
		form.validateForm(ctx);
	
		if (ctx.hasErrors())
		{
			ctx.forwardToInput();
			 return;
		}
		
		addLetterMaint(ctx);

	}
	
	private void addLetterMaint(ActionContext ctx) 
	{
		String service = "Adding: " + classAction;
		
		log.debug("Adding " + classAction);
		LetterForm form = (LetterForm) ctx.form();
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUDECMWResponseMSG_DATAHolder msgInfo = new MUDECMWResponseMSG_DATAHolder();
		MUDECMWResponseOUT_PARMSHolder outparms = new MUDECMWResponseOUT_PARMSHolder();
		
		MUDECMWResponseINOUT_PARMS1Holder inoutparms = new MUDECMWResponseINOUT_PARMS1Holder();
		
		MUDECMWINOUT_PARMS inputs =  new MUDECMWINOUT_PARMS();
		
		retrieveForm(inputs, PolicyNo, ctx);
		
		try {
			WSDecAppMemoMaint.add(inputs, inoutparms, msgInfo, outparms);
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.FILL_IN_FORM_EXCEPTION, service + " WS",PolicyNo);
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

	/**
	 * This method is called if the save button is clicked
	 * @param ctx The FormActionContext
	 */
	public void edit_onClick(FormActionContext ctx) throws Exception {
		LetterForm form = (LetterForm) ctx.form();
		
		form.validateForm(ctx);
	
		if (ctx.hasErrors())
		{
			ctx.forwardToInput();
			 return;
		}
		
		editDecAppMemoMaint(ctx);
	}
	
	public void recipientHelp_onClick(FormActionContext ctx) throws Exception 
	{
		LetterForm form = (LetterForm) ctx.form();
		form.save();
		
		ctx.forwardByName("recipientHelp", "LM", form.getEvent_id());
		
	}
	

	private void editDecAppMemoMaint(ActionContext ctx) {
		String service = "Edit: " + classAction;
		
		log.debug("Editing " + classAction);
		LetterForm form = (LetterForm) ctx.form();
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUDECMWResponseMSG_DATAHolder msgInfo = new MUDECMWResponseMSG_DATAHolder();
		MUDECMWResponseOUT_PARMSHolder outparms = new MUDECMWResponseOUT_PARMSHolder();
		
		MUDECMWResponseINOUT_PARMS1Holder inoutparms = new MUDECMWResponseINOUT_PARMS1Holder();
		
		MUDECMWINOUT_PARMS inputs =  new MUDECMWINOUT_PARMS();
		
		retrieveForm(inputs, PolicyNo, ctx);
		
		try {
			WSDecAppMemoMaint.edit(inputs, inoutparms, msgInfo, outparms);
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.FILL_IN_FORM_EXCEPTION, service + " WS",PolicyNo);
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
			log.debug("Finish....editing " + classAction);
		}
		
	}

	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		LetterForm form = (LetterForm) ctx.form();
		form.clear();
		
		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		
		ctx.forwardByName(Forwards.BACK);
	}
	
	public static String backspace(String  s, int repeat) {
	       
		StringBuffer buffer = new StringBuffer(repeat * s.length());
		for (int i = 0; i < repeat; i++)
			buffer.append(s);

		return buffer.toString();

    }
}
