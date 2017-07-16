/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.action;


import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.presentation.dsp.ConditionCodeDsp;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.bean.ConditionCode;
import com.epm.acmi.datamodel.ConditionCodesDisplayList;
import com.epm.acmi.struts.Constants;

import com.epm.acmi.struts.form.PolicyConditionCodesMainForm;
import com.epm.acmi.util.ACMICache;
import com.isdiary.entirex.WSPolicyPersonMaint1Call;
import com.softwarag.extirex.webservice.policypersonmaint1.client.ACCPEMWINOUT_PARM;
import com.softwarag.extirex.webservice.policypersonmaint1.client.holders.ACCPEMWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policypersonmaint1.client.holders.ACCPEMWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.policypersonmaint1.client.holders.ACCPEMWResponseOUT_PARMHolder;

/** 
 * MyEclipse Struts
 * Creation date: 04-08-2008
 * 
 * XDoclet definition:
 * @struts.action path="/iuauser/subStdCoverageMain" name="subStdCoverageMainForm" input="/jsp/ias/SubStdCoverageMainMin.jsp" scope="request" validate="true"
 * @struts.action-forward name="back" path="/iuauser/iasdiary.do" redirect="true"
 */
public class PolicyConditionCodesMainAction extends CCAction {

	private static Logger log = Logger.getLogger( PolicyConditionCodesMainAction.class);
	private static String classAction = "Policy Condition Codes Maintance Data";
	String comingFrom = null;
	String fillform =null;
	String proformAction = null;
	
	public void doExecute(ActionContext ctx) throws Exception 
	{
		fillform  = (String)ctx.request().getParameter("fillform");
			
		if(fillform != null)
		{
			log.debug("Begin execute doExecute");
			this.DisplayConditionCodes(ctx);
			log.debug("End execute doExecute");
		}
		else
		{
			log.debug("Begin execute doExecute: Get Selected Option and Load Text Data");
			this.loadSelectText(ctx);
			log.debug("End execute doExecute: Get Selected Option and Load Text Data");
		}
			
	}
	
