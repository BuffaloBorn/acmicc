package com.epm.acmi.struts.action;

/**
 * $Header: /usr/local/cvsroot/acmiroot/acmicc/src/com/epm/acmi/struts/action/DocMetaDataDispAction.java,v 1.4
 * 2005/12/13 07:47:31 jhombal Exp $ $Revision: 1.16 $ $Date: 2006/05/19 12:25:48 $
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TreeMap;

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
import com.cc.framework.ui.AlignmentType;
import com.cc.framework.ui.FormType;
import com.cc.framework.ui.ImageMap;
import com.cc.framework.ui.OrientationType;
import com.cc.framework.ui.control.FormButtonContainer;
import com.cc.framework.ui.control.FormButtonElement;
import com.cc.framework.ui.control.FormControlElement;
import com.cc.framework.ui.control.FormGroupElement;
import com.cc.framework.ui.control.FormHtmlElement;
import com.cc.framework.ui.control.SelectControl;
import com.cc.framework.ui.control.TextControl;
import com.cc.framework.ui.model.ClientEvent;
import com.cc.framework.ui.model.FormDesignModel;
import com.cc.framework.ui.model.FormLabelDesignModel;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.ui.model.OptionListDesignModel;
import com.cc.framework.ui.model.SelectDesignModel;
import com.cc.framework.ui.model.TextDesignModel;
import com.cc.framework.ui.model.imp.FormDesignModelImp;
import com.cc.framework.ui.model.imp.FormLabelDesignModelImp;
import com.cc.framework.ui.model.imp.ImageModelImp;
import com.cc.framework.ui.model.imp.OptionListDesignModelImp;
import com.cc.framework.ui.model.imp.SelectDesignModelImp;
import com.cc.framework.ui.model.imp.TextDesignModelImp;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.DocumentMetaDataForm;
import com.epm.acmi.struts.form.dsp.SimpleList;
import com.epm.acmi.struts.form.dsp.SimpleType;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.DateUtility;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.StellentUpdateAudit;
import com.epm.stellent.StellentAdapterFactory;
import com.epm.stellent.adapter.StellentAdapter;

/**
 * This examples shows how to create the designmodel for a form server side.
 * 
 * @author Jay Hombal
 * @version $Revision: 1.16 $
 */
public class DocMetaDataDispAction extends CCAction {

	// specify a width for the first/second label column
	String width1Colum = Integer.toString(160);

	String script = "this.blur();";

