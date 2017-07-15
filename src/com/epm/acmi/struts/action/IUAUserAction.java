package com.epm.acmi.struts.action;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.Optika.message.ModifyIndexResponse;
import com.Optika.message.SavedSearchPrompt;
import com.Optika.message.SavedSearchResult;
import com.Optika.message.SavedSearchResultsArray;
import com.Optika.message.UserDefinedField;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.Messages;
import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.http.HttpUtil;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.FormControl;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.control.TabsetControl;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.FormLoaderHelper;
import com.epm.acmi.struts.form.dsp.DisplayDocument;
import com.epm.acmi.struts.form.dsp.DisplayDocumentList;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.OnTabUpdates;
import com.epm.acmi.util.StellentUpdateAudit;
import com.epm.stellent.StellentAdapterFactory;
import com.epm.stellent.adapter.StellentAdapter;

/**
 * Action for TabPage5
 * 
 * @author Jay Hombal
 * @version $Revision: 1.31 $
 */

public class IUAUserAction extends MainTabPageBaseAction {

	private static final String ACMI_INSTANCE = "ACMIInstance";
	private static Logger log = Logger.getLogger(IUAUserAction.class);


	/**
	 * Constructor
	 */
	public IUAUserAction() {
		super();
	}


	public void doExecute(ActionContext ctx) throws Exception {

		log.debug("Begin doExecute");

		log.debug("End doExecute");

	}


	/**
	 * @see com.cc.sampleapp.tabset.sample401.action.TabPageBaseAction#getTabPageId()
	 */
	/**
	 * Returns tab page Id
	 */
	public String getTabPageId() {
		return TABPAGE1;
	}


	/**
	 * Fill's and refresh's the ListControl from the Database
	 * 
	 * @param ctx
	 *            ControlActionContext
	 * @throws java.lang.Exception
	 */
	private void refreshList(ActionContext ctx) throws Exception {

		log.debug("Begin refreshList()");
		try {
			User user = (User) ctx.session().getAttribute(Constants.loggedUser);
			if (null == user) {
				ctx.addGlobalError("message.nosession", "");
				ctx.forwardToAction("/logon");
				return;
			}
			
			String AAID = StellentClient.loginByUserNonRedundant(user);
			log.debug("AAID " + AAID);

			String GFID = (String) ctx.session().getAttribute(Constants.gfid);
			ACMICache ACMIInstance = (ACMICache) ctx.session().getAttribute(ACMI_INSTANCE);
			
			if (ACMIInstance == null)		
			{
				ACMIInstance = new ACMICache();
				ctx.session().setAttribute(ACMI_INSTANCE, ACMIInstance);
			}

			DisplayDocumentList ddl = StellentClient.getDisplayDocumentListInstance(GFID, AAID, ACMIInstance);
			if (ddl != null) {
				ddl.sortByColumn("LUCID", SortOrder.DESCENDING);
			}

			// secondly create the ListControl and populate it
			// with the Data to display
			SimpleListControl docList = new SimpleListControl();
			docList.setDataModel(ddl);

			ctx.session().setAttribute("doclist", docList);
			log.debug("End refreshList()");
		} catch (Exception ex) {
			log.error("refreshList Failed", ex);
			ctx.addGlobalError("stellent.exception", "");
			ctx.forwardToAction("/logon");

		}
	}
	/**
	 * This Method is called if the Sort-Icon in a Column is clicked
	 * 
	 * @param ctx
	 *            ControlActionContext
	 * @param column
	 *            Column to sort
	 * @param direction
	 *            Direction (ASC, DESC)
	 */
	public void doclist_onSort(ControlActionContext ctx, String column, SortOrder direction) throws Exception {
		log.debug("Begin doclist_onSort()");
		DisplayDocumentList ddl = (DisplayDocumentList) ctx.control().getDataModel();

		if (ddl != null) {
			ddl.sortByColumn(column, direction);
			ctx.control().execute(ctx, column, direction);
		}
		log.debug("End doclist_onSort()");
	}


