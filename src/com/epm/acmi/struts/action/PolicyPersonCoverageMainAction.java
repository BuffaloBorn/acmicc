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
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.datamodel.PolicyPersonCovMainDisplayList;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.PolicyPersonCoverageMainForm;
import com.epm.acmi.util.MiscellaneousUtils;
import com.isdiary.entirex.WSPolicyPersonCovMainListCall;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.IAPPCBWINOUT_PARM;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.holders.IAPPCBWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.holders.IAPPCBWResponseMESSAGE_INFOHolder;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.holders.IAPPCBWResponseOUT_PARMHolder;

/** 
 * MyEclipse Struts
 * Creation date: 04-03-2008
 * 
 * XDoclet definition:
 * @struts.action path="/policyPersonCoverageMain" name="policyPersonCoverageMainForm" input="/jsp/ias/policyPersonCoverageMainMain.jsp" scope="request" validate="true"
 * @struts.action-forward name="back" path="/iuauser/iasdiary.do" redirect="true"
 */
public class PolicyPersonCoverageMainAction extends CCAction 
{

	private static Logger log = MiscellaneousUtils.getIASLogger();
	private static String classAction = "Policy Person Coverage Data";
	
	public void doExecute(ActionContext ctx) throws Exception 
	{
		log.debug("Begin execute doExecute");
		this.loadList(ctx);
		log.debug("End execute doExecute");	
	}
	
