package com.epm.acmi.struts.form.dsp;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import com.Optika.message.AvailableApplication;
import com.Optika.message.AvailableApplications;
import com.Optika.message.CreateDocumentRequest;
import com.Optika.message.CreateDocumentResponse;
import com.Optika.message.IndexDefinition;
import com.Optika.message.IndexDefinitionFieldValue;
import com.Optika.message.IndexResult;
import com.Optika.message.ModifyIndexResponse;
import com.Optika.message.ModifyIndexResult;
import com.Optika.message.SavedSearchPrompt;
import com.Optika.message.SavedSearchResult;
import com.Optika.message.SavedSearchResultsArray;
import com.Optika.message.SystemFields;
import com.Optika.message.UserDefinedField;
import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.http.HttpUtil;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.DocumentMetaDataForm;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.DateUtility;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.MiscellaneousUtils;
import com.epm.acmi.util.PasswordEncryption;
import com.epm.acmi.util.StellentUpdateAudit;
import com.epm.stellent.StellentAdapterFactory;
import com.epm.stellent.adapter.StellentAdapter;

/**
 * This class uses Stellent Adapter to retrive document and create data structure required for web application
 * 
 * @author Jay Hombal
 */

public class StellentClient {

	private static final String STELLENT_WEB_SERVICE_FILE_RECEIVER_URL = "stellfilereceiver_ws";
	
	private static final String PDF_OUTPUT_DIR = "PDF_OUTPUT_DIR";

	private static final String NULL_STRING = "";

	private static Logger log = Logger.getLogger(StellentClient.class);
	
	private static String iupsAIID = ""; 

	private static String acorde2Soap_address = LocalProperties.getProperty("acorde2Soap_address");

	private static final Map userStellentTickets = new HashMap();
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	
	private static CreateDocumentRequest templateCDR = null;

	private static Object lock = new Object();
	
	/**
	 * Builds Document Display List
	 * 
	 * @param GFID
	 * @param AIID
	 * @return DisplayDocumentList
	 */
	public static DisplayDocumentList getDisplayDocumentList(String GFID, String AIID) throws Exception {
		DisplayDocumentList docList = null;

		try {
			if (isEmptyString(GFID))
				throw new Exception("Cannot Display document list with empty GFID");
			
			String searchName = Constants.allDocsSearchName;
	
			SavedSearchPrompt ssps[] = ACMICache.getSavedSearchPrompts(searchName);
			// SavedSearchPrompt ssps[] = null;
			if (ssps == null) {
				log.debug("Saved Search Prompts are not cached, so rebuilding the cache");
				ACMICache acmiCache = new ACMICache();
				acmiCache.loadStellentSearchPromptCache(AIID);
				log.debug("Saved Search Prompts cache rebuild");
			}
	
			ssps = ACMICache.getSavedSearchPrompts(searchName);
	
			for (int j = 0; j < ssps.length; j++) {
				SavedSearchPrompt lssp = ssps[j];
				String cmgfid = lssp.getDisplayText();
				if (cmgfid.equalsIgnoreCase(Constants.gfid)) {
					lssp.setValue(GFID);
					break;
				}
			}

			StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			String username = LocalProperties.getProperty("stellentUser");

			String password = PasswordEncryption.getDecryptedPassword(username);
			String lic_type = LocalProperties.getProperty("stellent_lic_type");

			SavedSearchResult[] ssrs = sAdapter
					.executeSavedSearch(searchName, username, password, lic_type, Constants.gfid, GFID);

			docList = buildDocumentDataModel(ssrs, AIID);
		} catch (Exception ex) {
			log.error("Failed to retrive Documents", ex);
			throw ex;
		}

		return docList;
	}


	public static DisplayDocumentList getDisplayDocumentListInstance(String GFID, String AIID, ACMICache ACMICacheInstance)
			throws Exception {
		DisplayDocumentList docList = null;

		try {
			if (isEmptyString(GFID))
				throw new Exception("Can't execute saved search with empty GFID");
			
			String searchName = Constants.allDocsSearchName;
	
			//Get the cached saved search prompt instance, no need to retrieve it every time from web service.
			SavedSearchPrompt ssps[] = ACMICache.getSavedSearchPromptsInstance(searchName);
	
			for (int j = 0; j < ssps.length; j++) {
				SavedSearchPrompt lssp = ssps[j];
				String cmgfid = lssp.getDisplayText();
				if (cmgfid.equalsIgnoreCase(Constants.gfid)) {
					lssp.setValue(GFID);
					break;
				}
			}

			//Retrieve results from saved search, and build document list.
			//Since using an MVC toolkit, we build the List data model instead...
			SavedSearchResult[] ssrs = getSavedSearchWithIUPSUser(searchName, Constants.gfid, GFID);
			docList = buildDocumentDataModel(ssrs, AIID);
		} catch (Exception ex) {
			log.error("Failed to retrieve documents", ex);
			throw ex;
		}

		return docList;
	}


