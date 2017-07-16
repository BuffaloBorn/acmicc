/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.action;

import java.io.IOException;
import java.rmi.RemoteException;

import javax.servlet.ServletException;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.DiaryMessages;
import com.cc.acmi.common.Forwards;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.datamodel.PolicyBrowseDisplayList;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.BrowsePoliciesForm;
import com.epm.acmi.util.MiscellaneousUtils;
import com.isdiary.entirex.WSPolicyBrowseCall;
import com.softwarag.extirex.webservice.browsepolicies.client.holders.MUPLYBWResponseINOUT_PARM1Holder;

public class BrowsePoliciesAction extends CCAction{

	private static Logger log = MiscellaneousUtils.getIASLogger();
	private static String classAction = "Browse Policies Data";
	String comingFrom = null;
	String service = "Display: " + classAction;
	
	public void doExecute(ActionContext ctx) throws IOException, ServletException 
	{
		log.debug("Begin execute doExecute");
		this.loadList(ctx);
		log.debug("End execute doExecute");
	}
	
	public void updateSearchCode_onClick(FormActionContext ctx) throws Exception
	{
		log.debug("Begin execute Search");
		loadUpdateList(ctx);
		log.debug("End execute Search");
	}
	
	
	private void loadUpdateList(FormActionContext ctx) 
	{
		BrowsePoliciesForm form = (BrowsePoliciesForm) ctx.form();
			
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUPLYBWResponseINOUT_PARM1Holder inoutparms = new MUPLYBWResponseINOUT_PARM1Holder();
		
		PolicyBrowseDisplayList dspData;
		try {
			dspData = WSPolicyBrowseCall.initFetch(form.getKEY_INSURED_SEARCH_NAME().toUpperCase(),inoutparms);
			SimpleListControl browseList = new SimpleListControl();
			browseList.setDataModel(dspData);
			ctx.session().setAttribute("browsePolicies", browseList);
			ctx.session().setAttribute("inoutparmsresponse", inoutparms);
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

	private void loadList(ActionContext ctx)  {
		
		String keyInsured = null;	
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		MUPLYBWResponseINOUT_PARM1Holder inoutparms = new MUPLYBWResponseINOUT_PARM1Holder();
		
		comingFrom = null;
		
		keyInsured  = (String)ctx.request().getParameter("keyInsured");
		
		comingFrom  = (String)ctx.request().getParameter("comingFrom");
		
		PolicyBrowseDisplayList dspData;
		try {
			dspData = WSPolicyBrowseCall.initFetch(keyInsured,inoutparms);
			SimpleListControl browseList = new SimpleListControl();
			browseList.setDataModel(dspData);
			ctx.session().setAttribute("browsePolicies", browseList);
			ctx.session().setAttribute("inoutparmsresponse", inoutparms);
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
		BrowsePoliciesForm form = (BrowsePoliciesForm) ctx.form();
		
		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		form.clear();
		log.debug("Redirect back previous page");
		ctx.forwardByName(Forwards.BACK);
		//ctx.forwardToAction("main/docexep?ctrl=nestedtabset&action=TabClick&param=tab12");
	}
	
	public void backward_onClick (FormActionContext ctx) throws Exception{
		
		String KeyInsured = null; 
		String PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		BrowsePoliciesForm form = (BrowsePoliciesForm) ctx.form();
		MUPLYBWResponseINOUT_PARM1Holder backwardFetch = (MUPLYBWResponseINOUT_PARM1Holder) ctx.session().getAttribute("inoutparmsresponse");
		MUPLYBWResponseINOUT_PARM1Holder inoutparms = new MUPLYBWResponseINOUT_PARM1Holder();
		
		KeyInsured = backwardFetch.value.getSTART_KEY1();
	
		log.debug("Key Insured: " +  KeyInsured);
		
		
		ctx.session().removeAttribute("browsePolicies");
		ctx.session().removeAttribute("inoutparmsresponse");		
		try {
		PolicyBrowseDisplayList dspData = WSPolicyBrowseCall.backwardFetch(backwardFetch, inoutparms);
		SimpleListControl browseList = new SimpleListControl();
		browseList.setDataModel(dspData);
		
		ctx.session().setAttribute("browsePolicies", browseList);
		ctx.session().setAttribute("inoutparmsresponse", inoutparms);
		
		log.debug("Redirect backward from the current page");
		form.clear();
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
	
	public void forward_onClick (FormActionContext ctx) throws Exception{
		
		String PolicyNo = null; 
		
		String KeyInsured = null; 
		BrowsePoliciesForm form = (BrowsePoliciesForm) ctx.form();
		
		MUPLYBWResponseINOUT_PARM1Holder forwardFetch = (MUPLYBWResponseINOUT_PARM1Holder) ctx.session().getAttribute("inoutparmsresponse");
		MUPLYBWResponseINOUT_PARM1Holder inoutparms = new MUPLYBWResponseINOUT_PARM1Holder();
		
		log.debug("Key Insured: " +  KeyInsured);
		
		ctx.session().removeAttribute("browsePolicies");
		ctx.session().removeAttribute("inoutparmsresponse");		
		try {
		PolicyBrowseDisplayList dspData = WSPolicyBrowseCall.forwardFetch(forwardFetch, inoutparms);
		SimpleListControl browseList = new SimpleListControl();
		browseList.setDataModel(dspData);
		
		ctx.session().setAttribute("browsePolicies", browseList);
		ctx.session().setAttribute("inoutparmsresponse", inoutparms);
		form.clear();
		log.debug("Redirect forward from the current page");
		ctx.forwardToInput();
	} catch (RemoteException e) {
		log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service +  " and Policy Number " + PolicyNo,e);
		ctx.addGlobalError(DiaryMessages.REMOTE_EXCEPTION, service + " WS",PolicyNo);
		ctx.forwardToInput();
		return;
		
	} catch (ServiceException e) {
		log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service + " and Policy Number " + PolicyNo,e);
		ctx.addGlobalError(DiaryMessages.SERCIVE_EXCEPTION,service + " WS",PolicyNo);
		ctx.forwardToInput();
		return;
	}
	}
	
	public void browsePolicies_onDrilldown(ControlActionContext ctx, String key) {
		
		
		log.debug("Selected from key:" + key + " from browse list");
		
		BrowsePoliciesForm form = (BrowsePoliciesForm) ctx.form();
		
		if (comingFrom != null)
		{
			if (comingFrom.equalsIgnoreCase("PD"))
			{
				//ctx.session().setAttribute(Constants.IASpolicyNumber, key);
				
			
				String PolicyNo  = (String)ctx.session().getAttribute(Constants.policyNumber);
				
				if (PolicyNo.equalsIgnoreCase(key))
				{
					ctx.session().setAttribute(Constants.IASpolicyNumber, PolicyNo);
					ctx.session().setAttribute(Constants.IASModify, "edit");
					log.debug("Set " + Constants.IASModify + " to edit");
				}
				else
				{	
					ctx.session().setAttribute(Constants.IASpolicyNumber, key);
					ctx.session().setAttribute(Constants.IASModify, "display");
					log.debug("Set " + Constants.IASModify + " to display");
				}	
				form.clear();
				ctx.forwardByName("back");
			}
		
		}
	
	}
	
	
	
}