	String style = "background-color:#E9E2E1";

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * Constructor
	 */
	public DocMetaDataDispAction() {
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
		String AAID = null;
		
		try {
			String lucId = ctx.request().getParameter("LUCID");

			if (isEmptyString(lucId))
				throw new Exception("Cannot proceed with saved search if RECID is empty or null");
			
			/**
			 * Note: After the requirement change we no are using ondemand stellent session, so create a session.
			 */

			AAID = StellentClient.login();// user.getAAID();
			// 
			String docCode = ctx.request().getParameter("docType");

			DocumentMetaDataForm dmf = new DocumentMetaDataForm();// ) ctx.form();

			SavedSearchPrompt ssps[] = null;

			String searchName = Constants.allDocsSearchName;

			// Retrive Prompts for the document Type Selected
			// This may be differnt then actual doctype stored in stellent
			// this can only happen when the user selected differnt doctype and
			// reloads the field

			ssps = ACMICache.getSavedSearchPrompts(searchName);
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
				if (ssrs != null) {
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
					sb.append("&xmlAIID=" + AAID);

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

			ctx.request().setAttribute("myFormControl", dmf);
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

	/**
	 * Cancel button click event handler
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void cancel_onClick(FormActionContext ctx) throws Exception {
		log.debug("Button 'cancel' clicked!");
		ctx.request().removeAttribute("myFormControl");
		ctx.session().removeAttribute(Constants.docMetaData);
		ctx.session().removeAttribute(Constants.lucId);

		// no action
		ctx.forwardToAction("main/docexep?ctrl=maintabset&action=TabClick&param=tab4");
	}

	/**
	 * reload button click handler
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void reload_onClick(FormActionContext ctx) throws Exception {
		log.debug("Button 'cancel' clicked!");
		// no action
		DocumentMetaDataForm dmf = (DocumentMetaDataForm) ctx.form();

		String lucId = (String) ctx.session().getAttribute(Constants.lucId);

		String docType = dmf.getDocCode();

		ctx.session().setAttribute(Constants.lucId, docType);

		if (lucId == null) {
			lucId = (String) ctx.session().getAttribute(Constants.lucId);
		}
		ctx.forwardToAction("/main/docexep/update?LUCID=" + lucId + "&docType=" + docType);
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
		UserDefinedField udf = null;
		ArrayList udfs = new ArrayList();
		String start = "secondarymain";
		SavedSearchPrompt ssps[] = null;

		String AAID = null;
		
		try {
			String lucId = (String) ctx.session().getAttribute(Constants.lucId);
			
			if (isEmptyString(lucId))
				throw new Exception("Cannot proceed with saved search if RECID is empty or null");
			
			String searchName = Constants.allDocsSearchName;

			// Retrive Prompts for the document Type Selected
			// This may be differnt then actual doctype stored in stellent
			// this can only happen when the user selected differnt doctype and
			// reloads the field

			ssps = ACMICache.getSavedSearchPrompts(searchName);

			// fix for issue 70 04/03/2006 cmontero
			if (ssps != null) {
				for (int j = 0; j < ssps.length; j++) {
					SavedSearchPrompt ssp = ssps[j];

					if (ssp.getDisplayText().equals("RECID")) {
						ssp.setValue(lucId);
						break;
					}

				}

				// if (ssps != null) {
				String acorde2Soap_address = LocalProperties.getProperty("acorde2Soap_address");
				StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			
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
					
					String errMsg = msr.getAcordeError().getErrorMessage();
					
					if ((errMsg != null) && (errMsg.length() > 0))
						log.error(errMsg);

					ctx.session().removeAttribute(Constants.lucId);

				}
			}

			Object object = ctx.session().getAttribute("start");

			if (object != null)

			{
				if (object.toString().length() > 0) {
					start = object.toString();
				}

			}
			// forward to the jsp page
			if (start.equals("main")) {
				ctx.forwardToAction("main/docexep?ctrl=maintabset&action=TabClick&param=tab4");
			} else {
				ctx.forwardToAction("secondarymain/docexep?ctrl=secondarymaintabset&action=TabClick&param=tab4");
			}
			log.debug("End save_onClick");
		} catch (Exception t) {
			log.error("saved search result not found");
			ActionMessages ams = new ActionMessages();
			ActionMessage am = new ActionMessage("Exception " + t.getClass().getName() + " thrown during saved search with message: " + t.getMessage());
			ams.add("Error", am);
			ctx.addErrors(ams);
			ctx.forwardToInput();
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
	 * Creates the DesignModel for our form
	 * 
	 * @return The designmodel for the form
	 */

	protected FormDesignModel getDesignModel(SavedSearchPrompt ssps[], String docTypeCode, DocumentMetaDataForm dmf) {

		ImageMap imageMap = new ImageMap();
		imageMap.addImage("warning", new ImageModelImp("fw/def/image/severity/imgWarning13x13.gif", 13, 13));
		imageMap.addImage("info", new ImageModelImp("fw/def/image/severity/imgInfo13x13.gif", 13, 13));
		imageMap.addImage("lnkExternal", new ImageModelImp("fw/def/image/severity/lnkExternal.gif", 13, 13));
		boolean isapplicantEditable = false;
		boolean isotherEditable = false;

		if (Constants.applicantLevelDocs.indexOf(docTypeCode) > 0) {
			isapplicantEditable = true;
			isotherEditable = false;
		} else {
			isotherEditable = true;
		}
		FormDesignModel form = new FormDesignModelImp();
		form.setFormId("frmEdit");

		form.setFormType(FormType.EDIT);
		form.setCaption("form.doc.metadata.edit");
		form.setWidth("930");
		form.setAlignment(AlignmentType.LEFT);
		form.setShowFrame(true);
		form.setImageMap(imageMap);

		FormGroupElement row = null;
		FormGroupElement section = new FormGroupElement();
		section.setTitle("form.doc.applicantion");
		section.setShowFrame(false);

		// add row
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		row.setAlignment(AlignmentType.LEFT);

		FormHtmlElement htmlformElement = null;
		htmlformElement = new FormHtmlElement();
		htmlformElement.setFilter(false);
		htmlformElement.setColSpan(2);
		FormControlElement element = createSelectElement("form.doc.DocType", "docCode", 30, 30, 1, true, ACMICache
				.getDocumentTypeList(), "docCode", "docType", false);
		row.addFormElement(element);

		FormButtonContainer buttonsection1 = new FormButtonContainer();
		buttonsection1.setHeight("18");
		buttonsection1.setOrientation(OrientationType.HORIZONTAL);
		buttonsection1.setShowFrame(false);
		buttonsection1.setWidth("10");
		buttonsection1.setAlignment(AlignmentType.TOP);
		FormButtonElement button1 = new FormButtonElement();
		buttonsection1.setTitle("Reload MD");
		buttonsection1.joinElements();
		button1.setId("btnReload");
		button1.setName("btnReload");
		button1.setText("Reload");
		button1.setLocaleName("false");
		button1.setTooltip("Refresh");
		buttonsection1.addFormElement(button1);
		row.addFormElement(buttonsection1);
		row.addFormElement(htmlformElement);
		row.setJoin(true);
		section.addFormElement(row);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		element = createSelectElement("form.doc.Product", "product", 30, 30, 2, true, ACMICache.getProductNameMap(), "key",
				"value", false);
		row.addFormElement(element);
		element = createSelectElement("form.doc.State", "state", 30, 30, 1, true, ACMICache.getStatesMap(), "key", "value", false);
		row.addFormElement(element);

		// createElement(row, "form.doc.State", "state", 2, 2, 1, true, true);
		// createElement(row, "form.doc.Product", "product", 8, 8, 1, true, true);
		section.addFormElement(row);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.PolicyNumber", "policyNumber", 15, 15, 1, true, false);
		createElement(row, "form.doc.AgentName", "agentName", 25, 25, 1, true, false);
		createElement(row, "form.doc.AgentNumber", "agentNumber", 5, 5, 1, true, false);

		section.addFormElement(row);

		// add row
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.Employer", "employer", 25, 25, 1, isotherEditable, false);
		createElement(row, "form.doc.LBID", "lbid", 15, 15, 1, false, false);
		section.addFormElement(row);

		form.addFormElement(section);

		section = new FormGroupElement();
		section.setTitle("form.doc.key.applicant");
		section.setShowFrame(false);
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.KeyAppLastName", "keyAppLastName", 25, 25, 1, true, false);
		createElement(row, "form.doc.KeyAppFirstName", "keyAppFirstName", 25, 25, 1, true, false);
		createElement(row, "form.doc.KeyAppMiddle", "keyAppMiddle", 1, 1, 1, true, false);
		createElement(row, "form.doc.KeyAppSuffix", "keyAppSuffix", 4, 4, 1, true, false);

		section.addFormElement(row);
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.KeyAppSSN", "keyAppSSN", 9, 9, 1, true, false);
		createElement(row, "form.doc.KeyAppDOB", "keyAppDOB", 10, 10, 1, true, false);
		section.addFormElement(row);
		form.addFormElement(section);

		section = new FormGroupElement();
		section.setTitle("form.doc.applicant");
		section.setShowFrame(false);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);

		createElement(row, "form.doc.AppLastName", "appLastName", 25, 25, 1, isapplicantEditable, false);
		createElement(row, "form.doc.AppFirstName", "appFirstName", 25, 25, 1, isapplicantEditable, false);
		createElement(row, "form.doc.AppMiddle", "appMiddle", 1, 1, 1, isapplicantEditable, false);
		createElement(row, "form.doc.AppSuffix", "appSuffix", 4, 4, 1, isapplicantEditable, false);

		section.addFormElement(row);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		createElement(row, "form.doc.AppDOB", "appDOB", 10, 10, 1, isapplicantEditable, false);
		section.addFormElement(row);
		form.addFormElement(section);

		section = new FormGroupElement();
		section.setTitle("form.doc.details");
		section.setShowFrame(false);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		ListDataModel ldl = getSimpleListDataModel();

		createSelectElement(row, "form.doc.epmReviewed", "epmReviewed", 1, 3, 1, true, ldl, "key", "value", false);
		createSelectElement(row, "form.doc.CFP", "cfp", 1, 3, 1, true, ldl, "key", "value", false);
		createElement(row, "form.doc.CheckMaker", "checkMaker", 25, 25, 1, isotherEditable, false);
		section.addFormElement(row);
		form.addFormElement(section);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);

		createElement(row, "form.doc.DocDescription", "docDescription", 35, 35, 2, true, false);
		section.addFormElement(row);

		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);

		htmlformElement = new FormHtmlElement();
		htmlformElement.setLabel("");
		htmlformElement.setBodyInclude(true);
		htmlformElement.setColSpan(2);
		htmlformElement.setAlignment(AlignmentType.LEFT);
		htmlformElement.setHtml("Click to " + dmf.getURL() + " the document");
		htmlformElement.setFilter(false);
		row.addFormElement(htmlformElement);

		section.addFormElement(row);

		// ------------------------------
		// Add a button section
		// ------------------------------
		row = new FormGroupElement();
		row.setOrientation(OrientationType.HORIZONTAL);
		row.setJoin(true);

		FormButtonContainer buttonsection = new FormButtonContainer();
		buttonsection.setColSpan(4);
		buttonsection.setShowFrame(true);
		buttonsection.setAlignment(AlignmentType.BASELINE);
		buttonsection.setDefaultButtonId("btnSave");

		FormButtonElement button2 = new FormButtonElement();
		button2 = new FormButtonElement();
		button2.setId("btnSave");
		button2.setName("btnSave");
		button2.setFilter(false);
		button2.setLocaleName("false");
		button2.setText("Save");
		button2.setTooltip("Save changes and return");
		button2.setHandler(ClientEvent.ONCLICK, "return validateInput();");
		buttonsection.addFormElement(button2);

		row.addFormElement(buttonsection);

		FormButtonElement button = new FormButtonElement();
		button.setFilter(false);
		button.setId("btnCancel");
		button.setName("btnCancel");
		button.setText("button.title.cancel");
		button.setLocaleName("true");
		button.setTooltip("button.title.cancel");
		buttonsection.addFormElement(button);
		form.addFormElement(row);
		return form;
	}

