package com.epm.acmi.struts.action;

import java.util.ArrayList;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.FormControl;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.control.TabsetControl;
import com.epm.acmi.bd.IUAProcessBD;
import com.epm.acmi.struts.form.FormLoaderHelper;
import com.epm.acmi.struts.form.dsp.*;
import com.epm.acmi.util.MedRecDBHelper;
import com.epm.acmi.util.OnTabUpdates;
import com.epm.acmi.util.PrevPolicyDBHelper;

public class NBWSAction extends MainTabPageBaseAction {

	/**
	 * Consructor
	 */
	public NBWSAction() {
		super();
	}

	/**
	 * @see com.cc.sampleapp.tabset.sample401.action.TabPageBaseAction#getTabPageId()
	 */
	/**
	 * Return tab page Id
	 */
	public String getTabPageId() {
		return TABPAGE1;
	}

	// ----------------------------------------------
	// TabSet-Callback Methods
	// ----------------------------------------------

	/**
	 * This Method is called, when the Tabpage is clicked
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 */
	public void maintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {

		log.debug(" inside NBWSAction - method maintabset_onTabClick... ");

		TabsetControl nbwstabset = (TabsetControl) ctx.session().getAttribute("nbwstabset");
		nbwstabset.setSelectedTab(TABPAGE111);
		ctx.session().setAttribute("nbwstabset", nbwstabset);
		
		// do this by default - an overhead but maybe tab111 is not selected
		// explicitly - need to load its stuff
		FormLoaderHelper formLoader = new FormLoaderHelper();
		formLoader.loadNBWSDetailsForm(ctx);
		FormControl control = new FormControl();
		control.setDesignModel(NBWSDetailsAction.getDesignModel(ctx.request()));
		ctx.session().setAttribute("ActivityActions", control);

		ctx.control().execute(ctx, seltab);

	}

	/**
	 * This Method is called, when the Tabpage is clicked
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 */
	public void nestedtabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {

		log.debug(" inside NBWSAction - method nestedtabset_onTabClick ending... ");

		TabsetControl nestedtabset = (TabsetControl) ctx.session().getAttribute("nestedtabset");
		nestedtabset.setSelectedTab(TABPAGE11);
		ctx.session().setAttribute("nestedtabset", nestedtabset);

		TabsetControl nbwstabset = (TabsetControl) ctx.session().getAttribute("nbwstabset");
		nbwstabset.setSelectedTab(TABPAGE111);
		ctx.session().setAttribute("nbwstabset", nbwstabset);

		// do this by default - an overhead but maybe tab111 is not selected
		// explicitly - need to load its stuff
		FormLoaderHelper formLoader = new FormLoaderHelper();
		formLoader.loadNBWSDetailsForm(ctx);
		FormControl control = new FormControl();
		control.setDesignModel(NBWSDetailsAction.getDesignModel(ctx.request()));
		ctx.session().setAttribute("ActivityActions", control);
		ctx.control().execute(ctx, seltab);
	}

