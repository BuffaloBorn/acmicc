package com.epm.acmi.struts.action;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;


import com.cc.acmi.common.DiaryMessages;
import com.cc.acmi.presentation.dsp.BrowseRiderCodesDsp;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ListControl;
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.datamodel.BrowseRiderCodesDisplatList;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.RiderCodesForm;
import com.epm.acmi.util.MiscellaneousUtils;
import com.isdiary.entirex.WSRiderCodesCall;
import com.softwarag.extirex.webservice.ridercodes.client.ACRIDBWPDA_FIELDS;
import com.softwarag.extirex.webservice.ridercodes.client.holders.ACRIDBWResponseINOUT_PARM1Holder;

public class RiderCodesAction extends CCAction{
	
	private static Logger log = MiscellaneousUtils.getIASLogger();
	private static String classAction = "Rider Codes Data";
	String riderPos = null;
	String personid = null;
	String actionstatus = null;
	
	
	public void doExecute(ActionContext  ctx) throws Exception {
	
		log.debug("Begin execute doExecute");
		this.loadList(ctx);
		log.debug("End execute doExecute");
		
	}

	private void loadList(ActionContext ctx) 
	{
		String service = "Display: " + classAction;
		riderPos = null;
		personid = null;
		actionstatus = null;
		
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		riderPos = (String)ctx.request().getAttribute("riderPos");
		

		personid =  (String)ctx.request().getParameter("personid");
		
			
		actionstatus = (String)ctx.request().getParameter("actionstatus");
		
		ACRIDBWResponseINOUT_PARM1Holder inoutparams = new ACRIDBWResponseINOUT_PARM1Holder();
		ACRIDBWPDA_FIELDS localParms = (ACRIDBWPDA_FIELDS) ctx.session().getAttribute("riderCodesParms");
		
			
		try {
			BrowseRiderCodesDisplatList dspData = WSRiderCodesCall.initFetch("", inoutparams, localParms);
			SimpleListControl riderCodeList = new SimpleListControl();
			riderCodeList.setDataModel(dspData);
			ctx.session().setAttribute("riderCodeList", riderCodeList);
			ctx.session().setAttribute("inoutparmsRiderCodesResponse", inoutparams);
			ctx.forwardToInput();
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
		}		
		
		
	}
	
	public void forward_onClick (FormActionContext ctx) 
	{
		
		String service = "Display: forward rider codes " + classAction;
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		log.debug("Rider Codes forward click");

		ACRIDBWResponseINOUT_PARM1Holder forwardFetch = (ACRIDBWResponseINOUT_PARM1Holder) ctx.session().getAttribute("inoutparmsRiderCodesResponse");;
		ACRIDBWResponseINOUT_PARM1Holder inoutparams = new ACRIDBWResponseINOUT_PARM1Holder();
		ACRIDBWPDA_FIELDS localParms = (ACRIDBWPDA_FIELDS) ctx.session().getAttribute("riderCodesParms");
		
		
		log.debug("START_KEY: " + forwardFetch.value.getSTART_KEY1());
		
		ctx.session().removeAttribute("riderCodeList");
		ctx.session().removeAttribute("inoutparmsRiderCodesResponse");	
		
		try {
			BrowseRiderCodesDisplatList dspData = WSRiderCodesCall.forwardFetch(forwardFetch, inoutparams, ctx, localParms);
			SimpleListControl riderCodeList = new SimpleListControl();
			riderCodeList.setDataModel(dspData);
			ctx.session().setAttribute("riderCodeList", riderCodeList);
			ctx.session().setAttribute("inoutparmsRiderCodesResponse", inoutparams);
			
			ctx.forwardToInput();
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
		}		
		
	}
	
	public void backward_onClick (FormActionContext ctx) 
	{
		
		String service = "Display: backward rider codes " + classAction;
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		log.debug("Rider Codes backward click");

		ACRIDBWResponseINOUT_PARM1Holder backwardFetch = (ACRIDBWResponseINOUT_PARM1Holder) ctx.session().getAttribute("inoutparmsRiderCodesResponse");;
		ACRIDBWResponseINOUT_PARM1Holder inoutparams = new ACRIDBWResponseINOUT_PARM1Holder();
		ACRIDBWPDA_FIELDS localParms = (ACRIDBWPDA_FIELDS) ctx.session().getAttribute("riderCodesParms");
		
		log.debug("START_KEY: " + backwardFetch.value.getSTART_KEY1());
		
		ctx.session().removeAttribute("riderCodeList");
		ctx.session().removeAttribute("inoutparmsRiderCodesResponse");	
		
		try {
			BrowseRiderCodesDisplatList dspData = WSRiderCodesCall.backwardFetch(backwardFetch, inoutparams, localParms);
			SimpleListControl riderCodeList = new SimpleListControl();
			riderCodeList.setDataModel(dspData);
			ctx.session().setAttribute("riderCodeList", riderCodeList);
			ctx.session().setAttribute("inoutparmsRiderCodesResponse", inoutparams);
			ctx.forwardToInput();
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
		}		
		
	}
	
	
	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		// in this example we go back to the jsp
		