	/**
	 * Retrieves search results for search saves with IUPS ID. 
	 * This is done since test users can't get to the saved searches IUPS uses.
	 * The method reuses a global token, and if the session for the token
	 * has expired, it gets a new token, and retries again...
	 * @param searchName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	private static SavedSearchResult[] getSavedSearchWithIUPSUser(String searchName, String fieldName, String fieldValue) throws Exception
	{
		String oldIUPSAIID = null;
		synchronized (StellentClient.class)
		{			
			if (isEmptyString(iupsAIID))
				retrieveIupsAIID();	
			
			oldIUPSAIID = iupsAIID;
		}
		
		try {
			return doGetSavedSearchWithIUPSUser(searchName, fieldName, fieldValue);
		} catch (Exception e) {
			synchronized (StellentClient.class)
			{
				//Assume current IUPS AAID is not working, retrieve new one...
				if (oldIUPSAIID == iupsAIID)
					retrieveIupsAIID();	
			}
			
			return doGetSavedSearchWithIUPSUser(searchName, fieldName, fieldValue);			
		}
	}
	
	/**
	 * Basic search with global stellent token.
	 * @param searchName
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 * @throws Exception
	 */
	private static SavedSearchResult[] doGetSavedSearchWithIUPSUser(String searchName, String fieldName, String fieldValue) throws Exception {
		StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
		SavedSearchResult[] ssrs = sAdapter.executeSavedSearch(iupsAIID, searchName, fieldName, fieldValue);
		return ssrs;
	}
	
	/**
	 * Generating a global stellent token by login in...
	 * @throws Exception
	 */
	private static void retrieveIupsAIID() throws Exception {
		iupsAIID = StellentClient.login();
	}
		
