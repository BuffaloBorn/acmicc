package com.epm.acmi.struts.action;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.acmi.common.IasDairyEdit;
import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.common.User;
import com.cc.acmi.common.TextProcessing.recipientDiaryFormat;
import com.cc.acmi.presentation.dsp.PolicyEventsBean;
import com.cc.acmi.presentation.dsp.PolicyEventsDsp;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ListControl;
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.datamodel.PolicyEventsDisplayList;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseIN_PARM1Holder;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseOUT_PARMHolder;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.util.MiscellaneousUtils;
import com.epm.acmi.struts.form.ActivateIASForm;
import com.isdiary.entirex.WSPolicyEventsCall;
import com.isdiary.entirex.WSPolicyMaintCall;

public class ActivateIASAction extends CCAction{

	private static Logger log = MiscellaneousUtils.getIASLogger();
	private static String classAction = "Activate IAS Action";
	
	
	/**
	 * Returns tab page Id
	 */
//	public String getTabPageId() {
//		return TABPAGE6;
//	}
//	
	public ActivateIASAction()
	{
		super();
		
	}
	
	public void doExecute(ActionContext ctx) throws IOException, ServletException 
	{
		
		log.debug("Begin execute doExecute");
		
		String intPolicy = (String)ctx.request().getParameter("intPolicy");
		
		ctx.session().setAttribute("iasdiaryFlag", "edit");
		
		log.debug("intPolicy: " + intPolicy);
		
		if (intPolicy != null)
		{
			
			if (intPolicy.equalsIgnoreCase("true"))
			{
				String PolicyNo  = (String)ctx.session().getAttribute(Constants.policyNumber);
				ctx.session().setAttribute(Constants.IASpolicyNumber, PolicyNo);
				ctx.session().setAttribute(Constants.IASModify, "edit");
				log.debug("Set " + Constants.IASModify + " to edit");
				ctx.session().setAttribute(Constants.IASDiaryModify, "edit");
				log.debug("Set " + Constants.IASDiaryModify + " to edit");
			}
			
			if (intPolicy.equalsIgnoreCase("false"))
			{
				String PolicyNo  = (String)ctx.request().getParameter("PolicyNo");
				ctx.session().setAttribute(Constants.IASpolicyNumber, PolicyNo);
				ctx.session().setAttribute(Constants.IASModify, "edit");
				log.debug("Set " + Constants.IASModify + " to display");
				ctx.session().setAttribute(Constants.IASDiaryModify, "edit");
				log.debug("Set " + Constants.IASDiaryModify + " to display");
			}
			
		}	
			
		this.loadList(ctx);
			// Display the Page with the UserList
		log.debug("End execute doExecute");
	}

