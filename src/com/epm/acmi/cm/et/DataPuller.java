 
 package com.epm.acmi.cm.et;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.epm.acmi.cm.et.rules.EPMRulesImpl;
import com.epm.acmi.cm.et.rules.JournalRecord;
import com.epm.acmi.struts.form.dsp.DisplayDocument;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.LDAPIUAUserUtil;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.Mailer;

/**
 * This class obtains records from the Stellent DB EPMEvents table and runs the records through business rules. The
 * rules determine how the record should be handled on the BPM side, whether a new process needs to be created or a
 * process restarted, etc.
 * 
 * @author Dragos Mandruleanu
 */
public class DataPuller {
	private static final String NULL_STRING = "";

	private static final String SEND_UA_EMAIL = "SendUAEmail";

	private static EPMRulesImpl ruleImpl;

	private static StellentClient stellent;

	private static PreparedStatement deletePS;

	private static PreparedStatement processedPS;

	private static String processedSQL;

	private static Logger log = Logger.getLogger("DataPuller.class");

	static {

		ruleImpl = new EPMRulesImpl();
		stellent = new StellentClient();

		processedSQL = "insert into ProcessedEPMEvents (GFID, PolicyNum, Doctype, RecdDate, EventStatus, RecID, FailureState) ";
		processedSQL += " values (?, ?, ?, ?, ?, ?, ?) ";
	}


	/**
	 * Moving event data into the processed events table
	 * @param epmEventsRec
	 * @throws Exception
	 */
	private void insertIntoTrackingTable(CMEventDTO epmEventsRec) throws Exception {
		log.debug("Entering method insertIntoTrackingTable()");
		Timestamp ts = null;

		JournalRecord journal = (JournalRecord)JournalRecord.journalMap.get(new Long(epmEventsRec.getRecID()));
		if (journal == null)
			journal = new JournalRecord();
		

		if (epmEventsRec.getDocRecdDate() != null) {
			ts = new Timestamp(epmEventsRec.getDocRecdDate().getTime());
		}

		processedPS.setString(1, epmEventsRec.getGFID());
		processedPS.setString(2, epmEventsRec.getPolicyNum());
		processedPS.setString(3, epmEventsRec.getDocType());
		processedPS.setTimestamp(4, ts);
		processedPS.setString(5, epmEventsRec.getStatus());
		processedPS.setLong(6, epmEventsRec.getRecID());
		processedPS.setInt(7, journal.getFailureState());
		
		processedPS.executeUpdate();

		log.debug("Exiting method insertIntoTrackingTable()");
	}

	/**
	 * deleting data from the processed events table
	 * @param epmEventsRec
	 * @throws Exception
	 */
	private void deleteFromInitialTrackingTable(CMEventDTO epmEventsRec) throws Exception {
		log.debug("Entering method deleteFromInitialTrackingTable()");
		DataPuller.deletePS.setLong(1, epmEventsRec
				.getEventID());
		DataPuller.deletePS.executeUpdate();
		log.debug("Exiting method deleteFromInitialTrackingTable()");
	}

