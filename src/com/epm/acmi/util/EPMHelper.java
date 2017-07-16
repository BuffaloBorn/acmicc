package com.epm.acmi.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.fujitsu.iflow.model.bpm.BPMSession;
import com.fujitsu.iflow.model.bpmcore.BPMSessionImpl;
import com.fujitsu.iflow.model.util.IflowEnumeration;
import com.fujitsu.iflow.model.util.ModelException;
import com.fujitsu.iflow.model.util.ModelInternalException;
import com.fujitsu.iflow.model.util.WFInternalException;
import com.fujitsu.iflow.model.util.WFServerException;
import com.fujitsu.iflow.model.workflow.DataItem;
import com.fujitsu.iflow.model.workflow.Filter;
import com.fujitsu.iflow.model.workflow.Plan;
import com.fujitsu.iflow.model.workflow.ProcessInstance;
import com.fujitsu.iflow.model.workflow.ProcessInstantiator;
import com.fujitsu.iflow.model.workflow.WFAdminSession;
import com.fujitsu.iflow.model.workflow.WFObjectFactory;
import com.fujitsu.iflow.model.workflow.WFObjectList;
import com.fujitsu.iflow.model.workflow.WFSession;
import com.fujitsu.iflow.model.workflow.WorkItem;
import com.fujitsu.iflow.server.dataobjects.FilterEnum;

/**
 * EPMHelper Class implments EPM specific funtions using EPM API exposed by Fujitsu EPM.
 * 
 * @author Rick Perry, Dragos Mandruliano Code Fixes by Jay Hombal
 */
public class EPMHelper {
	private static final int MAX_LOGIN_ATTEMPTS = 5;
	private static final String FAILED_TO_CONNECT = "Failed to connect to e-Flow server after " + MAX_LOGIN_ATTEMPTS + " retries...";
	private static Logger log = Logger.getLogger(EPMHelper.class);

	public EPMHelper() {
	}


	/**
	 * Update UDA
	 * 
	 * @param processID
	 * @param udaName
	 * @param udaValue
	 * @param session
	 * @throws Exception
	 */
	private void updateUDA(long processID, String udaName, String udaValue, WFSession session) throws Exception {
		ProcessInstance myProcess = null;
		log.debug("Begin updateUDA()");
		try {
			myProcess = WFObjectFactory.getProcessInstance(processID, session);
			if (myProcess != null) {
				if (!myProcess.hasDataItem(udaName) && !myProcess.isInStructuralEditMode() && !myProcess.isLockedForEdit()
						&& !myProcess.isInEditMode()) {
					myProcess.startStructuralEdit();
					log.debug("Start structural edit for process ID: " + myProcess.getId());
					myProcess.addDataItem(udaName, "STRING", udaValue);
					log.debug("added data item");
					myProcess.commitStructuralEdit();
					log.debug("commited structural edit for process ID: " + myProcess.getId());
				} else if (!myProcess.hasDataItem(udaName)
						&& (myProcess.isInStructuralEditMode() || myProcess.isInEditMode() || myProcess.isLockedForEdit())) {
					log.debug("cannot add this uda as the process ID" + myProcess.getId()
							+ " is already in structural edit mode!");
				} else if (myProcess.hasDataItem(udaName) && !myProcess.isInStructuralEditMode() && !myProcess.isLockedForEdit()
						&& !myProcess.isInEditMode()) {
					myProcess.startStructuralEdit();
					log.debug("Start structural edit for process ID: " + myProcess.getId());
					Properties newProp = new Properties();
					newProp.setProperty(udaName, udaValue);
					myProcess.setDataItemValues(newProp);
					log.debug("updated data item");
					myProcess.commitStructuralEdit();
					log.debug("commited structural edit for process ID: " + myProcess.getId());
				} else if (myProcess.hasDataItem(udaName)
						&& (myProcess.isInStructuralEditMode() || myProcess.isInEditMode() || myProcess.isLockedForEdit())) {
					log.debug("cannot update this uda as the process ID" + myProcess.getId()
							+ " is already in structural edit mode!");
				} else {
					log.debug(" no condition matched for process ID " + myProcess.getId());
				}
			}
			myProcess = null;
		} catch (Exception e) {
			log.error("updateUDA Failed", e);
			throw new Exception(" failed because\n" + e.getMessage());
		}

		log.debug("End updateUDA()");
	}


	/**
	 * Accept Task
	 * 
	 * @param nodeInstanceID
	 * @throws Exception
	 */
	public void acceptTask(long nodeInstanceID, WFSession session) throws Exception {
		log.debug("Begin acceptTask()");
		WorkItem myTask = null;
		String acceptedDate = null;
		try {
			myTask = WFObjectFactory.getWorkItem(session.getUserName(), nodeInstanceID, session);
			if (myTask != null) {
				myTask.accept();
				acceptedDate = (new Date()).toString();
				log.debug("Accepted work item ID: " + myTask.getId());
				String udaName = "acceptedDate";
				updateUDA(myTask.getProcessInstanceId(), udaName, acceptedDate, session);
				myTask = null;
			}
		} catch (Exception e) {
			log.error("acceptTask Failed", e);
			throw new Exception("acceptTask: failed because\n" + e.getMessage());
		}
		log.debug("End acceptTask()");
	}


	/**
	 * Complete Task
	 * 
	 * @param nodeInstanceID
	 * @throws Exception
	 */
	public void completeTask(long nodeInstanceID, WFSession session) throws Exception {
		log.debug("Begin completeTask()");
		WorkItem myTask = null;
		String completeDate = null;
		try {
			myTask = WFObjectFactory.getWorkItem(session.getUserName(), nodeInstanceID, session);
			if (myTask != null) {
				completeDate = (new Date()).toString();
				String udaName = "completeDate";
				updateUDA(myTask.getProcessInstanceId(), udaName, completeDate, session);
				String choiceName = myTask.getChoices()[0];
				myTask.makeChoice(choiceName);
				log.debug("WorkItem ID: " + myTask.getId() + " made choice: " + choiceName);
				myTask = null;
			}
		} catch (Exception e) {
			log.error("completeTask Failed", e);
			throw new Exception("completeTask: failed because\n" + e.getMessage());
		}
		log.debug("End completeTask()");
	}


