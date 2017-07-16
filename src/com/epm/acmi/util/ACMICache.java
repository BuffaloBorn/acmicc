package com.epm.acmi.util;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.naming.directory.DirContext;
import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.Optika.message.SavedSearchPrompt;
import com.Optika.message.SavedSearchResult;
import com.Optika.message.UserDefinedField;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.dsp.DocumentType;
import com.epm.acmi.struts.form.dsp.DocumentTypeList;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.stellent.StellentAdapterFactory;
import com.epm.stellent.adapter.StellentAdapter;
import com.isdiary.entirex.WSConditionCodesCall;
import com.isdiary.entirex.WSMemoCodesCall;
import com.isdiary.entirex.WSPersonStatusCodesCall;
import com.isdiary.entirex.WSPersonTypesCodesCall;
import com.isdiary.entirex.WSStdEventCodesCall;
import com.isdiary.entirex.WSStdEventCodesNoCall;
import com.isdiary.entirex.WSSubStandardReasonCall;
import com.isdiary.entirex.WSUndStatusCodesCall;


import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ACMICache Caches static information Populated from start up servelet
 * 
 * @author Dragos Mandruliano
 * @version $Revision: 1.21 $
 */
public class ACMICache {
	private static final String TRUE = "T";
	private static final String EQUALS = "=";
	private static final String NULL_STRING = "";
	private static final String DOCTYPE_COL_MAP = "doctypecolmap";
	
	//DocumentType field names:
	private static final String DOC_TYPE = "docType";
	private static final String DOC_CODE = "docCode";
	private static final String UPLOADABLE = "isUploadable";
	
	private static Map usersByRole = new HashMap();
	
	private static final Object lock = new Object();

	private static HashMap users;

	private static ArrayList userGroups = new ArrayList();

	private static HashMap searchprompts;

	private static HashMap searchPromptsInstance;


	private static DocumentTypeList docTypes;
	
	private static DocumentTypeList docTypesUploadable;


	private static TreeMap statesMap;


	private static TreeMap statusMap;


	private static TreeMap productNameMap;
	
	private static TreeMap obsoleteProductNameMap;


	private static TreeMap otsReasonMap;
	
	private static TreeMap unableToCompleteReasonMap;


	private static TreeMap returnReasonMap;
	
	private static TreeMap StdEventCodes;
	
	private static TreeMap StdEventCodesDescription;
	
	private static TreeMap UnderWriterStatusCodes;
	
	private static TreeMap PersonStatusCodes;
	
	private static TreeMap PersonTypesCodes;
	
	private static TreeMap ConditionCodes;
	
	private static TreeMap ConditionCodesExtendedFields;
	
	private static TreeMap SubStandardReason;
	
	private static TreeMap SubStandardReasonLookup;
	
	private static TreeMap SubStandardReasonCodeToSortLookup;
	
	private static ArrayList eMailUARecipients = null; 

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	private static final String statusSQL = " select * from IUAAppStatus order by description";

	private static final String statesSQL = " select * from States statename";


	private static final String otsReasonSQL = " select * from IUAOTSReasonCodes order by description";

	private static final String unableToCompleteReasonSQL = " select * from IUAUnableToCompleteReasonCode order by description";

	private static final String returnReasonSQL = " select * from IUAReturnedReasonCodes order by description";

	private static final String productNameSQL = " select * from IUAProducts description where Deleted='F'";

	private static final String obsoleteProductNameSQL = " select * from IUAProducts description where Deleted='T'";


	private static Logger log = Logger.getLogger(ACMICache.class);

	private static TreeMap MemoIdCodes;
	
	private static String acorde2Soap_address = LocalProperties.getProperty("acorde2Soap_address");