	/**
	 * This Method is called if the Sort-Icon in a Column is clicked
	 * 
	 * @param ctx
	 *            ControlActionContext
	 */
	public void doclist_onViewdoc(ControlActionContext ctx) throws Exception {
		log.debug("Begin doclist_onViewdoc()");

		String LUCID = ctx.request().getParameter("LUCID");

		try {
			if (LUCID != null) {
				SimpleListControl slc = (SimpleListControl) ctx.session().getAttribute("doclist");
				DisplayDocumentList ddl = (DisplayDocumentList) slc.getDataModel();

				int size = ddl.size();
				for (int i = 0; i < size; i++) {
					String lucId = ddl.getUniqueKey(i);
					if (lucId.equals(LUCID)) {
						StringBuffer sb = new StringBuffer();
						DisplayDocument dd = (DisplayDocument) ddl.getElementAt(i);
						sb.append("http://dev7000/ibpmweb/default.asp?ToolName=AWVWR&Viewer=Image");
						sb.append("&LUCID=" + LUCID);
						sb.append("&MIMETYPE=image/tiff");
						sb.append("&SSPROVIDERID=" + dd.getSSPROVIDERID());
						sb.append("&TABLENAME=" + dd.getTABLENAME());
						sb.append("&ROWIDENTIFIER=" + dd.getROWIDENTIFIER());
						sb.append("&INDEXPROVIDER=" + dd.getINDEXPROVIDER());
						sb.append("&EOF=1");

						String encodedURL = HttpUtil.urlEncode(sb.toString());
						ctx.response().sendRedirect(encodedURL);
					}
				}
			}
		} catch (Throwable t) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + t.getClass().getName() + " caught with message: " + t.getMessage());
			t.printStackTrace();
		}
		log.debug("End doclist_onViewdoc()");
	}


	/**
	 * This Method is called if the Refresh-Button is clicked
	 * 
	 * @param ctx
	 *            ControlActionContext
	 */
	public void doclist_onRefresh(ControlActionContext ctx) throws Exception {

		log.debug("Begin doclist_onRefresh()");
		try {
			this.refreshList(ctx);
		} catch (Throwable t) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + t.getClass().getName() + " caught with message: " + t.getMessage());
			t.printStackTrace();
		}
		log.debug("End doclist_onRefresh()");
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
		log.debug("Begin maintabset_onTabClick()");
		log.debug(" inside IUAUserAction - method maintabset_onTabClick started... ");
		
		if (seltab.equals("tab1")) {
			FormLoaderHelper formLoader = new FormLoaderHelper();

			TabsetControl nestedtabset = (TabsetControl) ctx.session().getAttribute("nestedtabset");
			nestedtabset.setSelectedTab(TABPAGE11);
			ctx.session().setAttribute("nestedtabset", nestedtabset);

			TabsetControl nbwstabset = (TabsetControl) ctx.session().getAttribute("nbwstabset");
			nbwstabset.setSelectedTab(TABPAGE111);
			
			/*
			 * Added for USR 8399-1 changes 
			 * on 02-12-2008 by Nagrathna Hiriyurkar 
			 */
			
			OnTabUpdates.medRecTabDataStatus(ctx);
			OnTabUpdates.prevPoliciesTabDataStatus(ctx);
			OnTabUpdates.MemosAmendTabDataStatus(ctx);
			OnTabUpdates.SharedInfoTabDataStatus(ctx);
			OnTabUpdates.CaseNotesTabDataStatus(ctx);
			OnTabUpdates.TelephoneInterviewStatus(ctx);
			
			// End of USR 8399-1 changes. 
			
			formLoader.loadNBWSDetailsForm(ctx);
			
			FormControl control = new FormControl();
			control.setDesignModel(NBWSDetailsAction.getDesignModel(ctx.request()));
			ctx.session().setAttribute("ActivityActions", control);
			ctx.session().setAttribute("nbwstabset", nbwstabset);

			// Clear Stellent Session Id when user tabs into document exceptions
			StellentClient.logoutByUserNonRedundant(ctx);
		}

		log.debug(" inside IUAUserAction - method maintabset_onTabClick ending... ");
		ctx.control().execute(ctx, seltab);
		log.debug("End maintabset_onTabClick()");
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

		log.debug("Begin nestedtabset_onTabClick()");

		FormLoaderHelper formLoader = new FormLoaderHelper();

		if (seltab.equalsIgnoreCase("tab11")) {
			ctx.session().removeAttribute("docUpdateMetaDataForm");
			ctx.session().removeAttribute("uploadFormControl");
			ctx.session().removeAttribute("docCode");

			TabsetControl nestedtabset = (TabsetControl) ctx.session().getAttribute("nestedtabset");
			nestedtabset.setSelectedTab(TABPAGE11);

			TabsetControl nbwstabset = (TabsetControl) ctx.session().getAttribute("nbwstabset");
			nbwstabset.setSelectedTab(TABPAGE111);
		}

		if (seltab.equalsIgnoreCase("tab12")) {

			ctx.session().removeAttribute("docUpdateMetaDataForm");
			ctx.session().removeAttribute("uploadFormControl");
			ctx.session().removeAttribute("docCode");
			refreshList(ctx);
		}
		if (seltab.equalsIgnoreCase("tab13")) {
			formLoader.loadUploadDocumentForm(ctx);
		}
		if (seltab.equalsIgnoreCase("tab14")) {
			ctx.session().removeAttribute("uploadFormControl");
			ctx.session().removeAttribute("docUpdateMetaDataForm");
			ctx.session().removeAttribute("docCode");
			formLoader.loadCaseNotesForm(ctx);
			
			/*
			 *  Added for USR 8399-1 changes
			 *  on 02-12-2008 by Nagrathna Hiriyurkar  
			 */
			
			OnTabUpdates.CaseNotesTabDataStatus(ctx);
			// End of USR -8399-1 changes.  
			
		}

		ctx.control().execute(ctx, seltab);

		log.debug("End nestedtabset_onTabClick()");
	}


	/**
	 * This Method is called when the Edit-Column is clicked. In our Example we want to edit the Userdata. Therefor we
	 * call the UserEditAction
	 * 
	 * @param ctx
	 *            ControlActionContext
	 * @param key
	 *            UniqueKey, as it was created in the Datamodel (e.g. the Primarykey)
	 * @throws Exception
	 */
	public void doclist_onEdit(ControlActionContext ctx, String key) throws Exception {

		log.debug("Begin doclist_onEdit()");
		ctx.forwardByName(Forwards.EDIT, key);
		log.debug("End doclist_onEdit()");
	}


	/**
	 * This Method is called when the Edit-Column is clicked. In our Example we want to edit the Userdata. Therefor we
	 * call the UserEditAction
	 * 
	 * @param ctx
	 *            ControlActionContext
	 * @param key
	 *            UniqueKey, as it was created in the Datamodel (e.g. the Primarykey)
	 * @throws Exception
	 */
	public void doclist_onDelete(ControlActionContext ctx, String key) throws Exception {

		log.debug("Begin doclist_onDelete()");
		String AAID = null;
		try {
			String lucId = key;
		
			AAID = StellentClient.login();

			HttpSession session = ctx.session();

			SimpleListControl slc = (SimpleListControl) session.getAttribute("doclist");

			DisplayDocumentList ddml = (DisplayDocumentList) slc.getDataModel();

			SavedSearchPrompt ssps[] = null;

			if (ddml != null) {

				int size = ddml.size();

				DisplayDocument dd = null;
				boolean found = false;
				
				// Retrive the document from the session
				for (int i = 0; i < size; i++) {
					dd = (DisplayDocument) ddml.getElementAt(i);

					if (dd.getLUCID().equals(lucId)) {
						found = true;
						break;
					}
				}


				if (!found || (isEmptyString(lucId) && (isEmptyString(dd.getGFID())) && (isEmptyString(dd.getDocCode()))))
					throw new Exception("Cannot proceed with saved search if parameters are either empty or null");
				
				// Create Search Name
				String searchName = Constants.allDocsSearchName; 

				// Retrieve Saved Search Prompts
				ssps = ACMICache.getSavedSearchPromptsInstance(searchName);

				for (int j = 0; j < ssps.length; j++) {
					SavedSearchPrompt ssp = ssps[j];
					if (ssp.getDisplayText().equals("GFID")) {
						ssp.setValue(dd.getGFID());
						continue;
					}
					if (ssp.getDisplayText().equals("DocCode")) {
						ssp.setValue(dd.getDocCode());
						continue;
					}
					if (ssp.getDisplayText().equals("RECID")) {
						ssp.setValue(lucId);
						continue;
					}

				}

				if (ssps != null) {

					String acorde2Soap_address = LocalProperties.getProperty("acorde2Soap_address");
					StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);

					/**
					 * Note: Each user when logging into EPM WebApp will obtain a AAID (Authentication Ticket) From ACMI
					 * web application
					 */
					
					SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(searchName, AAID, ssps);

					if (ssrs != null) {

						SavedSearchResult ssr = ssrs[0];

						UserDefinedField udfs[] = ssr.getUserDefinedFieldValues();

						for (int i = 0; i < udfs.length; i++) {
							UserDefinedField audf = udfs[i];

							if (audf.getName().equals("GFID")) {
								audf.setValue(Constants.expGFID);
								continue;
							}

							if (audf.getName().equals("PolicyNumber")) {
								audf.setValue(Constants.expPolicyNum);
								continue;
							}

						}

						SavedSearchResultsArray ssra = new SavedSearchResultsArray();

						ssr.setUserDefinedFieldValues(udfs);

						ssr.setIsSelected(true);

						ssrs[0] = ssr;

						ssra.setSearchResults(ssrs);

						ModifyIndexResponse msr = sAdapter.updateMetaData(AAID, ssra);
						StellentUpdateAudit.auditStellentUpdate(((User)ctx.session().getAttribute(Constants.loggedUser)).getUserId(), Constants.expGFID, lucId, StellentUpdateAudit.AUDIT_UPDATE);

						log.debug(msr.getAcordeError().getErrorMessage());

						refreshList(ctx);
					}

					ctx.forwardToAction("main/docexep?ctrl=nestedtabset&action=TabClick&param=tab12");
				}
			}
		} catch (Throwable t) {
			ctx.addGlobalError(Messages.STELLENT_ACCESS_FAILED, t.getClass().getName() + " exception thrown with message: " + t.getMessage());
			log.error("Exception " + t.getClass().getName() + " caught with message: " + t.getMessage());
			t.printStackTrace();
		} finally {
			StellentClient.logout(AAID);
		}
		log.debug("End doclist_onDelete()");
	}


	private static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}

}