	/**
	 * createElement dynamically creates simple text element
	 * 
	 * @param row
	 * @param labelName
	 * @param property
	 * @param size
	 * @param width
	 * @param colspan
	 * @param isEditable
	 * @param required
	 */
	private void createElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
			boolean isEditable, boolean required) {

		FormControlElement element = null;
		TextDesignModel text = null;
		FormLabelDesignModel label = null;

		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		// label.setWidth(width1Colum);
		label.setLocaleName("true");

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		text = new TextDesignModelImp();
		text.setProperty(property);
		text.setMaxLength(width);
		text.setSize(size);
		text.setTooltip("form.tooltip");
		text.setTooltip(property);
		text.setHandler(ClientEvent.ONCHANGE, "dataChanged();");

		if (!isEditable) {
			text.setDisabled(true);
			text.setStyle(style);
		}

		element.setColSpan(colspan);
		element.setControl(new TextControl(text));
		row.addFormElement(element);

	}

	/**
	 * createSelectElement method dynamically creates select drop down list
	 * 
	 * @param row
	 * @param labelName
	 * @param property
	 * @param size
	 * @param width
	 * @param colspan
	 * @param isEditable
	 * @param ldm
	 * @param keyProperty
	 * @param valueProperty
	 * @param required
	 */
	private void createSelectElement(FormGroupElement row, String labelName, String property, int size, int width, int colspan,
			boolean isEditable, ListDataModel ldm, String keyProperty, String valueProperty, boolean required) {

		FormControlElement element = null;
		FormLabelDesignModel label = null;
		SelectDesignModel select = null;
		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		label.setLocaleName("true");

		select = new SelectDesignModelImp();
		select.setMultiple(false);
		select.setProperty(property);
		select.setLocaleName("true");
		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
		select.setTooltip(property);

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		if (!isEditable) {
			select.setDisabled(false);
			select.setStyle(style);

		}
		SelectControl ctrl = new SelectControl(select);

		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
		element.setColSpan(colspan);
		element.setControl(ctrl);
		row.addFormElement(element);

	}

	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
			boolean isEditable, ListDataModel ldm, String keyProperty, String valueProperty, boolean required) {

		FormControlElement element = null;
		FormLabelDesignModel label = null;
		SelectDesignModel select = null;
		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		label.setLocaleName("true");

		select = new SelectDesignModelImp();
		select.setMultiple(false);
		select.setProperty(property);
		select.setLocaleName("true");
		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
		select.setTooltip(property);

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		if (!isEditable) {
			select.setDisabled(false);
			select.setStyle(style);

		}
		SelectControl ctrl = new SelectControl(select);

		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
		element.setColSpan(colspan);
		element.setControl(ctrl);
		return element;
	}