	/**
	 * Start Process
	 * 
	 * @param numOfPi
	 * @param planID
	 * @throws Exception
	 */
	public void startProcess(int numOfPi, long planID, WFSession session) throws Exception {

		log.debug("Begin startProcess()");
		try {
			log.debug("Begin startProcess()");
			Plan plan = WFObjectFactory.getPlan(planID, session);
			ProcessInstance pi = null;
			for (int i = 0; i < numOfPi; i++) {
				pi = plan.createProcessInstance();
				log.debug("//------------------------------------------------");
				log.debug("Process Name --> " + pi.getName());
				log.debug("Process ID --> " + pi.getId());
				log.debug("//------------------------------------------------");

				for (int j = 0; j < 10; j++)
				{
					try
					{
						pi.start();
						break;
					} catch (Exception e)
					{
						//Wait 1/10 of a second... Then retry...
						if (j < 9)
							Thread.sleep(100);
						else
							throw e;
					}
				} 
			}
		} catch (Exception ex) {
			log.error("startProcess Failed", ex);
			throw new Exception("TestAPI: startProcess: failed because \n" + ex.toString());
		}
		log.debug("End startProcess()");
	}


	private void deleteProcess(long processID, WFAdminSession session) throws Exception {
		try {
			log.debug("Begin  deleteProcess()");
			session.deleteProcessInstance(processID);
			log.debug("TestAPI: deleteProcess: process ID " + processID + " deleted from the database.");
		} catch (Exception ex) {
			log.error("deleteProcess Failed", ex);
			throw new Exception("TestAPI: deleteProcess: Fail to delete process ID " + processID + "because\n" + ex.toString());

		}
		log.debug("End deleteProcess()");
	}


	/**
	 * Connect to EPM Server As Admin
	 * 
	 * @return WFAdminSession
	 * @throws Exception
	 */
	public WFAdminSession connectAsAdminFromPropertiesFile() throws Exception {    	
    	WFAdminSession myAdminSession = null;
    	Exception thrownException = null;
    	
    	for (int i = 0; i < MAX_LOGIN_ATTEMPTS; i++)
    	{
    		try
    		{
    			myAdminSession = doConnectAsAdminFromPropertiesFile();
    		} catch (Exception e)
    		{
    			thrownException = e;
    		}
    		
    		if (myAdminSession != null)
    			return myAdminSession;
    		else if (thrownException instanceof EPMCommunicationException)
    		{
    			this.wait(100*(i+1));//waiting progressively longer
    		} else
    		{
    			//In this case there is no case in re-attempting the login,
    			//since this will cause the LDAP account to lock out.
    			log.error("Invalid user name and/or password.");
    			throw thrownException;
    		}
    			
    	}
    	
    	//This error indicates a communication error when attempting to connect to EPM server.
    	log.error(FAILED_TO_CONNECT, thrownException);
    	throw new Exception (FAILED_TO_CONNECT);    	
    }
	
	public WFAdminSession doConnectAsAdminFromPropertiesFile() throws Exception {
		Properties props;
		props = new Properties();
		PasswordEncryption passwordEncryption = new PasswordEncryption();
		WFAdminSession session = null;
		log.debug("Begin  connectAsAdminFromPropertiesFile()");
		String val1 = LocalProperties.getProperty("java.naming.factory.initial");
		props.put("java.naming.factory.initial", val1);
		String val2 = LocalProperties.getProperty("java.naming.provider.url");
		props.put("java.naming.provider.url", val2);
		String val3 = LocalProperties.getProperty("TWFTransportType");
		props.put("TWFTransportType", val3);

		String adminID = LocalProperties.getProperty("ADMIN_USER");
		// get decrytped password for Admin User.  	
		String adminPassword = PasswordEncryption.getDecryptedPassword(adminID);
		
		try {
			session = WFObjectFactory.getWFAdminSession();
			log.debug("TestAPI: connect: WFAdminSession created.");
		} catch (ModelInternalException mie) {
			log.warn("TestAPI: connect: failed to connect to i-Flow server.");
			throw new EPMCommunicationException("Error: TestAPI: connect: failed to obtain WFAdminSession object because " + mie.getMessage());
		}
		
		session.initForApplication(null, props);
		String Server = session.getServerList()[0];
		
		try {
			session.logIn(Server, adminID, adminPassword);
			adminID = session.getUserName();
		} catch (Exception e) {
			log.warn("TestAPI: connect: failed to login to i-Flow server.");
			throw e;
		}
		
		log.debug("End  connectAsAdminFromPropertiesFile() - user " + adminID + " connected to i-Flow Server!");
		return session;
	}


	/**
	 * Connect to EPM as Normal user
	 * 
	 * @param userID
	 * @param userPassword
	 * @return WFSession
	 * @throws Exception
	 */

	public WFSession connectAsUser(String userID, String userPassword) throws Exception {
		WFSession mySession = null;
    	Exception thrownException = null;
    	
    	for (int i = 0; i < MAX_LOGIN_ATTEMPTS; i++)
    	{
    		try
    		{
    			mySession = doConnectAsUser(userID, userPassword);
    		} catch (Exception e)
    		{
    			thrownException = e;
    		}
    		
    		if (mySession != null)
    			return mySession;
    		else if (thrownException instanceof EPMCommunicationException)
    		{
    			this.wait(100*(i+1));//waiting progressively longer
    		} else
    		{
    			//In this case there is no case in re-attempting the login,
    			//since this will cause the LDAP account to lock out.
    			log.warn("Invalid user name and/or password.");
    			throw thrownException;
    		}    			
    	}

    	//This error indicates a communication error when attempting to connect to EPM server.
    	log.error(FAILED_TO_CONNECT, thrownException);
    	throw new Exception (FAILED_TO_CONNECT);   
	}
	
	public WFSession doConnectAsUser(String userID, String userPassword) throws Exception {
		Properties props = null;
		WFSession usession = null;
		props = new Properties();
		usession = null;
	
		log.debug("Begin  connectAsUser()");
		if (null != userID || userID.equals("")) {
			log.debug("UserId = " + userID);
		} else {
			log.debug("UserId is null");
		}
		if (null != userPassword || userPassword.equals("")) {
			log.debug("userPassword is not null");
		} else {
			log.debug("userPassword is null");
		}
		
		String val1 = LocalProperties.getProperty("java.naming.factory.initial");
		props.put("java.naming.factory.initial", val1);
		String val2 = LocalProperties.getProperty("java.naming.provider.url");
		props.put("java.naming.provider.url", val2);
		String val3 = LocalProperties.getProperty("TWFTransportType");
		props.put("TWFTransportType", val3);
		
		try {
			usession = WFObjectFactory.getWFSession();
		} catch (ModelInternalException mie) {
			log.warn("TestAPI: connect: failed to connect to i-Flow server.");
			throw new EPMCommunicationException("Error: TestAPI: connect: failed to obtain WFSession object because " + mie.getMessage());
		}
		
		usession.initForApplication(null, props);
		String Server = usession.getServerList()[0];
		
		try {
			usession.logIn(Server, userID, userPassword);
			userID = usession.getUserName();
		} catch (Exception e) {
			log.warn("TestAPI: connect: failed to login to i-Flow server.");
			throw e;
		}
			
		log.debug("End  connectAsUser() - user " + userID + " connected to i-Flow Server!");
		return usession;
	}


