package com.epm.acmi.struts.action;

/**
 * $Header: /usr/local/cvsroot/acmiroot/acmicc/src/com/epm/acmi/struts/action/AppDocsMetaDataAction.java,v 1.5
 * 2005/12/16 00:35:49 jhombal Exp $ $Revision: 1.20 $ $Date: 2006/05/15 11:07:31 $
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.Optika.message.ModifyIndexResponse;
import com.Optika.message.SavedSearchPrompt;
import com.Optika.message.SavedSearchResult;
import com.Optika.message.SavedSearchResultsArray;
import com.Optika.message.SystemFields;
import com.Optika.message.UserDefinedField;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.Messages;
import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.http.HttpUtil;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.DocumentMetaDataForm;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.DateUtility;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.StellentUpdateAudit;
import com.epm.stellent.StellentAdapterFactory;
import com.epm.stellent.adapter.StellentAdapter;

/**
 * Renders Applications Document metadata panel and also implements save and cancel actions
 * - Many of the APIs in this class are no longer used as we moved from Dynamic template generation
 * to static template definition in JSP page
 * @author Jay Hombal
 * @version $Revision: 1.20 $
 */
public class AppDocsMetaDataAction extends CCAction {

	// specify a width for the first/second label column
	String width1Colum = Integer.toString(160);

	String script = "this.blur();";

	String style = "background-color:#E9E2E1";

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	private static Logger log = Logger.getLogger(AppDocsMetaDataAction.class);

	private static final String cancelURL = "main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab12";