/*	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
			boolean isEditable, HashMap ldm, String keyProperty, String valueProperty, boolean required) {

		FormControlElement element = null;
		FormLabelDesignModel label = null;
		SelectDesignModel select = null;
		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		label.setLocaleName("true");

		select = new SelectDesignModelImp();
		select.setMultiple(false);
		select.setProperty(property);
		select.setLocaleName("true");
		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
		select.setTooltip(property);

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		if (!isEditable) {
			select.setDisabled(false);
			select.setStyle(style);

		}
		SelectControl ctrl = new SelectControl(select);
		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
		element.setColSpan(colspan);
		element.setControl(ctrl);
		return element;
	}
*/
	/**
	 * createSelectElement method creates select text box
	 * @param labelName
	 * @param property
	 * @param size
	 * @param width
	 * @param colspan
	 * @param isEditable
	 * @param ldm
	 * @param keyProperty
	 * @param valueProperty
	 * @param required
	 * @return
	 */
	private FormControlElement createSelectElement(String labelName, String property, int size, int width, int colspan,
			boolean isEditable, TreeMap ldm, String keyProperty, String valueProperty, boolean required) {

		FormControlElement element = null;
		FormLabelDesignModel label = null;
		SelectDesignModel select = null;
		label = new FormLabelDesignModelImp();
		label.setNowrap(new Boolean(true));
		label.setText(labelName);
		label.setLocaleName("true");

		select = new SelectDesignModelImp();
		select.setMultiple(false);
		select.setProperty(property);
		select.setLocaleName("true");
		select.setHandler(ClientEvent.ONCHANGE, "dataChanged();");
		select.setTooltip(property);

		element = new FormControlElement();
		element.setLabel(label);
		element.setRequired(required);

		if (!isEditable) {
			select.setDisabled(false);
			select.setStyle(style);

		}
		SelectControl ctrl = new SelectControl(select);

		ctrl.setOptions(getOptionListDesignModel(keyProperty, valueProperty), ldm);
		element.setColSpan(colspan);
		element.setControl(ctrl);
		return element;
	}

	/**
	 * Returns the Designmodel of the Optionlist for our Select element
	 * 
	 * @return OptionListDesignModel
	 */
	protected OptionListDesignModel getTreeOptionListDesignModel() {

		OptionListDesignModel optionList = new OptionListDesignModelImp();
		optionList.setEmpty("<Please select>");
		optionList.setFilter(true);
		optionList.setKeyProperty("uniqueKey");
		optionList.setLabelProperty("name");
		optionList.setStyle("color: navy;");

		return optionList;
	}

	protected OptionListDesignModel getOptionListDesignModel(String labelKey, String propertyKey) {

		OptionListDesignModel optionList = new OptionListDesignModelImp();
		optionList.setFilter(true);
		optionList.setKeyProperty(labelKey);
		optionList.setLabelProperty(propertyKey);
		optionList.setStyle("color: navy;");

		return optionList;
	}

	protected OptionListDesignModel getListDesignModel() {

		OptionListDesignModel optionList = new OptionListDesignModelImp();
		optionList.setFilter(true);
		optionList.setKeyProperty("key");
		optionList.setLabelProperty("title");
		optionList.setStyle("color: navy;");
		return optionList;
	}

	protected ListDataModel getSimpleListDataModel() {

		SimpleType st[] = new SimpleType[3];
		st[0] = new SimpleType("", "");
		st[1] = new SimpleType("N", "No");
		st[2] = new SimpleType("Y", "Yes");
		ListDataModel data = new SimpleList(st);
		return data;
	}
}