	/**
	 * Disconnect EPMSession
	 * 
	 * @throws Exception
	 */
	public void disconnect(WFSession session) throws Exception {

		log.debug("Begin  disconnect()");
		try {			
			String username = session.getUserName();
			session.logOut();
			log.debug("TestAPI: disconnect: " + username + " disconnected from i-Flow server.");				
		} catch (Exception e) {
			log.debug("disconnect: fail to disconnect from server.");
			log.debug("Exception " + e.getClass().getName() + " caught with message: " + e.getMessage());
		}
		log.debug("End  disconnect()");
	}


	//
	// public boolean isConnected() throws Exception {
	// return connected;
	// }

	// public static String getStringInput() throws IOException {
	// InputStreamReader isr = new InputStreamReader(System.in);
	// BufferedReader br = new BufferedReader(isr);
	// String str = br.readLine();
	// return str;
	// }
	//
	//
	// public static long getLongInput() throws IOException {
	// String str = getStringInput();
	// return Long.parseLong(str);
	// }

	/**
	 * Returns WorkItem[]
	 * 
	 * @param _bps
	 * @param filterOption
	 * @param batchNumber
	 * @param batchSize
	 * @return WorkItem[]
	 * @throws Exception
	 */

	public WorkItem[] getFilteredWorkItems(BPMSession _bps, int filterOption, int batchNumber, int batchSize) throws Exception {
		WFObjectList wfObjectList = null;
		WorkItem[] wiList = null;
		log.debug("Begin  getFilteredWorkItems()");
		try {
			wfObjectList = WFObjectFactory.getWFObjectList(((BPMSessionImpl) _bps)._wfs);
			Object[] objList = null;
			wfObjectList.openBatchedList(filterOption);
			objList = wfObjectList.getNextBatch(batchSize);
			if (batchNumber > 1) {
				for (int i = 1; i < batchNumber; i++) {
					objList = wfObjectList.getNextBatch(batchSize);
				}
			}

			if (objList != null && objList.length > 0) {
				wiList = new WorkItem[objList.length];
				for (int i = 0; i < objList.length; i++) {
					wiList[i] = (WorkItem) objList[i];
				}
			}
		} catch (Exception me) {
			log.debug("getFilteredWorkItems() - Exception Caught " + me);
			throw me;
		} finally {
			if (wfObjectList != null)
				wfObjectList.closeList();
		}
		log.debug("End  getFilteredWorkItems()");
		return wiList;
	}


	/**
	 * Returns WorkItem[] using WFSession
	 * 
	 * @param filterOption
	 * @param batchNumber
	 * @param batchSize
	 * @param wfSession
	 * @return WorkItem[]
	 * @throws Exception
	 */
	public WorkItem[] getWorkItems(int filterOption, int batchNumber, int batchSize, WFSession wfSession) throws Exception {
		WFObjectList wfObjectList = null;
		WorkItem[] wiList = null;
		log.debug("Begin getWorkItems()");

		if (wfSession == null) {
			log.debug(" wfSession is null ");
		}

		log.debug(" in=side getWorkItems " + wfSession.getUserName());
		try {
			wfObjectList = WFObjectFactory.getWFObjectList(wfSession);
			if (filterOption == 100000) {

				log.debug("Begin Filter Option I");

				wfObjectList.openList(FilterEnum._MyWorkItems);
				Object[] objList2 = null;
				objList2 = wfObjectList.getAllObjects();

				if (objList2 != null) {
					wiList = new WorkItem[objList2.length];
				}

				if (objList2 != null && objList2.length > 0) {
					for (int i = 0; i < objList2.length; i++) {
						wiList[i] = (WorkItem) objList2[i];
					}
				}

				log.debug("End Filter Option I");

			} else {
				wfObjectList.openList(filterOption);

				log.debug("Begin Filter Option II");

				Object[] objList = null;
				objList = wfObjectList.getAllObjects();

				if (objList != null && objList.length > 0) {
					wiList = new WorkItem[objList.length];
					for (int i = 0; i < objList.length; i++) {
						wiList[i] = (WorkItem) objList[i];
					}
				}

				log.debug("End Filter Option II");

			}
		} finally {

			if (wfObjectList != null)
				wfObjectList.closeList();
		}

		log.debug("End  getWorkItems()");
		return wiList;
	}


	/**
	 * Return GFID for a given WorkItem Id
	 * 
	 * @param wid
	 * @param wfSession
	 * @return String
	 */
	public String getGFIDFromWorkId(String wid, User user) {
		log.debug("Begin getGFIDFromWorkId() ");
		String gfid = null;
		WFSession wfSession = null;
		WorkItem wi = null;
		try {
			long wIdLong = new Long(wid).longValue();
			wfSession = connectAsUser(user.getUserId(), user.getPassword());
			wi = WFObjectFactory.getWorkItem(wIdLong, wfSession);
			com.fujitsu.iflow.model.workflow.DataItem[] di = wi.getWorklistDataItems();
			for (int j = 0; j < di.length; j++) {
				if (di[j].getName().equalsIgnoreCase("gfId")) {
					gfid = di[j].getValue();
				}
			}
		} catch (Exception e) {
			log.error("getGFIDFromWorkId() Failed", e);
		} finally
		{
			try
			{
				disconnect(wfSession);
			} catch (Exception ex)
			{
				log.error("Exception while disconnecting from EPM Server", ex);
			}
		}
		log.debug("End getGFIDFromWorkId() ");
		return gfid;
	}


	/**
	 * Retrieve Choice for a workItem
	 * 
	 * @param workId
	 * @param request
	 * @return String[]
	 * @throws Exception
	 */
	public String[] getChoices(long workId, HttpServletRequest request) throws Exception {

		log.debug("Begin getChoices()");
		WorkItem epmWorkItem = null;
		String[] choices = null;
		WFSession wfSession = null;
		User user = (User) request.getSession().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
		
		try {
			wfSession = connectAsUser(user.getUserId(), user.getPassword());
			epmWorkItem = WFObjectFactory.getWorkItem(workId, wfSession);
			
			if (epmWorkItem != null) {
				choices = epmWorkItem.getChoices();
			} 
		} catch (Exception ex) {

			log.error("Could not create work Item " + workId
					+ " This process might have been killed or work item might be completed by others");
			log.error(ex);
		}
		finally
		{
			try
			{
				disconnect(wfSession);
			} catch (Exception e)
			{
				log.error("Exception while disconnection from EPM Server", e);
			}
		}
		
		log.debug("End getChoices()");
		// EPM Session has been lost, must redirect to login page agin
		
		return choices;
	}


