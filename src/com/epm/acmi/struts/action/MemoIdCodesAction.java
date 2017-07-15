package com.epm.acmi.struts.action;

import org.apache.log4j.Logger;

import com.cc.acmi.common.Forwards;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.datamodel.MemoCodesDisplayList;
import com.epm.acmi.bean.Filter;
import com.epm.acmi.bean.MemoCodes;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.MemoIdCodesForm;

public class MemoIdCodesAction extends CCAction {

	private static Logger log = Logger.getLogger(MemoIdCodesAction.class);
	
	String comingFrom = null;
	String eventid = null;
	
	public void doExecute(ActionContext ctx) throws Exception {
		log.debug("Begin execute doExecute");
		displayMemoIdCodes(ctx);
		log.debug("End execute doExecute");
	}

	

	private void  displayMemoIdCodes(ActionContext ctx) 
	{
		
		comingFrom = null;
		eventid = null;
		
		comingFrom  = (String)ctx.request().getParameter("comingFrom");
		eventid = (String)ctx.request().getParameter("eventid");
		
		MemoIdCodesForm form = (MemoIdCodesForm) ctx.form();
		
		MemoCodesDisplayList dspData = MemoCodes.fetch();
		SimpleListControl memoIdCodesList = new SimpleListControl();
		memoIdCodesList.setDataModel(dspData);
		ctx.session().setAttribute("memoIdCodesBrowse", memoIdCodesList);
		form.setSEARCH_CODE("");
		
		if (comingFrom != null)
			ctx.session().setAttribute("memoIdCodesComingForm", comingFrom);
		
		ctx.forwardToInput();
	}
	
	/**
	 * This method is called if the back button is clicked
	 * @param ctx The FormActionContext
	 */
	public void back_onClick(FormActionContext ctx) throws Exception {
		
		MemoIdCodesForm form = (MemoIdCodesForm) ctx.form();
		
		form.setSEARCH_CODE("");
		
		String IASDiaryModify = (String)ctx.session().getAttribute(Constants.IASDiaryModify);
		ctx.session().setAttribute(Constants.IASModify,IASDiaryModify);
		
		
		ctx.forwardByName(Forwards.BACK);
		
		
	}
	
	public void backEvent_onClick(FormActionContext ctx) throws Exception
	{
		
		MemoIdCodesForm form = (MemoIdCodesForm) ctx.form();
		
		String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
	
		if (comingFrom != null)
		{
			
			if (comingFrom.equalsIgnoreCase("FT") && IASModify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("freeTextCreateBack");
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && IASModify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("freeTextEditBack");
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && IASModify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("freeTextEditBack");
			}
			
			if(comingFrom.equalsIgnoreCase("SL")&& IASModify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("stdletterEditBack");
			}
			
			if(comingFrom.equalsIgnoreCase("SL")&& IASModify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("stdletterEditBack");
			}
			
			if(comingFrom.equalsIgnoreCase("SL")&& IASModify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("stdletterCreateBack");
			}
		}
		
		form.setSEARCH_CODE("");
	}
	
	public void memoIdCodesBrowse_onDrilldown(ControlActionContext ctx, String key)
	{
		
		MemoIdCodesForm form = (MemoIdCodesForm) ctx.form();
		
		String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
		
		if (comingFrom != null)
		{
			
			if (comingFrom.equalsIgnoreCase("FT") && IASModify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("freeTextCreate", key, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && IASModify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("freeTextEdit", key, eventid);
			}
			
			if (comingFrom.equalsIgnoreCase("FT") && IASModify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("freeTextEdit", key, eventid);
			}
			
			if(comingFrom.equalsIgnoreCase("SL")&& IASModify.equalsIgnoreCase("editWithStatus"))
			{
				ctx.forwardByName("stdletterEdit", key, eventid);
			}
			
			if(comingFrom.equalsIgnoreCase("SL")&& IASModify.equalsIgnoreCase("edit"))
			{
				ctx.forwardByName("stdletterEdit", key, eventid);
			}
			
			if(comingFrom.equalsIgnoreCase("SL")&& IASModify.equalsIgnoreCase("create"))
			{
				ctx.forwardByName("stdletterCreate", key);
			}
		}
		
		form.setSEARCH_CODE("");
		
	}

	public void updateSearchCode_onClick(FormActionContext ctx) throws Exception
	{
		log.debug("Begin execute Search");
		loadUpdateList(ctx);
		log.debug("End execute Search");
	}
	

	private void loadUpdateList(FormActionContext ctx) 
	{
	
		MemoIdCodesForm form = (MemoIdCodesForm) ctx.form();
		
		Filter filter = new Filter();
		filter.setAttribute("MEMO_ID", form.getSEARCH_CODE().toUpperCase());
		
		
		MemoCodesDisplayList dspData = MemoCodes.fetch(filter);
		SimpleListControl memoIdCodesList = new SimpleListControl();
		memoIdCodesList.setDataModel(dspData);
		ctx.session().setAttribute("memoIdCodesBrowse", memoIdCodesList);
		
	
		ctx.forwardToInput();
		
		
	}

}
