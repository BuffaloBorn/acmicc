/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.action;


import org.apache.log4j.Logger;
import java.io.IOException;
import javax.servlet.ServletException;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.presentation.dsp.PolicyBrowseCertPersonDsp;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.ListControl;
import com.cc.framework.ui.control.SimpleListControl;

import com.epm.acmi.datamodel.PolicyBrowseCertPersonDisplayList;

import com.epm.acmi.struts.Constants;
import com.epm.acmi.util.MiscellaneousUtils;
import com.isdiary.entirex.WSPolicyBrowseCertPerson;

/** 
 * MyEclipse Struts
 * Creation date: 04-01-2008
 * 
 * XDoclet definition:
 * @struts.action path="/browsePolicyCertPerson" name="browsePolicyCertPersonForm" input="/jsp/ias/BrowsePolicyCertPerson.jsp" scope="request" validate="true"
 * @struts.action-forward name="back" path="/iuauser/iasdiary.do" redirect="true"
 */
public class BrowsePolicyCertPersonAction extends CCAction {

	private static Logger log = MiscellaneousUtils.getIASLogger();
	String comingFrom = null;
	String eventid = null;
	String modify = null;
	
	public void doExecute(ActionContext ctx) throws IOException, ServletException  {
		
		log.debug("Begin execute doExecute");
		try {
			this.loadList(ctx);
		} catch (Throwable t) {
			log.error(t.getMessage(), t);
			
		}
		ctx.forwardToInput();
		log.debug("End execute doExecute");
	}
	
	
	/**
	 * Fill's the ListControl from the Database
	 * @param ctx	ActionContext
	 * @throws Exception
	 */
	private void loadList(ActionContext ctx) throws Exception {
		
		String PolicyNo = null;
		comingFrom = null;
		eventid = null;
		
		String method = "loadList";
		
		PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
		
		/*PolicyNo  = (String)ctx.request().getParameter("policyno");
		log.debug("requset policyno:" + PolicyNo);
		
		if (PolicyNo == null) 
		{
			PolicyNo  = (String)ctx.session().getAttribute(Constants.IASpolicyNumber);
			log.debug("session policyno:" + PolicyNo);
		}*/
		
		comingFrom  = (String)ctx.request().getParameter("comingFrom");
		eventid = (String)ctx.request().getParameter("eventid");
		modify = (String) ctx.session().getAttribute(Constants.IASModify);
		
		log.debug(method + "Coming From: " + comingFrom + " ->> Event Id: " + eventid + " ->> Modify: " + modify);
		
		if (comingFrom != null)
			ctx.session().setAttribute("policyPersonCodesComingForm", comingFrom);
		
		PolicyBrowseCertPersonDisplayList dspData = WSPolicyBrowseCertPerson.fetch(PolicyNo);
		
		SimpleListControl browsePersonList = new SimpleListControl();
		browsePersonList.setDataModel(dspData);
		ctx.session().setAttribute("browsePersons", browsePersonList);
		
		
		log.debug("placed browsePolicyCertPersons session");
		
	}	
	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		// in this example we go back to the jsp
		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		log.debug("Redirect back previous page");
		ctx.forwardByName(Forwards.BACK);
	}
	
	/**
	* This Method is called when the Drilldown-Column is clicked
	* In our Example we switch to the DetailView, which shows
	* more Information about the User. It's a readonly View.
	* @param ctx ControlActionContext
	* @param key UniqueKey, as it was defined in the UserDisplayList
	* to identify the Row. In this Example the UserId.
	*/
	public void browsePersons_onDrilldown(ControlActionContext ctx, String key) {
		
		//SimpleListControl browsePersonList = new SimpleListControl();
		ListControl browsePersonList = (ListControl)ctx.control();
		PolicyBrowseCertPersonDsp bpDisplay  = (PolicyBrowseCertPersonDsp)browsePersonList.getRowFromKey(key); 
		
		String person_seach_address = bpDisplay.getPERSON_SEARCH_NAME1();

		if (comingFrom != null)
		{
			
			if (comingFrom.equalsIgnoreCase("FT") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("freeTextCreate", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("freeTextEdit", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("freeTextEdit", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("ESM") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("eventStdMemoCreate", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("ESM") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("eventStdMemoEdit", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("ESM") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("eventStdMemoEdit", key, person_seach_address, eventid);
			}	
		
			if (comingFrom.equalsIgnoreCase("EP") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("eventPortamedicCreate", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("EP") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("eventPortamedicEdit", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("EP") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("eventPortamedicEdit", key, person_seach_address, eventid);
			}	
			
		
			if (comingFrom.equalsIgnoreCase("LM") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("letterCreate", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("LM") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("letterEdit", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("LM") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("letterEdit", key, person_seach_address, eventid);
			}	
			
			if (comingFrom.equalsIgnoreCase("SL") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("stdletterCreate", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("SL") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("stdLetterEdit", key, person_seach_address, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("SL") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("stdLetterEdit", key, person_seach_address, eventid);
			}	
			
		}
	}
	
	public void backEvent_onClick(FormActionContext ctx) throws Exception {
		
		//SimpleListControl browsePersonList = new SimpleListControl();
	
		String method = "Back Event";
		
		log.debug(method + "Coming From: " + comingFrom + "->> Modify: " + modify);
	
		if (comingFrom != null)
		{
			
			if (comingFrom.equalsIgnoreCase("FT") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("freeTextCreateBack");
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("freeTextEditBack");
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("freeTextEditBack");
			}
			
			if (comingFrom.equalsIgnoreCase("ESM") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("eventStdMemoCreateBack");
			}
			
			if (comingFrom.equalsIgnoreCase("ESM") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("eventStdMemoEditBack");
			}
			
			if (comingFrom.equalsIgnoreCase("ESM") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("eventStdMemoEditBack");
			}	
		
			if (comingFrom.equalsIgnoreCase("EP") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("eventPortamedicCreateBack");
			}
			
			if (comingFrom.equalsIgnoreCase("EP") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("eventPortamedicEditBack");
			}
			
			if (comingFrom.equalsIgnoreCase("EP") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("eventPortamedicEditBack");
			}	
			
		
			if (comingFrom.equalsIgnoreCase("LM") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("letterCreateBack");
			}
			
			if (comingFrom.equalsIgnoreCase("LM") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("letterEditBack");
			}
			
			if (comingFrom.equalsIgnoreCase("LM") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("letterEditBack");
			}	
			
			if (comingFrom.equalsIgnoreCase("SL") && modify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("stdletterCreateBack");
			}
			
			if (comingFrom.equalsIgnoreCase("SL") && modify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("stdLetterEditBack");
			}
			
			if (comingFrom.equalsIgnoreCase("SL") && modify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("stdLetterEditBack");
			}	
			
		}
	
	}
	
}