	// This is the method that is used to resume the given process ID.
	// The return Boolean value indicates whether the process is resumed or not.
	// manually Started should be a string "-1" or "1"

	public boolean resumeProcessNew(long processID, String action, String manuallystarted) throws Exception {
		log.debug("Begin resumeProcess()");
		PreparedStatement psmt = null;
		ResultSet rs = null;
		WFSession wfSession = null;
		String processStatus = null;
		ProcessInstance pi = null;
		if (manuallystarted == null)
			manuallystarted = "-1";
		// Connect As Suspender "Suspender" - pwd = "1nterst@ge"
	
		String pSuspendUserName = LocalProperties.getProperty("SUSPEND_USER");
		// get decrytped password for Suspend user.
		String pSuspendPassword = PasswordEncryption.getDecryptedPassword(pSuspendUserName);
		wfSession = this.connectAsUser(pSuspendUserName, pSuspendPassword);
	
		// assuming the process already suspended
		boolean resumed = false;
		Connection conn = null;

		log.debug("processID = " + processID);
		log.debug("maunuall started =" + manuallystarted);
		if (null == manuallystarted)
			log.debug("manually started is null");

		try {

			pi = WFObjectFactory.getProcessInstance(processID, wfSession);

			if (null != pi) {
				// get current work Item
				WFObjectList wfObjectList = WFObjectFactory.getWFObjectList(wfSession);
				wfObjectList.addFilter(WFObjectList.LISTFIELD_PROCESSINSTANCE_ID, WFObjectList.SQLOP_EQUALTO, "'" + processID
						+ "'");
				wfObjectList.openBatchedList(Filter.MyWorkItems);
				Object[] wiList = wfObjectList.getNextBatch(1);
				if (null != wiList) {
					for (int i = 0; i < wiList.length; i++) {
						WorkItem wi = (WorkItem) wiList[i];
						log.debug("before accept");
						wi.accept();
						log.debug("after accept");

						log.debug("wi.getName() = " + wi.getName());
						log.debug("wi.getProcessInstanceId() = " + wi.getProcessInstanceId());
						if (wi.getName().equalsIgnoreCase("SUSPEND")
								&& String.valueOf(processID).equals(new Long(wi.getProcessInstanceId()).toString())) {
							String[] choices = wi.getChoices();
							java.util.Properties nvlist = new java.util.Properties();
							nvlist.setProperty("manuallyStarted", manuallystarted);
							int waittime = 2;
							while (pi.isLockedForEdit()) {
								log.debug(" Process instance Id :" + pi.getId());
								log.debug(" Process Instance is locked wating 1");
								if (waittime == 900) {
									log.debug("Process Instance was locked for more than 90 seconds, UDA update failed");
									break;
								}
								Thread.sleep(100);
								waittime = waittime + 1;
							}
							pi.startEdit();
							pi.setDataItemValues(nvlist);
							pi.commitEdit();

							log.debug("Before make choice");
							wi.makeChoice(choices[0]);
							resumed = true;
							log.debug("After make choice");
							long piId = pi.getId();
							String gfId = null;

							DataItem gfIdDataItem = pi.getDataItem("gfId");
							if (gfIdDataItem != null) {
								gfId = gfIdDataItem.getValue();
							}
							log.debug("gfId = " + gfId);

							DateUtility gd = new DateUtility();
							String GMTDate = gd.calculateDate();

							log.debug("GMTDate = " + GMTDate);

							conn = Connect.getConnection();

							psmt = conn.prepareStatement("select code from IUAProcessStatus where upper(description) = 'ACTIVE'");
							rs = psmt.executeQuery();
							if (rs.next()) {
								processStatus = rs.getString("code");
							}
							if (processStatus == null)
								processStatus = "-1";
							log.debug("ProcessStatus = " + processStatus);
							log.debug("Updating info in IUAAppDocProcesses table");

							psmt = conn
									.prepareStatement("update IUAAppDocProcesses set status = ?, resumeDate = ? where gfid = ? and PID = ?");

							psmt.setString(1, processStatus);
							psmt.setString(2, GMTDate);
							psmt.setString(3, gfId);
							psmt.setLong(4, piId);
							psmt.execute();

							log.debug("IUAAppDocProcesses table updated successfully");
							log.debug("Updating info in IUAActivities table");

							psmt = conn
									.prepareStatement("update IUAActivities set activeDate = ? where PID = ? and completeDate is null");

							psmt.setString(1, GMTDate);
							psmt.setLong(2, piId);
							psmt.execute();

							log.debug("IUAActivities table updated successfully");

							break;
						}
					}
				}

			}

		} catch (Exception e) {
			throw new Exception("Failed to resume process ID:\t" + processID + "\nReason:\t" + e.getMessage());
		} finally {
			if (wfSession != null) {
				wfSession.logOut();
			}
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		}
		log.debug("End resumeProcess()");
		return resumed;
	}


	public boolean resumeProcessNewET(long processID, String manuallystarted) throws Exception {
		log.debug("Begin resumeProcess()");
		WFSession wfSession = null;
		ProcessInstance pi = null;
		if (manuallystarted == null)
			manuallystarted = "-1";
		// Connect As Suspender "Suspender" - pwd = "1nterst@ge"
		
		String pSuspendUserName = LocalProperties.getProperty("SUSPEND_USER");
		// get decrytped password for suspend user.
		String pSuspendPassword = PasswordEncryption.getDecryptedPassword(pSuspendUserName);
		wfSession = this.connectAsUser(pSuspendUserName, pSuspendPassword);
		
		// assuming the process already suspended
		boolean resumed = false;

		log.debug("processID = " + processID);
		log.debug("manually started " + manuallystarted);
		try {

			pi = WFObjectFactory.getProcessInstance(processID, wfSession);

			if (null != pi) {
				// get current work Item
				WFObjectList wfObjectList = WFObjectFactory.getWFObjectList(wfSession);
				wfObjectList.addFilter(WFObjectList.LISTFIELD_PROCESSINSTANCE_ID, WFObjectList.SQLOP_EQUALTO, "'" + processID
						+ "'");
				wfObjectList.openBatchedList(Filter.MyWorkItems);
				Object[] wiList = wfObjectList.getNextBatch(1);
				if (null != wiList) {
					for (int i = 0; i < wiList.length; i++) {
						WorkItem wi = (WorkItem) wiList[i];
						log.debug("before accept");
						wi.accept();
						log.debug("after accept");

						log.debug("wi.getName() = " + wi.getName());
						log.debug("wi.getProcessInstanceId() = " + wi.getProcessInstanceId());
						if (wi.getName().equalsIgnoreCase("SUSPEND")
								&& String.valueOf(processID).equals(new Long(wi.getProcessInstanceId()).toString())) {
							String[] choices = wi.getChoices();
							java.util.Properties nvlist = new java.util.Properties();
							nvlist.setProperty("manuallyStarted", manuallystarted);
							int waittime = 2;
							while (pi.isLockedForEdit()) {
								log.debug(" Process instance Id :" + pi.getId());
								log.debug(" Process Instance is locked wating 1");
								if (waittime == 90) {
									log.debug("Process Instance was locked for more than 90 seconds, UDA update failed");
									break;
								}
								Thread.sleep(1000 * 2);
								waittime = waittime + 2;
							}
							pi.startEdit();
							pi.setDataItemValues(nvlist);
							pi.commitEdit();
							log.debug(" before make choice");
							wi.makeChoice(choices[0]);
							log.debug(" after make choice");
							resumed = true;
							break;
						}
					}
				}

			}

		} catch (Exception e) {
			throw new Exception("Failed to resume process ID:\t" + processID + "\nReason:\t" + e.getMessage());
		} finally {
			if (wfSession != null) {
				wfSession.logOut();
			}
		}
		log.debug("End resumeProcess()");
		return resumed;
	}