 	public ACMICache() {
	}

 
	/**
	 * Refresh ACMI Cache
	 */
	public static synchronized void refreshCache() {
		Connection conn = null;
		PreparedStatement psmt = null;
		String AAID = null;
		DirContext ctx = null;
		
		try {
			log.debug("ACMI Cache is being refreshed....");

			// rebuild LDAPUser Cache
			LDAPUtil ldapUtil = new LDAPUtil();
			ctx = LDAPUtil.getContext();
			java.util.ArrayList IUAGroups = ldapUtil.getAllGroups(ctx);
			userGroups = IUAGroups;
			HashMap tempusers = ldapUtil.getUsersFromGroup(IUAGroups, ctx);			
			LDAPUtil.closeContext(ctx);
			
			// rebuild Stellent Search prompts
			StellentAdapter sAdapter = null;
			// StellentClient sc = new StellentClient();
			AAID = StellentClient.login();
			String docTypeSearchName = null;
			String docTypeSearchFieldName = null;
			String docTypeSearchFieldValue = null;
			DocumentTypeList tempdocTypes = null;
			log.debug("Begin Reloading loadStellentSearchPromptCache()...");
			sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			HashMap tempsearchprompts = new HashMap();

			// for (int i = 0; i < savedSearchNames.size(); i++) {
			// String ssn = savedSearchNames.get(i).toString();
			String ssn = Constants.allDocsSearchName;
			log.debug("Loading Saved Search Prompts  for " + ssn);
			if (!ssn.equalsIgnoreCase("get_PresentationConditions")) {
				com.Optika.message.SavedSearchPrompt ssps[] = sAdapter.getSavedSearchPrompts(AAID, ssn);
				tempsearchprompts.put(ssn, ssps);
			}
			// }
			log.debug("End Reloading loadStellentSearchPromptCache...");

			log.debug("Begin Reloading loadStellentDocumentTypes()...");

			docTypeSearchName = LocalProperties.getProperty("doc_type_search");
			docTypeSearchFieldName = LocalProperties.getProperty("doc_typ_search_field");
			docTypeSearchFieldValue = LocalProperties.getProperty("doc_typ_search_field_value");
			String username = LocalProperties.getProperty("stellentUser");
			String password = PasswordEncryption.getDecryptedPassword(username);
			String lic_type = LocalProperties.getProperty("stellent_lic_type");


			if (isEmptyString(docTypeSearchFieldValue))
				throw new Exception("Cannot execute saved search with empty value for field " + docTypeSearchFieldName);
			
			SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(docTypeSearchName, username, password, lic_type,
					docTypeSearchFieldName, docTypeSearchFieldValue);
		
			if (ssrs != null) {
				log.debug("IUA Document Types found: Count " + ssrs.length);
				//				
				DocumentType docType[] = new DocumentType[ssrs.length];
				tempdocTypes = new DocumentTypeList(docType);
				for (int i = 0; i < ssrs.length; i++) {
					SavedSearchResult ssr = ssrs[i];
					UserDefinedField udfs[] = ssr.getUserDefinedFieldValues();
					DocumentType dt = new DocumentType();
					docType[i] = dt;
					
					if (udfs != null) {
						Map udfMap = new HashMap();
						
						for (int j = 0; j < udfs.length; j++) {
							UserDefinedField udf = udfs[j];
							udfMap.put(udf.getName(), udf.getValue());
						}
						
						Map docTypeColNameMap = getDocTypeColNameMap();
						String docCodeColName = (String)docTypeColNameMap.get(DOC_CODE);
						String docTypeColName = (String)docTypeColNameMap.get(DOC_TYPE);
						String uploadableColName = (String)docTypeColNameMap.get(UPLOADABLE);
						
						if ((String)udfMap.get(docCodeColName) != null)
							dt.setDocCode(((String)udfMap.get(docCodeColName)).trim());
						else
							dt.setDocCode(NULL_STRING);
						
						
						if ((String)udfMap.get(docTypeColName) != null)
							dt.setDocType(((String)udfMap.get(docTypeColName)).trim());
						else
							dt.setDocType(NULL_STRING);
						
						if ((String)udfMap.get(uploadableColName) != null)
							dt.setUploadable(TRUE.equalsIgnoreCase(((String)udfMap.get(uploadableColName)).trim()));
						else
							dt.setUploadable(true);
					}
				}

				log.debug("End Reloading loadStellentDocumentTypes()...");
			} else {
				log.error("IUA Document Types not defined in Stellent");
				log.error("IUA Documents must be defined for this application to work properly");
			}

			// reload code tables
			log.debug("Begin Rebuildind code tables");
			conn = Connect.getConnection();

			// Load States HashMap
			TreeMap tempstatesMap = new TreeMap();

			psmt = conn.prepareStatement(statesSQL);
			ResultSet statesResultSet = psmt.executeQuery();
			while (statesResultSet.next()) {
				tempstatesMap.put(statesResultSet.getString("Code"), statesResultSet.getString("StateName"));
			}
			statesResultSet.close();
			// Load Status HashMap
			TreeMap tempstatusMap = new TreeMap();

			psmt = conn.prepareStatement(statusSQL);
			ResultSet statusResultSet = psmt.executeQuery();
			while (statusResultSet.next()) {
				tempstatusMap.put(statusResultSet.getString("Code"), statusResultSet.getString("Description"));
			}
			statusResultSet.close();
			//

			// Load ProductName HashMap
			TreeMap tempProductNameMap = new TreeMap();
			psmt = conn.prepareStatement(productNameSQL);
			ResultSet productNameResultSet = psmt.executeQuery();
			while (productNameResultSet.next()) {
				tempProductNameMap.put(productNameResultSet.getString("ProductCode"), productNameResultSet
						.getString("Description"));
			}

			productNameResultSet.close();
			psmt.close();

			TreeMap tempObsoleteProductNameMap = new TreeMap();
			psmt = conn.prepareStatement(obsoleteProductNameSQL);
			ResultSet obsoleteProductNameResultSet = psmt.executeQuery();
			while (obsoleteProductNameResultSet.next()) {
				tempObsoleteProductNameMap.put(obsoleteProductNameResultSet.getString("ProductCode"), obsoleteProductNameResultSet
						.getString("Description"));
			}

			obsoleteProductNameResultSet.close();
			psmt.close();
			
			// Load OTS Reason Code HashMap
			TreeMap tempotsReasonMap = new TreeMap();

			psmt = conn.prepareStatement(otsReasonSQL);
			ResultSet otsReasonResultSet = psmt.executeQuery();
			while (otsReasonResultSet.next()) {
				tempotsReasonMap.put(otsReasonResultSet.getString("Code"), otsReasonResultSet.getString("Description"));
			}

			otsReasonResultSet.close();


			// Load Unable To Complete Reason Code HashMap
			TreeMap tempUnableToCompleteReasonMap = new TreeMap();
			
			psmt = conn.prepareStatement(unableToCompleteReasonSQL);
			ResultSet unableToCompleteReasonResultSet = psmt.executeQuery();
			while (unableToCompleteReasonResultSet.next()) {
				tempUnableToCompleteReasonMap.put(unableToCompleteReasonResultSet.getString("Code"), unableToCompleteReasonResultSet.getString("Description"));
			}

			unableToCompleteReasonResultSet.close();
			
			
			// Load Returned Reason Code HashMap
			TreeMap tempreturnReasonMap = new TreeMap();

			psmt = conn.prepareStatement(returnReasonSQL);
			ResultSet returnReasonSQLResultSet = psmt.executeQuery();
			while (returnReasonSQLResultSet.next()) {
				tempreturnReasonMap.put(returnReasonSQLResultSet.getString("Code"), returnReasonSQLResultSet
						.getString("Description"));
			}
			returnReasonSQLResultSet.close();
			log.debug("End Rebuildind code tables");

			rwl.writeLock().lock();
			log.debug("Locking cache for refresh");
			users = tempusers;
			searchprompts = tempsearchprompts;
			docTypes = tempdocTypes;
			statesMap = tempstatesMap;
			statusMap = tempstatusMap;
			productNameMap = tempProductNameMap;
			obsoleteProductNameMap = tempObsoleteProductNameMap;
			otsReasonMap = tempotsReasonMap;
			unableToCompleteReasonMap = tempUnableToCompleteReasonMap;
			returnReasonMap = tempreturnReasonMap;
			fillUploadableDocTypeList();
			refreshUAEmailMemberAddress();
			arrangeUsersByRole(users);
		} catch (Exception ex) {
			log.error("loadStellentDocumentTypes failed", ex);

		} finally {
			if (AAID != null)
			{
				try
				{
					StellentClient.logout(AAID);
				} catch (Exception e)
				{
					log.debug("Exception " + e.getClass().getName() + " with message: " + e.getMessage() + " thrown while attemping to log out from Stellent");
				}
			}
			
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);
			rwl.writeLock().unlock();
			log.debug("Unlocking cache after refresh");
		}

