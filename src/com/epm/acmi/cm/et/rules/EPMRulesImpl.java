package com.epm.acmi.cm.et.rules;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.DateUtility;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.LocalProperties;
import com.epm.acmi.util.Mailer;
import com.epm.acmi.util.PasswordEncryption;
import com.fujitsu.iflow.model.workflow.ProcessInstance;

/**
 * This class contains several helper methods for BPM.
 * 
 * @author Dragos Mandruleanu/Rick Perry
 */
public class EPMRulesImpl implements EPMRules {

	private static final String COMMA = ",";
	private static final String EPM_ALERT_RECIPIENTS = "epmalertrecipients";
	private static final String ACMI_UPDATE_FAILED_STR = "ACMI DB Update failed";
	private static final String DUPLICATE_GFID_APP_STR = "Duplicate GFID attempt on inserting on ACMI DB (IUAApplication)";
	private static final String DUPLICATE_GFID_APP_OR_PROC_STR = "Duplicate GFID attempt on inserting on ACMI DB (IUAApplication or IUAAppDocProcesses)";
	
	private static Logger log = Logger.getLogger(EPMRulesImpl.class);
	

	public EPMRulesImpl() {

	}


	/**
	 * Determines in a given process exists in BPM.
	 * 
	 * @see com.epm.acmi.cm.et.rules.EPMRules#isProcessExists(java.lang.String, java.lang.String)
	 */
	public String isProcessExists(String gfId, String docType) throws Exception {
		Connection conn = null;
		String pid = "-1";
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method isProcessExists()");

		try {
			conn = Connect.getConnection();
			stmt = conn.createStatement();
			String sql = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where GFID = '" + gfId
					+ "' and TypeCode = (select code from IUAProcessTypes WITH (NOLOCK) where stellentcode='" + docType
					+ "' ) and ENDDATE is null";
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				pid = rst.getString("pid");
			}
			if (!pid.equals("-1")) {
				log.info("process is exists PID=" + pid);
			} else if (pid.equals("-1")) {
				log.info(" process is not exists PID=" + pid);
			}
		} catch (Exception ex) {
			log.error("Error in isProcessExists(String GFID, String DocType). GFID = " + gfId + " , DocType = " + docType , ex);
			throw ex;
		} finally {
			
				try {
					closeConnection(conn, stmt, rst);
				} catch (Exception ex) {
					log.error(ex);
				}
		}

		log.debug("Exiting method isProcessExists()");