	/**
	 * Return role from given Workitem Id.
	 * 
	 * @param request
	 * @param selectedItems
	 * @return String
	 * @throws Exception
	 */
	public String getCurrentRoleFromWorkItemIdInSession(HttpServletRequest request, Map selectedItems) throws Exception {
		log.debug("Begin getCurrentRoleFromWorkItemIdInSession(HttpServletRequest request, Map selectedItems) ");
		String role = "";
		WFSession wfSession = null;
		
		User user = (User) request.getSession().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
		// String workIdString = (String)
		// request.getSession().getAttribute(com.epm.acmi.struts.Constants.workItemId);
		long workIdlong = -1;

		if (selectedItems != null) {
			Iterator itr = selectedItems.keySet().iterator();
			while (itr.hasNext()) {
				Object obj = itr.next();
				log.debug(obj.getClass().getName());
				if (obj != null)
					workIdlong = Long.parseLong(obj.toString());
				break;
			}
		}

		try
		{
			wfSession = connectAsUser(user.getUserId(), user.getPassword());
			
			WorkItem wi = null;
			wi = WFObjectFactory.getWorkItem(workIdlong, wfSession);
			log.debug("End getCurrentRoleFromWorkItemIdInSession(HttpServletRequest request, Map selectedItems) ");
			role = wi.getNodeInstance().getRole();
		} finally
		{
			try
			{
				disconnect(wfSession);
			} catch (Exception e)
			{
				log.error("Error while disconnecting from EPM Server", e);
			}
		}

		return role;
	}


	/**
	 * Return role from given Workitem Id.
	 * 
	 * @param request
	 * @return String
	 * @throws Exception
	 */
	public String getCurrentRoleFromWorkItemIdInSession(HttpServletRequest request) throws Exception {
		log.debug("Begin getCurrentRoleFromWorkItemIdInSession(HttpServletRequest requests) ");
		User user = (User) request.getSession().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
		String workIdString = (String) request.getSession().getAttribute(com.epm.acmi.struts.Constants.workItemId);
		long workIdlong = -1;
		if (workIdString != null) {
			workIdlong = Long.parseLong(workIdString);
		}
		log.debug("workItemId " + workIdlong);
		log.debug("user " + user);
		
		if (workIdlong != -1) {
			WFSession wfSession = null;
			WorkItem wi = null; 
			
			try {
				wfSession = connectAsUser(user.getUserId(), user.getPassword());
				wi = WFObjectFactory.getWorkItem(workIdlong, wfSession);
				
				if (wi != null) {
					log.debug("End getCurrentRoleFromWorkItemIdInSession(HttpServletRequest requests) ");
					return wi.getNodeInstance().getRole();
				} else {
					log.debug("End getCurrentRoleFromWorkItemIdInSession(HttpServletRequest requests) ");
					return null;
				}
			} catch (Exception ex) {
				log.error("This error is handled in the code");
				log.error("Failed to getCurrentRoleFromWorkItemIdInSession()", ex);

				return null;
			} finally
			{
				try
				{
					disconnect(wfSession);
				} catch (Exception e)
				{
					log.error("Error while disconnecting from EPM Server", e);
				}
			}
		} else {
			log.debug("End getCurrentRoleFromWorkItemIdInSession(HttpServletRequest requests) ");

			return null;
		}

	}


	/**
	 * Start new EPM process with UDAs
	 * 
	 * @param wfAdminSession
	 * @param templateName
	 * @param processName
	 * @param initialValues
	 * @return ProcessInstance
	 * @throws Exception
	 */
	public ProcessInstance startNewProcessWithUDAs(WFAdminSession wfAdminSession, String templateName, String processName,
			Properties initialValues) throws Exception {
		log.debug("Begin startNewProcessWithUDAs() ");

		ProcessInstance pi = null;
		try {
			long processDefinitionID = getProcessDefinitionID(wfAdminSession, templateName);
			log.debug("EPMHelper:processDefinitionID = " + processDefinitionID);

			ProcessInstantiator processInstantiator = WFObjectFactory.getProcessInstantiator(processDefinitionID, wfAdminSession);
			processInstantiator.setName(processName);
			processInstantiator.setDataItemValues(initialValues);
			pi = processInstantiator.startNewProcess();
		} catch (Exception me) {
			log.debug("startNewProcessWithUDAs() - Exception Caught " + me);
			throw me;
		}
		log.debug("End  startNewProcessWithUDAs()");

		return pi;
	}


	/**
	 * Start New process with UDAs
	 * 
	 * @param userID
	 * @param password
	 * @param templateName
	 * @param processName
	 * @param initialValues
	 * @return ProcessInstance
	 * @throws Exception
	 */
	public ProcessInstance startNewProcessWithUDAs(String userID, String password, String templateName, String processName,
			Properties initialValues) throws Exception {
		ProcessInstance pi = null;
		WFAdminSession wfAdminSession = null;
		log.debug("Begin startNewProcessWithUDAs-II() ");

		try {
			wfAdminSession = connectAsAdminFromPropertiesFile();
			log.debug("EPMHelper:login to iFlow Successful");

			long processDefinitionID = getProcessDefinitionID(wfAdminSession, templateName);
			log.debug("EPMHelper:processDefinitionID = " + processDefinitionID);

			initialValues.setProperty("thisTemplateID", new Long(processDefinitionID).toString());

			ProcessInstantiator processInstantiator = WFObjectFactory.getProcessInstantiator(processDefinitionID, wfAdminSession);
			processInstantiator.setName(processName);
			processInstantiator.setDataItemValues(initialValues);
			pi = processInstantiator.startNewProcess();

		} catch (Exception me) {
			log.debug(me);
			throw me;
		} finally // Disconnect if session is connected
		{
			if (wfAdminSession != null) {
				try {
					wfAdminSession.logOut();
				} catch (Exception ex) {
					ex.printStackTrace();
					log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage());
				}
				;
			}
		}
		log.debug("End startNewProcessWithUDAs-II() ");