	/**
	 * Builds DataModel for Document Display List
	 * 
	 * @param ssr
	 * @return
	 */
	private static DisplayDocumentList buildDocumentDataModel(SavedSearchResult ssr[], String AIID) throws Exception {
		DisplayDocumentList docList = null;
		if (ssr == null) {
			log.error("No Search Data available");
			return null;
		} else {
			DisplayDocument dispDocs[] = new DisplayDocument[ssr.length];

			for (int k = 0; k < ssr.length; k++) {
				SavedSearchResult ssrs = ssr[k];
				SystemFields sfs = ssrs.getSystemFields();

				DisplayDocument dispDoc = new DisplayDocument();

				dispDoc.setLUCID(sfs.getLUCID());
				dispDoc.setINDEXPROVIDER(sfs.getINDEXPROVIDER());
				dispDoc.setMIMETYPE(sfs.getMIMETYPE());
				dispDoc.setROWIDENTIFIER(sfs.getROWIDENTIFIER());
				dispDoc.setTABLENAME(sfs.getTABLENAME());
				dispDoc.setSSPROVIDERID(sfs.getSSPROVIDERID());

				StringBuffer sb = new StringBuffer();
				sb.append("http://" + LocalProperties.getProperty("StellentHost")
						+ "/ibpmweb/default.asp?ToolName=AWVWR&Viewer=Optika");
				sb.append("&LUCID=" + sfs.getLUCID());
				sb.append("&MIMETYPE=image/tiff");
				sb.append("&TABLENAME=" + HttpUtil.urlEncode(sfs.getTABLENAME()));
				sb.append("&ROWIDENTIFIER=" + HttpUtil.urlEncode(sfs.getROWIDENTIFIER()));
				sb.append("&EOF=1");
				sb.append("&xmlAIID=" + AIID);

				dispDoc.setSystemFields(sfs);
				String encodedURL = sb.toString();

				dispDoc.setURL("<a href='" + encodedURL + "' target=_blank>View</a>");
				UserDefinedField udfs[] = ssrs.getUserDefinedFieldValues();

				for (int l = 0; l < udfs.length; l++) {
					UserDefinedField udf = udfs[l];
					if (udf.getName().equals("DocType")) {
						dispDoc.setDocType(udf.getValue());
						continue;
					}
					if (udf.getName().equals("DocDescription")) {
						dispDoc.setDocDescription(udf.getValue());
						continue;
					}
					if (udf.getName().equals("DocCode")) {
						dispDoc.setDocCode(udf.getValue());
						continue;
					}
					if (udf.getName().equals("AppFirstName")) {
						dispDoc.setAppFirstName(udf.getValue());
						continue;
					}
					if (udf.getName().equals("AppLastName")) {
						dispDoc.setAppLastName(udf.getValue());
						continue;
					}
					if (udf.getName().equals("AppMiddle")) {
						dispDoc.setAppMiddle(udf.getValue());
						continue;
					}
					if (udf.getName().equals("KeyAppFirstName")) {
						dispDoc.setKeyAppFirstName(udf.getValue());
						continue;
					}
					if (udf.getName().equals("KeyAppLastName")) {
						dispDoc.setKeyAppLastName(udf.getValue());
						continue;
					}
					if (udf.getName().equals("KeyAppMiddle")) {
						dispDoc.setKeyAppMiddle(udf.getValue());
						continue;
					}
										
					if (udf.getName().equals("epmReviewed")) {
						dispDoc.setReviewed(udf.getValue());
						continue;
					}
										
					if (udf.getName().equals("AgentNumber")) {
						dispDoc.setAgentNumber(udf.getValue());
						continue;
					}
										
					if (udf.getName().equals("AgentName")) {
						dispDoc.setAgentName(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("Product")) {
						dispDoc.setProduct(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("ScanDate")) {

						if (udf.getValue() != null) {
							try {
								String sd = sdf.format(DateUtility.convertStringToDate(udf.getValue()));
								// DateFormat.getDateInstance().format(DateUtility.convertStringToDate(udf.getValue()));
								dispDoc.setScanDate(sd);
							} catch (Exception ex) {
								log.error("scan date parse error");
								throw new Exception("scan date parse error");
							}
						}
						continue;
					}
					if (udf.getName().equals("GFID")) {
						dispDoc.setGFID(udf.getValue());
						continue;
					}
					if (udf.getName().equals("CFP")) {
						dispDoc.setCFP(udf.getValue());
						continue;
					}
					if (udf.getName().equals("Annotation")) {
						if (udf.getValue().equals("1")) {
							dispDoc.setAnnotate("Y");
						} else {
							dispDoc.setAnnotate("N");
						}

						continue;
					}
				}
				dispDocs[k] = dispDoc;
				docList = new DisplayDocumentList(dispDocs);
			}

		}
		return docList;
	}


	/**
	 * Upload New Document Client Method
	 * 
	 * @param dmf
	 */
	public String createDocument(DocumentMetaDataForm dmf) throws Exception {
		return createDocument(dmf, null);
	}


	/**
	 * Upload New Document Client Method
	 * 
	 * @param dmf
	 * @param filename
	 */
	public String createDocument(DocumentMetaDataForm dmf, String filename) throws Exception {
		String AIID = null;
		String recId = NULL_STRING;
		
		try {

			AIID = login();

			StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			
			CreateDocumentRequest cdr = getNewCreateDocumentRequest();

			IndexDefinition[] ids = cdr.getIndexDefinitions();

			for (int k = 0; k < ids.length; k++) {
				IndexDefinition id = ids[k];
				String fileURI = null;

				if (filename == null) {
					fileURI = (new StringBuffer(getUploadFolder())).append(dmf.getFile()).toString();
				} else {
					fileURI = (new StringBuffer(getUploadFolder())).append(filename).toString();
				}
				
				String stellentFileTicket = uploadFile(fileURI);
			    
				id.setReferenceID("" + System.currentTimeMillis());

				if (filename == null) {
					id.setURI(stellentFileTicket);
				} else {
					id.setURI(stellentFileTicket);
				}
				
				id.setImplicitAppend(false);
				IndexDefinitionFieldValue[] idfvs = id.getFieldValues();

				for (int j = 0; j < idfvs.length; j++)
					fillFieldValue(idfvs[j], dmf);	
			}

			CreateDocumentResponse cdrs = sAdapter.uploadDocument(AIID, cdr);
			log.debug(cdrs.toString());
			IndexResult[] indexes = cdrs.getIndexResults();
			
			if (indexes.length > 0)
				recId = indexes[0].getSystemFields().getLUCID();

			return recId;
		} catch (Exception re) {
			log.error("createDocument() Failed: " + re.getMessage(), re);
			throw re;
		} finally {
			if (AIID != null)
				logout(AIID);
		}
	}

	private CreateDocumentRequest getNewCreateDocumentRequest() throws Exception
	{
		String AIID = null;
		
		if (templateCDR == null)
		{
			synchronized (lock)
			{
				//Had to ask again to avoid race conditions...
				if (templateCDR == null)
				{
					try
					{
						AIID = login();
			
						StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			
						AvailableApplication aApps[] = sAdapter.getAvailableApplications(AIID);
			
						// AvailableApplication aApps[] = aaApps.getAvailableApplication();
						AvailableApplications nNewApps = new AvailableApplications();
						// naApps[] = new ArrayOfAvailableApplication();
						AvailableApplication naApps[] = new AvailableApplication[1];
						// naaApps.setAvailableApplication(naApps);
						nNewApps.setApplications(naApps);
			
						for (int i = 0; i < aApps.length; i++) {
							AvailableApplication aApp = aApps[i];
							aApp.setSelect(true);
							if (aApp.getName().equals(Constants.NEWBIZMain)) {
								naApps[0] = aApp;
							}
						}
			
						templateCDR = sAdapter.getApplicationFieldsForCreateDocument(AIID, nNewApps);
					}  finally {
						if (AIID != null)
							logout(AIID);
					}
				}
			}
		}
		
		return (CreateDocumentRequest)MiscellaneousUtils.deepClone(templateCDR);
	}
	
    /**
     * Fills one index meta-data value from form data
     * 
     * @param idfv
     * @param dmf
     */
    private void fillFieldValue(IndexDefinitionFieldValue idfv, DocumentMetaDataForm dmf)
    {
    	String fieldName = idfv.getName();

		if (fieldName.equals("GFID")) {
			if (dmf.getGfid() != null)
				idfv.setValue(dmf.getGfid());
		}
		else if (fieldName.equals("DocType")) {
			String type = ACMICache.getDocType(dmf.getDocCode());
			idfv.setValue(type);
		}
		else if (fieldName.equals("DocCode")) {
			if (dmf.getDocCode() != null)
				idfv.setValue(dmf.getDocCode());
		}
		else if (fieldName.equals("PolicyNumber")) {
			if (dmf.getPolicyNumber() != null)
				idfv.setValue(dmf.getPolicyNumber());
		}
		else if (fieldName.equals("State")) {
			if (dmf.getState() != null)
				idfv.setValue(dmf.getState());
		}
		else if (fieldName.equals("KeyAppSSN")) {
			if (dmf.getKeyAppSSN() != null)
				idfv.setValue(dmf.getKeyAppSSN());
		}
		else if (fieldName.equals("KeyAppFirstName")) {
			if (dmf.getKeyAppFirstName() != null)
				idfv.setValue(dmf.getKeyAppFirstName());
		}
		else if (fieldName.equals("KeyAppMiddle")) {
			if (dmf.getKeyAppMiddle() != null)
				idfv.setValue(dmf.getKeyAppMiddle());
		}
		else if (fieldName.equals("KeyAppLastName")) {
			if (dmf.getKeyAppLastName() != null)
				idfv.setValue(dmf.getKeyAppLastName());
		}
		else if (fieldName.equals("KeyAppSuffix")) {
			if (dmf.getKeyAppSuffix() != null)
				idfv.setValue(dmf.getKeyAppSuffix());	
		}
		else if (fieldName.equals("KeyAppDOB")) {
			if (dmf.getKeyAppDOB() != null && !dmf.getKeyAppDOB().equals("")) {
				String dt = sdf.format(DateUtility.convertStringToDate(dmf.getKeyAppDOB()));
				idfv.setValue(dt);
			} else {
				idfv.setValue("01/01/1801");
			}
		}
		else if (fieldName.equals("AppFirstName")) {
			if (dmf.getAppFirstName() != null)
				idfv.setValue(dmf.getAppFirstName());
		}
		else if (fieldName.equals("AppMiddle")) {
			if (dmf.getAppMiddle() != null)
				idfv.setValue(dmf.getAppMiddle());	
		}
		else if (fieldName.equals("AppLastName")) {
			if (dmf.getAppLastName() != null)
				idfv.setValue(dmf.getAppLastName());			
		}
		else if (fieldName.equals("AppSuffix")) {
			if (dmf.getAppSuffix() != null)
				idfv.setValue(dmf.getAppSuffix());			
		}
		else if (fieldName.equals("AppDOB")) {
			if (null != dmf.getAppDOB() && !dmf.getKeyAppDOB().equals("")) {
				String dt = sdf.format(DateUtility.convertStringToDate(dmf.getAppDOB()));
				idfv.setValue(dt);
			} else {
				idfv.setValue("01/01/1801");
			}			
		}
		else if (fieldName.equals("ScanDate")) {
			String dt = sdf.format(new Date(System.currentTimeMillis()));
			idfv.setValue(dt);			
		}
		else if (fieldName.equals("Product")) {
			if (dmf.getProduct() != null)
				idfv.setValue(dmf.getProduct());
		}
		else if (fieldName.equals("LBID")) {
			if (dmf.getLbid() != null)
				idfv.setValue(dmf.getLbid());
		}
		else if (fieldName.equals("DocDescription")) {
			if (dmf.getDocDescription() != null)
				idfv.setValue(dmf.getDocDescription());
		}
		else if (fieldName.equals("epmReviewed")) {
			if (dmf.getEpmReviewed() != null)
				idfv.setValue(dmf.getEpmReviewed());
		}
		else if (fieldName.equals("CFP")) {
			if (dmf.getCfp() != null)
				idfv.setValue(dmf.getCfp());
		}
		else if (fieldName.equals("Employer")) {
			if (dmf.getEmployer() != null)
				idfv.setValue(dmf.getEmployer());
		}
		else if (fieldName.equals("AgentName")) {
			if (dmf.getAgentName() != null)
				idfv.setValue(dmf.getAgentName());
		}
		else if (fieldName.equals("AgentNumber")) {
			if (dmf.getAgentNumber() != null)
				idfv.setValue(dmf.getAgentNumber());
		}
    }

	/**
	 * Gets the upload folder from the class location...
	 * @return
	 */
    /*
	private static String getUploadFolder_Old()
	{
		URL codeSourceURL = StellentClient.class.getProtectionDomain().getCodeSource().getLocation();
		String codeSource = codeSourceURL.getPath();
		int index = codeSource.indexOf(WEB_INF_CLASSES);
		StringBuffer result = new StringBuffer(codeSource.substring(0, index));
		result.append(UPLOAD_SUFFIX);
		return result.toString();
	}
*/
	/**
	 * Gets the upload folder from the class location...
	 * @return
	 */
	private static String getUploadFolder()
	{		
		return LocalProperties.getProperty(PDF_OUTPUT_DIR);
	}
	
	/**
	 * 
	 * @param targetFileName
	 * @return
	 */
	private static String uploadFile(String targetFileName) {
		String responseString = null;
		String targetURL = LocalProperties.getProperty(STELLENT_WEB_SERVICE_FILE_RECEIVER_URL);
		PostMethod filePost = new PostMethod(targetURL);
		File targetFile = new File(targetFileName);
		filePost.getParams().setBooleanParameter(
				HttpMethodParams.USE_EXPECT_CONTINUE, false);

		try {

			Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					5000);
			int status = client.executeMethod(filePost);

			if (status == HttpStatus.SC_OK) {
				responseString = filePost.getResponseBodyAsString();
				log.debug("Upload complete, response=" + responseString);
			} else {
				responseString = HttpStatus.getStatusText(status);
				log.debug("Upload failed, response=" + responseString);
			}
		} catch (Exception ex) {
			log.error("ERROR: " + ex.getClass().getName() + " "
					+ ex.getMessage() + " , Target File Name = " + targetFileName );
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}

		return responseString;
	}
	
	/**
	 * Bulk Update of All Documents that Belong to GFID
	 * 
	 * @param AIID
	 * @param GFID
	 * @param policyNum
	 * @param keyAppFN
	 * @param keyAppLN
	 * @param keyAppMI
	 * @param keyAppSfx
	 * @param agentName
	 * @param agentNum
	 * @param lbId
	 * @param product
	 * @param state
	 */
	public void updateAllDocuments(final String userId, final String AIID, final String GFID, final String policyNum, final String keyAppFN, final String keyAppLN, final String keyAppMI,
			final String keyAppSfx, final String agentName, final String agentNum, final String lbId, final String product, final String state) throws Exception {
		

		if (isEmptyString(GFID))
			throw new Exception("Cannot update documents with empty or null GFID");
		
		Thread updateDocsThread = new Thread(new Runnable() {
			public void run()
			{
				try
				{
					doUpdateAllDocuments(userId, AIID, GFID, policyNum, keyAppFN, keyAppLN, keyAppMI,
							keyAppSfx, agentName, agentNum, lbId, product, state);
				} catch (Exception e)
				{
					log.error(e.getClass().getName() + " exception thrown while updating documents", e);
				}
			}
		});
		
		updateDocsThread.start();		
	}

	/**
	 * Trivial method to see if string is blank
	 * @param str
	 * @return
	 */
	private static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}