		return pid;
	}


	/**
	 * Determines in a given process exists in BPM.
	 * 
	 * @see com.epm.acmi.cm.et.rules.EPMRules#isProcessExists(java.lang.String, java.lang.String)
	 */
	public String isProcessClosed(String gfId, String docType) throws Exception {
		Connection conn = null;
		String pid = "-1";
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method isProcessClosed()");

		try {
			conn = Connect.getConnection();
			stmt = conn.createStatement();
			String sql = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where GFID = '" + gfId
					+ "' and TypeCode = (select code from IUAProcessTypes WITH (NOLOCK) where stellentcode='" + docType
					+ "') and ENDDATE is not null";

			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				pid = rst.getString("pid");
			}
			if (!pid.equals("-1")) {
				log.info(" process is not closed PID=" + pid);
			} else if (pid.equals("-1")) {
				log.info(" process is closed PID=" + pid);
			}

			
		} catch (Exception ex) {
			log.error("Error in isProcessClosed(String GFID, String DocType). GFID = " + gfId + " , DocType = " + docType , ex);
			throw ex;
		} finally {
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {
				log.error(ex);
			}
		}

		log.debug("Exiting method isProcessClosed()");

		return pid;
	}


	/**
	 * Returns 1 If the application is not closed in EPM
	 * 
	 * @param gfId
	 * @param docType
	 * @return int
	 * @throws Exception
	 */
	public boolean isApplicationClosed(String gfId, String policyNo, String docType) throws Exception {
		int count = getAppCountWithCompletedDateNull(gfId, policyNo);
		
		if (count > 0)
		{
			log.info(gfId + " Application is not closed");
			return false;
		}
		else
		{
			count = getAppCount(gfId, policyNo);
			
			if (count > 0)
			{
				log.info(gfId + " Application is closed");
				return true;
			}
			else
			{
				log.info(gfId + " Application is not closed");
				return false;
			}
		}	
	}
	

	public int getAppCountWithCompletedDateNull(String gfId, String policyNo) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst  = null;
		
		int count = 0;
		log.debug("Entering method getAppCountWithCompletedDateNull()");

		try {
			conn = Connect.getConnection();

			stmt = conn.createStatement();
			String sql = "Select count(gfid) as count from IUAApplication WITH (NOLOCK) where GFID = '" + gfId + "' and PolicyNo = '" + policyNo + "' and completedDate is null";

			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				count = rst.getInt("count");
			}

			log.debug("Exiting method getAppCountWithCompletedDateNull()");
			return count;
		} catch (Exception ex) {
			log.error("Error in getAppCountWithCompletedDateNull(String gfid, String policyNo). GFID = " + gfId, ex);
			throw ex;
		} finally {
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {
				log.error(ex);
			}	
		}
	}

	public int getAppCount(String gfId, String policyNo) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rst  = null;
		
		int count = 0;
		log.debug("Entering method getAppCount()");

		try {
			conn = Connect.getConnection();

			stmt = conn.createStatement();
			String sql = "Select count(gfid) as count from IUAApplication WITH (NOLOCK) where GFID = '" + gfId + "' and PolicyNo = '" + policyNo + "'";

			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				count = rst.getInt("count");
			}

			log.debug("Exiting method getAppCount()");
			return count;
		} catch (Exception ex) {
			log.error("Error in getAppCount(String gfid, String policyNo). GFID = " + gfId, ex);
			throw ex;
		} finally {
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {	
				log.error(ex);
			}
		}
	}


	/**
	 * Return EPM process type code.
	 * 
	 * @param stellentdocType
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private String getEPMProcessTypeCode(String stellentdocType, Connection conn) throws Exception {
		String epmProcessTypeCode = null;
		Statement stmt = null;
		ResultSet rst = null;
		
		try {
			String sql = "select code from IUAProcessTypes WITH (NOLOCK) where stellentcode = '" + stellentdocType + "'";
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			
			while (rst.next()) {
				epmProcessTypeCode = rst.getString("code");
			}
			
		} catch (Exception ex) {
			log.error("Error in isProcessExists(String GFID, String DocType)", ex);
			throw ex;
		
		} finally {
			try {
				closeConnection(null, stmt, rst);
			} catch (Exception e) {
				log.error(e);
			}
		}
		return epmProcessTypeCode;
	}


	/**
	 * Checks to see if the provided GFID exists in the table IUAApplication
	 * 
	 * @param gfId
	 * @return boolean
	 * @throws Exception
	 */
	public boolean doesGFIDExist(String gfId) throws Exception {
		Connection conn = null;
		boolean retVal = false;
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method doesGFIDExist()");

		try {

			conn = Connect.getConnection();

			String sql = "Select count(gfid) as count from IUAApplication WITH (NOLOCK) where GFID = '" + gfId + "' and status != 'ACT' ";
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			int count = 0;
			
			while (rst.next()) {
				count = rst.getInt("count");
			}
			if (count > 0) {
				log.info("GFID=" + gfId + " exists");
				retVal = true;
			}

		} catch (Exception ex) {
			log.error("Error in doesGFIDExist(String GFID). GFID = " + gfId , ex);
			throw ex;
		} finally {
			
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {
				log.error(ex);
			}
		}

		log.debug("Exiting method doesGFIDExist()");

		return retVal;
	}


	/**
	 * Checks if a process is running in BPM
	 * 
	 * @see com.epm.acmi.cm.et.rules.EPMRules#isProcessRunning(java.lang.String, java.lang.String)
	 */
	public String isProcessRunning(String gfId, String docType) throws Exception {
		Connection conn = null;
		String pid = "-1";
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method isProcessRunning()");

		try {

			conn = Connect.getConnection();

			String sql = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where GFID = '" + gfId
					+ "' and TYPECODE = (select code from IUAProcessTypes WITH (NOLOCK) where stellentcode='" + docType
					+ "') and STATUS = 'ACT'";

			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				pid = rst.getString("pid");
				log.info(gfId + " process is running PID=" + pid);
			}

		} catch (Exception ex) {
			log.error("Error in isProcessRunning(String GFID, String DocType). GFID = " + gfId + " , DocType = " + docType , ex);
			throw ex;
		} finally {
			if (conn != null) {
				try {
					closeConnection(conn, stmt, rst);
				} catch (Exception ex) {
					log.error(ex);
				}
			}
		}

		log.debug("Exiting method isProcessRunning()");

		return pid;
	}


	/**
	 * Checks if a process is suspended in BPM.
	 * 
	 * @see com.epm.acmi.cm.et.rules.EPMRules#isProcessSuspended(java.lang.String, java.lang.String)
	 */
	public String isProcessSuspended(String gfId, String docType) throws Exception {
		Connection conn = null;
		String pid = "-1";
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method isProcessSuspended()");

		try {

			conn = Connect.getConnection();
			
			String sql = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where GFID = '" + gfId
					+ "' and TYPECODE = (select code from IUAProcessTypes WITH (NOLOCK) where stellentcode='" + docType
					+ "') and STATUS = 'SUSP'";
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				pid = rst.getString("pid");
				log.info(gfId + " process is suspended PID=" + pid);
			}

		} catch (Exception ex) {
			log.error("Error in isProcessSuspended(String GFID, String DocType). GFID = " + gfId + " , DocType = " + docType , ex);
			throw ex;
		} finally {
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {
					log.error(ex);
			}
		}

		log.debug("Exiting method isProcessSuspended()");

		return pid;
	}


	/**
	 * Resumes a process in BPM.
	 */
	public boolean resumeProcess(String gfId, String docType, String mauallystarted) throws Exception {
		Connection conn = null;
		boolean returnVal = false;
		String pid = "-1";
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method ResumeProcess()");
		
		try {

			conn = Connect.getConnection();
			String sql = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where GFID = '" + gfId
					+ "' and TYPECODE = (select code from IUAProcessTypes WITH (NOLOCK) where stellentcode='" + docType
					+ "') and status = 'SUSP'";
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);

			while (rst.next()) {
				pid = rst.getString("pid");
			}
			if (!pid.equals("-1")) {

				EPMHelper epmHelper = new EPMHelper();
				if (epmHelper.resumeProcessNew(Long.parseLong(pid), null, mauallystarted) == true)
					returnVal = true;
			} else {
				log.debug("There are no processes found for GFID = " + gfId + " and STATUS = 'SUSP'");

			}
		} catch (Exception ex) {
			log.error("Error in ResumeProcess(String GFID, String DocType). GFID = " + gfId + " , DocType = " + docType , ex);
			throw ex;
		} finally {
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {
				log.error(ex);
			}
		}

		log.debug("Exiting method ResumeProcess()");

		return returnVal;
	}


	public String CreateProcess(String gfId, String policyNum, String stelentDocTypeCode, String recId) throws Exception {
		Connection conn = null;
		String pid = "-1";
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method CreateProcess()");

		try {

			conn = Connect.getConnection();

			String epmProcessTypeCode = getEPMProcessTypeCode(stelentDocTypeCode, conn);

			log.debug("epmProcessTypeCode = " + epmProcessTypeCode);

			if (policyNum == null)
				policyNum = "";

			long piId = -1;
			if (epmProcessTypeCode.trim().equalsIgnoreCase("MAIN")) {
				piId = InstantiateMainProcess(gfId, policyNum, recId, conn);
				if (piId == -1)
					log.debug("DID NOT instantiate ACMI_Main for GFID=" + gfId);
				else
					log.info("New ACMI_Main process created successfully" + " PID: " + piId);

			} else {
				stmt = conn.createStatement();
				String sql = "select pid from IUAAppDocProcesses WITH (NOLOCK) where GFID = '" + gfId + "' and typecode = 'MAIN'";
				rst = stmt.executeQuery(sql);
				String mainPID = "-1";
				while (rst.next()) {
					mainPID = rst.getString("pid");
				}

				log.debug("mainPID = " + mainPID);

				if (!mainPID.equals("-1")) {
					if (epmProcessTypeCode.trim().equalsIgnoreCase("MEMO")) {
						piId = InstantiateDocProcess(gfId, policyNum, mainPID, epmProcessTypeCode, "ACMI_Memo",
								LocalProperties.getProperties(), conn);
						if (piId == -1)
							log.debug("DID NOT instantiate ACMI_Memo for GFID=" + gfId);
						else
							log.info("New ACMI_Memo process created successfully for GFID=" + gfId + "  PID: " + piId);

					} else if (epmProcessTypeCode.trim().equalsIgnoreCase("EXAM")) {
						piId = InstantiateDocProcess(gfId, policyNum, mainPID, epmProcessTypeCode, "ACMI_Paramed_Exam",
								LocalProperties.getProperties(), conn);
						if (piId == -1)
							log.debug("DID NOT instantiate ACMI_Paramed_Exam");
						else
							log.info("New ACMI_Paramed_Exam process created successfully for GFID=" + gfId + " PID: " + piId);

					} else if (epmProcessTypeCode.trim().equalsIgnoreCase("DMV")) {
						piId = InstantiateMVRDocProcess(gfId, policyNum, mainPID, epmProcessTypeCode, "ACMI_DMV",
								LocalProperties.getProperties(), conn);
						if (piId == -1)
							log.debug("DID NOT instantiate ACMI_DMV for GFID=" + gfId);
						else
							log.info("New ACMI_DMV process created successfully for GFID=" + gfId + " PID: " + piId);

					} else if (epmProcessTypeCode.trim().equalsIgnoreCase("MRREC")) {
						piId = InstantiateDocProcess(gfId, policyNum, mainPID, epmProcessTypeCode, "ACMI_MedRec_Receive",
								LocalProperties.getProperties(), conn);
						if (piId == -1)
							log.debug("DID NOT instantiate ACMI_MedRec_Receive for GFID=" + gfId);
						else
							log.info("New ACMI_MedRec_Receive process created successfully for GFID=" + gfId + " PID: " + piId);

					} else if (epmProcessTypeCode.trim().equalsIgnoreCase("QUOT")) {
						piId = InstantiateDocProcess(gfId, policyNum, mainPID, epmProcessTypeCode, "ACMI_Quote_Letter",
								LocalProperties.getProperties(), conn);
						if (piId == -1)
							log.debug("DID NOT instantiate ACMI_Quote_Letter for GFID=" + gfId);
						else
							log.info("New ACMI_Quote_Letter process created successfully for GFID=" + gfId + " PID: " + piId);
					}
				}
			}

			pid = new Long(piId).toString();

		} catch (Exception ex) {
			log.error("Error in CreateProcess(String GFID, String policyNum, String DocType). GFID = " + gfId + " , DocType = " + stelentDocTypeCode , ex);
			throw ex;
		} finally {
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {
				log.error("Error. GFID = " + gfId + " , DocType = " + stelentDocTypeCode , ex);
			}
		}

		log.debug("Exiting method CreateProcess()");

		return pid;
	}


	/**
	 * Create Document Processes
	 * 
	 * @param gfId
	 * @param policyNumber
	 * @param mainPID
	 * @param epmProcessTypeCode
	 * @param templateName
	 * @param props2
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private long InstantiateDocProcess(String gfId, String policyNumber, String mainPID, String epmProcessTypeCode,
			String templateName, Properties props2, Connection conn) throws Exception {
		long piId = -1;
		EPMHelper epmHelper = null;
		Statement stmt = null;
		ResultSet rst = null;
		PreparedStatement psmt = null;
		
		log.debug("Entering method InstantiateDocProcess()");

		try {
			Properties props = new Properties();
			props.put("gfId", gfId);
			props.put("mainProcessID", mainPID);
			props.put("policyNumber", policyNumber);
			props.put("startedByET", "Yes");

			epmHelper = new EPMHelper();
	
			String pAdminUserName = props2.getProperty("ADMIN_USER");
			// get decrytped password for admin user
			String pAdminPassword = PasswordEncryption.getDecryptedPassword(pAdminUserName);
			ProcessInstance pi = epmHelper.startNewProcessWithUDAs(pAdminUserName, pAdminPassword,
					 templateName, gfId + "_" + epmProcessTypeCode, props);

			if (pi == null)
				log.debug("Instantiation of " + templateName + " Failed");
			else {
				log.debug("Process Instantiated Successfully");

				piId = pi.getId();

				DateUtility gd = new DateUtility();
				String GMTDate = gd.calculateDate();
				String processStatus = "=1";
				String sql = "select code from iuaProcessStatus WITH (NOLOCK) where upper(description) = 'ACTIVE'";
				stmt = conn.createStatement();

				rst = stmt.executeQuery(sql);
				while (rst.next()) {
					processStatus = rst.getString("code");
				}

				if (processStatus == null)
					processStatus = "-1";
				log.debug("processStatus = " + processStatus);

				log.debug("Inserting info into IUAAppDocProcesses table");
				String sql1 = "insert into iuaappdocprocesses (pid,gfid,typecode,status,startdate)values(?,?,?,?,?)";
				psmt = conn.prepareStatement(sql1);

				psmt.setLong(1, piId);
				psmt.setString(2, gfId);
				psmt.setString(3, epmProcessTypeCode);
				psmt.setString(4, processStatus);
				psmt.setString(5, GMTDate);
				psmt.executeUpdate();

				log.debug("IUAAppDocProcesses table updated successfully");
			}

		} catch (Exception e) {
			log.error("Exception Caught instantiating " + templateName + ": GFID =  " + gfId + e );
			throw e;
		} finally {
			try {
				if (psmt != null ) {
					psmt.close();
				}
				closeConnection(null, stmt, rst);
			} catch (Exception e) {
				log.error(e);
			}
			epmHelper = null; // remove reference
		}

		log.debug("Exiting method InstantiateDocProcess()");

		return piId;
	}


	/**
	 * Create MVR document type process
	 * 
	 * @param gfId
	 * @param policyNumber
	 * @param mainPID
	 * @param epmProcessTypeCode
	 * @param templateName
	 * @param props2
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private long InstantiateMVRDocProcess(String gfId, String policyNumber, String mainPID, String epmProcessTypeCode,
			String templateName, Properties props2, Connection conn) throws Exception {
		long piId = -1;
		EPMHelper epmHelper = null;
		Statement stmt = null;
		ResultSet rst = null;
		PreparedStatement psmt = null;
		
		log.debug("Entering method InstantiateMVRDocProcess()");

		try {
			Properties props = new Properties();
			props.put("gfId", gfId);
			props.put("mainProcessID", mainPID);
			props.put("policyNumber", policyNumber);

			epmHelper = new EPMHelper();
			String pUserName = props2.getProperty("username");
			String pUserPassword = PasswordEncryption.getDecryptedPassword(pUserName);
			ProcessInstance pi = epmHelper.startNewProcessWithUDAs(pUserName,pUserPassword,
					 templateName, gfId + "_" + epmProcessTypeCode, props);
		
			if (pi == null)
				log.debug("Instantiation of " + templateName + " Failed");
			else {
				log.debug("Process Instantiated Successfully");

				piId = pi.getId();

				DateUtility gd = new DateUtility();
				String GMTDate = gd.calculateDate();
				String processStatus = "=1";
				String sql = "select code from iuaProcessStatus WITH (NOLOCK) where upper(description) = 'ACTIVE'";
				stmt = conn.createStatement();

				rst = stmt.executeQuery(sql);
				while (rst.next()) {
					processStatus = rst.getString("code");
				}

				if (processStatus == null)
					processStatus = "-1";
				log.debug("processStatus = " + processStatus);

				log.debug("Inserting info into IUAAppDocProcesses table");
				String sql1 = "insert into iuaappdocprocesses (pid,gfid,typecode,status,startdate)values(?,?,?,?,?)";
				psmt = conn.prepareStatement(sql1);

				psmt.setLong(1, piId);
				psmt.setString(2, gfId);
				psmt.setString(3, epmProcessTypeCode);
				psmt.setString(4, processStatus);
				psmt.setString(5, GMTDate);
				psmt.executeUpdate();

				log.debug("IUAAppDocProcesses table updated successfully");
			}

		} catch (Exception e) {
			log.error("Exception Caught instantiating " + templateName + ": GFID =  " + gfId + e);
			throw e;
		} finally {
			try {
				if (psmt != null ) {
					psmt.close();
				}
				closeConnection(null, stmt, rst);
			} catch (Exception e) {
				log.error(e);
			}
			epmHelper = null; // remove reference
		}

		log.debug("Exiting method InstantiateDocProcess()");

		return piId;
	}


	/**
	 * Instantiate MAIN process
	 * 
	 * @param gfId
	 * @param policyNumber
	 * @param recId
	 * @param conn
	 * @return
	 * @throws Exception
	 */
	private long InstantiateMainProcess(String gfId, String policyNumber, String recId, Connection conn) throws Exception {
		long piId = -1;
		EPMHelper epmHelper = null;
		String templateName = null;
		Statement stmt = null;
		ResultSet rst = null;
		PreparedStatement psmt = null;
		PreparedStatement psmt1 = null, psmt2 = null;
		
		log.debug("Entering method InstantiateMainProcess()");

		try {
			DateUtility gd = new DateUtility();
			String GMTDate = gd.calculateDate();

			Properties props = new Properties();
			props.put("gfId", gfId);
			props.put("policyNumber", policyNumber);
			props.put("startedByET", "Yes");

			rst = null;
			epmHelper = new EPMHelper();
			stmt = null;
			log.debug("policy number=" + policyNumber +" GFID= "+ gfId);
			if (!policyNumber.trim().equals("0"))

			{
				stmt = conn.createStatement();
				String sql = "Select count(gfid) as count  from iuaApplication WITH (NOLOCK) where"  
						+ " policyNo='"+ policyNumber +"' and completeddate is null";

				rst = stmt.executeQuery(sql);

				int numRecs = 0;

				// GFID is primary key so there can be only one row in database for a given GFID value
				while (rst.next()) {
					numRecs = rst.getInt("count");
				}

				if (numRecs > 0) {
					log.debug("This gfId - " + gfId + " - already exists in the iuaApplication table. So Application process will not be created, instead a MEMO process will be creating it to be duplicate app");

					String sql1 = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where gfId = '" + gfId
							+ "' and status = 'SUSP' and typeCode = 'MEMO'";
					rst = stmt.executeQuery(sql1);
					String memoPID = "-1";
					while (rst.next()) {
						memoPID = rst.getString("pid");
					}

					if (!memoPID.equals("-1")) {
						log.debug("Resuming existing suspended ACMI_Memo process, PID = " + memoPID);
						
						try
						{
							epmHelper.resumeProcessNewET(new Long(memoPID).longValue(), null);	
						} catch (Exception ex)
						{
							log.error("Exception " + ex.getClass().getName() + " thrown with message: " + ex.getMessage() + " while trying to resume MEMO process", ex);
							modifyJournalEntry(new Long(recId), JournalRecord.PROCESSED_SUCCESSFULLY, false);
							throw ex;
						}
					} else {
						log.debug("Instantiating new ACMI_Memo process.");

						String sql2 = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where gfId = '" + gfId
								+ "' and status != 'DEAD' and status != 'ABORT' and typeCode = 'MAIN'";
						rst = stmt.executeQuery(sql2);

						while (rst.next()) {
							memoPID = rst.getString("pid");
							props.put("mainProcessID", memoPID);
						}
						templateName = "ACMI_Memo";
						String sql3 = "select code from iuaProcessTypes WITH (NOLOCK) where upper(templateName) = upper('" + templateName
								+ "')";
						String epmProcessTypeCode = "-1";

						rst = stmt.executeQuery(sql3);
						while (rst.next()) {
							epmProcessTypeCode = rst.getString("code");
						}
						if (epmProcessTypeCode == null)
							epmProcessTypeCode = "-1";
						log.debug("epmProcessTypeCode = " + epmProcessTypeCode);
			
						String pUserName = LocalProperties.getProperty("username");
						String pUserPassword = PasswordEncryption.getDecryptedPassword(pUserName);
						ProcessInstance pi = null;
						
						try
						{
							pi = epmHelper.startNewProcessWithUDAs(pUserName, pUserPassword, templateName, gfId + "_" + epmProcessTypeCode, props);
						} catch (Exception ex)
						{
							log.error("Exception " + ex.getClass().getName() + " thrown with message: " + ex.getMessage() + " while trying to start MEMO process", ex);
							modifyJournalEntry(new Long(recId), JournalRecord.PROCESSED_SUCCESSFULLY, false);
							throw ex;
						}
						
						
						if (pi == null)
							log.debug("Instantiation of ACMI_Memo Process Failed");
						else {
							log.debug("Instantiation of ACMI_Memo Process Successful");
							try
							{
							piId = pi.getId();

							String sql4 = "select code from iuaProcessStatus WITH (NOLOCK) where upper(description) = 'ACTIVE'";
							String processStatus = "-1";

							rst = stmt.executeQuery(sql4);
							while (rst.next()) {
								processStatus = rst.getString("code");
							}

							if (processStatus == null)
								processStatus = "-1";
							log.debug("processStatus = " + processStatus);

							log.debug("Inserting info into IUAAppDocProcesses table");

							String sql5 = "Insert into IUAAppDocProcesses (pid,gfid,typecode,status,startDate) "
									+ "values (?,?,?,?,?)";
							psmt = conn.prepareStatement(sql5);

							psmt.setLong(1, piId);
							psmt.setString(2, gfId);
							psmt.setString(3, epmProcessTypeCode);
							psmt.setString(4, processStatus);
							psmt.setString(5, GMTDate);
							psmt.executeUpdate();

							log.debug("IUAAppDocProcesses table updated successfully");

							} catch (Exception ex)
							{
								log.error("Exception " + ex.getClass().getName() + " thrown with message: '" + ex.getMessage() + "' while trying to add record to table IUAAppDocProcesses.", ex);
								modifyJournalEntry(new Long(recId), JournalRecord.ACMI_UPDATE_FAILED, true);
								sendEmailAlert(policyNumber, gfId, ACMI_UPDATE_FAILED_STR);
								throw ex;
							}
						}
					}
				} else if (numRecs == 0) {
					EPMRulesImpl ruleImpl = new EPMRulesImpl();
					boolean appIsClosed = ruleImpl.isApplicationClosed(gfId, policyNumber, "APP");
					// if PID is -1 then MAIN does not exists
					if (appIsClosed) {
						// If The main is closed and GFID does not exist
						// then set the CMSReviewed Attribute to 'N'.
						// It will then be picked up by Reasearch Queue on stellent
						log.info(gfId + " Application is closed in EPM. So Set CMSReviewed Flag in Stellent to 'N'");
						String stellentAAID = null;
						try
						{
							stellentAAID = StellentClient.login();
							StellentClient stellent = new StellentClient();
							stellent.setCMSReviewedFlag(stellentAAID, recId);
						} 
						catch (Exception ex)
						{
							log.error("Exception " + ex.getClass().getName() + " thrown while trying to login to Stellent", ex);
							modifyJournalEntry(new Long(recId), JournalRecord.PROCESSED_SUCCESSFULLY, false);
						}
						finally
						{
							if (stellentAAID != null)
								StellentClient.logout(stellentAAID);				
						}
						
						log.info("Exit event processeing");
						return piId;
					}
				}
			} else {//Policy Number == 0
				stmt = conn.createStatement();
				
				String sql = "Select count(gfid) as count  from iuaApplication WITH (NOLOCK) where"  
						+ " policyNo='0' and GFID = '" + gfId + "'";

				rst = stmt.executeQuery(sql);

				int numRecs = 0;

				// GFID is primary key so there can be only one row in database for a given GFID value
				while (rst.next()) {
					numRecs = rst.getInt("count");
				}

				if (numRecs > 0) {
				    stmt.close();
				    modifyJournalEntry(new Long(recId), JournalRecord.DUPLICATE_GFID, true);
				    sendEmailAlert("0", gfId, DUPLICATE_GFID_APP_STR);
				    throw new Exception("Record already exists in IUAApplication for policyNo = 0 and gfid = " + gfId);
				} else
				{
					templateName = "ACMI_Main";
					String sql6 = "select code from iuaProcessTypes WITH (NOLOCK) where upper(templateName) = upper('" + templateName + "')";
	
					String epmProcessTypeCode = "-1";
					rst = stmt.executeQuery(sql6);
	
					while (rst.next()) {
						epmProcessTypeCode = rst.getString("code");
					}
					if (epmProcessTypeCode == null)
						epmProcessTypeCode = "-1";
					log.debug("epmProcessTypeCode = " + epmProcessTypeCode);
					
					String pUserName = LocalProperties.getProperty("username");
					String pUserPassword = PasswordEncryption.getDecryptedPassword(pUserName);
					ProcessInstance pi = null;
					
					try
					{
						pi = epmHelper.startNewProcessWithUDAs(pUserName, pUserPassword,
								templateName, gfId + "_" + epmProcessTypeCode, props);
					} catch (Exception ex)
					{
						log.error("Exception " + ex.getClass().getName() + " thrown with message " + ex.getMessage() + " trying to start new main process", ex);
						modifyJournalEntry(new Long(recId), JournalRecord.PROCESSED_SUCCESSFULLY, false);
						throw ex;
					}
					
					if (pi == null)
						log.debug("Instantiation of ACMI_Main Process Failed");
					else {
						try
						{
							log.debug("Instantiation of ACMI_Main Process Successful");
		
							piId = pi.getId();
		
							String sql7 = "select code from iuaAppStatus WITH (NOLOCK) where upper(description) = 'ACTIVE'";
							String appStatus = "-1";
							rst = stmt.executeQuery(sql7);
							
							while (rst.next()) {
								appStatus = rst.getString("code");
							}
							
							if (appStatus == null)
								appStatus = "-1";
							
							log.debug("appStatus = " + appStatus);		
							log.debug("Inserting info into IUAApplication table");		
							String sql8 = "Insert into IUAApplication (gfid,policyNo,status,receivedDate,LastUpdatedDateTime,LastUpdatedUserID) "
									+ "values (?,?,?,?,?,?)";
		
							psmt1 = conn.prepareStatement(sql8);
							psmt1.setString(1, gfId);
							psmt1.setString(2, policyNumber);
							psmt1.setString(3, appStatus);
							psmt1.setString(4, GMTDate);
							psmt1.setString(5, GMTDate);
							psmt1.setString(6, "EventTrackerProcess");
							psmt1.executeUpdate();
		
							log.debug("IUAApplication table updated successfully");
							String processStatus = "-1";
		
							String sql9 = "select code from iuaProcessStatus WITH (NOLOCK) where upper(description) = 'ACTIVE'";
							rst = stmt.executeQuery(sql9);
							while (rst.next()) {
								processStatus = rst.getString("code");
							}
							if (processStatus == null)
								processStatus = "-1";
							log.debug("processStatus = " + processStatus);		
							log.debug("Inserting info into IUAAppDocProcesses table");		
							String sql10 = "Insert into IUAAppDocProcesses (pid,gfid,typecode,status,startDate) " + "values (?,?,?,?,?)";
							psmt2 = conn.prepareStatement(sql10);
		
							psmt2.setLong(1, piId);
							psmt2.setString(2, gfId);
							psmt2.setString(3, epmProcessTypeCode);
							psmt2.setString(4, processStatus);
							psmt2.setString(5, GMTDate);
							psmt2.executeUpdate();
		
							log.debug("IUAAppDocProcesses table updated successfully");
		
							rst.close();
							stmt.close();
							psmt2.close();
							psmt1.close();	
						} catch (Exception e)
						{
							log.error("Exception " + e.getClass().getName() + " thrown with message: " + e.getMessage() + " while trying to insert into tables IUAApplication and IUAAppDocProcesses");
							modifyJournalEntry(new Long(recId), JournalRecord.ACMI_UPDATE_FAILED, true);
							sendEmailAlert("0", gfId, DUPLICATE_GFID_APP_OR_PROC_STR);
						}
					}
				}
			}

		} catch (Exception e) {
			log.error("Exception Caught during processing for process of type " + templateName + ": GFID = " +gfId + e);
			throw e;
		} finally {
			try {
				if (psmt != null) {
					psmt.close();
				}
				if (psmt1 != null) {
					psmt1.close();
				}
				if (psmt2 != null) {
					psmt2.close();
				}
				closeConnection(null, stmt, rst);
			} catch(Exception e) {
				log.error(e);
			}
			epmHelper = null; // remove reference
		}

		log.debug("Exiting method InstantiateMainProcess()");

		return piId;
	}

	/**
	 * @param policyNumber
	 * @param gfid
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void sendEmailAlert(String policyNumber, String gfid, String cause) throws MessagingException, IOException
	{
		String emailSubject = "EPM Event Tracker Alert - " + cause;
		String emailMessage = getEmailMessageForEventAlerts(policyNumber, gfid, cause);
		String epmAlertRecipients = LocalProperties.getProperty(EPM_ALERT_RECIPIENTS);
		ArrayList recipients = getRecipientsFromString(epmAlertRecipients);		
		Mailer ctp = Mailer.getInstance();
		ctp.sendMail(emailSubject, emailMessage, recipients);
	}

	/**
	 * @param policyNumber
	 * @param gfid
	 * @param cause
	 * @return
	 */
	private static String getEmailMessageForEventAlerts(String policyNumber, String gfid, String cause)
	{
		StringBuffer str = new StringBuffer();
		str.append("Event Tracker failed for policy number = ");
		str.append(policyNumber);
		str.append(" and GFID = ");
		str.append(gfid);
		str.append(", with cause: ");
		str.append(cause);
		return str.toString();
	}
	
	/**
	 * @param recipients
	 * @return
	 */
	private static ArrayList getRecipientsFromString(String recipients)
	{
		ArrayList result = new ArrayList();
		StringTokenizer tok = new StringTokenizer(recipients, COMMA);
		
		while (tok.hasMoreTokens())
		{
			String element = tok.nextToken();
			result.add(element);
		}
		
		return result;
	}
	
	/**
	 * @param recId
	 * @param failureState
	 * @param moveEventTableContents
	 */
	public static void modifyJournalEntry(Long recId, int failureState, boolean moveEventTableContents)
	{
		JournalRecord journal = (JournalRecord)JournalRecord.journalMap.get(recId);
		
		if (journal != null)
		{
			journal.setMoveEventTableContents(moveEventTableContents);
			journal.setFailureState(failureState);
		}
	}
	/**
	 * Checks if a process is suspended.
	 * 
	 * @see com.epm.acmi.cm.et.rules.EPMRules#isEPMProcessSuspended(java.lang.String, java.lang.String)
	 */

	public String isEPMProcessSuspended(String gfId, String epmDocType) throws Exception {
		Connection conn = null;
		String pid = "-1";
		Statement stmt = null;
		ResultSet rst = null;
		
		log.debug("Entering method isEPMProcessSuspended()");

		try {

			conn = Connect.getConnection();

			String sql = "Select pid from IUAAppDocProcesses WITH (NOLOCK) where GFID = '" + gfId + "' and TYPECODE = '" + epmDocType
					+ "' and STATUS = 'SUSP'";
			stmt = conn.createStatement();
			rst = stmt.executeQuery(sql);
			while (rst.next()) {
				pid = rst.getString("pid");
			}
		} catch (Exception ex) {
			log.error("Error in isProcessSuspended(String GFID, String DocType) GFID = " + gfId  + " , DocType = " + epmDocType, ex);
			throw ex;
		} finally {
			try {
				closeConnection(conn, stmt, rst);
			} catch (Exception ex) {
				log.error(ex);
			}
		}

		log.debug("Exiting method isEPMProcessSuspended()");

		return pid;
	}
	
	public void closeConnection(Connection conn, Statement stmt, ResultSet rst) throws SQLException {
		if (stmt != null) {
			stmt.close();
			stmt = null;
		}
		if (rst != null) {
			rst.close();
			rst = null;
		}
		if (conn != null) {
			conn.close();
			conn = null;
		}
	}
}