		return pi;
	}


	/**
	 * Retrun PID
	 * 
	 * @param wfSession
	 * @param ptn
	 * @return long
	 * @throws Exception
	 * @throws WFInternalException
	 * @throws WFServerException
	 */
	public long getProcessDefinitionID(WFSession wfSession, String ptn) throws Exception, WFInternalException, WFServerException {
		log.debug("Begin  getProcessDefinitionID() ");
		log.debug("EPMHelper:planName=" + ptn);

		WFObjectList wfObjectList = null;
		wfObjectList = WFObjectFactory.getWFObjectList(wfSession);
		Plan plan = null;
		long processDefinitionID = -1;

		try {
			wfObjectList.addFilter(WFObjectList.LISTFIELD_PLAN_NAME, WFObjectList.SQLOP_EQUALTO, "'" + ptn + "'");
			wfObjectList.openBatchedList(Filter.AllPlans);
			Object[] planList = wfObjectList.getNextBatch(999); // I always do this but there should only ever be one.
			if (planList == null)
				throw new Exception("WorkFlowHelper::GetProcessDefinitionID() - No PlanList returned");
			else if (planList.length > 1) {
				boolean planFound = false;
				for (int i = 0; i < planList.length; i++) {
					Plan thisPlan = (Plan) planList[i];
					int planState = thisPlan.getState();
					if (planState == Plan.STATE_DRAFT) {
						plan = (Plan) planList[i];
						planFound = true;
					} else if (planState == Plan.STATE_PUBLISHED)
						log.debug("State = Published");
					else if (planState == Plan.STATE_PRIVATE)
						log.debug("State = Private");
					else if (planState == Plan.STATE_OBSOLETE)
						log.debug("State = Obsolete");
					else if (planState == Plan.STATE_DELETED)
						log.debug("State = Deleted");
				}
				if (planFound == false)
					throw new Exception("WorkFlowHelper::GetProcessDefinitionID() - No PlanList returned");
			} else
				plan = (Plan) planList[0];

			processDefinitionID = plan.getId();
			wfObjectList.closeList();

		} catch (ModelInternalException e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: ", e);
			throw e;
		} catch (WFInternalException e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: ", e);
			throw e;
		} catch (WFServerException e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e);
			throw e;
		} catch (Exception e) {
			log.error("Exception " + e.getClass().getName() + " caught with message: " + e);
			throw e;
		}
		log.debug("End  getProcessDefinitionID() ");
		return processDefinitionID;
	}


	/**
	 * Load Properties
	 * 
	 * @return Properties
	 * @throws Exception
	 */
	public static Properties loadProperties() throws Exception {
		Properties prop = new Properties();
		String fileName = "TaskManager.conf";
		log.debug("Begin  getProcessDefinitionID() ");
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			String line = null;
			for (line = in.readLine(); line != null; line = in.readLine()) {
				int pos = line.indexOf("=");
				if (pos == -1 || line.charAt(1) == '#') {
					continue;
				}
				String name = line.substring(0, pos);
				String value = line.substring(pos + 1);
				prop.setProperty(name.trim(), value.trim());
			}
			if (prop.isEmpty()) {
				throw new Exception("A properties file is expected for this application at the following location: " + fileName
						+ " A system administrator will need to place a configuration file with the right settings there.");
			}
		} catch (Exception e) {
			throw new Exception("Error: EPMHelper: loadProperties: fail to read " + fileName + " file because\n" + e.getMessage());
		}
		log.debug("End  getProcessDefinitionID() ");
		return prop;
	}


	/**
	 * Return WorkItem State
	 * 
	 * @param workItem
	 * @return String
	 * @throws Exception
	 */
	public String getState(WorkItem workItem) throws Exception {
		String strState = "";
		log.debug("Begin  getState() ");
		try {
			int wiState = workItem.getState();
			if (WorkItem.STATE_ACCEPTED == wiState)
				strState = "STATE_ACCEPTED";
			if (WorkItem.STATE_ACTIVE == wiState)
				strState = "STATE_ACTIVE";
			if (WorkItem.STATE_ASSIGNED == wiState)
				strState = "STATE_ASSIGNED";
			if (WorkItem.STATE_COMPLETED == wiState)
				strState = "STATE_COMPLETED";
			if (WorkItem.STATE_DEACTIVE == wiState)
				strState = "STATE_DEACTIVE";
			if (WorkItem.STATE_DECLINED == wiState)
				strState = "STATE_DECLINED";
			if (WorkItem.STATE_READ == wiState)
				strState = "STATE_READ";
			if (WorkItem.STATE_SUSPENDED == wiState)
				strState = "STATE_SUSPENDED";
			if (WorkItem.STATE_UNASSIGNED == wiState)
				strState = "STATE_UNASSIGNED";
			if (WorkItem.STATE_VOTED == wiState)
				strState = "STATE_VOTED";
			if (WorkItem.STATE_WAITING_FOR_PROCESS == wiState)
				strState = "STATE_WAITING_FOR_PROCESS";
			if (WorkItem.STATE_WAITING_FOR_SUB_PROCESS == wiState)
				strState = "STATE_WAITING_FOR_SUB_PROCESS";
		} catch (Exception e) {
			log.debug("WorkFlowHelper::getState() - Exception Caught " + e);
			throw e;
		}
		log.debug("End  getState() ");

		return strState;
	}


	/**
	 * Return FilteredWorkItems
	 * 
	 * @param wfSession
	 * @param filterOption
	 * @param batchNumber
	 * @param batchSize
	 * @return WorkItem[]
	 * @throws Exception
	 */
	public WorkItem[] getFilteredWorkItems(WFSession wfSession, int filterOption, int batchNumber, int batchSize)
			throws Exception {
		WFObjectList wfObjectList = null;
		WorkItem[] wiList = null;
		log.debug("Begin  getFilteredWorkItems() ");

		try {
			wfObjectList = WFObjectFactory.getWFObjectList(wfSession);
			Object[] objList = null;
			wfObjectList.openBatchedList(filterOption);
			objList = wfObjectList.getNextBatch(batchSize);
			if (batchNumber > 1) {
				for (int i = 1; i < batchNumber; i++) {
					objList = wfObjectList.getNextBatch(batchSize);
				}
			}

			if (objList != null && objList.length > 0) {
				wiList = new WorkItem[objList.length];
				for (int i = 0; i < objList.length; i++) {
					wiList[i] = (WorkItem) objList[i];
				}
			}
		} catch (Exception me) {
			log.debug("getFilteredWorkItems() - Exception Caught " + me);
			throw me;
		} finally {
			if (wfObjectList != null)
				wfObjectList.closeList();
		}
		log.debug("End getFilteredWorkItems() ");
		return wiList;
	}


	/**
	 * Return FilteredWorkItems
	 * 
	 * @param wfSession
	 * @param filterOption
	 * @param WFlowFilter
	 * @param batchNumber
	 * @param batchSize
	 * @return WorkItem[]
	 * @throws Exception
	 */
	public WorkItem[] getFilteredWorkItems(WFSession wfSession, int filterOption, WorkFlowFilter WFlowFilter, int batchNumber,
			int batchSize) throws Exception {
		WFObjectList wfObjectList = null;
		WorkItem[] wiList = null;
		log.debug("Begin getFilteredWorkItems()-II ");
		try {
			wfObjectList = WFObjectFactory.getWFObjectList(wfSession);
			Object[] objList = null;
			String udaName = "", sqlOp = "", value = "", dataType = "";
			int field = -1;
			Vector FilterList = WFlowFilter.getFilterList();
			for (int i = 0; i < FilterList.size(); i++) {
				WorkFlowQueryFilter qf = (WorkFlowQueryFilter) FilterList.elementAt(i);
				if (qf.getUdaName() == null) {
					// Field Type Filter
					field = qf.getField();
					sqlOp = qf.getOperator();
					value = qf.getValue();
					log.debug("EPMHelper:addFilter(" + field + "," + sqlOp + "," + value + ")");
					wfObjectList.addFilter(field, sqlOp, value);
				} else {
					// Uda Type Filter
					udaName = qf.getUdaName();
					dataType = qf.getDataType();
					sqlOp = qf.getOperator();
					value = qf.getValue();
					log.debug("EPMHelper:addFilter(" + udaName + "," + dataType + "," + sqlOp + "," + value + ")");
					wfObjectList.addFilter(udaName, dataType, sqlOp, value);
				}
			}

			// Add SORTING HERE
			// NOTE: UDA sorting not supported at this time, will have to modify WorkFlowFilter
			if (WFlowFilter.isSortOrderUsed()) {
				// See which type UDA or Field Sorting
				if (WFlowFilter.getSortField() == -1) {
					// UDA Sort
					log.debug("EPMHelper:addSortOrder(" + WFlowFilter.getSortField() + "," + WFlowFilter.getSortOrder() + ")");
					wfObjectList.addSortOrder(WFlowFilter.udaSortColumnName(), WFlowFilter.udaSortDataType(), WFlowFilter
							.getSortOrder());
				} else {
					// FIELD Sort
					log.debug("EPMHelper:addSortOrder(" + WFlowFilter.getSortField() + "," + WFlowFilter.getSortOrder() + ")");
					wfObjectList.addSortOrder(WFlowFilter.getSortField(), WFlowFilter.getSortOrder());
				}
			}

			wfObjectList.openBatchedList(filterOption);
			objList = wfObjectList.getNextBatch(batchSize);
			if (batchNumber > 1) {
				for (int i = 1; i < batchNumber; i++) {
					objList = wfObjectList.getNextBatch(batchSize);
				}
			}

			if (objList != null && objList.length > 0) {
				wiList = new WorkItem[objList.length];
				for (int i = 0; i < objList.length; i++) {
					wiList[i] = (WorkItem) objList[i];
				}
			}
		} catch (Exception me) {
			log.debug("getFilteredWorkItems() - Exception Caught " + me);
			throw me;
		} finally {
			if (wfObjectList != null)
				wfObjectList.closeList();
		}
		log.debug("End getFilteredWorkItems()-II ");
		return wiList;
	}


	// This is the method that is used to suspend the given process ID.
	// The return Boolean value indicates whether the process is suspended or not.

	/**
	 * Suspend EPM process by processID (PID)
	 */
	public boolean suspendProcess(long processID) throws Exception {
		WFAdminSession wfAdminSession = null;
		ProcessInstance pi = null;
		boolean processSuspended = false;
		Connection conn = null;
		Statement stmt = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		log.debug("Begin suspendProcess() ");
		log.debug("suspendProcess():processID = " + processID);

		try {
			wfAdminSession = connectAsAdminFromPropertiesFile();
			log.debug("EPMHelper:login to iFlow Successful");

			pi = WFObjectFactory.getProcessInstance(processID, wfAdminSession);

			if (pi != null && (pi.getState() == ProcessInstance.STATE_RUNNING)) {
				java.util.Properties nvlist = new java.util.Properties();
				nvlist.setProperty("suspended", "true");
				if (!pi.isInEditMode())
					pi.startEdit();
				pi.setDataItemValues(nvlist);
				pi.commitEdit();

				pi.suspend(); // suspending the process instance
				processSuspended = true;

				long piId = pi.getId();

				String gfId = null;
				DataItem gfIdDataItem = pi.getDataItem("gfId");
				if (gfIdDataItem != null)
					gfId = gfIdDataItem.getValue();
				log.debug("suspendProcess():gfId = " + gfId);

				conn = Connect.getConnection();

				DateUtility gd = new DateUtility();
				String GMTDate = gd.calculateDate();
				log.debug("suspendProcess():GMTDate = " + GMTDate);

				String processStatusSql = "select code from iuaProcessStatus where upper(description) = 'SUSPENDED'";
				stmt = conn.createStatement();

				rst = stmt.executeQuery(processStatusSql);
				String processStatus = null;
				while (rst.next()) {
					processStatus = rst.getString("code");
				}

				if (processStatus == null)
					processStatus = "-1";
				log.debug("suspendProcess():processStatus = " + processStatus);

				log.debug("suspendProcess():Updating info in IUAAppDocProcesses table");

				String updateSql = "update IUAAppDocProcesses set status=?, suspendDate=?, resumeDate=? where gfId = ? and PID = ?";

				psmt = conn.prepareStatement(updateSql);
				psmt.setString(1, processStatus);
				psmt.setString(2, GMTDate);
				psmt.setNull(3, Types.NULL);
				psmt.setString(4, gfId);
				psmt.setLong(5, piId);
				psmt.executeUpdate();

				log.debug("suspendProcess():IUAAppDocProcesses table updated successfully");
			}

		} catch (Exception e) {
			throw new Exception("Failed to suspend process ID:\t" + processID + "\nReason:\t" + e.getMessage());
		} finally {
			if (wfAdminSession != null) {
				wfAdminSession.logOut();
			}

			if (rst != null)
				rst.close();
			if (stmt != null)
				stmt.close();
			if (psmt != null)
				psmt.close();
			// Always disconnect from database //
			if (conn != null) {
				conn.close();
				conn = null;
			}
		}
		log.debug("End suspendProcess() ");
		return processSuspended;
	}


	// This is the method that is used to abort the given process ID.
	// The return Boolean value indicates whether the process is aborted or not.

	/**
	 * Abort EPM process by ProcessID (PID)
	 */
	public boolean abortProcess(long processID) throws Exception {
		WFAdminSession wfAdminSession = null;
		ProcessInstance pi = null;
		boolean processAborted = false;
		log.debug("Begin abortProcess() ");
		log.debug("abortProcess():processID = " + processID);

		try {
			wfAdminSession = connectAsAdminFromPropertiesFile();
			log.debug("EPMHelper:login to iFlow Successful");

			pi = WFObjectFactory.getProcessInstance(processID, wfAdminSession);

			if (pi != null) {
				pi.abort(); // aborting the process instance
				processAborted = true;
			}

		} catch (Exception e) {
			throw new Exception("Failed to abort process ID:\t" + processID + "\nReason:\t" + e.getMessage());
		} finally {
			if (wfAdminSession != null) {
				wfAdminSession.logOut();
			}
		}
		log.debug("End abortProcess() ");
		return processAborted;
	}

	
	/**
	 * Retrieves a history event corresponding to the work item...
	 * @param wi
	 * @return
	 * @throws WFInternalException
	 * @throws WFServerException
	 * @throws ModelInternalException
	 */
	public static HistoryEvent retrieveWorkItemEventFromHistoryTimestamp(WorkItem wi) throws WFInternalException, WFServerException, ModelInternalException
	{
//		First, get history events from process instance...
		ProcessInstance pi = wi.getProcessInstance();				
		IflowEnumeration elements = pi.getHistory(getHistoryEnumParamsTimestamp());
		Map historyEventMap = null;
		
		//Retrieve history event corresponding to work item from history enumeration
		while (elements.hasMoreElements())
		{
			Object element = elements.nextElement();
			Map currentEvent = (Map)element;
			//event type string has embedded the event type substring and the work item id... Retrieve it.
			String eventType = (String)currentEvent.get(new Integer(ProcessInstance.HISTORY_EVENT_TYPE));
			
			if (eventType.indexOf(String.valueOf(wi.getId())) > -1)
			{
				historyEventMap = currentEvent;
			}			
		}
		
		HistoryEvent historyEvent = new HistoryEvent(historyEventMap);
		
		return historyEvent;
	}
	

    /**
     *  Gets the process instance object whose processDefinitionID is specified in the argument.
     *
     *  @return the <code>ProcessInstance</code> object whose processDefinitionID is specified in
     *  the argument.
     */
    public ProcessInstance getProcessInstance(long processDefinitionID, WFAdminSession wfAdminSession) throws Exception {
        ProcessInstance pi = null;
        
        try {
            pi = WFObjectFactory.getProcessInstance(processDefinitionID, wfAdminSession);
        } catch (ModelException me) {
            log.error("getProcessInstance() - Exception Caught " + me);
            throw me;
        } 
        
        return pi;
    }
	/**
	 * Fills in the int array that will be sent to processInstance.getHistory(...).
	 * @param ai - the key corresponding to the history field we want to retrieve.
	 */
	private static int[] getHistoryEnumParams()
	{
		int[] ai = new int[11];
		
		ai[0] = ProcessInstance.HISTORY_ID;
		ai[1] = ProcessInstance.HISTORY_TIME_STAMP;
		ai[2] = ProcessInstance.HISTORY_EVENT_TYPE;
		ai[3] = ProcessInstance.HISTORY_RESPONSIBLE;
		ai[4] = ProcessInstance.HISTORY_PRODUCER_TYPE;
		ai[5] = ProcessInstance.HISTORY_PRODUCER_ID;
		ai[6] = ProcessInstance.HISTORY_CONSUMER_TYPE;
		ai[7] = ProcessInstance.HISTORY_CONSUMER_ID;
		ai[8] = ProcessInstance.HISTORY_PROCESSINSTANCE_ID;
		ai[9] = ProcessInstance.HISTORY_ISHANDLED;
		ai[10] = ProcessInstance.HISTORY_EVENTDATA;
		
		return ai;
	}
	
	/**
	 * Fills in the int array that will be sent to processInstance.getHistory(...).
	 * @param ai - the key corresponding to the history field we want to retrieve.
	 */
	private static int[] getHistoryEnumParamsTimestamp()
	{
		int[] ai = new int[2];		
		ai[0] = ProcessInstance.HISTORY_TIME_STAMP;
		ai[1] = ProcessInstance.HISTORY_EVENT_TYPE;		
		return ai;
	}
	
	private class EPMCommunicationException extends Exception {
		public EPMCommunicationException() {
			super();
		}

		public EPMCommunicationException(String message) {
			super(message);
		}

		public EPMCommunicationException(String message, Throwable ex) {
			super(message, ex);
		}
	}
	
	public void synchronizeEPMLDAPUserCache() throws ModelInternalException,
		WFServerException, WFInternalException, FileNotFoundException,
		ModelException, IOException, EncryptionException {
		
		WFAdminSession session = null;
		{
			try {
				log.debug("synchronizeEPMLDAPUserCache() - Start");
				log.debug("connecting as Administrative user");
				session = connectAsAdminFromPropertiesFile();
				log.debug("Resetting the EPM LDAP user list");
				session.resetLDAPUsersList();
			
			} catch (Exception e) {
				log.error(e);
			} finally { 
				if (session != null) {
					session.logOut();
				}
			}
			log.debug("synchronizeEPMLDAPUserCache() - End");
		}
	}


	public void synchronizeEPMLDAPGroupCache() throws ModelInternalException,
		WFServerException, WFInternalException, FileNotFoundException,
		ModelException, IOException, EncryptionException  {
		
		WFAdminSession session = null;
		
		try  {
			log.debug("synchronizeEPMLDAPGroupCache() - Start");
			log.debug("connecting as Administrative user");
			session = connectAsAdminFromPropertiesFile();
			log.debug("Resetting the EPM LDAP user list");
			session.resetLDAPGroupsList();
			
		}catch (Exception e) {
			log.error(e);
		} finally {
			session.logOut();
		}

		log.debug("synchronizeEPMLDAPUserCache() - End");
	}

}