	private void loadList(ActionContext ctx) 
	{
		String service = "Display: " + classAction;
		String personid  = (String)ctx.request().getParameter("personid");
		String user  = (String)ctx.session().getAttribute(Constants.IASuser);
		String PolicyNo = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		log.debug("Policy Number from IASpolicyNumber:" + PolicyNo);
		
		if(personid==null)
		{
			ctx.forwardToInput();
			return;
		}
		
		PolicyPersonCovMainDisplayList dspDisplay = new PolicyPersonCovMainDisplayList();
		
		IAPPCBWResponseINOUT_PARM1Holder inoutparms = new IAPPCBWResponseINOUT_PARM1Holder();
		IAPPCBWResponseOUT_PARMHolder outparms = new IAPPCBWResponseOUT_PARMHolder();
		IAPPCBWResponseMESSAGE_INFOHolder msgInfo =  new IAPPCBWResponseMESSAGE_INFOHolder();
		
		try {
			WSPolicyPersonCovMainListCall.fetch(dspDisplay, PolicyNo, user, personid,msgInfo, inoutparms, outparms);
			SimpleListControl policyCoverageList = new SimpleListControl();
			policyCoverageList.setDataModel(dspDisplay);
			ctx.session().setAttribute("coverages", policyCoverageList);
			loadForm(ctx,inoutparms, outparms  );
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

	private void loadForm(ActionContext ctx,IAPPCBWResponseINOUT_PARM1Holder inoutparms,IAPPCBWResponseOUT_PARMHolder outparms) 
	{
		PolicyPersonCoverageMainForm form = (PolicyPersonCoverageMainForm) ctx.form();
		
		form.setDisplay_date(TextProcessing.dateFormat(inoutparms.value.getDISPLAY_DATE1()));
		form.setMode(outparms.value.getMODE());
		form.setName(outparms.value.getNAME());
		form.setPerson_status(outparms.value.getPOLICY_PERSON_STATUS());
		form.setPersonid(inoutparms.value.getPERSON_ID1().toString());
		form.setPolicystatus(outparms.value.getPOLICY_STATUS());
		form.setPerson_effective_date(TextProcessing.plainDateFormat(outparms.value.getPERSON_STATUS_EFF_DATE()));
		form.setPolicy_effective_date(TextProcessing.plainDateFormat(outparms.value.getPOLICY_STATUS_EFF_DATE()));
		form.setPolicyid(inoutparms.value.getPOLICY_ID1().toString());
	}

	public void coverages_onEditstdcoverage(ControlActionContext ctx, String key)
	{
		PolicyPersonCoverageMainForm form = (PolicyPersonCoverageMainForm) ctx.form();

		String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
		
		if(IASModify.equalsIgnoreCase("display"))
			ctx.forwardByName("subStdCoverageMainDisplay", form.getPersonid(), key, TextProcessing.YYYYMMDDFormat(form.getDisplay_date()));
		else
			ctx.forwardByName("subStdCoverageMainEdit", form.getPersonid(), key, TextProcessing.YYYYMMDDFormat(form.getDisplay_date()));
	}
	
	public void back_onClick(FormActionContext ctx) throws Exception 
	{
		ctx.forwardByName(Forwards.BACK);	
	}
	
	public void updateDisplayDate_onClick(FormActionContext ctx) throws Exception
	{
		PolicyPersonCoverageMainForm form = (PolicyPersonCoverageMainForm) ctx.form();
		
		form.validateForm(ctx);
		
		if (ctx.hasErrors())
		{
			ctx.forwardToInput();
			 return;
		}
		updateDisplayDate(ctx);
		
	}
	
	private void updateDisplayDate(FormActionContext ctx)  throws Exception
	{
		log.debug("Starting....Display Date " + classAction);
		//form.setDisplay_date(form.getDisplay_date());
		//ctx.addGlobalMessage(DiaryMessages.UI_EDIT_MSG,"Display Date Status Updated");
		updateDisplayDateLoadList(ctx);
		log.debug("Finish....Updating Underwriter Status " + classAction);
	}
	
	private void updateDisplayDateLoadList(FormActionContext ctx) 
	{
		String service = "Update Display Date: " + classAction;
		String PolicyNo = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		log.debug("Policy Number from IASpolicyNumber:" + PolicyNo);
		
	
		PolicyPersonCovMainDisplayList dspDisplay = new PolicyPersonCovMainDisplayList();
		
		IAPPCBWINOUT_PARM inputs = new IAPPCBWINOUT_PARM();
		
		retrieveForm(inputs, ctx);
		
		IAPPCBWResponseINOUT_PARM1Holder inoutparms = new IAPPCBWResponseINOUT_PARM1Holder();
		IAPPCBWResponseOUT_PARMHolder outparms = new IAPPCBWResponseOUT_PARMHolder();
		IAPPCBWResponseMESSAGE_INFOHolder msgInfo =  new IAPPCBWResponseMESSAGE_INFOHolder();
		
		try {
			WSPolicyPersonCovMainListCall.updateDisplayDate(dspDisplay, inputs ,msgInfo, inoutparms, outparms);
			SimpleListControl policyCoverageList = new SimpleListControl();
			policyCoverageList.setDataModel(dspDisplay);
			ctx.session().setAttribute("coverages", policyCoverageList);
			loadForm(ctx,inoutparms, outparms  );
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
	
	
	private void retrieveForm(IAPPCBWINOUT_PARM inputs, FormActionContext ctx)
	{
		PolicyPersonCoverageMainForm form = (PolicyPersonCoverageMainForm) ctx.form();
		
		inputs.setPOLICY_ID(new BigDecimal(form.getPolicyid()));
		inputs.setPERSON_ID(new BigDecimal(form.getPersonid()));
		inputs.setDISPLAY_DATE(TextProcessing.YYYYMMDDFormat(form.getDisplay_date()));
		
		inputs.setMORE_DATA(new String(""));
		inputs.setSTART_COVERAGE_CODE(new String(""));
		
		inputs.setANNUAL_PERM_EXTRA_PREM_AMT(new BigDecimal[20]);
		inputs.setANNUAL_PREM_AMT( new BigDecimal[20]);
		inputs.setCOVERAGE_CODE(new String[20]);
		inputs.setSUB_STANDARD_RISK_CODE(new String[20]);
		
		inputs.setSTART_DATE(new String[20]);
		inputs.setEND_DATE(new String[20]);
		
		
	}
}