		log.debug("ACMI Cache refresh is complete");
	}

	/**
	 * Builds a map field/column name for DocumentType class from configuration
	 * @return initialized map
	 */
	private static Map getDocTypeColNameMap()
	{
		Map colMap = new HashMap();
		String colMapStr = LocalProperties.getProperty(DOCTYPE_COL_MAP);
		
		if (colMapStr != null)
		{
			StringTokenizer tok = new StringTokenizer(colMapStr, ",");
			
			while (tok.hasMoreTokens())
			{
				String mapItem = tok.nextToken();
				String key = getKeyFromMapItem(mapItem);
				String value = getValueFromMapItem(mapItem);
				colMap.put(key, value);
			}
		}
		
		return colMap;
	}

	private static void arrangeUsersByRole(HashMap users) {
		
		if (usersByRole != null ) {
			usersByRole.clear();
		}
		
		if (users != null) {		
			Iterator userItr = users.values().iterator();
			while (userItr.hasNext()) {
				LDAPUser user = (LDAPUser) userItr.next();
				ArrayList roleList = user.getRoles();  //Get all roles for that user
				for (int i=0; i < roleList.size(); i++) { //Loop thru each role and add it to the right list
					String role = (String)roleList.get(i);
					List userByRole = (ArrayList)usersByRole.get(role); //See if that role exists first in the map
					if (userByRole == null) {
						userByRole = new ArrayList();
					}
					userByRole.add(user);
					usersByRole.put(role,userByRole);
				}
			}
		}
	}
	
	/**
	 * returns the key from a key=value string
	 * @param mapItem in key=value format
	 * @return key
	 */
	public static String getKeyFromMapItem(String mapItem)
	{
		if (mapItem != null)
		{
			if (mapItem.indexOf(EQUALS) != -1)
				return mapItem.substring(0, mapItem.indexOf(EQUALS));
			else
				return NULL_STRING;
		} else
			return NULL_STRING;
	}

	/**
	 * returns the value from a key=value string
	 * @param mapItem in key=value format
	 * @return value
	 */
	public static String getValueFromMapItem(String mapItem)
	{

		if (mapItem != null)
		{
			if (mapItem.indexOf(EQUALS) != -1)
				return mapItem.substring(mapItem.indexOf(EQUALS) + 1, mapItem.length());
			else
				return NULL_STRING;
		} else
			return NULL_STRING;
	}
	
	/**
	 * Return Activer Directory User
	 * 
	 * @param userId
	 * @return LDAPUser
	 */
	public LDAPUser getUser(String userId) {
		rwl.readLock().lock();
		try {
			return (LDAPUser) users.get(userId.toLowerCase());
		} finally {
			rwl.readLock().unlock();
		}
	}

      public void addUser(String userId, LDAPUser user) {
		rwl.readLock().lock();
		try {
			users.put(userId.toLowerCase(), user);
		} finally {
			rwl.readLock().unlock();
		}
	}

	/**
	 * Returns true if the user has the role
	 * 
	 * @param userId
	 * @param role
	 * @return boolean
	 */
	public boolean isInRole(String userId, String role) {
		boolean isInRole = false;

		rwl.readLock().lock();
		try {
			LDAPUser user = (LDAPUser) users.get(userId);
			if (user != null) {
				return user.hasRole(role);
			} else {
				return isInRole;
			}
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * build activer directory user and group cache.
	 */
	// NOTE: This role is only used for display information on UI.
	// Role resolution DOES NOT happen in UI. It is controlled by EPM code.
	public void loadADUsersAndGroup() {
		LDAPUtil ldapUtil = new LDAPUtil();
		DirContext ctx = LDAPUtil.getContext();
		java.util.ArrayList IUAGroups = ldapUtil.getAllGroups(ctx);
		userGroups = IUAGroups;
		
		HashMap localCache = new HashMap();
		localCache = ldapUtil.getUsersFromGroup(IUAGroups, ctx);
		users = localCache;
		
		LDAPUtil.closeContext(ctx);
	}


	/**
	 * Return SavedSearchPrompt from Cache
	 * 
	 * @param searchName
	 * @return SavedSearchPrompt[] 
	 */
	public static synchronized SavedSearchPrompt[] getSavedSearchPrompts(String searchName) {
		com.Optika.message.SavedSearchPrompt ssps[] = null;
		log.debug("Begin  getSavedSearchPrompts(String searchName)");
		rwl.readLock().lock();
		try {
			if (searchprompts != null) {
				ssps = (SavedSearchPrompt[]) searchprompts.get(searchName);
			}
			SavedSearchPrompt[] copy = (SavedSearchPrompt[])MiscellaneousUtils.deepClone(ssps);
			log.debug("End getSavedSearchPrompts(String searchName) - returning cloned copy");
			return copy;
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * Return SavedSearchPrompt from Cache Instance
	 * 
	 * @param searchName
	 * @return  SavedSearchPrompt[]
	 */
	public static SavedSearchPrompt[] getSavedSearchPromptsInstance(String searchName) {

		com.Optika.message.SavedSearchPrompt ssps[] = null;
		log.debug("Begin  getSavedSearchPromptsInstance(String searchName)");
		if (searchPromptsInstance == null) {
			//localize the lock as much as possible...			
			
			synchronized (lock)
			{
				//Ask again for null to avoid race conditions...
				if (searchPromptsInstance == null)
					initializeCacheInstance();
			}
		}

		if (searchPromptsInstance != null) {
			ssps = (SavedSearchPrompt[]) searchPromptsInstance.get(searchName);
		}
		log.debug("End getSavedSearchPromptsInstance(String searchName)");
		
		SavedSearchPrompt[] copy = cloneSearchPrompt(ssps);
		log.debug("returning clone saved search prompt instance");
		return copy;

	}

	private static SavedSearchPrompt[] cloneSearchPrompt(SavedSearchPrompt[] original)
	{
		if (original == null)
			return null;
		
		SavedSearchPrompt[] copy = (SavedSearchPrompt[])MiscellaneousUtils.deepClone(original);
		return copy;
	}

	/**
	 * Load Stellent Serach Prompts into ACMICache
	 * 
	 * @param AAID
	 */
	public void loadStellentSearchPromptCache(String AAID) {
		StellentAdapter sAdapter = null;

		try {

			log.debug("Begin  loadStellentSearchPromptCache()...");
			sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			searchprompts = new HashMap();
			// for (int i = 0; i < savedSearchNames.size(); i++) {
			// String ssn = savedSearchNames.get(i).toString();
			String ssn = Constants.allDocsSearchName;
			log.debug("Loading Saved Search Prompts  for " + ssn);
			if (!ssn.equalsIgnoreCase("get_PresentationConditions")) {
				com.Optika.message.SavedSearchPrompt ssps[] = sAdapter.getSavedSearchPrompts(AAID, ssn);
				searchprompts.put(ssn, ssps);
			}
			// }
			log.debug("End loadStellentSearchPromptCache...");
		} catch (Exception ex) {
			log.error("loadStellentSearchPromptCache failed", ex);
		} finally {
			sAdapter = null;
		}
	}


	/**
	 * Trivial method to see if string is blank
	 * @param str
	 * @return
	 */
	private static boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}
	
	/**
	 * Load Stellent Document types into ACMI Cache
	 * 
	 * @param AAID
	 * @param docTypeSearchName
	 * @param fieldName
	 * @param value
	 */
	public void loadStellentDocumentTypes(String AAID, String docTypeSearchName, String fieldName, String value) {
		StellentAdapter sAdapter = null;
	
		try {
			log.debug("Begin  loadStellentDocumentTypes()...");
			sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			String username = LocalProperties.getProperty("stellentUser");
			String password = PasswordEncryption.getDecryptedPassword(username);
			String lic_type = LocalProperties.getProperty("stellent_lic_type");

			if (isEmptyString(value))
				throw new Exception("Cannot execute saved search with empty value for field " + fieldName);
			
			SavedSearchResult ssrs[] = sAdapter.executeSavedSearch(docTypeSearchName, username, password, lic_type, fieldName, value);
			
			if (ssrs != null) {
				log.debug("IUA Document Types found: Count " + ssrs.length);
				//				
				DocumentType docType[] = new DocumentType[ssrs.length];
				docTypes = new DocumentTypeList(docType);
				for (int i = 0; i < ssrs.length; i++) {
					SavedSearchResult ssr = ssrs[i];
					UserDefinedField udfs[] = ssr.getUserDefinedFieldValues();
					DocumentType dt = new DocumentType();
					docType[i] = dt;

					if (udfs != null) {
						Map udfMap = new HashMap();
						
						for (int j = 0; j < udfs.length; j++) {
							UserDefinedField udf = udfs[j];
							udfMap.put(udf.getName(), udf.getValue());
						}
						
						Map docTypeColNameMap = getDocTypeColNameMap();
						String docCodeColName = (String)docTypeColNameMap.get(DOC_CODE);
						String docTypeColName = (String)docTypeColNameMap.get(DOC_TYPE);
						String uploadableColName = (String)docTypeColNameMap.get(UPLOADABLE);
						
						if ((String)udfMap.get(docCodeColName) != null)
							dt.setDocCode(((String)udfMap.get(docCodeColName)).trim());
						else
							dt.setDocCode(NULL_STRING);
						
						
						if ((String)udfMap.get(docTypeColName) != null)
							dt.setDocType(((String)udfMap.get(docTypeColName)).trim());
						else
							dt.setDocType(NULL_STRING);
						
						if ((String)udfMap.get(uploadableColName) != null)
							dt.setUploadable(TRUE.equalsIgnoreCase(((String)udfMap.get(uploadableColName)).trim()));
						else
							dt.setUploadable(true);
					}
				}
				
				fillUploadableDocTypeList();
				refreshUAEmailMemberAddress();
				log.debug("End  loadStellentDocumentTypes()...");
			} else {
				log.error("IUA Document Types not defined in Stellent");
				log.error("IUA Documents must be defined for this application to work properly");
			}
		} catch (Exception ex) {
			log.error("loadStellentDocumentTypes failed", ex);
		} finally {
			sAdapter = null;
		}
	}

	/**
	 * Fills docTypesUploadable with uploadable document type objects.
	 */
	private static void fillUploadableDocTypeList()
	{
		List filteredDocTypes = new ArrayList();
		
		for (int i = 0; i < docTypes.size(); i++)
		{
			DocumentType dt = (DocumentType)docTypes.getElementAt(i);
						
			if (dt.isUploadable())
			{
				filteredDocTypes.add(dt);
			}
		}

		DocumentType docTypeUploadableList[] = new DocumentType[filteredDocTypes.size()];
		
		for (int i = 0; i < filteredDocTypes.size(); i++)
		{
			docTypeUploadableList[i] = (DocumentType)filteredDocTypes.get(i);
		}
		
		docTypesUploadable = new DocumentTypeList(docTypeUploadableList);
	}

	/**
	 * Load all Standard Event Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDStdEventCodesDescription()
	{
		
		//Web service is a hard code lookup because of limited web screens  with no description
		
		String service = "Std Event Codes Description";
		try {
			StdEventCodesDescription = WSStdEventCodesNoCall.fetch();
			
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	/**
	 * Load all Standard Event Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDStdEventCodes()
	{
		
		//Web service is a hard code lookup because of limited web screens 
		
		String service = "Std Event Codes";
		try {
			StdEventCodes = WSStdEventCodesCall.fetch();
			
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	/**
	 * Load all Condition Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDConditionCodes()
	{
		
		String service = "Condition Codes";
		try {
			ConditionCodes =  new TreeMap();
			ConditionCodesExtendedFields = new TreeMap();
			
			WSConditionCodesCall.fetch(ConditionCodes, ConditionCodesExtendedFields);
			
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	/**
	 * Load all Condition Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDSubStandardReason()
	{
		
		String service = "Sub-Standard Reason";
		try {
			SubStandardReason = new TreeMap();
			SubStandardReasonLookup = new TreeMap();
			SubStandardReasonCodeToSortLookup = new TreeMap();
				
		   WSSubStandardReasonCall.fetch(SubStandardReason, SubStandardReasonLookup, SubStandardReasonCodeToSortLookup);
			
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	
	/**
	 * Load all Standard Event Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDUnderWriterStatusCodes()
	{
		String service = "Under Writer Status Codes";
		
		try {
			UnderWriterStatusCodes = WSUndStatusCodesCall.fetch();
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	/**
	 * Load all Memo Id Codes  from WS acmi into ACMICache
	 */
	public static synchronized void loaDMemoIdCodes()
	{
		String service = "Memo Id Codes";
		
		try {
			MemoIdCodes = WSMemoCodesCall.fetch();
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	/**
	 * Load all Person Status Codes  from WS acmi into ACMICache
	 */
	public static synchronized void loaDPersonStatusCodes()
	{
		String service = "Person Status Codes";
		
		try {
			PersonStatusCodes = WSPersonStatusCodesCall.fetch();
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	/**
	 * Load all Person Types Codes  from WS acmi into ACMICache
	 */
	public static synchronized void loaDPersonTypesCodes()
	{
		String service = "Person Types Codes";
		
		try {
			PersonTypesCodes = WSPersonTypesCodesCall.fetch();
		} catch (RemoteException e) {
			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
			
		} catch (ServiceException e) {
			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		}
	}
	
	/**
	 * Load all code tables from acmi db into ACMICache
	 */
	public static synchronized void loaDBdCodeTables() {

		Connection conn = null;
		PreparedStatement psmt = null;
		try {

			conn = Connect.getConnection();

			// Load States HashMap
			statesMap = new TreeMap();

			psmt = conn.prepareStatement(statesSQL);
			ResultSet statesResultSet = psmt.executeQuery();
			while (statesResultSet.next()) {
				statesMap.put(statesResultSet.getString("Code"), statesResultSet.getString("StateName"));
			}

			statesResultSet.close();
			// Load Status HashMap
			statusMap = new TreeMap();

			psmt = conn.prepareStatement(statusSQL);
			ResultSet statusResultSet = psmt.executeQuery();
			while (statusResultSet.next()) {
				statusMap.put(statusResultSet.getString("Code"), statusResultSet.getString("Description"));
			}
			
			statusResultSet.close();
			//

			// Load ProductName HashMap
			productNameMap = new TreeMap();

			psmt = conn.prepareStatement(productNameSQL);
			ResultSet productNameResultSet = psmt.executeQuery();
			while (productNameResultSet.next()) {
				productNameMap.put(productNameResultSet.getString("ProductCode"), productNameResultSet.getString("Description"));
			}

			productNameResultSet.close();
			psmt.close();

			obsoleteProductNameMap = new TreeMap();
			
			psmt = conn.prepareStatement(obsoleteProductNameSQL);
			ResultSet obsoleteProductNameResultSet = psmt.executeQuery();
			while (obsoleteProductNameResultSet.next()) {
				obsoleteProductNameMap.put(obsoleteProductNameResultSet.getString("ProductCode"), obsoleteProductNameResultSet.getString("Description"));
			}

			obsoleteProductNameResultSet.close();
			psmt.close();

			// Load OTS Reason Code HashMap
			otsReasonMap = new TreeMap();

			psmt = conn.prepareStatement(otsReasonSQL);
			ResultSet otsReasonResultSet = psmt.executeQuery();
			while (otsReasonResultSet.next()) {
				otsReasonMap.put(otsReasonResultSet.getString("Code"), otsReasonResultSet.getString("Description"));
			}

			otsReasonResultSet.close();

			// Load Unable To Complete Reason Code HashMap
			unableToCompleteReasonMap = new TreeMap();

			psmt = conn.prepareStatement(unableToCompleteReasonSQL);
			ResultSet unableToCompleteReasonResultSet = psmt.executeQuery();
			while (unableToCompleteReasonResultSet.next()) {
				unableToCompleteReasonMap.put(unableToCompleteReasonResultSet.getString("Code"), unableToCompleteReasonResultSet.getString("Description"));
			}

			unableToCompleteReasonResultSet.close();
			
			
			// Load Returned Reason Code HashMap
			returnReasonMap = new TreeMap();

			psmt = conn.prepareStatement(returnReasonSQL);
			ResultSet returnReasonSQLResultSet = psmt.executeQuery();
			while (returnReasonSQLResultSet.next()) {
				returnReasonMap
						.put(returnReasonSQLResultSet.getString("Code"), returnReasonSQLResultSet.getString("Description"));
			}

			returnReasonSQLResultSet.close();
		} catch (Exception e) {

		} finally {
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);
		}
	}


	/**
	 * Return DocymentTypeList
	 * 
	 * @return DocumentTypeList
	 */
	public static synchronized DocumentTypeList getDocumentTypeList() {
		rwl.readLock().lock();
		try {
			return docTypes;
		} finally {
			rwl.readLock().unlock();
		}
	}

	/**
	 * Return DocymentTypeFilteredList
	 * 
	 * @return DocumentTypeFilteredList
	 */
	public static synchronized DocumentTypeList getDocumentTypeUploadableList() {
		rwl.readLock().lock();
		try {
			return docTypesUploadable;
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * Return docType for a give doc code.
	 * 
	 * @param docCode
	 * @return String
	 */
	public static synchronized String getDocType(String docCode) {
		rwl.readLock().lock();
		try {
			return docTypes.getDocType(docCode);
		} finally {
			rwl.readLock().unlock();
		}
	}


	public static void main(String args[]) throws Exception {

		System.out.println("key = " + getKeyFromMapItem("myKey=myValue"));
		System.out.println("value = " + getValueFromMapItem("myKey1=myValue1"));
		System.out.println("key = " + getKeyFromMapItem("myKeyTmyValue"));
		System.out.println("value = " + getValueFromMapItem("myKey1TmyValue1"));
	}

	static {
		log = Logger.getLogger(com.epm.acmi.util.ACMICache.class);
	}


	/**
	 * Return Active directory User Groupss
	 * 
	 * @return ArrayList
	 */
	public static synchronized ArrayList getUserGroups() {
		rwl.readLock().lock();
		try {
			return userGroups;
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * Set user Groups
	 * 
	 * @param userGroups
	 */
	public static synchronized void setUserGroups(ArrayList userGroups) {
		ACMICache.userGroups = userGroups;
	}


	/**
	 * Returns Users from Cache
	 * 
	 * @return HashMap
	 */
	public static synchronized HashMap getUsers() {
		rwl.readLock().lock();
		try {
			return users;
		} finally {
			rwl.readLock().unlock();
		}
	}

	public static TreeMap getProductMapForListBox(String productName)
	{
		TreeMap temporaryProductNameMap = null;
		
		if ((!isEmptyString(productName)) && (getProductNameMap().get(productName) == null))
		{
			temporaryProductNameMap = new TreeMap();
			temporaryProductNameMap.putAll(getProductNameMap());
			temporaryProductNameMap.put(productName, getObsoleteProductDescription(productName));
		} else
			temporaryProductNameMap = getProductNameMap();
		
		return temporaryProductNameMap;
	}

	public static String getObsoleteProductDescription(String productCode) {
		
		String result = NULL_STRING;
		TreeMap map = getObsoleteProductNameMap();		
		result = (String)map.get(productCode);
		return result;
	}

	/**
	 * Returns OATS return reason code.
	 * 
	 * @return TreeMap
	 */ 
	public static synchronized TreeMap getOtsReasonMap() {
		rwl.readLock().lock();
		try {
			return otsReasonMap;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	/**
	 * Returns Unable to complete Reason Map.
	 * 
	 * @return TreeMap
	 */ 
	public static synchronized TreeMap getUnableToCompleteReasonMap() {
		rwl.readLock().lock();
		try {
			return unableToCompleteReasonMap;
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * Returns ProductName Map
	 * 
	 * @return TreeMap
	 */
	public static synchronized TreeMap getProductNameMap() {
		rwl.readLock().lock();
		try {
			return productNameMap;
		} finally {
			rwl.readLock().unlock();
		}
	}

	/**
	 * Returns ProductName Map
	 * 
	 * @return TreeMap
	 */
	public static synchronized TreeMap getObsoleteProductNameMap() {
		rwl.readLock().lock();
		try {
			return obsoleteProductNameMap;
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * Returns ReturnReason Map
	 * 
	 * @return TreeMap
	 */ 
	public static synchronized TreeMap getReturnReasonMap() {
		rwl.readLock().lock();
		try {
			return returnReasonMap;
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * Return States Map
	 * 
	 * @return TreeMap
	 */
	public static synchronized TreeMap getStatesMap() {
		rwl.readLock().lock();
		try {
			return statesMap;
		} finally {
			rwl.readLock().unlock();
		}
	}


	/**
	 * Return Status Map
	 * 
	 * @return TreeMap
	 */
	public static synchronized TreeMap getStatusMap() {
		rwl.readLock().lock();
		try {
			return statusMap;
		} finally {
			rwl.readLock().unlock();
		}

	}


	/**
	 * initializeCacheInstance method is called from Cache Instance
	 */
	public static void initializeCacheInstance() {
		log.debug("initializeCacheInstance() - Start");
		if (searchPromptsInstance == null)
		{
			synchronized (lock)
			{
				//Ask again to avoid race condition
				if (searchPromptsInstance == null)
				{
					String AAID = null;
					
					try {
						log.debug("Start reloading searchPromptsInstance....");
			
						// rebuild Stellent Search prompts
						StellentAdapter sAdapter = null;
						AAID = StellentClient.login();
			
						log.debug("Begin Reloading loadStellentSearchPromptCache()...");
						sAdapter = StellentAdapterFactory.getStellentAdapter(acorde2Soap_address);
			
						HashMap tempSearchPromptsInstance = new HashMap();
			
						 //for (int i = 0; i < savedSearchNames.size(); i++) {
						 //String ssn = savedSearchNames.get(i).toString();
						String ssn = Constants.allDocsSearchName;
						log.debug("Loading Saved Search Prompts  for " + ssn);
						if (!ssn.equalsIgnoreCase("get_PresentationConditions")) {
							com.Optika.message.SavedSearchPrompt ssps[] = sAdapter.getSavedSearchPrompts(AAID, ssn);
							tempSearchPromptsInstance.put(ssn, ssps);				
						}
						// }
						
						searchPromptsInstance = tempSearchPromptsInstance;
						log.debug("End reloading searchPromptsInstance...");
			
					} catch (Exception ex) {
						log.error("initializeCacheInstance() failed on reloading searchPromptsInstance", ex);
			
					} finally {
						if (AAID != null)
						{
							try
							{
								StellentClient.logout(AAID);
							} catch (Exception e)
							{
								log.debug("Exception " + e.getClass().getName() + " with message: " + e.getMessage() + " thrown while attemping to log out from Stellent");
							}
						}
					}
				}
				
				log.debug("initializeCacheInstance() - End");
			}
		}
	}

	/* Begin changes for USR 7930- EVENT TRACKER *
	 *  Nagrathna Hiriyurkar , 07/28/2007 
	 */
	public static void refreshUAEmailMemberAddress() {
		if (eMailUARecipients == null)
		{
			synchronized (lock)
			{
				//Ask again to avoid race condition
				if (eMailUARecipients == null)
					doRefreshUAEmailMemberAddress();
			}
		}
			
	}
	
	public static void doRefreshUAEmailMemberAddress() {
 		LDAPUtil ldapUtil = new LDAPUtil();
		ArrayList recipients = new ArrayList();

		List users1 = ldapUtil.getMembersForGroup("UA");
		DirContext ctx = LDAPUtil.getContext();
		
		for (int i = 0; i < users1.size(); i++) {
			String dn = (String) users1.get(i);

			LDAPUser ldapUser = ldapUtil.getUserAttributes(dn, ctx);
			String emailAddress = ldapUser.getEmail();
			if (null != emailAddress) {
				if (emailAddress.trim().length() > 0) {
					recipients.add(emailAddress);
					log.debug("UA users Email:************* "+ emailAddress);
				}
			}
		}
		
		eMailUARecipients = recipients;		
		LDAPUtil.closeContext(ctx);
	} 
	/* End of changes for USR 7930- EVENT TRACKER */


	public static ArrayList getEMailUARecipients() {
		refreshUAEmailMemberAddress();		
		return eMailUARecipients;
	}
	
	public static ArrayList getUsersByRole(String role) {
		if (role != null) {
			return (ArrayList)((ArrayList)usersByRole.get(role)).clone();
		}
		return null;
	}
	
	public static TreeMap getStdEventCodes() {
		rwl.readLock().lock();
		try {
			return StdEventCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	
	public static TreeMap getStdEventCodesDescription() {
		rwl.readLock().lock();
		try {
			return StdEventCodesDescription;
		} finally {
			rwl.readLock().unlock();
		}
	}

	public static TreeMap getUnderWriterStatusCodes() {
		rwl.readLock().lock();
		try {
			return UnderWriterStatusCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static TreeMap getConditionCodes() {
		rwl.readLock().lock();
		try {
			return ConditionCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static TreeMap getConditionCodesExtendedFields() {
		rwl.readLock().lock();
		try {
			return ConditionCodesExtendedFields;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	
	public static TreeMap getSubStandardReasons() {
		rwl.readLock().lock();
		try {
			return SubStandardReason;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static TreeMap getSubStandardReasonLookup() {
		rwl.readLock().lock();
		try {
			return SubStandardReasonLookup;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	
	public static TreeMap getSubStandardReasonCodeToSortLookup() {
		rwl.readLock().lock();
		try {
			return SubStandardReasonCodeToSortLookup;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static TreeMap getMemoIdCodes() {
		rwl.readLock().lock();
		try {
			return MemoIdCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static TreeMap getPersonTypesCodes() {
		rwl.readLock().lock();
		try {
			return PersonTypesCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static TreeMap getPersonStatusCodes() {
		rwl.readLock().lock();
		try {
			return PersonStatusCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static void getIasCodesTable()
	{
		log.info("Building Standard Event Codes Cache...");
		ACMICache.loaDStdEventCodes();
		log.info("Standard Event Codes Cache built....");
		
		log.info("Building Standard Event Codes and Descriptions Cache...");
		ACMICache.loaDStdEventCodesDescription();
		log.info("Standard Event Codes and Descriptions Cache built....");
		
		log.info("Building Condition Codes Cache...");
		ACMICache.loaDConditionCodes();
		log.info("Condition Codes Cache built....");
		
		log.info("Building Sub Standard Reason Cache...");
		ACMICache.loaDSubStandardReason();
		log.info("Standard Sub Standard Reason and Descriptions Cache built....");
		
		log.info("Building Under Writer Status Codes Cache...");
		ACMICache.loaDUnderWriterStatusCodes();
		log.info("Under Writer Status Codes Cache built....");
		
		log.info("Building Memo Id Codes Cache...");
		ACMICache.loaDMemoIdCodes();
		log.info("Under Memo Id Codes Cache built....");
		
		log.info("Building Person Types Cache...");
		ACMICache.loaDPersonTypesCodes();
		log.info("Under Person Types Codes Cache built....");
		
		log.info("Building Person Status Cache...");
		ACMICache.loaDPersonStatusCodes();
		log.info("Under Person Status Codes Cache built....");
	}
	
}