	/**
	 * Constructor
	 */
	public AppDocsMetaDataAction() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cc.framework.adapter.struts.FrameworkAction#doExecute(com.cc.framework.adapter.struts.ActionContext)
	 */

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {

		log.debug("Begin doExecute");
		String AAID  = null;
		
		try {
			String lucId = ctx.request().getParameter("LUCID");
			
			if (isEmptyString(lucId))
				throw new Exception("Cannot proceed with saved search with empty or null RECID");

			User user = (User) ctx.session().getAttribute(Constants.loggedUser);
			
			AAID = StellentClient.login();

			// Store this stellent AAID in user object until user tabs out from this page.
			// user.setAAID(AAID);

			String docCode = ctx.request().getParameter("docType");

			// System.err.println("About to read the ACMICI from session");
			// System.err.println("Read it ok!");
			DocumentMetaDataForm dmf = new DocumentMetaDataForm();// ) ctx.form();

			SavedSearchPrompt ssps[] = null;

			String searchName = Constants.allDocsSearchName;

			// Retrive Prompts for the document Type Selected
			// This may be differnt then actual doctype stored in stellent
			// this can only happen when the user selected differnt doctype and
			// reloads the field

			ssps = ACMICache.getSavedSearchPromptsInstance(searchName);
			for (int j = 0; j < ssps.length; j++) {
				SavedSearchPrompt ssp = ssps[j];

				if (ssp.getDisplayText().equals("RECID")) {
					ssp.setValue(lucId);
					break;
				}
			}

			if (ssps != null) {
				String acorde2Soap_address = LocalProperties.getProperty("acorde2Soap_address");
				StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
				
				SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(searchName, AAID, ssps);
				if (ssrs != null && ssrs.length > 0) {
					SavedSearchResult ssr = ssrs[0];
					// ctx.session().setAttribute(Constants.docMetaData, ssr);
					ctx.session().setAttribute(Constants.lucId, lucId);
					UserDefinedField udfs[] = ssr.getUserDefinedFieldValues();
					SystemFields lsdfs = ssr.getSystemFields();

					StringBuffer sb = new StringBuffer();
					sb.append("http://" + LocalProperties.getProperty("StellentHost")
							+ "/ibpmweb/default.asp?ToolName=AWVWR&Viewer=Optika");
					sb.append("&LUCID=" + lsdfs.getLUCID());
					sb.append("&MIMETYPE=image/tiff");
					sb.append("&TABLENAME=" + HttpUtil.urlEncode(lsdfs.getTABLENAME()));
					sb.append("&ROWIDENTIFIER=" + HttpUtil.urlEncode(lsdfs.getROWIDENTIFIER()));
					sb.append("&EOF=1");
					sb.append("&xmlAIID=" + user.getAAID());

					String encodedURL = sb.toString();

					dmf.setURL("<a href='" + encodedURL + "' target=_blank>View</a>");

					/**
					 * STORE the saved Search Result in Session so that the same object can used to Update
					 */

					for (int k = 0; k < udfs.length; k++) {

						UserDefinedField udf = udfs[k];

						if (udf.getName().equals("ScanDate")) {
							if (udf.getValue() != null) {

								dmf.setScanDate(sdf.format(DateUtility.convertStringToDate(udf.getValue())));
							}
							continue;
						}
						if (udf.getName().equals("AgentName")) {
							dmf.setAgentName(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("AgentNumber")) {
							dmf.setAgentNumber(udf.getValue().trim());
							continue;
						}

						if (udf.getName().equals("DocCode")) {
							if (docCode == null) {
								docCode = udf.getValue();

							}
							dmf.setDocCode(docCode);
							dmf.setDocType(ACMICache.getDocType(docCode));
							continue;
						}

						if (udf.getName().equals("AppFirstName")) {
							dmf.setAppFirstName(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("AppLastName")) {
							dmf.setAppLastName(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("AppMiddle")) {
							dmf.setAppMiddle(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("AppSuffix")) {
							dmf.setAppSuffix(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("AppDOB")) {
							if (udf.getValue() != null) {
								dmf.setAppDOB(sdf.format(DateUtility.convertStringToDate(udf.getValue().trim())));
							}
							continue;
						}
						if (udf.getName().equals("KeyAppFirstName")) {
							dmf.setKeyAppFirstName(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("KeyAppLastName")) {
							dmf.setKeyAppLastName(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("KeyAppSuffix")) {
							dmf.setKeyAppSuffix(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("KeyAppMiddle")) {
							dmf.setKeyAppMiddle(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("KeyAppDOB")) {
							if (udf.getValue() != null) {
								dmf.setKeyAppDOB(sdf.format(DateUtility.convertStringToDate(udf.getValue().trim())));
							}
							continue;
						}
						if (udf.getName().equals("KeyAppSSN")) {
							dmf.setKeyAppSSN(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("State")) {
							dmf.setState(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("PAGEANNOTS")) {
							dmf.setAnnotation(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("LBID")) {
							dmf.setLbid(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("epmReviewed")) {
							dmf.setEpmReviewed(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("Employer")) {
							dmf.setEmployer(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("CFP")) {
							dmf.setCfp(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("DocDescription")) {
							dmf.setDocDescription(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("Product")) {
							dmf.setProduct(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("PolicyNumber")) {
							dmf.setPolicyNumber(udf.getValue().trim());
							continue;
						}
						if (udf.getName().equals("CheckMaker")) {
							dmf.setCheckMaker(udf.getValue().trim());
							continue;
						}
					}

				}

			}

			ctx.session().setAttribute("myFormControl", dmf);

			// forward to the jsp page
			ctx.forwardToInput();
			
			log.debug("End doExecute");

		} catch (Throwable ex) {
			ctx.addGlobalError(Messages.STELLENT_ACCESS_FAILED, ex.getClass().getName() + " exception thrown with message: " + ex.getMessage());
			ctx.forwardByName(Forwards.BACK);
			throw new Exception(ex);
		} finally
		{
			if (AAID != null)
				StellentClient.logout(AAID);
		}
	}

	private static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}


	/**
	 * Handles Cancel Button event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void cancel_onClick(FormActionContext ctx) throws Exception {
		log.debug("Begin cancel_onClick()");
		ctx.session().removeAttribute("myFormControl");
		ctx.session().removeAttribute(Constants.docMetaData);
		ctx.session().removeAttribute(Constants.lucId);

		ctx.forwardToAction(cancelURL);
		log.debug("End cancel_onClick()");
	}

	/**
	 * Handles list/datagrid reload event
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void reload_onClick(FormActionContext ctx) throws Exception {

		log.debug("Begin reload_onClick");
		try {
			DocumentMetaDataForm dmf = (DocumentMetaDataForm) ctx.form();

			String lucId = (String) ctx.session().getAttribute(Constants.lucId);

			String docType = dmf.getDocCode();

			// ctx.session().setAttribute(Constants.lucId, docType);

			if (lucId == null) {
				lucId = (String) ctx.session().getAttribute(Constants.lucId);
			}
			ctx.forwardToAction("/main/appdocs/update?LUCID=" + lucId + "&docType=" + docType);
		} catch (Throwable ex) {
			ctx.addGlobalError(Messages.ERROR, ex.getClass().getName() + " " + ex.getMessage());
			ctx.forwardByName(Forwards.BACK);
			throw new Exception(ex);
		}
		log.debug("End reload_onClick");
	}

	/**
	 * This Method is called if the save button on the HTML-Page is pressed.
	 * 
	 * @param ctx
	 *            FormActionContext
	 * @throws Exception
	 */
	public void save_onClick(FormActionContext ctx) throws Exception {

		log.debug("Begin save_onClick");

		DocumentMetaDataForm form = (DocumentMetaDataForm) ctx.form();
		SavedSearchPrompt ssps[] = null;
		String lucId = (String) ctx.session().getAttribute(Constants.lucId);
		String AAID = null;
		ArrayList udfs = new ArrayList();
		UserDefinedField udf = null;

		try {
			if (isEmptyString(lucId))
				throw new Exception("Cannot proceed with saved search with RECID empty or null");
			
			String searchName = Constants.allDocsSearchName;

			// Retrieve Prompts for the document Type Selected
			// This may be different then actual doctype stored in stellent
			// this can only happen when the user selected different doctype and
			// reloads the field

			ssps = ACMICache.getSavedSearchPromptsInstance(searchName);
			for (int j = 0; j < ssps.length; j++) {
				SavedSearchPrompt ssp = ssps[j];

				if (ssp.getDisplayText().equals("RECID")) {
					ssp.setValue(lucId);
					break;
				}

			}

			if (ssps != null) {

				String acorde2Soap_address = LocalProperties.getProperty("acorde2Soap_address");
				StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
		
				//User user = (User) ctx.session().getAttribute(Constants.loggedUser);

				AAID = StellentClient.login();
				SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(searchName, AAID, ssps);

				if (ssrs != null) {
					SavedSearchResult ssr = ssrs[0];

					if (form.getPolicyNumber() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.policyNumberUDA);
						udf.setValue(form.getPolicyNumber().trim());
						udfs.add(udf);
					}

					if (form.getLbid() != null) {
						udf = new UserDefinedField();
						udf.setName("LBID");
						udf.setValue(form.getLbid().trim());
						udfs.add(udf);
					}

					if (form.getDocCode() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.docCode);
						udf.setValue(form.getDocCode().trim());
						udfs.add(udf);
					}

					if (form.getDocType() != null) {
						udf = new UserDefinedField();
						String type = ACMICache.getDocType(form.getDocCode());
						udf.setName(Constants.docType);
						udf.setValue(type);
						udfs.add(udf);
					}

					if (form.getKeyAppSuffix() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.keyApplicantSuffix);
						udf.setValue(form.getKeyAppSuffix().trim());
						udfs.add(udf);
					}

					if (form.getKeyAppFirstName() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.keyApplicantFirstName);
						udf.setValue(form.getKeyAppFirstName().trim());
						udfs.add(udf);
					}

					if (form.getKeyAppLastName() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.keyApplicantLastName);
						udf.setValue(form.getKeyAppLastName().trim());
						udfs.add(udf);
					}

					if (form.getKeyAppMiddle() != null) {
						udf = new UserDefinedField();
						udf.setName("KeyAppMiddle");
						udf.setValue(form.getKeyAppMiddle().trim());
						udfs.add(udf);
					}

					if (form.getKeyAppDOB() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.keyApplicantDOB);
						String dt = form.getKeyAppDOB();
						if (dt.length() > 0) {
							dt = sdf.format(sdf.parse(dt));
							udf.setValue(dt);
							udfs.add(udf);

						} else {
							udf.setValue("01/01/1801");
							udfs.add(udf);
						}
					}
					if (form.getKeyAppSSN() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.keyApplicantSSN);
						udf.setValue(form.getKeyAppSSN().trim());
						udfs.add(udf);
					}
					if (form.getAppSuffix() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.applicantSuffix);
						udf.setValue(form.getAppSuffix().trim());
						udfs.add(udf);
					}

					if (form.getAppFirstName() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.applicantFirstName);
						udf.setValue(form.getAppFirstName().trim());
						udfs.add(udf);
					}

					if (form.getAppLastName() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.applicantLastName);
						udf.setValue(form.getAppLastName().trim());
						udfs.add(udf);
					}
					if (form.getAppMiddle() != null) {
						udf = new UserDefinedField();
						udf.setName("AppMiddle");
						udf.setValue(form.getAppMiddle().trim());
						udfs.add(udf);
					}

					if (form.getAppDOB() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.applicantDOB);
						String dt = form.getAppDOB();
						if (dt.length() > 0) {
							dt = sdf.format(sdf.parse(dt.trim()));

							udf.setValue(dt);
							udfs.add(udf);

						} else {
							udf.setValue("01/01/1801");
							udfs.add(udf);
						}
					}

					if (form.getDocDescription() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.docDescription);
						udf.setValue(form.getDocDescription().trim());
						udfs.add(udf);
					}

					if (form.getState() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.state);
						udf.setValue(form.getState().trim());
						udfs.add(udf);
					}

					if (form.getLbid() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.lbid);
						udf.setValue(form.getLbid().trim());
						udfs.add(udf);
					}

					if (form.getAgentName() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.agentName);
						udf.setValue(form.getAgentName().trim());
						udfs.add(udf);
					}

					if (form.getAgentNumber() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.agentNumber);
						udf.setValue(form.getAgentNumber().trim());
						udfs.add(udf);
					}

					if (form.getProduct() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.productName);
						udf.setValue(form.getProduct().trim());
						udfs.add(udf);
					}

					if (form.getEmployer() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.employer);
						udf.setValue(form.getEmployer());
						udfs.add(udf);
					}

					if (form.getEpmReviewed() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.epmReviewed);
						udf.setValue(form.getEpmReviewed().trim());
						udfs.add(udf);
					}

					if (form.getCfp() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.cfp);
						udf.setValue(form.getCfp().trim());
						udfs.add(udf);
					}

					if (form.getCheckMaker() != null) {
						udf = new UserDefinedField();
						udf.setName(Constants.checkMaker);
						udf.setValue(form.getCheckMaker().trim());
						udfs.add(udf);
					}

					// Retrive AAID from the Session
					UserDefinedField udfsa[] = new UserDefinedField[udfs.size()];
					for (int i = 0; i < udfs.size(); i++) {
						UserDefinedField ludf = (UserDefinedField) udfs.get(i);
						udfsa[i] = ludf;
					}

					// Retrive the saved Search result from Session

					SavedSearchResultsArray ssra = new SavedSearchResultsArray();
					ssr.setUserDefinedFieldValues(udfsa);
					ssr.setIsSelected(true);

					ssrs[0] = ssr;

					ssra.setSearchResults(ssrs);

					ModifyIndexResponse msr = sAdapter.updateMetaData(AAID, ssra);
					StellentUpdateAudit.auditStellentUpdate(((User)ctx.session().getAttribute(Constants.loggedUser)).getUserId(), form.getGfid(), lucId, StellentUpdateAudit.AUDIT_UPDATE);

					log.debug(msr.getAcordeError().getErrorMessage());

					ctx.session().setAttribute(Constants.docMetaData, null);

				}
			}

			// forward to the jsp page
			ctx.forwardToAction("main/iuauser?ctrl=nestedtabset&action=TabClick&param=tab12");

		} catch (Exception t) {
			log.error("saved search result not found", t);
			ActionMessages ams = new ActionMessages();
			ActionMessage am = new ActionMessage("Saved Search Result failed with exception " + t.getClass().getName(), t.getMessage());
			ams.add("Error", am);
			ctx.addErrors(ams);
			ctx.forwardToInput();
		} finally { 
			if (AAID != null)
				StellentClient.logout(AAID);
		}
		log.debug("End save_onClick");

	}

	/**
	 * Creates the DesignModel for our form
	 * 
	 * @return The designmodel for the form
	 */