	/**
	 * Fill's the ListControl from the Database
	 * @param ctx	ActionContext
	 * @throws Exception
	 */
	private void loadList(ActionContext ctx)  {

		String service = "Display List: " + classAction;
		
		// first get the Displaydata for our List
		//PolicyDetailDisplayList dspData = WSPolicyDiaryCall.fetch();
		String PolicyNo = null;
		
		PolicyEventsDisplayList dspData = new PolicyEventsDisplayList();
		//PolicyEventsBean OtherDisplay = new PolicyEventsBean();
		
		PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		log.debug("Policy Number from IASpolicyNumber: " + PolicyNo);
		
		if (PolicyNo.trim().length() == 0)
		{
			log.debug("PolicyNo length:" + PolicyNo.trim().length());
			ctx.addGlobalMessage(DiaryMessages.NO_POLICY_ERROR_IASDIARY);
			ctx.forwardToInput();
			return;
		}
		
		
		try {
			log.debug("Calling Policy Event List");
			WSPolicyEventsCall.fetch(dspData, PolicyNo);
			log.debug("Finish calling Policy Event List");
			// secondly create the ListControl and populate it
			// with the Data to display
			log.debug("list: " + dspData.size());
			SimpleListControl userList = new SimpleListControl();
			userList.setDataModel(dspData);
			//initForm(ctx, OtherDisplay);
			//initButton(ctx, OtherDisplay);
			// highlight a single row
			// userList.mark("FAS");
			
			// third put the ListControl into the Session-Object.
			// Our ListControl is a statefull Object.
			// Normaly  you can use an Objectmanager or an other
			// workflow Component that manage the Livecyle of the
			// our ListControl-Object.
			ctx.session().setAttribute("events", userList);
			loadForm(ctx,PolicyNo );
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return; 
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
			
		}catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() + " Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.REGULAR_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
		}
		
		
	}
	
	
	private void loadForm(ActionContext ctx, String PolicyNo)
	{
		
		String service = "Policy Maintenance";
		
		ACPLYMWResponseINOUT_PARM1Holder inoutparms = new ACPLYMWResponseINOUT_PARM1Holder();
		ACPLYMWResponseOUT_PARMHolder outparms = new ACPLYMWResponseOUT_PARMHolder();
		ACPLYMWResponseIN_PARM1Holder inparms = new ACPLYMWResponseIN_PARM1Holder();
		ACPLYMWResponseMSG_INFOHolder msgInfo = new ACPLYMWResponseMSG_INFOHolder();
		
		User loggedUser = (User)ctx.session().getAttribute(Constants.loggedUser);
		 
		String userid = loggedUser.getUserId();
		log.debug("userid: " + userid);
		
		ctx.session().setAttribute(Constants.IASuser, userid);
		
		try {
			WSPolicyMaintCall.fetch(PolicyNo, userid,inparms, inoutparms,  outparms, msgInfo);
			fillForm(ctx,userid, inparms, inoutparms,  outparms);
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardToInput();
			return;
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo, e);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
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
			log.debug("Mainframe Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.addGlobalError(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.forwardToInput();
			log.debug("Error occurred " + classAction);
		}
		else
		{
			log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.forwardToInput();
			log.debug("Finish....Display " + classAction);
		}

	}

	private void fillForm(ActionContext ctx, String user_id, ACPLYMWResponseIN_PARM1Holder inparms, ACPLYMWResponseINOUT_PARM1Holder inoutparms, ACPLYMWResponseOUT_PARMHolder outparms) throws Exception
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		form.setKEY_PRODUCT_ID(outparms.value.getKEY_PRODUCT_ID());
		TextProcessing textProcess = new TextProcessing();
		recipientDiaryFormat respn = textProcess.new recipientDiaryFormat(outparms.value.getKEY_INSURED());
		
		form.setKEY_INSURED(respn.getRecipient_name());
		form.setUNDERW_DESCRIPTION(outparms.value.getUNDERW_DESCRIPTION());
		form.setUNDERWRITER(outparms.value.getUNDERWRITER());
		form.setPOLICY_ID(inoutparms.value.getPOLICY_ID1().toString());
		form.setDECISION_DATE(TextProcessing.dateFormat(outparms.value.getDECISION_DATE()));
		form.setUSER(outparms.value.getUNDERWRITER());
		form.setNUMOFRIDER(outparms.value.getNO_OF_RIDERS());
		form.setISSUED_PLACE_FLAG(outparms.value.getISSUED_PLACED_FLAG());
		form.setUdwselectedItem(inoutparms.value.getUNDERW_STATUS_CURRENT1());
		form.setPOLICY_TRANS_TYPE(outparms.value.getPOLICY_TRANS_TYPE());
		form.setPOLICY_TRANS_TYPE_COND(outparms.value.getPOLICY_TRANS_TYPE_COND());
		form.setRIDER_IND(outparms.value.getRIDER_IND());
		form.setAMENDMENT_IND(outparms.value.getAMENDMENT_IND());
		form.setLog_counter(inoutparms.value.getLOG_COUNTER1().toString());
		
		if(form.getUNDERWRITER_OLD().trim().length() == 0)
		{
			form.setUNDERWRITER_OLD(inoutparms.value.getUNDERW_STATUS_CURRENT1());
		}
		
		ctx.session().setAttribute(Constants.IASkeyInsured, form.getKEY_INSURED());
		ctx.session().setAttribute(Constants.IASkeyInsuredId, respn.getRecipient_id());
		
		ctx.request().setAttribute("RIDER_IND", outparms.value.getRIDER_IND());
		
		ctx.session().setAttribute("riderind", outparms.value.getRIDER_IND());  
		ctx.session().setAttribute("form_id", outparms.value.getAPPLICATION_FORM_ID());
		
		ctx.session().setAttribute("AMENDMENT_IND", outparms.value.getAMENDMENT_IND());
		ctx.request().setAttribute("AMENDMENT_IND", outparms.value.getAMENDMENT_IND());
		ctx.request().setAttribute("NOTES_IND", outparms.value.getNOTES_IND());
		ctx.request().setAttribute("COMMENTS_IND", outparms.value.getCOMMENTS_IND());
		ctx.request().setAttribute("DELIVERY_REQ", outparms.value.getDELIVERY_REQ());
	}

	/**
	 * This Method is called, when the Tabpage is clicked
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 * @throws Exception
	 */
	public void secondarymaintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {
		log.debug("Begin secondarymaintabset_onTabClick-IAS");
		this.loadList(ctx);
		ctx.control().execute(ctx, seltab);
		log.debug("End secondarymaintabset_onTabClick-IAS");
	}
	
	/**
	 * Fill's and refresh's the ListControl from the Database
	 * @param	ctx	ControlActionContext
	 * @throws	Exception
	 */