	/**
	 * This method gets records from the Stellent EPMEvents table and runs the records through business logic that
	 * determines how to handle them on the BPM side.
	 * 
	 * @throws Exception
	 */
	public void getDataFromStellentDB() throws Exception {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Connection conn = null;
		Connection acmiConn = null;
		CMEventDTO epmEventsRec;
		String theGFID = null; 
		
		log.debug("Entering method getDataFromStellentDB()");

		try {
			conn = Connect.getConnectionToStellent();
			acmiConn = Connect.getConnection();
				
			if (null != conn && null != acmiConn) {
				conn.setAutoCommit(false);
				acmiConn.setAutoCommit(false);

				ps = conn.prepareStatement("select * from EPMEvents WITH (NOLOCK) order by eventid");
				rs = ps.executeQuery();
				
				DataPuller.deletePS = conn.prepareStatement("delete EPMEvents where EventId = ?");
				DataPuller.processedPS = conn.prepareStatement(processedSQL);

				while (rs.next()) {
					// if (rs.next()) {
					epmEventsRec = new CMEventDTO();
					epmEventsRec.setDocRecdDate(rs.getTimestamp("RecdDate"));
					epmEventsRec.setDocType(rs.getString("Doctype"));
					epmEventsRec.setGFID(rs.getString("GFID"));
					epmEventsRec.setPolicyNum(rs.getString("PolicyNum"));
					epmEventsRec.setRecID(rs.getLong("RecID"));
					epmEventsRec.setStatus(rs.getString("EventStatus"));
					epmEventsRec.setEventID(rs.getLong("EventId"));

					JournalRecord journal = null;
					journal = new JournalRecord();
					journal.setId(epmEventsRec.getRecID());
					JournalRecord.journalMap.put(new Long(journal.getId()), journal);
					theGFID =  epmEventsRec.getGFID(); 
					try {
						log.info(NULL_STRING);
						log.info(" *********** Start Processing Event*************");
						log.debug("Got a record from EPMEvents. " + epmEventsRec);
						log.debug("Running business logic, processing according to doc type....");
						//Processing the event according to its document type...
						processEventRecordForDocType(acmiConn, epmEventsRec);
						log.info("Business logic terminated.");
					} catch (Exception ex) {
						log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage()
								  + " ,GFID = " + theGFID + " , Doctype =  " + epmEventsRec.getDocType(), ex);
					}
					
					try
					{
						//If it is okay to move table contents, do it...
						if (journal.getMoveEventTableContents())
						{
							log.info("Inserting tracking record in ProcessedEPMEvents table");
							insertIntoTrackingTable(epmEventsRec);
							log.info("Deleting tracking record from EPMEvents table");
							deleteFromInitialTrackingTable(epmEventsRec);
							
							if (!conn.isClosed())
								conn.commit();
						}
						

						if (!acmiConn.isClosed())
							acmiConn.commit();
						
						log.info("Records commited to DB");
						log.info(" *********** End Processing Event*************");
						log.info(NULL_STRING);
					} catch (Exception ex) {
						log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage()
								 + " ,GFID = " + theGFID + " , Doctype =  " + epmEventsRec.getDocType(), ex);
						
						if (!conn.isClosed())
							conn.rollback();
					} finally
					{
						//Journal Record no longer needed. Remove it from Map.
						if (journal != null)
							JournalRecord.journalMap.remove(new Long(journal.getId()));
					}
					
				}
			} else {
				if (null == conn)
					log.info("Stellent Connection Failed");
				if (null == acmiConn)
					log.info("ACMI Connection Failed");

			}

		} catch (Exception e) {
			log.error("Error : GFID = " + theGFID, e);
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (ps != null)
				ps.close();
			if (DataPuller.deletePS != null)
				DataPuller.deletePS.close();
			if (DataPuller.processedPS != null)
				DataPuller.processedPS.close();
			if (conn != null)
				conn.close();
			if (acmiConn != null)
				acmiConn.close();
			log.debug("Released ACMI and Stellent connections.");
		}

		log.debug("Exiting method getDataFromStellentDB()");
	}


	private void processEventRecordForDocType(Connection acmiConn, CMEventDTO epmEventsRec) throws Exception {

		log.info("Entering method processEventRecordForDocType()");
		log.info("Doc Type = " + epmEventsRec.getDocType());

		if (epmEventsRec.getDocType().equals("APP")) {
			runAppRules(acmiConn, epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("IBC")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("EXM")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("MVR")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("MED")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("QUO")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("CHK")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("QST")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("EFT")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("HIP")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("HSA")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("LBS")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("LBI")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("RPL")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("MRI")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("SCF")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equalsIgnoreCase("APA")) {
			/* Changes for USR 7930- EVENT TRACKER. Added by Nagrathna Hiriyurkar*/
			runAppAddRules(acmiConn, epmEventsRec);   
		} else if (epmEventsRec.getDocType().equals("PHI")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("SPA")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("EMC")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("MRA")) {
			runOtherRules(epmEventsRec);
		} else if (epmEventsRec.getDocType().equals("MRS")) {
			runOtherRules(epmEventsRec);
		}

		log.debug("Exiting method processEventRecordForDocType()");
	}


	private void runAppRules(Connection acmiConn, CMEventDTO epmEventsRec) throws Exception {
		
		DisplayDocument dc = null;
		String stellentAAID = null;

		log.debug("Entering method runAppRules()");
		log.info("GFID = " + epmEventsRec.getGFID());
		log.info("Stellent Doc Type = " + epmEventsRec.getDocType());
		String gfid = epmEventsRec.getGFID();
		if (null == gfid) {
			log.info("GFID is null Main Process will not be created");
			return;
		}

		String policynum = epmEventsRec.getPolicyNum();

		if (null == policynum)
			policynum = "0";

		if (null != gfid && epmEventsRec.getRecID() != 0) {
			// Get stellent web service data

			log.debug("Getting info from stellent");
			try {
				stellentAAID = StellentClient.login();
				dc = DataPuller.stellent.searchDocumentByRecId(stellentAAID,
						"RECID", String.valueOf(epmEventsRec.getRecID()));
				log.debug("Stellent returned");
			} catch (Exception e) {
				log.error("Exception " + e.getClass().getName()
						+ " thrown while trying to login to Stellent. PolicyNumber = " + policynum , e);
				EPMRulesImpl.modifyJournalEntry(new Long(epmEventsRec.getRecID()), JournalRecord.PROCESSED_SUCCESSFULLY, false);
				return;
			} finally {
				if (stellentAAID != null)
					StellentClient.logout(stellentAAID);
			}
			
			// if process exists create a new MEMO process else create a new
			// MAIN process
			log.debug("Application/Main Process does not exist");
			log.info("Trying to create a new process");
			ruleImpl.CreateProcess(epmEventsRec.getGFID(), policynum,
					epmEventsRec.getDocType(), Long.toString(epmEventsRec
							.getRecID()));
			// process does not exist before the createProcess method above
			// now that it is created update it with info from stellent

			log.info("Current rec ID = " + epmEventsRec.getRecID());
			// update the iuaapplication table for this gfid with data from
			// stellent
			try
			{
			   updateIUAApplicationTable(acmiConn, epmEventsRec, dc);
			} catch (Exception e) {
				log.error("Exception " + e.getClass().getName() + " with message: " + e.getMessage() 
						+ " thrown while trying to update IUAApplicationTable with Stellent metadata. PolicyNumber = " + policynum, e);
				EPMRulesImpl.modifyJournalEntry(new Long(epmEventsRec.getRecID()), JournalRecord.DUPLICATE_GFID, true);
			}
			

		}

		log.debug("Exiting method runAppRules()");

	} // end of runAppRules() method


	private void updateIUAApplicationTable(Connection acmiConn, CMEventDTO epmEventsRec, DisplayDocument dc) throws Exception {
		String updateSQL = NULL_STRING;
		PreparedStatement st = null;
		String firstName = NULL_STRING;
		String middleName = NULL_STRING;
		String lastName = NULL_STRING;
		String policyNumber = NULL_STRING;
		String gfID = NULL_STRING;
		String cmsLBID = NULL_STRING;
		String state = NULL_STRING;
		String suffix = NULL_STRING;
		String agentName = NULL_STRING;
		String agentNumber = NULL_STRING;
		String product = NULL_STRING;

		log.debug("Entering method updateIUAApplicationTable()");

		if (null == dc) {
			log.error("stellent search failed retry...");
			throw new Exception("DisplayDocument instance is null. Cannot update IUAApplication table.");
		}

		gfID = dc.getGFID();
		
		try {
			if (null != dc.getKeyAppFirstName())
				firstName = dc.getKeyAppFirstName().trim();

			if (null != dc.getKeyAppMiddle())
				middleName = dc.getKeyAppMiddle().trim();

			if (null != dc.getKeyAppLastName())
				lastName = dc.getKeyAppLastName().trim();

			if (null != dc.getKeyAppSuffix())
				suffix = dc.getKeyAppSuffix().trim();

			if (null != dc.getState())
				state = dc.getState().trim();

			if (null != dc.getPolicyNumber())
				policyNumber = dc.getPolicyNumber().trim();

			if (null != dc.getCmslbId()) {
				if (dc.getCmslbId().length() > 0)
					cmsLBID = dc.getCmslbId().trim();	
			}
			
			
			if (!isEmptyString(dc.getAgentNumber()))
				agentNumber = dc.getAgentNumber().trim();

			if (!isEmptyString(dc.getAgentName()))
				agentName = dc.getAgentName().trim();

			if (!isEmptyString(dc.getProduct()))
				product = dc.getProduct().trim();
			
			log.info("GFID = '" + gfID + "'");
			log.info("first name = '" + firstName + "'");
			log.info("last name = '" + lastName + "'");
			log.info("middle name = '" + middleName + "'");
			log.info("suffix = '" + suffix + "'");
			log.info("state = '" + state + "'");
			log.info("policy number = '" + policyNumber + "'");
			log.info("agent number = '" + agentNumber + "'");
			log.info("agent name = '" + agentName + "'");
			log.info("product = '" + product + "'");
			log.info("CMListBill = '" + cmsLBID + "'");

			updateSQL = "update iuaapplication set ";
			updateSQL += "PolicyNo = ?";	
			updateSQL += ", LastUpdatedDateTime=getdate(), LastUpdatedUserId='EventTrackerProcess' ";
			updateSQL += ", KeyAppFirstName = ?";
			updateSQL += ", KeyAppLastName = ?";
			updateSQL += ", KeyAppMI = ?";
			updateSQL += ", KeyAppSuffix = ?";
			updateSQL += ", State = ?";
			updateSQL += ", CMListBill = ?";
			
			if (!isEmptyString(dc.getAgentNumber()))
				updateSQL += ", AgentNumber = ?";
			
			if (!isEmptyString(dc.getAgentName()))
				updateSQL += ", AgentName = ?";
			
			if (!isEmptyString(dc.getProduct()))
				updateSQL += ", Product = ?";
			
			
			updateSQL += " where GFID = ?";

			log.debug("IUAApplication update stmt = " + updateSQL);

			st = acmiConn.prepareStatement(updateSQL);			
			int currentIndex = 1;
			
			st.setString(currentIndex++, epmEventsRec.getPolicyNum());
			st.setString(currentIndex++, firstName);
			st.setString(currentIndex++, lastName);
			st.setString(currentIndex++, middleName);
			st.setString(currentIndex++, suffix);
			st.setString(currentIndex++, state);
			st.setString(currentIndex++, cmsLBID);	
			
			if (!isEmptyString(dc.getAgentNumber()))
				st.setString(currentIndex++, agentNumber);
			
			if (!isEmptyString(dc.getAgentName()))
				st.setString(currentIndex++, agentName);
			
			if (!isEmptyString(dc.getProduct()))
				st.setString(currentIndex++, product);
						
			st.setString(currentIndex, epmEventsRec.getGFID());

			st.executeUpdate();
		} catch (Exception ex) {
			log.error("Exception "	+ ex.getClass().getName() + " thrown with message: " + ex.getMessage()
					  + " while trying to update IUAApplication table with metadata. PolicyNumber = " + policyNumber, ex);
			throw ex;
		} finally {
			if (st != null)
				st.close();
		}

		log.debug("Exiting method updateIUAApplicationTable()");

	}

	private boolean isEmptyString(String str) {
		return str == null || str.length() == 0;
	}

	private void runOtherRules(CMEventDTO epmEventsRec) throws Exception {
		String pid;
		String stellentAAID = null;

		log.debug("Entering method runOtherRules()");
		log.debug("Checking to see if main exists for GFID " + epmEventsRec.getGFID());
		EPMHelper epmh = new EPMHelper();
		try {

			boolean appIsClosed = ruleImpl.isApplicationClosed(epmEventsRec.getGFID(), epmEventsRec.getPolicyNum(), "APP");

			// if PID is -1 then MAIN does not exists
			if (appIsClosed) {
				// If The main is closed and GFID does not exist
				// then set the CMSReviewed Attribute to 'N'.
				// It will then be picked up by Reasearch Queue on stellent
				log.info(epmEventsRec.getGFID() + " Application is closed in EPM. So Set CMSReviewed Flag in Stellent to 'N'");
				
				try
				{
					stellentAAID = StellentClient.login();
					stellent.setCMSReviewedFlag(stellentAAID, Long.toString(epmEventsRec.getRecID()));
				} 
				catch (Exception e)
				{
					log.debug("Exception " + e.getClass().getName() + " thrown while trying to login to Stellent");
				}
				finally
				{
					if (stellentAAID != null)
						StellentClient.logout(stellentAAID);				
				}
				log.info("Exit event processeing");
				return;

			}

			String policynum = epmEventsRec.getPolicyNum();
			if (policynum == null)
				policynum = "0";
			policynum = policynum.trim();
			log.info("policy number: " + policynum);
			if (policynum.equals("0")) {
				log.info("No Policy Number Event will not be processed for GFID: " + epmEventsRec.getGFID() + " DocCode: "
						+ epmEventsRec.getDocType());
				return;
			}
			// HERE CHECK IF MAIN EXISTS
			pid = ruleImpl.isProcessExists(epmEventsRec.getGFID(), "APP");

			// if PID is -1 then MAIN does not exists
			if (pid.equals("-1")) {
				log.info("MAIN does not exist");

				if (ruleImpl.doesGFIDExist(epmEventsRec.getGFID()) == false) {
					log.info("GFID does not exist. Setting CMS reviewed flag to N");

					// If The main does not exist and GFID does not exist
					// then set the CMSReviewed Attribute to 'N'.
					// It will then be picked up by Reasearch Queue on stellent
					stellentAAID = StellentClient.login();
					stellent.setCMSReviewedFlag(stellentAAID, Long.toString(epmEventsRec.getRecID()));
					StellentClient.logout(stellentAAID);
				}
				log.info("Exit event processeing");
				return;
			}

			log.info("MAIN exists. Calling EPMRulesImpl for subprocess");
			log.info("GFID = " + epmEventsRec.getGFID());
			log.info("Stellent Doc Type = " + epmEventsRec.getDocType());
			log.info("PID returned from EPMRulesImpl = " + pid);

			pid = ruleImpl.isProcessExists(epmEventsRec.getGFID(), epmEventsRec.getDocType());

			// process exists.
			if (!pid.equals("-1")) {
				log.info("Process Exists");
				pid = ruleImpl.isProcessSuspended(epmEventsRec.getGFID(), epmEventsRec.getDocType());

				// process is suspended.
				if (!pid.equals("-1")) {
					log.info("Process is suspended");
					// manuallystarted is set to null so it will be defaulted to -1
					// ruleImpl.resumeProcess(epmEventsRec.getGFID(), epmEventsRec.getDocType(), null);
					if (!epmEventsRec.getDocType().equalsIgnoreCase("MVR")) {
						epmh.resumeProcessNewET(Long.parseLong(pid), null);
					} else {
						log.info("This is MVR docType, no resume action taken on the process");
					}
				} else {
					// process is not suspended.
					log.info("Process is not suspended");

					pid = ruleImpl.isProcessRunning(epmEventsRec.getGFID(), epmEventsRec.getDocType());

					// process is running.
					if (!pid.equals("-1")) {
						log.info("Process is running");
						pid = ruleImpl.CreateProcess(epmEventsRec.getGFID(), String.valueOf(epmEventsRec.getPolicyNum()),
								epmEventsRec.getDocType(), Long.toString(epmEventsRec.getRecID()));
						// resume the process after creating it from event tracker
						log
								.info("Moving the process out of suspend node to the next activity as proc was created by Event Tracker");
						if (!epmEventsRec.getDocType().equalsIgnoreCase("MVR")) {
							epmh.resumeProcessNewET(Long.parseLong(pid), null);
						} else {
							log.info("This is MVR docType, no resume action taken on the process");
						}
					}
				}
			} else {
				// process doesn't exist
				log.info("Process does not exist");
				log.info("Trying to create a new process");
				pid = ruleImpl.CreateProcess(epmEventsRec.getGFID(), String.valueOf(epmEventsRec.getPolicyNum()), epmEventsRec
						.getDocType(), Long.toString(epmEventsRec.getRecID()));
				// resume the process after creating it from event tracker
				log.info("Moving the process out of suspend node to the next activity as proc was created by Event Tracker");
				if (!epmEventsRec.getDocType().equalsIgnoreCase("MVR")) {
					epmh.resumeProcessNewET(Long.parseLong(pid), null);
				} else {
					log.info("This is MVR docType, no resume action taken on the process");
				}
				// ruleImpl.resumeProcess(epmEventsRec.getGFID(), epmEventsRec.getDocType(), null);
			}
		} catch (Exception ex) {
			log.error("Error. GFID = " + epmEventsRec.getGFID(), ex);
			throw ex;
		}

		log.info("Exiting method runOtherRules()");
	}


	/* Begin changes for USR 7930- EVENT TRACKER *
	 *  Nagrathna Hiriyurkar , 07/28.2007 
	 */
	private void runAppAddRules(Connection acmiConn,CMEventDTO epmEventsRec) throws Exception {
	
		boolean sendEmail    = new Boolean(LocalProperties.getProperty(SEND_UA_EMAIL)).booleanValue();
		boolean isProduction = new Boolean( LocalProperties.getProperty("isProduction") ).booleanValue();
		String escalationTestEmailIds = LocalProperties.getProperty("EscalationTestEmailIds");
		String pid = null;
		String stellentAAID = null;
		String policynum = epmEventsRec.getPolicyNum();
		String emailMessage = NULL_STRING;
		String emailSubject = NULL_STRING;
		//String CatalinaHome = System.getProperty(CATALINA_HOME);	
		//String fullFileName = CatalinaHome + APPLICATION_RESOURCES_EN_PROPERTIES; 
		Properties properties = new Properties();
		ArrayList recipients =null;
		
		log.debug("Entering method runAppAddRules()");
		log.debug("Checking to see if main exists for GFID = "+ epmEventsRec.getGFID());

		try {
			boolean appIsClosed = ruleImpl.isApplicationClosed(epmEventsRec.getGFID(),epmEventsRec.getPolicyNum(), "APP");
			// if PID is -1 then MAIN does not exists
			if (appIsClosed) {
				// If The main is closed and GFID does not exist
				// then set the CMSReviewed Attribute to 'N'.
				// It will then be picked up by Research Queue on Stellent
				log.info(epmEventsRec.getGFID()+ " Application is closed in EPM. So Set CMSReviewed Flag in Stellent to 'N'");
				try {
					stellentAAID = StellentClient.login();
					stellent.setCMSReviewedFlag(stellentAAID, Long.toString(epmEventsRec.getRecID()));
				} catch (Exception e) {
					log.debug("Exception " + e.getClass().getName()+ " thrown while trying to login to Stellent");
				} finally {
					if (stellentAAID != null)
						StellentClient.logout(stellentAAID);
				}
				log.info("Exit event processing");
				return;
			}

			if (policynum == null)
				policynum = "0";
			policynum = policynum.trim();
			log.info("policy number = " + policynum);
			
			if (policynum.equals("0")) {
				log.info("No Policy Number Event will not be processed for GFID = "+ epmEventsRec.getGFID() + " & DocCode = "+ epmEventsRec.getDocType());
				
				if (sendEmail) {
					try {
						
						properties = getEmailMsgInfo(epmEventsRec.getGFID());
						emailSubject = properties.getProperty("emailSubject");
						emailMessage = properties.getProperty("emailMessage");

					} catch (Exception e) {							
						String ErrorMsg = e.getMessage();
						Exception excp = new Exception("Failure  in Datapuller.runAppAddRules();"+ ErrorMsg);
						log.error(excp.getMessage() + "GFID = " + epmEventsRec.getGFID());
						throw e;
					}
					if(isProduction){	
						recipients = ACMICache.getEMailUARecipients();
					}else {
						if (escalationTestEmailIds  != null){
							recipients  = (ArrayList)LocalProperties.separateString(escalationTestEmailIds);
						}
					}
					for (int i = 0; i < recipients.size(); i++)
					{
						log.debug("Email recipent # " + (i + 1) + ": " + recipients.get(i));
					}
					try {
						Mailer ctp = Mailer.getInstance();
						ctp.sendMail(emailSubject, emailMessage, recipients);
						log.debug("Escalation Email was successful...");
					} catch (Exception e) {
						log.debug("Email failed in Datapuller.runAppAddRules()!");
					}
				}
				return;
				
			}
			// HERE CHECK IF MAIN EXISTS
			pid = ruleImpl.isProcessExists(epmEventsRec.getGFID(), "APP");
			// if PID is -1 then MAIN does not exists
			if (pid.equals("-1")) {
				log.info("MAIN does not exist");

				if (ruleImpl.doesGFIDExist(epmEventsRec.getGFID()) == false) {
					log.info("GFID does not exist. Setting CMS reviewed flag to N");
					// If The main does not exist and GFID does not exist
					// then set the CMSReviewed Attribute to 'N'.
					// It will then be picked up by Reasearch Queue on stellent
					stellentAAID = StellentClient.login();
					stellent.setCMSReviewedFlag(stellentAAID, Long.toString(epmEventsRec.getRecID()));
					StellentClient.logout(stellentAAID);
				}
				log.info("Exit event processing");
				return;
			}

			log.info("MAIN exists. Calling EPMRulesImpl for subprocess");
			log.info("GFID = " + epmEventsRec.getGFID());
			log.info("Stellent Doc Type = " + epmEventsRec.getDocType());
			log.info("PID returned from EPMRulesImpl = " + pid);

			pid = ruleImpl.isProcessExists(epmEventsRec.getGFID(), epmEventsRec.getDocType());

			// process exists.
			if (!pid.equals("-1")) {
				log.info("Process Exists");
				pid = ruleImpl.isProcessSuspended(epmEventsRec.getGFID(),epmEventsRec.getDocType());
				// process is suspended.
				if (pid.equals("-1")) {
					log.info("Process is not suspended");
					pid = ruleImpl.isProcessRunning(epmEventsRec.getGFID(),epmEventsRec.getDocType());
					// process is running.
					if (!pid.equals("-1")) {
						log.info("Process is running");
						pid = ruleImpl.CreateProcess(epmEventsRec.getGFID(),
								String.valueOf(epmEventsRec.getPolicyNum()),
								epmEventsRec.getDocType(), Long.toString(epmEventsRec.getRecID()));
						// resume the process after creating it from event tracker
						log.info("Moving the process out of suspend node to the next activity as proc was created by Event Tracker");
					}
				}
			} else {
			//process doesn't exist	
				log.info("Process does not exist");
				log.info("Trying to create a new process");
				pid = ruleImpl.CreateProcess(epmEventsRec.getGFID(), String
						.valueOf(epmEventsRec.getPolicyNum()), epmEventsRec
						.getDocType(), Long.toString(epmEventsRec.getRecID()));
				log.info("Created the MEMO process for the APP ADD event.");
			}
		} catch (Exception ex) {
			log.error("Error. GFID = " + epmEventsRec.getGFID(), ex);
			throw ex;
		}
		log.info("Exiting method runAppAddRules()");
	}
	 
	private static String getFullFileName(String path, String fileName)
	{
		StringBuffer str = new StringBuffer(path);
		
		if (!path.endsWith("/"))
			str.append("/");
		
		str.append(fileName);
		
		return str.toString();			
	}
	
	private Properties getEmailMsgInfo(String gfid) {

		Connection acmiConn = acmiConn = Connect.getConnection();
		Statement stmt = null;
		ResultSet res = null;
		
		Properties properties = new Properties();
		String keyAppName = NULL_STRING;
		String acceptDate = NULL_STRING;
		String employeeID = NULL_STRING;
		String completeDate = NULL_STRING;
		String emailMessage = NULL_STRING;

		String sqlString = "SELECT app.KeyAppFirstName + ' ' + app.KeyAppLastname AS keyApplicant, "
				+ " act.AcceptDate as acceptDate, act.EmployeeID as employeeID, act.CompleteDate as completeDate "
				+ " from IUAAppDocProcesses procs WITH (NOLOCK),IUAActivities act WITH (NOLOCK), IUAApplication app WITH (NOLOCK)"
				+ " Where app.GFID = '"
				+ gfid
				+ "' and procs.status = 'ACT'"
				+ " AND app.GFID = procs.GFID and procs.PID *= act.PID ";

		String emailSubject = "RE: Key Applicant - ";
		String emailMsgg = "Activity accepted by ";

		String emailMsgStr = "A new document has been added to the list for the file listed above."
				+ " Please check to see if you have a process for this applicant and if"
				+ " this is the information needed.";

		try {

			stmt = acmiConn.createStatement();
			res = stmt.executeQuery(sqlString);

			while (res.next()) {

				keyAppName = res.getString("keyApplicant");
				employeeID = res.getString("employeeID");
				acceptDate = res.getString("acceptDate");
				completeDate = res.getString("completeDate");
				if (completeDate == null) {
					break;
				}

			}

			if (employeeID != null)
				employeeID.trim();

			if (acceptDate != null)
				acceptDate.trim();

			emailSubject = emailSubject + keyAppName + ", GFID - " + gfid ;
			

			if (completeDate == null && acceptDate != null) {

				String userName = "";

				try {
					userName = LDAPIUAUserUtil.SelectUsersFromID(employeeID);

				} catch (Exception e) {

					log.debug("Exception caught : " + e.getMessage());
				}

				emailMessage = emailMsgg + employeeID + ", " + userName
						+ ", when this email was sent."
						+ System.getProperty("line.separator")
						+ System.getProperty("line.separator") + emailMsgStr;

			} else {

				emailMessage = emailMsgStr;

			}

			properties.setProperty("emailSubject", emailSubject);
			properties.setProperty("emailMessage", emailMessage);
		} catch (SQLException e) {

			log.debug("Exception caught in getEmailDataInfo() method - "
					+ e.getMessage());

		}
		finally
		{
			if (acmiConn != null)
			{
				Connect.closeResultSet(res);
				Connect.closeSTMT(stmt);
				Connect.closeConnection(acmiConn);
			}
		}
		
		return properties;
	}
	
	/* End of changes for USR 7930- EVENT TRACKER */
	
	/*
	 * public static void main(String[] args) throws Exception { DataPuller dp =
	 * new DataPuller(); dp.getDataFromStellentDB();
	 *  }
	 */
}
