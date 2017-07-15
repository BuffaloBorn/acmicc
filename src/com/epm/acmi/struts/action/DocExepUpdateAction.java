package com.epm.acmi.struts.action;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.Optika.message.SavedSearchPrompt;
import com.Optika.message.SavedSearchResult;
import com.Optika.message.SystemFields;
import com.Optika.message.UserDefinedField;
import com.cc.acmi.common.Forwards;
import com.cc.acmi.common.Messages;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.http.HttpUtil;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.DocumentMetaDataForm;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.DateUtility;
import com.epm.acmi.util.LocalProperties;
import com.epm.stellent.StellentAdapterFactory;
import com.epm.stellent.adapter.StellentAdapter;

/**
 * DocExepUpdateAction handles document excpetion page actions
 * @author Jay Hombal
 *
 */
public class DocExepUpdateAction extends CCAction {

	private static Logger log = Logger.getLogger(DocExepUpdateAction.class);
	String width1Colum = Integer.toString(160);

	String script = "this.blur();";

	String style = "background-color:#E9E2E1";

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
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
			 * Note: Each user when logging into EPM WebApp will obtain a AAID (Authentication Ticket) From ACMI web
			 * application
			 */

			AAID = StellentClient.login(); 
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
					ctx.session().setAttribute(Constants.docMetaData, ssr);
					ctx.session().setAttribute(Constants.lucId, lucId);
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
							if (docCode==null) {
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
						if (udf.getName().equals("state")) {
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
		
		log.debug("End doExecute");
		ctx.forwardToInput();
	}
	

	private static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}

}