	private void DisplayConditionCodes(ActionContext ctx) 
	{
		String service = "Display: " + classAction + " PolicyPersonMaint1";
		comingFrom  = null;
		log.debug("Display " + classAction + " PolicyPersonMaint1" );
		
	
		ACCPEMWResponseINOUT_PARM1Holder inoutparms = new ACCPEMWResponseINOUT_PARM1Holder();
		ACCPEMWResponseOUT_PARMHolder outparms = new ACCPEMWResponseOUT_PARMHolder();
		ACCPEMWResponseMSG_INFOHolder msgInfo = new ACCPEMWResponseMSG_INFOHolder();
		
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
		String person_id= (String)ctx.request().getParameter("personid");		
		String PolicyNo= (String)ctx.request().getParameter("policyid");
		comingFrom  = (String)ctx.request().getParameter("comingFrom");
		proformAction = (String)ctx.request().getParameter("proformAction");
		
		try {
			
			WSPolicyPersonMaint1Call.fecth(person_id,  PolicyNo, user, inoutparms, msgInfo, outparms);
			fillForm(ctx, inoutparms,  outparms);
			ctx.forwardToInput();
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
			ctx.forwardByName(Forwards.BACK);
			return;
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo);
			ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
			ctx.forwardByName(Forwards.BACK);
			return;
		}
		
		
		
	}
	
	private void loadSelectText(ActionContext ctx) 
	{
		SimpleListControl conditionCodeList = (SimpleListControl) ctx.session().getAttribute("conditionCodeList");
		ConditionCodesDisplayList ConditionDspData = (ConditionCodesDisplayList)conditionCodeList.getDataModel();
		ConditionCodeDsp[] conditionCodeData = ConditionDspData.getData();
		  
		PolicyConditionCodesMainForm  form = (PolicyConditionCodesMainForm) ctx.form();
		
		int cc = ConditionDspData.getIndex(form.getCurrentPosition().toString());
		
		ConditionCodeDsp item = (ConditionCodeDsp) ACMICache.getConditionCodesExtendedFields().get(conditionCodeData[cc].getCODE().toString());
		
		if(item != null)
		{
			conditionCodeData[cc].setDESCRIPTION_REQUIRED_IND(item.getDESCRIPTION_REQUIRED_IND());
			if (item.getDESCRIPTION_REQUIRED_IND().equalsIgnoreCase("y"))
				conditionCodeData[cc].setDESCRIPTION("");
			else
				conditionCodeData[cc].setDESCRIPTION( item.getDESCRIPTION());
		}
		else
		{
			conditionCodeData[cc].setDESCRIPTION_REQUIRED_IND("");
			conditionCodeData[cc].setDESCRIPTION("");
		}
		
		
		ConditionDspData.setData(conditionCodeData);
		conditionCodeList.setDataModel(ConditionDspData);
		ctx.session().setAttribute("conditionCodeList", conditionCodeList);
		ctx.forwardToInput();
	}


	private void fillForm(ActionContext ctx,	ACCPEMWResponseINOUT_PARM1Holder inoutparms, ACCPEMWResponseOUT_PARMHolder outparms) 
	{
		PolicyConditionCodesMainForm  form = (PolicyConditionCodesMainForm) ctx.form();
		
		
		form.setPOLICY_ID(inoutparms.value.getPOLICY_ID1().toString());
		
		form.setPERSON_ID(inoutparms.value.getPERSON_ID1().toString());
		
		form.setLog_counter(inoutparms.value.getLOG_COUNTER1().toString());
		
		form.setDISPLAY_DATE(TextProcessing.dateFormat(inoutparms.value.getDISPLAY_DATE1().toString()));
	
		form.setPERSON_STATUS_IND("D");
		
		String[] CONDITION_CODE = inoutparms.value.getCONDITION_CODE1();
		String[] CONDITION_TEXT = inoutparms.value.getCONDITION_TEXT1();

		ConditionCodesDisplayList ConditionDspData = ConditionCode.loadList(CONDITION_CODE, CONDITION_TEXT);
		
		SimpleListControl conditionCodeList = new SimpleListControl();
		
		conditionCodeList.setDataModel(ConditionDspData);
		ctx.session().setAttribute("conditionCodeList", conditionCodeList);
		
		form.setPOLICY_STATUS(outparms.value.getPOLICY_STATUS());
		
		form.setSTATUS_EFF_DATE(TextProcessing.dateFormat(outparms.value.getSTATUS_EFF_DATE()));
		
		form.setPERSON_NAME(outparms.value.getPERSON_NAME());
		
		form.setNEXT_DUE_DATE(TextProcessing.dateFormat(outparms.value.getNEXT_DUE_DATE()));
	
		form.setPERSON_STATUS_EFF_DATE(TextProcessing.dateFormat(outparms.value.getPERSON_STATUS_EFF_DATE()));
	
		form.setREQ_EFF_DATE(TextProcessing.dateFormat(outparms.value.getREQ_EFF_DATE()));
		
		
		form.setCCODE_START_DATE(TextProcessing.dateFormat(outparms.value.getCCODE_START_DATE()));
		
		form.setCCODE_TERM_DATE(TextProcessing.dateFormat(outparms.value.getCCODE_TERM_DATE()));
	
		form.setCCODE_TIME_PERIOD(outparms.value.getCCODE_TIME_PERIOD());
		
		form.setSTATE_ISSUED(outparms.value.getSTATE_ISSUED());

	}

	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void edit_onClick(FormActionContext ctx) throws Exception {
		
		PolicyConditionCodesMainForm  form = (PolicyConditionCodesMainForm) ctx.form();
		
		form.validateForm(ctx);
	
		if (ctx.hasErrors())
		{
			ctx.forwardToInput();
			 return;
		}
		
		editPolicyConditionCodeMaint(ctx);

	}
	
	
	private void editPolicyConditionCodeMaint(FormActionContext ctx) 
	{
		String service = "Edit: " + classAction + " PolicyPersonMaint1";
		
		log.debug("Edit " + classAction + " PolicyPersonMaint1" );
		
		PolicyConditionCodesMainForm  form = (PolicyConditionCodesMainForm) ctx.form();
		
		ACCPEMWResponseINOUT_PARM1Holder inoutparms = new ACCPEMWResponseINOUT_PARM1Holder();
		ACCPEMWResponseOUT_PARMHolder outparms = new ACCPEMWResponseOUT_PARMHolder();
		ACCPEMWResponseMSG_INFOHolder msgInfo = new ACCPEMWResponseMSG_INFOHolder();
		
		
		ACCPEMWINOUT_PARM inputs = new ACCPEMWINOUT_PARM();
		
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
		
		String PolicyNo = null;
		
		retrieveForm( inputs, PolicyNo ,  ctx);
		
		
		try {
			
			WSPolicyPersonMaint1Call.update(user, inputs, inoutparms,msgInfo, outparms, proformAction);
	
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
			log.debug("Mainframe Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.addGlobalError(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.forwardToInput();
			log.debug("Error occurred " + classAction);
		}
		else
		{
			log.debug("Message: " + TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.addGlobalMessage(DiaryMessages.NATUAL_BUS_MSG, TextProcessing.formatMainFrameMessage(msgInfo.value.getMESSAGE_TEXT()));
			ctx.forwardByName("backPolicyPersonMain");
			form.clear();	
			log.debug("Finish....Adding " + classAction);
		}
		
		
	}

	private void retrieveForm(ACCPEMWINOUT_PARM inputs, String policyNo,	FormActionContext ctx) 
	{
		PolicyConditionCodesMainForm  form = (PolicyConditionCodesMainForm) ctx.form();
		
		String[] CONDITION_CODE = new String[4];
		String[] CONDITION_TEXT = new String[4];
		
		
		policyNo = form.getPOLICY_ID();
		inputs.setPOLICY_ID(new BigDecimal(form.getPOLICY_ID()));
		inputs.setPERSON_STATUS_IND(form.getPERSON_STATUS_IND());
		inputs.setDISPLAY_DATE(new BigDecimal(TextProcessing.YYYYMMDDFormat(form.getDISPLAY_DATE())));
		inputs.setPERSON_ID(new BigDecimal(form.getPERSON_ID()));
		
		SimpleListControl conditionCodeList = (SimpleListControl) ctx.session().getAttribute("conditionCodeList");
		ConditionCodesDisplayList ConditionDspData = (ConditionCodesDisplayList)conditionCodeList.getDataModel();
		ConditionCodeDsp[] conditionCodeData = ConditionDspData.getData();
		  
		CONDITION_CODE[0] = conditionCodeData[0].getCODE();
		CONDITION_CODE[1] = conditionCodeData[1].getCODE();
		CONDITION_CODE[2] = conditionCodeData[2].getCODE();
		CONDITION_CODE[3] = conditionCodeData[3].getCODE();
		
		inputs.setCONDITION_CODE(CONDITION_CODE);
		
		CONDITION_TEXT[0] = conditionCodeData[0].getDESCRIPTION();
		CONDITION_TEXT[1] = conditionCodeData[1].getDESCRIPTION();
		CONDITION_TEXT[2] = conditionCodeData[2].getDESCRIPTION();
		CONDITION_TEXT[3] = conditionCodeData[3].getDESCRIPTION();
		
		inputs.setCONDITION_TEXT(CONDITION_TEXT);
		inputs.setLOG_COUNTER(new BigDecimal(form.getLog_counter()));
		
	}
	

	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		if (comingFrom != null)
		{
			if (comingFrom.equalsIgnoreCase("PD"))
			{
				ctx.forwardByName("backPolicyDiary");
			}
			
			if (comingFrom.equalsIgnoreCase("PP"))
			{
				ctx.forwardByName("backPolicyPersonMain");
			}
		}
	}
}