	/**
	 * This Method is called, when the Tabpage is clicked
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 */
	public void nbwstabset_onTabClick(ControlActionContext ctx, String seltab) {

		try {

			log.debug(" inside NBWSAction - method nbwstabset_onTabClick started... ");
		    TabsetControl nbwstabset = (TabsetControl) ctx.session().getAttribute("nbwstabset");
		
		    nbwstabset.setSelectedTab(TABPAGE111);
		    FormLoaderHelper formLoader = new FormLoaderHelper();
			
			if (seltab.equalsIgnoreCase("tab111")) {
				log.debug("worklist selected");
				// do this by default - an overhead but maybe tab111 is not
				// selected explicitly - need to load its stuff
				OnTabUpdates.TelephoneInterviewStatus(ctx);
				formLoader.loadNBWSDetailsForm(ctx);
				FormControl control = new FormControl();
				control.setDesignModel(NBWSDetailsAction.getDesignModel(ctx.request()));
				ctx.session().setAttribute("ActivityActions", control);
				log.debug("worklist selected");

			}

			if (seltab.equalsIgnoreCase("tab112")) {
				log.debug("med recs selected");
				
				// Get data from DB for List control
				String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
				MedRecDBHelper dbHelper = new MedRecDBHelper();
				MedRecList medRecList = dbHelper.getListFromDB(gfid);
				

				log.debug("size of med recs list data item " + medRecList.size());

				// Create the ListControl and populate it.
				// with the Data to be displayed
				SimpleListControl medRecDisp = new SimpleListControl();
				medRecDisp.setDataModel(medRecList);
				
				// Put the ListControl into the Session-Object.
				// Our ListControl is a statefull Object.
				ctx.session().setAttribute("medRecs", medRecDisp);
				
				/*
				 *  Added for USR 8399-1 changes
				 *  on 02-12-2008 by Nagrathna Hiriyurkar  
				 */
				
				ArrayList tabDataList = new ArrayList();
				tabDataList.add(new Integer(medRecList.size()).toString());
				ctx.session().setAttribute("medRecsDataStatus", tabDataList);
		
				// End of USR -8399-1 changes.  
				
			}
			if (seltab.equalsIgnoreCase("tab113")) {

				log.debug("prev policies selected");

				// Get data from DB for List control
				String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
				PrevPolicyDBHelper ppDBHelp = new PrevPolicyDBHelper();
				PrevPolicyList prevPolList = ppDBHelp.getListFromDB(gfid);

				log.debug("size of prev policy list data item " + prevPolList.size());

				// Create the ListControl and populate it.
				// with the Data to be displayed
				SimpleListControl prevPolDisp = new SimpleListControl();
				prevPolDisp.setDataModel(prevPolList);

				// Put the ListControl into the Session-Object.
				// Our ListControl is a statefull Object.
				ctx.session().setAttribute("prevPolicies", prevPolDisp);
				
				/*
				 *  Added for USR 8399-1 changes
				 *  on 02-12-2008 by Nagrathna Hiriyurkar  
				 */
				
				ArrayList tabDataList = new ArrayList();
				tabDataList.add(new Integer(prevPolList.size()).toString());
				ctx.session().setAttribute("prevPolicieDataStatus", tabDataList);
			
				// End of USR -8399-1 changes.  
				
			}
			if (seltab.equalsIgnoreCase("tab114")) {
				formLoader.loadMemosAmendForm(ctx);
			}
			if (seltab.equalsIgnoreCase("tab115")) {
				formLoader.loadSharedInfoForm(ctx);
			}
			//Telephone interview tab
			if (seltab.equalsIgnoreCase("tab116")) {
				
				String refreshList = (String) ctx.session().getAttribute("refreshList");
				if (refreshList == null) { //This is the first time accessing this tab
					refreshList = "true";  //needs to be set to true.
				}
				if (refreshList.equalsIgnoreCase("true")) {
					//Get all interviews requests/scheduled
					String gfid = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.gfid);
					IUAProcessBD processBD = new IUAProcessBD();
					TelInterviewList itnerviewList = processBD.getTelInterviewRequests(gfid);
					
					SimpleListControl interviewCtrl = new SimpleListControl();
					interviewCtrl.setDataModel(itnerviewList);
	
					ctx.session().setAttribute("interviewRecs", interviewCtrl);
				} 
			}
			
			ctx.control().execute(ctx, seltab);
		} catch (Exception e) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {
		try {
			TabsetControl tsctrl = (TabsetControl) ctx.session().getAttribute("maintabset");
			tsctrl.setSelectedTab(getTabPageId());
		} catch (Throwable t) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + t.getClass().getName() + " caught with message: " + t.getMessage());
			t.printStackTrace();
		}

		ctx.forwardToInput();
	}
}
