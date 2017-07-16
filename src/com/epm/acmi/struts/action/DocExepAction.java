package com.epm.acmi.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
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
import com.cc.framework.ui.control.SimpleListControl;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.dsp.DisplayDocument;
import com.epm.acmi.struts.form.dsp.DisplayDocumentList;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.StellentUpdateAudit;
import com.epm.stellent.StellentAdapterFactory;
import com.epm.stellent.adapter.StellentAdapter;

/**
 * DocException handles requests from document exception page.
 * 
 * @author Jay Hombal
 */
public class DocExepAction extends MainTabPageBaseAction {

	private static Logger log = Logger.getLogger(DocExepAction.class);

	/**
	 * Constructor
	 */
	public DocExepAction() {
		super();
	}

	public String getTabPageId() {
		return TABPAGE3;
	}

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws IOException, ServletException {

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

			if (null == user)
			{
				ctx.addGlobalError("message.nosession", "");
				ctx.forwardToAction("/logon");
				return;
			}
			
			String AAID = StellentClient.loginByUserNonRedundant(user);
			log.debug("AAID " + AAID);

			DisplayDocumentList ddl = StellentClient.getDisplayDocumentList(Constants.expGFID, AAID);
			// secondly create the ListControl and populate it
			// with the Data to display
			SimpleListControl docList = new SimpleListControl();
			docList.setDataModel(ddl);
			ctx.session().setAttribute("expdoclist", docList);
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
	public void expdoclist_onSort(ControlActionContext ctx, String column, SortOrder direction) throws Exception {
		log.debug("Begin expdoclist_onSort");
		DisplayDocumentList ddl = (DisplayDocumentList) ctx.control().getDataModel();

		ddl.sortByColumn(column, direction);

		ctx.control().execute(ctx, column, direction);
		log.debug("End expdoclist_onSort");
	}

	/**
	 * This Method is called if the Sort-Icon in a Column is clicked
	 * 
	 * @param ctx
	 *            ControlActionContext
	 */
	public void expdoclist_onViewdoc(ControlActionContext ctx) throws Exception {
		log.debug("Begin expdoclist_onViewdoc");
		String LUCID = ctx.request().getParameter("LUCID");

		try {
			if (LUCID != null) {
				SimpleListControl slc = (SimpleListControl) ctx.session().getAttribute("expdoclist");
				DisplayDocumentList ddl = (DisplayDocumentList) slc.getDataModel();

				int size = ddl.size();
				for (int i = 0; i < size; i++) {
					String lucId = ddl.getUniqueKey(i);
					if (lucId.equals(LUCID)) {
						StringBuffer sb = new StringBuffer();
						DisplayDocument dd = (DisplayDocument) ddl.getElementAt(i);
						sb.append("http://dev7000/ibpmweb/default.asp?ToolName=AWVWR&Viewer=Optika");
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
		} catch (Throwable ex) {
			ctx.addGlobalError(Messages.ERROR_INIT_FORMBEAN, ex.getClass().getName() + " " + ex.getMessage());
			ctx.forwardByName(Forwards.BACK);
			throw new Exception(ex);
		}
		log.debug("End expdoclist_onViewdoc");
	}

	/**
	 * This Method is called if the Refresh-Button is clicked
	 * 
	 * @param ctx
	 *            ControlActionContext
	 */
	public void expdoclist_onRefresh(ControlActionContext ctx) throws Exception {
		try {
			this.refreshList(ctx);
		} catch (Throwable t) {
			log.error(t);
			ctx.addGlobalError(Messages.ERROR_DATABASE_QUERY);
			throw new Exception(t);
		}
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
		refreshList(ctx);
		ctx.control().execute(ctx, seltab);
		ctx.session().setAttribute("start", "main");
		ctx.forwardToAction("/main/actpend?ctrl=maintabset&action=TabClick&param=tab4&start=1");
	}

	/**
	 * This Method is called, when the secondary Tabpage is clicked
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 */
	public void secondarymaintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {
		refreshList(ctx);
		ctx.control().execute(ctx, seltab);
		ctx.session().setAttribute("start", "secondarymain");
		ctx.forwardToAction("/secondarymain/actpend?ctrl=secondarymaintabset&action=TabClick&param=tab4&start=2");

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
	public void expdoclist_onEdit(ControlActionContext ctx, String key) throws Exception {
		ctx.forwardByName(Forwards.EDIT, key);
	}


	private static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
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
	public void expdoclist_onDelete(ControlActionContext ctx, String key) throws Exception {

		String AAID = null;

		try {
			String lucId = key;

			AAID = StellentClient.login();

			HttpSession session = ctx.session();

			SimpleListControl slc = (SimpleListControl) session.getAttribute("expdoclist");

			DisplayDocumentList ddml = (DisplayDocumentList) slc.getDataModel();

			SavedSearchPrompt ssps[] = null;

			if (ddml != null) {

				int size = ddml.size();

				DisplayDocument dd = null;
				boolean found = false;
				
				// Retrieve the document from the session
				for (int i = 0; i < size; i++) {
					dd = (DisplayDocument) ddml.getElementAt(i);

					if (dd.getLUCID().equals(lucId)) {
						found = true;
						break;
					}
				}

				//Check for valid parameters
				
				if (!found || (isEmptyString(lucId) && (isEmptyString(dd.getGFID())) && (isEmptyString(dd.getDocCode()))))
					throw new Exception("Cannot proceed with saved search if parameters are either empty or null");
				
				// Create Search Name
				String searchName = Constants.allDocsSearchName;

				// Retrieve Saved Search Prompts
				ssps = ACMICache.getSavedSearchPrompts(searchName);

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
								audf.setValue(Constants.delGFID);
							}
							// Setting Policy Number to '999999999999999' as default exp value
							if (audf.getName().equals("PolicyNumber")) {
								audf.setValue(Constants.expPolicyNum);
							}
						}

						SavedSearchResultsArray ssra = new SavedSearchResultsArray();

						ssr.setUserDefinedFieldValues(udfs);

						ssr.setIsSelected(true);

						ssrs[0] = ssr;

						ssra.setSearchResults(ssrs);

						ModifyIndexResponse msr = sAdapter.updateMetaData(AAID, ssra);
						StellentUpdateAudit.auditStellentUpdate(((User)ctx.session().getAttribute(Constants.loggedUser)).getUserId(), Constants.delGFID, lucId, StellentUpdateAudit.AUDIT_UPDATE);
						
						String error = msr.getAcordeError().getErrorMessage();
						if (error != null) {
							log.error(error);
						}

					}

					ctx.forwardToAction("main/docexep?ctrl=maintabset&action=TabClick&param=tab4");
				}
			}

		} catch (Throwable t) {
			log.error(Messages.STELLENT_ACCESS_FAILED, t);
			ctx.addGlobalError(Messages.STELLENT_ACCESS_FAILED, t.getClass().getName() + " exception thrown with message: " + t.getMessage());
			throw new Exception(t);
		} finally {
			StellentClient.logout(AAID);
		}
	}
}