	private void doUpdateAllDocuments(String userId, String AIID, String GFID, String policyNum, String keyAppFN, String keyAppLN, String keyAppMI,
				String keyAppSfx, String agentName, String agentNum, String lbId, String product, String state) throws Exception {
		log.debug("Begin doUpdateAllDocuments");

		if (policyNum == null)
			policyNum = "";
		if (keyAppFN == null)
			keyAppFN = "";
		if (keyAppLN == null)
			keyAppLN = "";
		if (keyAppMI == null)
			keyAppMI = "";
		if (keyAppSfx == null)
			keyAppSfx = "";
		if (agentName == null)
			agentName = "";
		if (agentNum == null)
			agentNum = "";
		if (lbId == null)
			lbId = "";
		if (state == null)
			state = "";
		if (product == null)
			product = "";

		try {

			// Create Stellent Adapter Instance

			StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);

			// Get Saved Search Prompts for AllDocs
			// SavedSearchPrompt ssps[] = ACMICache.getSavedSearchPrompts(Constants.allDocsSearchName);
			SavedSearchPrompt ssps[] = sAdapter.getSavedSearchPrompts(AIID, Constants.allDocsSearchName);

			if (ssps == null) {
				ssps = sAdapter.getSavedSearchPrompts(AIID, Constants.allDocsSearchName);
			}

			// Retrive all docs that match the GFID

			for (int i = 0; i < ssps.length; i++) {
				SavedSearchPrompt ssp = ssps[i];

				if (ssp.getDisplayText().equals(Constants.gfid)) {
					ssp.setValue(GFID);
					break;
				}
			}

			SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(Constants.allDocsSearchName, AIID, ssps);

			SavedSearchResult ssrsorginal[] = ssrs;
			// Now update saved search result with new values
			if (ssrs != null) {
				if (ssrs.length > 0) {
					for (int k = 0; k < ssrs.length; k++) {

						SavedSearchResult ssr = ssrs[k];

						UserDefinedField orgUdfs[] = ssr.getUserDefinedFieldValues();
						String docType = "";
						for (int ou = 0; ou < orgUdfs.length; ou++) {
							UserDefinedField orgudf = orgUdfs[ou];
							if (orgudf != null) {
								if (orgudf.getName().equals("DocCode"))
									docType = orgudf.getValue();
								if (docType.equals("APP"))
									break;
							}
						}

						if (docType.equals("APP")) {
							log.debug("Begin Updating Application ");
							UserDefinedField udfs[] = new UserDefinedField[14];

							for (int l = 0; l < ssrs.length; l++) {

								UserDefinedField udf = new UserDefinedField();
								udf.setName("PolicyNumber");
								udf.setValue(policyNum);
								udfs[0] = udf;

								udf = new UserDefinedField();
								udf.setName("LBID");
								udf.setValue(lbId);
								udfs[1] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppSuffix");
								udf.setValue(keyAppSfx);
								udfs[2] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppFirstName");
								udf.setValue(keyAppFN);
								udfs[3] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppLastName");
								udf.setValue(keyAppLN);
								udfs[4] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppMiddleName");
								udf.setValue(keyAppMI);
								udfs[5] = udf;

								udf = new UserDefinedField();
								udf.setName("State");
								udf.setValue(state);
								udfs[8] = udf;

								udf = new UserDefinedField();
								udf.setName("LBID");
								udf.setValue(lbId);
								udfs[9] = udf;

								udf = new UserDefinedField();
								udf.setName("AgentName");
								udf.setValue(agentName);
								udfs[10] = udf;

								udf = new UserDefinedField();
								udf.setName("AgentNumber");
								udf.setValue(agentNum);
								udfs[11] = udf;

								udf = new UserDefinedField();
								udf.setName("Product");
								udf.setValue(product);
								udfs[12] = udf;

								udf = new UserDefinedField();
								udf.setName("DocFlags");
								udf.setValue("16");
								udfs[13] = udf;
							}
							ssr.setUserDefinedFieldValues(udfs);
							ssr.setIsSelected(true);

							SavedSearchResultsArray ssa = new SavedSearchResultsArray();
							ssa.setSearchResults(ssrsorginal);
							sAdapter.updateMetaData(AIID, ssa);
							log.debug("End Updating Application");

						}
					}

					for (int k1 = 0; k1 < ssrsorginal.length; k1++) {

						SavedSearchResult ssr = ssrsorginal[k1];

						UserDefinedField orgUdfs[] = ssr.getUserDefinedFieldValues();
						String docType = "";
						for (int ou = 0; ou < orgUdfs.length; ou++) {
							UserDefinedField orgudf = orgUdfs[ou];
							if (orgudf != null) {
								if (orgudf.getName().equals("DocCode"))
									docType = orgudf.getValue();
								if (!docType.equals("APP"))
									break;

							}
						}

						if (!docType.equals("APP")) {
							UserDefinedField udfs[] = new UserDefinedField[14];

							for (int l = 0; l < ssrs.length; l++) {

								UserDefinedField udf = new UserDefinedField();
								udf.setName("PolicyNumber");
								udf.setValue(policyNum);
								udfs[0] = udf;

								udf = new UserDefinedField();
								udf.setName("LBID");
								udf.setValue(lbId);
								udfs[1] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppSuffix");
								udf.setValue(keyAppSfx);
								udfs[2] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppFirstName");
								udf.setValue(keyAppFN);
								udfs[3] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppLastName");
								udf.setValue(keyAppLN);
								udfs[4] = udf;

								udf = new UserDefinedField();
								udf.setName("KeyAppMiddleName");
								udf.setValue(keyAppMI);
								udfs[5] = udf;

								udf = new UserDefinedField();
								udf.setName("State");
								udf.setValue(state);
								udfs[8] = udf;

								udf = new UserDefinedField();
								udf.setName("LBID");
								udf.setValue(lbId);
								udfs[9] = udf;

								udf = new UserDefinedField();
								udf.setName("AgentName");
								udf.setValue(agentName);
								udfs[10] = udf;

								udf = new UserDefinedField();
								udf.setName("AgentNumber");
								udf.setValue(agentNum);
								udfs[11] = udf;

								udf = new UserDefinedField();
								udf.setName("Product");
								udf.setValue(product);
								udfs[12] = udf;

								udf = new UserDefinedField();
								udf.setName("DocFlags");
								udf.setValue("16");
								udfs[13] = udf;
							}
							ssr.setUserDefinedFieldValues(udfs);
							ssr.setIsSelected(true);
						}
					}

					SavedSearchResultsArray ssa = new SavedSearchResultsArray();
					ssa.setSearchResults(ssrs);
					ModifyIndexResponse response = sAdapter.updateMetaData(AIID, ssa);
					
					ModifyIndexResult[] results = response.getModifyIndexResults();
					
					for (int i = 0; i < results.length; i++)
					{
						ModifyIndexResult result = results[i];
						String recId = result.getSystemFields().getLUCID();
						StellentUpdateAudit.auditStellentUpdate(userId, GFID, recId, StellentUpdateAudit.AUDIT_UPDATE);
					}
					
					log.debug("Finished updating all documents");
				}

			}
		} catch (Exception re) {
			log.error("updateAllDocuments() Failed: " + re.getMessage(), re);
			throw re;
		}
		