//	private FormDesignModel getDesignModel(SavedSearchPrompt ssps[], String docTypeCode, DocumentMetaDataForm dmf) {
//
//		ImageMap imageMap = new ImageMap();
//		imageMap.addImage("warning", new ImageModelImp("fw/def/image/severity/imgWarning13x13.gif", 13, 13));
//		imageMap.addImage("info", new ImageModelImp("fw/def/image/severity/imgInfo13x13.gif", 13, 13));
//		imageMap.addImage("lnkExternal", new ImageModelImp("fw/def/image/severity/lnkExternal.gif", 13, 13));
//		boolean isapplicantEditable = false;
//		boolean isotherEditable = false;
//
//		if (Constants.applicantLevelDocs.indexOf(docTypeCode) > 0) {
//			isapplicantEditable = true;
//			isotherEditable = false;
//		} else {
//			isotherEditable = true;
//		}
//		FormDesignModel form = new FormDesignModelImp();
//		form.setFormId("frmEdit");
//
//		form.setFormType(FormType.EDIT);
//		form.setCaption("form.doc.metadata.edit");
//		form.setWidth("930");
//		form.setAlignment(AlignmentType.LEFT);
//		form.setShowFrame(true);
//		form.setImageMap(imageMap);
//
//		FormGroupElement row = null;
//		FormGroupElement section = new FormGroupElement();
//		section.setTitle("form.doc.applicantion");
//		section.setShowFrame(false);
//
//		// add row
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		row.setAlignment(AlignmentType.LEFT);
//
//		FormHtmlElement htmlformElement = null;
//		htmlformElement = new FormHtmlElement();
//		htmlformElement.setFilter(false);
//		htmlformElement.setColSpan(2);
//		FormControlElement element = createSelectElement("form.doc.DocType", "docCode", 30, 30, 1, true, ACMICache
//				.getDocumentTypeList(), "docCode", "docType", false);
//		row.addFormElement(element);
//
//		FormButtonContainer buttonsection1 = new FormButtonContainer();
//		buttonsection1.setHeight("18");
//		buttonsection1.setOrientation(OrientationType.HORIZONTAL);
//		buttonsection1.setShowFrame(false);
//		buttonsection1.setWidth("10");
//		buttonsection1.setAlignment(AlignmentType.TOP);
//		FormButtonElement button1 = new FormButtonElement();
//		buttonsection1.setTitle("Reload MD");
//		buttonsection1.joinElements();
//		button1.setId("btnReload");
//		button1.setName("btnReload");
//		button1.setText("Reload");
//		button1.setLocaleName("false");
//		button1.setTooltip("Refresh");
//		buttonsection1.addFormElement(button1);
//		row.addFormElement(buttonsection1);
//		row.addFormElement(htmlformElement);
//		row.setJoin(true);
//		section.addFormElement(row);
//
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		element = createSelectElement("form.doc.Product", "product", 30, 30, 2, true, ACMICache.getProductNameMap(), "key",
//				"value", false);
//		row.addFormElement(element);
//		element = createSelectElement("form.doc.State", "state", 30, 30, 1, true, ACMICache.getStatesMap(), "key", "value", false);
//		row.addFormElement(element);
//
//		// createElement(row, "form.doc.State", "state", 2, 2, 1, true, true);
//		// createElement(row, "form.doc.Product", "product", 8, 8, 1, true, true);
//		section.addFormElement(row);
//
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		createElement(row, "form.doc.PolicyNumber", "policyNumber", 15, 15, 1, true, true);
//		createElement(row, "form.doc.AgentName", "agentName", 25, 25, 1, true, false);
//		createElement(row, "form.doc.AgentNumber", "agentNumber", 5, 5, 1, true, false);
//
//		section.addFormElement(row);
//
//		// add row
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		createElement(row, "form.doc.Employer", "employer", 25, 25, 1, isotherEditable, false);
//		createElement(row, "form.doc.LBID", "lbid", 15, 15, 1, false, false);
//		section.addFormElement(row);
//
//		form.addFormElement(section);
//
//		section = new FormGroupElement();
//		section.setTitle("form.doc.key.applicant");
//		section.setShowFrame(false);
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		createElement(row, "form.doc.KeyAppLastName", "keyAppLastName", 25, 25, 1, true, true);
//		createElement(row, "form.doc.KeyAppFirstName", "keyAppFirstName", 25, 25, 1, true, true);
//		createElement(row, "form.doc.KeyAppMiddle", "keyAppMiddle", 1, 1, 1, true, false);
//		createElement(row, "form.doc.KeyAppSuffix", "keyAppSuffix", 4, 4, 1, true, false);
//
//		section.addFormElement(row);
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		createElement(row, "form.doc.KeyAppSSN", "keyAppSSN", 9, 9, 1, true, false);
//		createElement(row, "form.doc.KeyAppDOB", "keyAppDOB", 10, 10, 1, true, false);
//		section.addFormElement(row);
//		form.addFormElement(section);
//
//		section = new FormGroupElement();
//		section.setTitle("form.doc.applicant");
//		section.setShowFrame(false);
//
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//
//		createElement(row, "form.doc.AppLastName", "appLastName", 25, 25, 1, isapplicantEditable, false);
//		createElement(row, "form.doc.AppFirstName", "appFirstName", 25, 25, 1, isapplicantEditable, false);
//		createElement(row, "form.doc.AppMiddle", "appMiddle", 1, 1, 1, isapplicantEditable, false);
//		createElement(row, "form.doc.AppSuffix", "appSuffix", 4, 4, 1, isapplicantEditable, false);
//
//		section.addFormElement(row);
//
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		createElement(row, "form.doc.AppDOB", "appDOB", 10, 10, 1, isapplicantEditable, false);
//		section.addFormElement(row);
//		form.addFormElement(section);
//
//		section = new FormGroupElement();
//		section.setTitle("form.doc.details");
//		section.setShowFrame(false);
//
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		ListDataModel ldl = getSimpleListDataModel();
//
//		createSelectElement(row, "form.doc.epmReviewed", "epmReviewed", 1, 3, 1, true, ldl, "key", "value", false);
//		createSelectElement(row, "form.doc.CFP", "cfp", 1, 3, 1, true, ldl, "key", "value", false);
//		createElement(row, "form.doc.CheckMaker", "checkMaker", 25, 25, 1, isotherEditable, false);
//		section.addFormElement(row);
//		form.addFormElement(section);
//
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//
//		createElement(row, "form.doc.DocDescription", "docDescription", 35, 35, 2, true, false);
//		section.addFormElement(row);
//
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//
//		htmlformElement = new FormHtmlElement();
//		htmlformElement.setLabel("");
//		htmlformElement.setBodyInclude(true);
//		htmlformElement.setColSpan(2);
//		htmlformElement.setAlignment(AlignmentType.LEFT);
//		htmlformElement.setHtml("Click to " + dmf.getURL() + " the document");
//		htmlformElement.setFilter(false);
//		row.addFormElement(htmlformElement);
//
//		section.addFormElement(row);
//
//		// ------------------------------
//		// Add a button section
//		// ------------------------------
//		row = new FormGroupElement();
//		row.setOrientation(OrientationType.HORIZONTAL);
//		row.setJoin(true);
//
//		FormButtonContainer buttonsection = new FormButtonContainer();
//		buttonsection.setColSpan(4);
//		buttonsection.setShowFrame(true);
//		buttonsection.setAlignment(AlignmentType.BASELINE);
//		buttonsection.setDefaultButtonId("btnSave");
//
//		FormButtonElement button2 = new FormButtonElement();
//		button2 = new FormButtonElement();
//		button2.setId("btnSave");
//		button2.setName("btnSave");
//		button2.setFilter(false);
//		button2.setLocaleName("false");
//		button2.setText("Save");
//		button2.setTooltip("Save changes and return");
//		button2.setHandler(ClientEvent.ONCLICK, "return validateInput();");
//		buttonsection.addFormElement(button2);
//
//		row.addFormElement(buttonsection);
//
//		FormButtonElement button = new FormButtonElement();
//		button.setFilter(false);
//		button.setId("btnCancel");
//		button.setName("btnCancel");
//		button.setText("button.title.cancel");
//		button.setLocaleName("true");
//		button.setTooltip("button.title.cancel");
//		buttonsection.addFormElement(button);
//		form.addFormElement(row);
//		return form;
//	}

//	private void createElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, boolean required) {
//
//		FormControlElement element = null;
//		TextDesignModel text = null;
//		FormLabelDesignModel label = null;
//
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		// label.setWidth(width1Colum);
//		label.setLocaleName("true");
//
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//
//		text = new TextDesignModelImp();
//		text.setDynamicDesignModel(false);
//		text.setFormElement(true);
//		text.setTransaction(false);
//		text.setRunAt(RunAt.SERVER);
//		text.setProperty(property);
//		text.setMaxLength(width);
//		text.setSize(size);
//		text.setTooltip("form.tooltip");
//		text.setTooltip(property);
//		text.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//
//		if (!isEditable) {
//			text.setDisabled(true);
//			text.setStyle(style);
//		}
//
//		element.setColSpan(colspan);
//		element.setControl(new TextControl(text));
//		row.addFormElement(element);
//
//	}
//
//	private void createCalendarElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, boolean required) {
//
//		FormControlElement element = null;
//		FormLabelDesignModel label = null;
//
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		label.setLocaleName("true");
//
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//		CalendarDesignModel calendar = new CalendarDesignModelImp();
//		calendar.setRunAt(RunAt.SERVER);
//		calendar.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//
//		calendar.setFormat("MM/dd/yyyy");
//		calendar.setFilter(true);
//		calendar.setMaxLength(10);
//		calendar.setSize(8);
//		calendar.setShowFormat("true");
//		calendar.setProperty(property);
//		calendar.setTooltip(property);
//
//		if (!isEditable) {
//			calendar.setDisabled(true);
//			calendar.setStyle(style);
//		}
//
//		calendar.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//
//		element.setControl(new CalendarControl(calendar));
//		element.setColSpan(colspan);
//		row.addFormElement(element);
//
//	}
//
//	private void createSelectElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, ListDataModel ldm, String keyProperty, String valueProperty, boolean required) {
//
//		FormControlElement element = null;
//		FormLabelDesignModel label = null;
//		SelectDesignModel select = null;
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		label.setLocaleName("true");
//
//		select = new SelectDesignModelImp();
//		select.setRunAt(RunAt.SERVER);
//		select.setMultiple(false);
//		select.setProperty(property);
//		select.setLocaleName("true");
//		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//		select.setTooltip(property);
//
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//
//		if (!isEditable) {
//			select.setDisabled(false);
//			select.setStyle(style);
//
//		}
//		SelectControl ctrl = new SelectControl(select);
//
//		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
//		element.setColSpan(colspan);
//		element.setControl(ctrl);
//		row.addFormElement(element);
//
//	}
//
//	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, HashMap ldm, String keyProperty, String valueProperty, boolean required) {
//
//		FormControlElement element = null;
//		FormLabelDesignModel label = null;
//		SelectDesignModel select = null;
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		label.setLocaleName("true");
//
//		select = new SelectDesignModelImp();
//		select.setRunAt(RunAt.SERVER);
//		select.setMultiple(false);
//		select.setProperty(property);
//		select.setLocaleName("true");
//		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//		select.setTooltip(property);
//
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//
//		if (!isEditable) {
//			select.setDisabled(false);
//			select.setStyle(style);
//
//		}
//		SelectControl ctrl = new SelectControl(select);
//
//		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
//		element.setColSpan(colspan);
//		element.setControl(ctrl);
//		return element;
//	}
//
//	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, TreeMap ldm, String keyProperty, String valueProperty, boolean required) {
//
//		FormControlElement element = null;
//		FormLabelDesignModel label = null;
//		SelectDesignModel select = null;
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		label.setLocaleName("true");
//
//		select = new SelectDesignModelImp();
//		select.setRunAt(RunAt.SERVER);
//		select.setMultiple(false);
//		select.setProperty(property);
//		select.setLocaleName("true");
//		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//		select.setTooltip(property);
//
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//
//		if (!isEditable) {
//			select.setDisabled(false);
//			select.setStyle(style);
//
//		}
//		SelectControl ctrl = new SelectControl(select);
//
//		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
//		element.setColSpan(colspan);
//		element.setControl(ctrl);
//		return element;
//	}
//
//	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
//			boolean isEditable, ListDataModel ldm, String keyProperty, String valueProperty, boolean required) {
//
//		FormControlElement element = null;
//		FormLabelDesignModel label = null;
//		SelectDesignModel select = null;
//		label = new FormLabelDesignModelImp();
//		label.setNowrap(new Boolean(true));
//		label.setText(labelName);
//		label.setLocaleName("true");
//
//		select = new SelectDesignModelImp();
//		select.setRunAt(RunAt.SERVER);
//		select.setMultiple(false);
//		select.setProperty(property);
//		select.setLocaleName("true");
//		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
//		select.setTooltip(property);
//
//		element = new FormControlElement();
//		element.setLabel(label);
//		element.setRequired(required);
//
//		if (!isEditable) {
//			select.setDisabled(false);
//			select.setStyle(style);
//
//		}
//		SelectControl ctrl = new SelectControl(select);
//
//		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
//		element.setColSpan(colspan);
//		element.setControl(ctrl);
//		return element;
//	}
////
	// /**
	// * Returns the Designmodel of the Optionlist for our Select element
	// *
	// * @return OptionListDesignModel
	// */
	// private OptionListDesignModel getTreeOptionListDesignModel() {
	//
	// OptionListDesignModel optionList = new OptionListDesignModelImp();
	// optionList.setEmpty("<Please select>");
	// optionList.setFilter(true);
	// optionList.setKeyProperty("uniqueKey");
	// optionList.setLabelProperty("name");
	// optionList.setStyle("color: navy;");
	//
	// return optionList;
	// }

//	private OptionListDesignModel getOptionListDesignModel(String labelKey, String propertyKey) {
//
//		OptionListDesignModel optionList = new OptionListDesignModelImp();
//		optionList.setFilter(true);
//		optionList.setKeyProperty(labelKey);
//		optionList.setLabelProperty(propertyKey);
//		optionList.setStyle("color: navy;");
//
//		return optionList;
//	}

	// private OptionListDesignModel getListDesignModel() {
	//
	// OptionListDesignModel optionList = new OptionListDesignModelImp();
	// optionList.setFilter(true);
	// optionList.setKeyProperty("key");
	// optionList.setLabelProperty("title");
	// optionList.setStyle("color: navy;");
	// return optionList;
	// }

//	private ListDataModel getSimpleListDataModel() {
//
//		SimpleType st[] = new SimpleType[3];
//		st[0] = new SimpleType("", "");
//		st[1] = new SimpleType("N", "No");
//		st[2] = new SimpleType("Y", "Yes");
//		ListDataModel data = new SimpleList(st);
//		return data;
//	}

}