		ACRIDBWPDA_FIELDS localParms = (ACRIDBWPDA_FIELDS) ctx.session().getAttribute("riderCodesParms");
		
		String personstatus = localParms.getPDA_PERSON_STATUS();
		String persondate =  localParms.getPDA_PERSON_DATE();
		
		RiderCodesForm form = (RiderCodesForm) ctx.form();
		
		riderPos = (String) ctx.session().getAttribute("riderPos");
		actionstatus = (String) ctx.session().getAttribute("actionstatus");
		
		
		if ((riderPos.equalsIgnoreCase("One")) && (actionstatus.equalsIgnoreCase("edit")))
		{
			ctx.forwardByName("riderMaintNoCodeOneEdit", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("One")) && (actionstatus.equalsIgnoreCase("create")))
		{
			ctx.forwardByName("riderMaintNoCodeOneCreate", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("Two")) && (actionstatus.equalsIgnoreCase("edit")))
		{
			ctx.forwardByName("riderMaintNoCodeTwoEdit", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("Two")) && (actionstatus.equalsIgnoreCase("create")))
		{
			ctx.forwardByName("riderMaintNoCodeTwoCreate", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("Three")) && (actionstatus.equalsIgnoreCase("create")))
		{
			ctx.forwardByName("riderMaintNoCodeThreeCreate", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("Three")) && (actionstatus.equalsIgnoreCase("edit")))
		{
			ctx.forwardByName("riderMaintNoCodeThreeEdit", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("Four")) && (actionstatus.equalsIgnoreCase("create")))
		{
			ctx.forwardByName("riderMaintNoCodeFourCreate", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("Four")) && (actionstatus.equalsIgnoreCase("edit")))
		{
			ctx.forwardByName("riderMaintNoCodeFourEdit", personid, personstatus, persondate );
		}
	
		if ((riderPos.equalsIgnoreCase("Five")) && (actionstatus.equalsIgnoreCase("create")))
		{
			ctx.forwardByName("riderMaintNoCodeFiveCreate", personid, personstatus, persondate );
		}
		
		if ((riderPos.equalsIgnoreCase("Five")) && (actionstatus.equalsIgnoreCase("edit")))
		{
			ctx.forwardByName("riderMaintNoCodeFiveEdit", personid, personstatus, persondate );
		}
		
		ctx.session().removeAttribute("riderPos");
		ctx.session().removeAttribute("actionstatus");
		ctx.session().removeAttribute("personid");
		
		form.clear();
	}
	
	public void riderCodeList_onDrilldown(ControlActionContext ctx, String key) 
	{
		log.debug("Rider Codes key:" + key);
		ListControl ridersCodes = (ListControl)ctx.control();
		BrowseRiderCodesDsp dceRiders = (BrowseRiderCodesDsp) ridersCodes.getRowFromKey(key);
		
		RiderCodesForm form = (RiderCodesForm) ctx.form();
		
		
		riderPos = (String) ctx.session().getAttribute("riderPos");
		actionstatus = (String) ctx.session().getAttribute("actionstatus");
		personid =(String) ctx.session().getAttribute("personid");
		
		Object aobj[] = { key, riderPos, personid, dceRiders.getTEXT_REQUIRED()};
		
		if ((actionstatus.equalsIgnoreCase("edit")))
		{
			ctx.forwardByName("riderMaintWithCodeEdit", aobj);
		}
		
		if ((actionstatus.equalsIgnoreCase("create")))
		{
			ctx.forwardByName("riderMaintWithCodeCreate", aobj);
		}
		
		
		ctx.session().removeAttribute("riderPos");
		ctx.session().removeAttribute("actionstatus");
		ctx.session().removeAttribute("personid");
		
		form.clear();
	}	

	public void updateSearchCode_onClick(FormActionContext ctx) throws Exception
	{
		loadUpdateList(ctx);
	}

	private void loadUpdateList(FormActionContext ctx) 
	{
		String service = "Display: update list filter rider codes " + classAction;
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		RiderCodesForm form = (RiderCodesForm) ctx.form();
		
		String searchCode = null;
		
		searchCode = form.getSEARCH_CODE().toUpperCase();
		
		if (searchCode == null)
		{	
			searchCode="";
		}
			
		ctx.session().removeAttribute("riderCodeList");
		
		try {
			ACRIDBWResponseINOUT_PARM1Holder inoutparams = new ACRIDBWResponseINOUT_PARM1Holder();
			ACRIDBWPDA_FIELDS localParms = (ACRIDBWPDA_FIELDS) ctx.session().getAttribute("riderCodesParms");
			
			BrowseRiderCodesDisplatList dspData = WSRiderCodesCall.initFetch(searchCode, inoutparams, localParms);
			SimpleListControl riderCodeList = new SimpleListControl();
			riderCodeList.setDataModel(dspData);
			ctx.session().setAttribute("riderCodeList", riderCodeList);
			ctx.session().setAttribute("inoutparmsRiderCodesResponse", inoutparams);
			ctx.forwardToInput();
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
		}		
		
	}
}