//	private void refreshList(ControlActionContext ctx) throws Exception {
//
//		String PolicyNo = null; 
//		
//		PolicyEventsDisplayList dspData = new PolicyEventsDisplayList();
//		//PolicyEventsBean OtherDisplay = new PolicyEventsBean();
//		
//		PolicyNo  = (String)ctx.request().getParameter("policyno");
//		log.debug("requset policyno:" + PolicyNo);
//		
//		if (PolicyNo == null) 
//		{
//			PolicyNo  = (String)ctx.session().getAttribute(Constants.policyNumber);
//			log.debug("session policyno:" + PolicyNo);
//		}
//		ctx.request().setAttribute("policyno", PolicyNo);
//		WSPolicyEventsCall.fetch(dspData, PolicyNo);
//		
//		SimpleListControl userList = (SimpleListControl) ctx.control();
//		userList.setDataModel(dspData);
//		//	ctx.session().setAttribute("users", userList);
//		loadForm(ctx,PolicyNo );
//	}
	
	/**
	 * This Method is called if the Refresh-Button is clicked
	 * @param	ctx	ControlActionContext
	 * @throws	java.lang.Exception
	 */
	public void events_onRefresh(ControlActionContext ctx) throws Exception {
		ActivateIASForm form = (ActivateIASForm) ctx.form();

		ctx.session().setAttribute(Constants.IASpolicyNumber, form.getPOLICY_ID());
		this.loadList(ctx);
	}
	
	public void events_onCreate(ControlActionContext ctx) throws Exception
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		log.debug("Forwarding to Create Events Page");
		ctx.session().setAttribute(Constants.IASpolicyNumber, form.getPOLICY_ID());
		ctx.session().setAttribute(Constants.IAStaskName, "Creating New Event");
		
		ctx.forwardByName("stdEventCode");
		log.debug("Forwarding to Create Event Page: Complete Setting Up");
	}
	
	
	/**
	* This Method is called when the Edit-Column is clicked
	*/
	public void events_onEdit(ControlActionContext ctx, String key) throws Exception {
		String PolicyNo = null;
		String StdEventCode = null;
		
		String scrName = null;
		
		String method = "Method: events_onEdit ->> ";
		
		PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		ListControl events = (ListControl)ctx.control();
		PolicyEventsDsp dceDisplay = (PolicyEventsDsp)events.getRowFromKey(key);

		StdEventCode = dceDisplay.getSTD_EVENT_ID().toString();
		
		scrName = dceDisplay.getSCRNAME();
		
		log.debug(method + "Session Policy Number: " + PolicyNo);
		log.debug(method + "Std Event Code: " + StdEventCode + " ->> Event Id: " + key + " ->> Scr Name: " + scrName);
		
		if(ctx.mapping().findForward(scrName + "onEdit") == null)
		{
			log.debug(method + "Do not have a case for:" + StdEventCode);
			ctx.addGlobalError(DiaryMessages.STD_EVENTS_CODES_INVALID,StdEventCode);
			ctx.forwardToInput();
		}
		else
		{
			ctx.forwardByName(scrName + "onEdit", key);
		}
	}
	
	/**
	* This Method is called when the Drilldown-Column is clicked
	* In our Example we switch to the DetailView, which shows
	* more Information about the User. It's a readonly View.
	* @param ctx ControlActionContext
	* @param key UniqueKey, as it was defined in the UserDisplayList
	* to identify the Row. In this Example the UserId.
	*/
	public void events_onDrilldown(ControlActionContext ctx, String key) {
		//take the key and 	
		String PolicyNo = null;
		
		String StdEventCode = null;
		String scrName = null;
		
		String method = "Method: events_onDrilldown ->> ";
		
	    PolicyNo = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		ListControl events = (ListControl)ctx.control();
		PolicyEventsDsp dceDisplay = (PolicyEventsDsp)events.getRowFromKey(key);
	
		StdEventCode = dceDisplay.getSTD_EVENT_ID().toString();
		scrName = dceDisplay.getSCRNAME();
		
		log.debug(method + "Session Policy Number: " + PolicyNo);
		log.debug(method + "Std Event Code: " + StdEventCode + " ->> Event Id: " + key + " ->> Scr Name: " + scrName);
		
		if(ctx.mapping().findForward(scrName + "onDrilldown") == null)
		{
			log.debug(method + "Do not have a case for:" + StdEventCode);
			ctx.addGlobalError(DiaryMessages.STD_EVENTS_CODES_INVALID,StdEventCode);
			ctx.forwardToInput();
		}
		else
		{
			ctx.forwardByName(scrName + "onDrilldown", key);		
			ctx.request().setAttribute("showFocus", "showFocus");
		}
		
	}
	
	
	protected void initForm(ActionContext ctx, PolicyEventsBean dis)
	{
		ActivateIASForm form = (ActivateIASForm) ctx.form();
	
		form.setKEY_PRODUCT_ID(dis.getKEY_PRODUCT_ID());
		form.setKEY_INSURED(dis.getKEY_INSURED());
		form.setUNDERW_DESCRIPTION(dis.getUNDERW_DESCRIPTION());
		form.setUNDERWRITER(dis.getUNDERWRITER());
		form.setPOLICY_ID(dis.getPOLICY_ID());
		form.setDECISION_DATE(dis.getDECISION_DATE());
	}
	
	
	public void portamedic_onClick(FormActionContext ctx)throws Exception
	{
		String PolicyNo = null; 
		PolicyNo  = (String)ctx.request().getParameter("policyno");
		log.debug("requset policyno:" + PolicyNo);
		
		if (PolicyNo == null) 
		{
			PolicyNo  = (String)ctx.session().getAttribute(Constants.policyNumber);
			log.debug("session policyno:" + PolicyNo);
		}
		log.debug("Portamedic Request");		
		
		ctx.forwardToAction("iuauser/eventPortamedic?policyno="+PolicyNo);
	}
	
	public void browsePoliciesHelp_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirect to Browse Policies Help");
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		ctx.forwardByName("browsePolicies", form.getKEY_INSURED());
	}
	
	public void updatePolicyNo_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Calling Update Policy");
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		updatePolicyNo(ctx, form.getPOLICY_ID().trim());
	}
	
	private void updatePolicyNo(FormActionContext ctx, String policy_id) 
	{
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.policyNumber);
		log.debug("Current Policy in Session: " + PolicyNo);
		
		ctx.session().setAttribute("iasdiaryFlag", "display");
		log.debug("Setting iasdiaryFlag to display");
		
		
		log.debug("New Policy to set in Session: " + policy_id);
		if (PolicyNo.equalsIgnoreCase(policy_id))
		{
			ctx.session().setAttribute(Constants.IASpolicyNumber, PolicyNo);
			ctx.request().setAttribute("modify", "edit");
			log.debug("modify: Holds the current of page: edit" );
			ctx.session().setAttribute(Constants.IASDiaryModify, "edit");
			log.debug("Set " + Constants.IASDiaryModify + " to edit");
			ctx.session().setAttribute(Constants.IASModify, "edit");
			log.debug("Set " + Constants.IASModify + " to edit");
		}
		else
		{	
			ctx.session().setAttribute(Constants.IASpolicyNumber, policy_id);
			ctx.request().setAttribute("modify", "display");
			log.debug("modify: Holds the current of page: display" );
			ctx.session().setAttribute(Constants.IASDiaryModify, "display");
			log.debug("Set " + Constants.IASDiaryModify + " to display");
			ctx.session().setAttribute(Constants.IASModify, "display");
			log.debug("Set " + Constants.IASModify + " to display");
		}	

		log.debug("Reload Policy Events List");
		this.loadList(ctx);
	}

	public void updateUnderwriterStatus_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Calling Update Underwriter Status");
		updateUnderwriterStatus(ctx);
	}
	
	private void updateUnderwriterStatus(FormActionContext ctx ) throws Exception
	{
		String service = "Update: Underwriterstatus " + classAction;
		ActivateIASForm form = (ActivateIASForm) ctx.form();
		
		ACPLYMWResponseMSG_INFOHolder msgInfo = new ACPLYMWResponseMSG_INFOHolder();
		
		String amendment_ind = (String) ctx.session().getAttribute("AMENDMENT_IND");
		log.debug("AMENDMENT_IND: " + amendment_ind );
		
		ctx.session().removeAttribute("AMENDMENT_IND");
		
		String rider_ind = (String) ctx.session().getAttribute("RIDER_IND");
		log.debug("rider_ind: " + rider_ind );
		
		if(rider_ind == null)
		{
			rider_ind = "n";
		}
		
		log.debug("Set modify request and session to edit" );
		ctx.request().setAttribute("modify", "edit");
		ctx.session().setAttribute("modify", "edit");
		
		if(amendment_ind == null)
		{
			amendment_ind = "n";
		}
		
		String personId = (String)ctx.session().getAttribute(Constants.IASkeyInsuredId);
		log.debug("personId: " + personId );
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
		log.debug("user: " + user );
		String policyNo = form.getPOLICY_ID();
		log.debug("policyNo: " + policyNo );
		//String underWriterStatus = form.getUdwselectedItem();
		String logCounter = form.getLog_counter();
		log.debug("logCounter: " + logCounter );
		
		log.info("Calling Edit Rountines");
		if (IasDairyEdit.CallErrorEdits(ctx))
		{
			loadForm(ctx,policyNo);
			ctx.forwardToInput();
			return;
		}
		log.info("No Error Edits Occurred");	
		
		try {
			log.debug("Current Underwriter Status: " + form.getUNDERWRITER_OLD().toString() );
			log.debug("New Underwriter Status: " + form.getUdwselectedItem().toString() );
			WSPolicyMaintCall.updateUnderwriterStatus(policyNo, user, form.getUdwselectedItem(), msgInfo, logCounter);
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + policyNo, e);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",policyNo);
			ctx.forwardToInput();
			return;
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + policyNo, e);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",policyNo);
			ctx.forwardToInput();
			return;
		}
		
		if( msgInfo.value.getRETURN_CODE().equalsIgnoreCase("E"))
		{
			log.debug("Mainframe Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.addGlobalError(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			form.setUdwselectedItem(form.getUNDERWRITER_OLD());
			ctx.forwardToInput();
			log.debug("Error occurred " + classAction);
		}
		else
		{
			if (form.getUdwselectedItem().equalsIgnoreCase("m") || form.getUdwselectedItem().equalsIgnoreCase("v") )
			{
				
				log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
				ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
				log.debug("Finish....Updating Underwriter Status " + classAction);
				loadForm(ctx,policyNo);
				ctx.forwardByName("amendmentwarning");
				return;
			}
			
		
			if ((form.getUdwselectedItem().equalsIgnoreCase("d")) || ( (form.getUNDERWRITER_OLD().equalsIgnoreCase("i")) && (form.getUdwselectedItem().equalsIgnoreCase("v")) && (amendment_ind.equalsIgnoreCase("n"))))
			{
			
				if ( (form.getUNDERWRITER_OLD().equalsIgnoreCase("i")) && (form.getUdwselectedItem().equalsIgnoreCase("v")) && (amendment_ind.equalsIgnoreCase("n")))
				{
					
					log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
					ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
					log.debug("Finish....Updating Underwriter Status " + classAction);
					loadForm(ctx,policyNo);
					ctx.forwardByName("amendmentwarning");
					return;
				}
				
				if (form.getUdwselectedItem().equalsIgnoreCase("d"))
				{
					log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
					ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
					log.debug("Finish....Updating Decline Status " + classAction);
					loadForm(ctx,policyNo);
					ctx.forwardByName("conditionCodes",personId ,policyNo);
					return;
				}
			}
			else
			{
				log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
				ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
				log.debug("Finish....Updating Underwriter Status " + classAction);
				loadForm(ctx,policyNo);
				ctx.forwardToInput();
				
			}
		}
		log.info("Setting new underwriter status to old underwriter object holder" );
		form.setUNDERWRITER_OLD(form.getUdwselectedItem());
	}

	public void policyPersonEdit_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Policy Person in edit mode");
		ctx.forwardByName("policyPersonMainEdit");
	}
	
	public void policyPersonDisplay_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Policy Person in display mode");
		ctx.forwardByName("policyPersonMainDisplay");
	}
	
	public void policyPersonCoverageMain_onClick(FormActionContext ctx)throws Exception
	{
		ctx.forwardToAction("iuauser/policyPersonCoverageMain");
	}
	
	public void policyExtendCommentsEdit_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Policy Extended Comments in edit mode");
		ctx.forwardByName("policyExtendedCommentsEdit");
	}
	
	public void policyExtendCommentsCreate_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Policy Extended Comments in create mode");
		ctx.forwardByName("policyExtendedCommentsCreate");
	}
	
	public void policyExtendCommentsDisplay_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Policy Extended Comments in display mode");
		ctx.forwardByName("policyExtendedCommentsDisplay");
	}
	
	public void underwritingNotesEdit_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Underwriting Notes in edit mode");
		ctx.forwardByName("underwritingNotesEdit");
	}
	
	public void underwritingNotesCreate_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Underwriting Notes in create mode");
		ctx.forwardByName("underwritingNotesCreate");
	}
	
	
	public void underwritingNotesDisplay_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Underwriting Notes in display mode");
		ctx.forwardByName("underwritingNotesDisplay");
	}
	
	
	public void amendmentCreate_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Amendment in create mode");
		ctx.forwardByName("amendmentCreate");
	}
	
	public void amendmentEdit_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Amendment in edit mode");
		ctx.forwardByName("amendmentEdit");
	}
	
	public void amendmentDisplay_onClick(FormActionContext ctx)throws Exception
	{
		log.debug("Redirecting to Amendment in display mode");
		ctx.forwardByName("amendmentDisplay");
	}
	
	
}