		log.debug("End doUpdateAllDocuments");
	}


	// This Method is only used by event tracker.

	/**
	 * Set CMS Reviewed Flag in Stellent
	 */
	public void setCMSReviewedFlag(String AIID, String recId) throws Exception {
		Category log = Category.getInstance("EventTrackerCat");

		if (recId == null) {
			log.error("RecId is null - CMSReviewed flag will not be updated");
			return;
		} else if (recId.length() == 0) {
			log.error("RecId is not valid - CMSReviewed flag will not be updated");
			return;
		}
		
		try {
			AIID = login();
			log.debug("Begin setCMSReviewedFlag() RECID " + recId);

			StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			String username = LocalProperties.getProperty("stellentUser");
			String password = PasswordEncryption.getDecryptedPassword(username);
			String lic_type = LocalProperties.getProperty("stellent_lic_type");

			// Get Saved Search Prompts for AllDocs
			SavedSearchPrompt ssps[] = sAdapter.getSavedSearchPrompts(AIID, Constants.allDocsSearchName);// ACMICache.getSavedSearchPrompts(Constants.allDocsSearchName);

			if (ssps == null) {
				ssps = sAdapter.getSavedSearchPrompts(AIID, Constants.allDocsSearchName);
			}

			// Retrive all docs that match the GFID

			for (int i = 0; i < ssps.length; i++) {
				SavedSearchPrompt ssp = ssps[i];
				if (ssp.getDisplayText().equals(Constants.recId)) {
					ssp.setValue(recId);
					break;
				}
			}

			SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(Constants.allDocsSearchName, username, password, lic_type,
					ssps);
			boolean iscmsreviewedfound = false;
			// Now update saved search result with new values
			if (ssrs != null) {
				if (ssrs.length > 0) {
					SavedSearchResult ssr = ssrs[0];

					UserDefinedField udfs[] = ssr.getUserDefinedFieldValues();

					for (int l = 0; l < (udfs.length); l++) {

						UserDefinedField udf = udfs[l];

						if (udf.getName().equals("cmsReviewed")) {
							udf.setValue("N");
							iscmsreviewedfound = true;
							break;
						}
					}

					if (iscmsreviewedfound) {
						ssr.setUserDefinedFieldValues(udfs);
						ssr.setIsSelected(true);
					} else {
						udfs = new UserDefinedField[1];
						UserDefinedField udf = new UserDefinedField();
						udfs[0] = udf;
						udf.setName("cmsReviewed");
						udf.setValue("N");
						ssr.setUserDefinedFieldValues(udfs);
						ssr.setIsSelected(true);
					}
				}

			}

			SavedSearchResultsArray ssa = new SavedSearchResultsArray();
			ssa.setSearchResults(ssrs);
			ModifyIndexResponse mrs = sAdapter.updateMetaData(AIID, ssa);
			log.debug(" Response " + mrs.getAcordeError().getErrorMessage());
		} catch (Exception ex) {
			log.error("updateAllDocuments() Failed: " + ex.getMessage(), ex);
			throw ex;
		} finally
		{
			if (AIID != null)
				logout(AIID);
		}

		log.debug("End setCMSReviewedFlag()");

	}


	/**
	 * Search Document by record Id (LUCID or RECID)
	 * 
	 * @param AIID
	 * @param fieldName
	 * @param value
	 * @return DisplayDocument
	 * @throws Exception
	 */
	public DisplayDocument searchDocumentByRecId(String AIID, String fieldName, String value) throws Exception {

		Category log = Category.getInstance("EventTrackerCat");
		DisplayDocument dispDoc = null;
		try {
			log.debug("Begin searchDocumentByRecId()" + fieldName + "=" + value);
			
			if (isEmptyString(value))
				throw new Exception("Cannot search documents with empty RecId");
			
			StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			String username = LocalProperties.getProperty("stellentUser");
			String password = PasswordEncryption.getDecryptedPassword(username);
			String lic_type = LocalProperties.getProperty("stellent_lic_type");

			String savedSearchName = Constants.allDocsSearchName;
			SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(savedSearchName, username, password, lic_type, fieldName,
					value);
			dispDoc = new DisplayDocument();

			if (ssrs != null && ssrs.length > 0) {
				SavedSearchResult ssr = ssrs[0];
				UserDefinedField udfs[] = ssr.getUserDefinedFieldValues();

				for (int l = 0; l < udfs.length; l++) {
					UserDefinedField udf = udfs[l];

					if (udf.getName().equals("AppFirstName")) {
						dispDoc.setAppFirstName(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("AppLastName")) {
						dispDoc.setAppLastName(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("AppMiddle")) {
						dispDoc.setAppMiddle(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("AppSuffix")) {
						dispDoc.setAppSuffix(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("KeyAppSuffix")) {
						dispDoc.setKeyAppSuffix(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("PolicyNumber")) {
						dispDoc.setPolicyNumber(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("KeyAppFirstName")) {
						dispDoc.setKeyAppFirstName(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("KeyAppLastName")) {
						dispDoc.setKeyAppLastName(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("KeyAppMiddle")) {
						dispDoc.setKeyAppMiddle(udf.getValue());
						continue;
					}

					if (udf.getName().equals("AgentNumber")) {
						dispDoc.setAgentNumber(udf.getValue());
						continue;
					}					

					if (udf.getName().equals("AgentName")) {
						dispDoc.setAgentName(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("Product")) {
						dispDoc.setProduct(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("GFID")) {
						dispDoc.setGFID(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("TempLBID")) {
						dispDoc.setCmslbId(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("State")) {
						dispDoc.setState(udf.getValue());
						continue;
					}
					
					if (udf.getName().equals("ScanDate")) {
						dispDoc.setScanDate(udf.getValue());
						continue;
					}

				}
			}
		} catch (Exception ex) {
			log.error("searchDocumentByRecId Failed", ex);
			throw ex;
		}
		log.debug("Begin searchDocumentByRecId()");
		return dispDoc;
	}


	/**
	 * Login as Stellent Admin (Stelrunp)
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String login() throws Exception {
		log.debug("Begin Stellent Web Application Authentication ...");
		String AIID = null;

		StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);

		try {

			String username = LocalProperties.getProperty("stellentUser");
			String password = PasswordEncryption.getDecryptedPassword(username);
			String lic_type = LocalProperties.getProperty("stellent_lic_type");
			AIID = sAdapter.login(username, password, lic_type);

		} catch (Exception ex) {
			log.error("Stellent Authentication Failed", ex);
			log.error("Stellent Authentication failed, Saved searche prompts are not cahced.");
			throw ex;
		}
		log.debug("Compete Stellent Web Application Authentication...");
		return AIID;
	}


	/**
	 * Logout from Stellent Session
	 * 
	 * @param AIID
	 * @throws Exception
	 */
	public static void logout(String AIID) throws Exception {
		log.debug("Begin Stellent Web Application logout...");

		StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);

		try {
			sAdapter.logout(AIID);
		} catch (Exception ex) {
			log.error("Stellent louout Failed", ex);
			log.error("Stellent logoutfailed, Saved searche prompts are not cahced.");
			throw ex;
		}
		log.debug("Compete Stellent Web Application logout...");

	}


	/**
	 * Retuurns AIID Login into Stellent Server
	 * 
	 * @param user
	 * @return String
	 * @throws Exception
	 */
	public static String login(User user) throws Exception {
		log.debug("Begin Stellent Web Application Authentication ...");
		String AIID = null;

		StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
		try {

			String username = user.getUserId();
			String password = user.getPassword();
			String lic_type = LocalProperties.getProperty("stellent_lic_type");
			AIID = sAdapter.login(username, password, lic_type);

		} catch (Exception ex) {
			log.error("Stellent Authentication Failed", ex);
			log.error("Stellent Authentication failed, Saved searche prompts are not cahced.");
			throw ex;
		}
		log.debug("Compete Stellent Web Application Authentication...");
		return AIID;

	}

	/**
	 * @param ctx
	 * @return
	 * @throws RemoteException
	 */
	public static String loginByUserNonRedundant(ActionContext ctx) throws RemoteException
	{
		User user = (User) ctx.session().getAttribute(Constants.loggedUser);
		return loginByUserNonRedundant(user);
	}
	/**
	 * @param user
	 * @return
	 * @throws RemoteException
	 */
	public static String loginByUserNonRedundant(User user) throws RemoteException
	{
		log.debug("StellentClient.loginByUserNonRedundant() - Start");
		StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
		String username = user.getUserId();		
		String AIID = getAIIDForUser(username);
		
		if (isEmptyString(AIID)) {
			try {

				String password = user.getPassword();
				String lic_type = LocalProperties.getProperty("stellent_lic_type");
				AIID = sAdapter.login(username, password, lic_type);
				//Set AIID, since it will be used in one of the action classes...
				user.setAAID(AIID);
				userStellentTickets.put(username, AIID);
				log.debug("StellentClient.loginByUserNonRedundant() - End");
			} catch (RemoteException ex) {
				log.error("Stellent Authentication Failed", ex);
				log.error("Stellent Authentication failed, Saved searche prompts are not cahced.");
				log.debug("StellentClient.loginByUserNonRedundant() - End (failed Stellent login)");
				throw ex;
			}
		}

		return AIID;
	}

	/**
	 * @param ctx
	 * @throws RemoteException
	 */
	public static void logoutByUserNonRedundant(ActionContext ctx) throws RemoteException{
		User user = (User) ctx.session().getAttribute(Constants.loggedUser);
		logoutByUserNonRedundant(user);
	}
	
	/**
	 * @param user
	 * @throws RemoteException
	 */
	public static void logoutByUserNonRedundant(User user) throws RemoteException{
		log.debug("StellentClient.logoutByUserNonRedundant() - Start");
		String username = user.getUserId();
		String AIID = getAIIDForUser(username);

		if (!isEmptyString(AIID)) {
			StellentAdapter sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);

			try {
				sAdapter.logout(AIID);
			} catch (RemoteException ex) {
				log.error("Stellent loguout Failed", ex);
				log.error("Stellent logoutfailed, Saved searche prompts are not cached.");
				log.debug("StellentClient.logoutByUserNonRedundant() - End (failed Stellent logout)");
				throw ex;
			}
			finally
			{
				user.setAAID(NULL_STRING);
				userStellentTickets.remove(username);
			}
		}		
		

		log.debug("StellentClient.logoutByUserNonRedundant() - End");
	}
	
	/**
	 * @param userName
	 * @return
	 */
	private static String getAIIDForUser(String userName)
	{
		String AIID = NULL_STRING;
		AIID = (String)userStellentTickets.get(userName);
		return AIID;
	}
		
	public static void main(String[] args)
	{
		//String fileName = "D:/Cesar/BPM/THREE-05-07-ART-DoYouHaveaBusOrientedArch-Favoron-Final.pdf";
		//StellentClient.uploadFile(fileName);
		log.debug(getUploadFolder());
	}
	// public static void main(String args[]) throws Exception {
	// StellentClient sc = new StellentClient();
	// DisplayDocument dc = sc.searchDocumentByRecId(AIID, "RECID", "12451890");
	//
	// // sc.updateAllDocuments(AIID,"11111","55555","Mukta","Hombal","M","Mrs","123456789","12/12/1973",
	// // "Rohan","12345","89898", "PHSP", "MD");
	//
	// }

}
