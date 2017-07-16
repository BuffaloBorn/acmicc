package com.epm.acmi.struts.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.SelectMode;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.util.ListHelp;
import com.epm.acmi.bean.ReassignTaskBean;
import com.epm.acmi.datamodel.ReassignTaskList;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.ReassignTaskForm;
import com.epm.acmi.util.ACMICache;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.LDAPUser;
import com.fujitsu.iflow.model.workflow.WFAdminSession;
import com.fujitsu.iflow.model.workflow.WFObjectFactory;
import com.fujitsu.iflow.model.workflow.WorkItem;
import com.epm.acmi.bd.IUAProcessBD;

/**
 * Action class for Reassign Page
 * 
 * @author Rajeev Chachra
 */

public class ReassignAction extends MainTabPageBaseAction {
	private static final int MAX_UPDATE_ATTEMPTS = 5;
	private static final long INITIAL_WAITING_TIME = 100;
	
	
	private static Logger log = Logger.getLogger(ReassignAction.class);
	private static HashMap userNameUserIDMap = new HashMap();
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	

	/**
	 * Constructor
	 */
	public ReassignAction() {
		super();
	}


	/**
	 * @see com.cc.sampleapp.tabset.sample401.action.TabPageBaseAction#getTabPageId()
	 */
	/**
	 * Returns tab page Id
	 */
	public String getTabPageId() {
		return TABPAGE3;
	}


	/**
	 * @see com.cc.framework.adapter.struts.FrameworkAction#doExecute(com.cc.framework.adapter.struts.ActionContext)
	 * @throws Exception
	 */

	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws Exception {

		String action;
		HashMap userMap;
		TreeMap roleValues;
		ArrayList userRoleList;
		String userRole;
		LDAPUser userObj;
		String selectedUser;
		ReassignTaskForm form;
		EPMHelper epmHelper = null;
		WFAdminSession wfAdminSession = null;
		
		log.debug("Begin doExecute");

		try {			
			action = ctx.request().getParameter("action");
			form = (ReassignTaskForm) ctx.form();
			log.debug("Action = " + action);

			if (action.equals("getrole")) {
				//Just getting the roles for a user, and adding them to the Action Form...
				selectedUser = (String) ReassignAction.userNameUserIDMap.get(form.getItem());
				log.info("Selected User = " + selectedUser);

				if (selectedUser == null || selectedUser.equals("")) {
					form.setRoleValues(null);
					ctx.session().setAttribute("ReassignTaskList", null);
				} else {
					userMap = ACMICache.getUsers();

					userObj = (LDAPUser) userMap.get(selectedUser);

					roleValues = new TreeMap();
					userRoleList = userObj.getRoles();
					for (int j = 0; j < userRoleList.size(); j++) {
						userRole = (String) userRoleList.get(j);

						roleValues.put(userRole, userRole);
					} // end inner for

					form.setRoleValues(roleValues);
				}
			} else {
				//Reassigning either to a role or a user.
				SimpleListControl list = (SimpleListControl) ctx.session().getAttribute("ReassignTaskList");

				boolean isAssignToRole = false;
				boolean isAssignToUser = false;

				epmHelper = new EPMHelper();
				wfAdminSession = epmHelper.connectAsAdminFromPropertiesFile();
				String roleToAssignTo = null;
				String userToAssignTo = null;

				String reassignTo = (String) ctx.session().getAttribute(com.epm.acmi.struts.Constants.reassignkey);
				if (reassignTo != null) {
					String roleOrUser = reassignTo.substring(0, 4);
					if (roleOrUser.equalsIgnoreCase("user")) {
						isAssignToUser = true;
						userToAssignTo = reassignTo.replaceFirst("user", "");
					} else if (roleOrUser.equalsIgnoreCase("role")) {
						isAssignToRole = true;
						roleToAssignTo = reassignTo.replaceFirst("role", "");
					}

					log.info("Assign to role = " + isAssignToRole);
					log.info("Assign to user = " + isAssignToUser);

					if (list != null) {
						ListDataModel data = (ListDataModel) list.getDataModel();

						log.debug("Data model size = " + data.size());

						for (int i = 0; i < data.size(); i++) {
							ReassignTaskBean rtb = (ReassignTaskBean) data.getElementAt(i);

							log.debug("checked state = " + rtb.getCheckState());

							if (rtb.getCheckState() == true) {
								long currentId = rtb.getTaskId();
								WorkItem currentWorkItem = WFObjectFactory.getWorkItem(currentId, wfAdminSession);

								log.debug("reassigning WI Id = " + currentId);

								SimpleListControl control = (SimpleListControl)ctx.session().getAttribute("ReassignTaskList");
								ReassignTaskList dataModel = (ReassignTaskList)control.getDataModel();
								String key = Long.toString(currentWorkItem.getId());
								ReassignTaskBean reassignTaskBean = (ReassignTaskBean) ListHelp.getLineFromKey(dataModel, key);
								Date activeDate = reassignTaskBean.getCreatedDate();
								
								if (isAssignToRole) {
									if (currentWorkItem.getNodeInstance().getRole().equalsIgnoreCase(roleToAssignTo)) {

										String userName;
										HashMap usersDispHash = new HashMap();
										HashMap userList = ACMICache.getUsers();

										Object[] userArray = userList.values().toArray();
										for (int ii = 0; ii < userArray.length; ii++) {
											userObj = (LDAPUser) userArray[ii];
											userRoleList = userObj.getRoles();
											for (int j = 0; j < userRoleList.size(); j++) {
												userRole = (String) userRoleList.get(j);
												if (userRole.equalsIgnoreCase(roleToAssignTo)) {
													userName = userObj.getLastName() + " " + userObj.getFirstName();
													usersDispHash.put(userObj.getUserId(), userName);
													break;
												}
											}
										}
										Iterator membersIterator = usersDispHash.keySet().iterator();
										String[] usersOfGroup = new String[usersDispHash.keySet().size()];
										int count = 0;
										while (membersIterator.hasNext()) {
											usersOfGroup[count] = (String) membersIterator.next();
											count++;
										}

										currentWorkItem.reassignTo(usersOfGroup);
										// Update the IUAActivities Table - put null for
										// Employee Id, Accepted Date - since assigned to role
										updateIUAActivitiesTable(currentWorkItem.getName(), currentWorkItem.getProcessInstance()
												.getId(), activeDate, null);

									}
								} else if (isAssignToUser) {
									String[] oneUser = new String[1];
									oneUser[0] = userToAssignTo;
									currentWorkItem.reassignTo(oneUser);
									updateIUAActivitiesTable(currentWorkItem.getName(), currentWorkItem.getProcessInstance()
											.getId(), activeDate, userToAssignTo);
								}
								
								if (Constants.EPM_ACTIVITY_CONDUCT_INITIAL_INTERVIEW.equals(currentWorkItem.getName()))
									new IUAProcessBD().updateInterviewForReassign(currentWorkItem);
								
							}// end if rtb is checked
						} // end data for loop
						 
						ctx.session().setAttribute(com.epm.acmi.struts.Constants.reassignkey, null);

					}
					this.search_onClick((FormActionContext) ctx);
				}
			} // end action else

		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage());
			ex.printStackTrace();
		} finally
		{
			try
			{
				if ((epmHelper != null) && (wfAdminSession != null))
						epmHelper.disconnect(wfAdminSession);
			} catch (Exception e)
			{
				log.error("Exception while disconnecting from EPM Server", e);
			}
		}

		log.debug("End doExecute");
		ctx.forwardToInput();

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
	 * @throws Exception
	 */
	public void maintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {

		log.debug("Begin maintabset_onTabClick");

		ReassignTaskForm form = new ReassignTaskForm();
		HashMap map1 = new HashMap();

		HashMap map = ACMICache.getUsers();
		Iterator itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			LDAPUser usr = (LDAPUser) map.get(key);
			map1.put(usr.getLastName() + " " + usr.getFirstName(), usr.getLastName() + " " + usr.getFirstName());

			ReassignAction.userNameUserIDMap.put(usr.getLastName() + " " + usr.getFirstName(), key);
		}

		Map sortedMap = new TreeMap(map1);

		form.setMap(sortedMap);
		ctx.session().setAttribute("ReassignTaskForm", form);
		ctx.session().setAttribute("ReassignTaskList", null);

		log.debug("End maintabset_onTabClick");
		ctx.control().execute(ctx, seltab);
	}


	/**
	 * This Method is called, when the Tabpage is clicked
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 * @throws Exception
	 */
	public void secondarymaintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {

		ReassignTaskForm form;
		HashMap map1;

		log.debug("Begin secondarymaintabset_onTabClick");

		form = new ReassignTaskForm();
		map1 = new HashMap();

		HashMap map = ACMICache.getUsers();
		Iterator itr = map.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			LDAPUser usr = (LDAPUser) map.get(key);
			map1.put(usr.getLastName() + " " + usr.getFirstName(), usr.getLastName() + " " + usr.getFirstName());

			ReassignAction.userNameUserIDMap.put(usr.getLastName() + " " + usr.getFirstName(), key);
		}

		Map sortedMap = new TreeMap(map1);

		form.setMap(sortedMap);
		ctx.session().setAttribute("ReassignTaskForm", form);
		ctx.session().setAttribute("ReassignTaskList", null);

		log.debug("End secondarymaintabset_onTabClick");

		ctx.control().execute(ctx, seltab);
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
	 * @throws Exception
	 */

	public void ReassignTaskList_onSort(ControlActionContext ctx, String column, SortOrder direction) throws Exception {

		ReassignTaskList dataModel = (ReassignTaskList) ctx.control().getDataModel();

		dataModel.sortByColumn(column, direction);

		ctx.control().execute(ctx, column, direction);
	}
	
	/**
	 * This Method is called when the Search Button is clicked
	 * 
	 * @param ctx
	 *            ControlActionContext
	 * @throws Exception
	 */
	public void search_onClick(FormActionContext ctx) throws Exception {

		Connection conn = null;
		EPMHelper epmHelper = null;
		WFAdminSession wfAdminSession = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		String userID;
		String pl = null;
		try {
			log.debug("Begin search_onClick");
			ReassignTaskForm reassignForm = (ReassignTaskForm) ctx.form();
			String userRole = reassignForm.getRoleDropdown();

			conn = Connect.getConnection();
			userID = (String) ReassignAction.userNameUserIDMap.get(reassignForm.getItem());

			log.debug("Retrieving from reassignworklist with Responsible = " + userID + " and Activity Role = " + userRole);
			// Build Work List
			epmHelper = new EPMHelper();
			wfAdminSession = epmHelper.connectAsAdminFromPropertiesFile();
			String sql = " select * from reassignworklist WITH (NOLOCK) where responsible= ? and activityRole= ? order by state";
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, userID);
			psmt.setString(2, userRole);
			rst= psmt.executeQuery();
			ArrayList acmiDBWIBMap = new ArrayList();
			
			while (rst.next()) {
				ReassignTaskBean lacmiWIB = new ReassignTaskBean();
				
				long workId = rst.getLong("workItemId");
				lacmiWIB.setTaskId(workId);
				
				
				pl = rst.getString("PolicyNo");
				if (null != pl) {
					if (pl.trim().length() > 0) {
						lacmiWIB.setPolicyNumber(pl);
					}
				}
				String uw = rst.getString("UWInitial");
				if (null != uw) {
					if (uw.trim().length() > 0) {
						lacmiWIB.setUnderWriter(uw);
					}
				}
				String st = rst.getString("State");
				if (null != st) {
					if (st.trim().length() > 0) {
						lacmiWIB.setState(st);
					}
				}

				String keyAppFirstName = rst.getString("KeyAppFirstName");
				String keyAppLastName = rst.getString("KeyAppLastName");
				lacmiWIB.setCreatedDate(rst.getTimestamp("ActiveDate"));
				lacmiWIB.setRole(rst.getString("activityRole"));
				if (keyAppLastName != null) {
					lacmiWIB.setKeyApplicantName(keyAppLastName + " " + keyAppFirstName);
				} else {
					lacmiWIB.setKeyApplicantName(keyAppFirstName);
				}
				
				String status ="";
				long wistatus = rst.getLong("status");
				if (wistatus == WorkItem.STATE_ACTIVE) {
					status = "Active";
				} else if (wistatus == WorkItem.STATE_ACCEPTED) {
					status = "Accepted";
				} else if (wistatus == WorkItem.STATE_DECLINED) {
					status = "Declined";
				} else if (wistatus == WorkItem.STATE_DEACTIVE) {
					status = "DeActive";
				}
				lacmiWIB.setTaskName(rst.getString("label"));
				lacmiWIB.setStatus(status);
				acmiDBWIBMap.add(lacmiWIB);
				
			}
			 
			//WorkItem[] actualWlist = new WorkItem[acmiDBWIBMap.size()];
			SimpleListControl theWorkList = new SimpleListControl();
			ReassignTaskBean[] itemsFirDisplay = null;
			if (acmiDBWIBMap.size() != 0) {
				itemsFirDisplay = new ReassignTaskBean[acmiDBWIBMap.size()];
				for (int numberOfWorkItem = 0; numberOfWorkItem < acmiDBWIBMap.size(); numberOfWorkItem++) {
					itemsFirDisplay[numberOfWorkItem] = (ReassignTaskBean) acmiDBWIBMap.get(numberOfWorkItem);
				}


				ReassignTaskList list = new ReassignTaskList(itemsFirDisplay);
				theWorkList.setDataModel(list);
			}

			if (itemsFirDisplay == null) {
				SimpleListControl theWorkListNull = new SimpleListControl();
				ctx.session().setAttribute("ReassignTaskList", theWorkListNull);
			} else {
				ctx.session().setAttribute("ReassignTaskList", theWorkList);
			}

		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Exception " + ex.getClass().getName() + " caught with message: " + ex.getMessage() + " . PolicyNumber = " + pl);
			ex.printStackTrace();
		} finally {
		
			Connect.closeResultSet(rst);
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);

			try
			{
				if ((epmHelper != null) && (wfAdminSession != null))
					epmHelper.disconnect(wfAdminSession);
			} catch (Exception e)
			{
				log.error("Exception while disconnecting from EPM Server", e);
			}
		}

		log.debug("End search_onClick");
		// back to the jsp
		ctx.forwardToInput();

	}


	/**
	 * This Method is called if an item is check/unchecked - We are not using this - as Single select is turned on This
	 * is still present in case we would like to enable multiple select at some point in time Normally this method needs
	 * not to be implemented within you action, because this is handled by the control automatically. If you need a
	 * different behaviour you can add this eventhandler. An other solution is to extend the Control Class and overwrite
	 * the method.
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param mode
	 *            The selctionMode single/multiple
	 * @param checked
	 *            Flag, which indicates if the item was checked.
	 */
	public void reassign_onCheckAll(ControlActionContext ctx, SelectMode mode, boolean checked) throws Exception {

		// default processing, which updates the datamodel
		ctx.control().execute(ctx, mode, new Boolean(checked));

		// ctx.addGlobalMessage(Messages.MESSAGE, "All items " + (checked ?
		// "checked" : "unchecked") + "!");

		// back to the jsp
		ctx.forwardToInput();
	}

	/**
	 * This Method is called if an item is check/unchecked Normally this method needs not to be implemented within you
	 * action, because this is handled by the control automatically. If you need a different behaviour you can add this
	 * eventhandler. An other solution is to extend the Control Class and overwrite the method.
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param key
	 *            The key of the checked item from the datamodel
	 * @param mode
	 *            The selctionMode single/multiple
	 * @param checked
	 *            Flag, which indicates if the item was checked.
	 */
	public void reassign_onCheck(ControlActionContext ctx, String key, SelectMode mode, boolean checked) throws Exception {

		// default processing, which updates the datamodel
		ctx.control().execute(ctx, key, mode, new Boolean(checked));

		log.debug("Item key: " + key + " checked: " + checked);

		// ctx.addGlobalMessage(Messages.MESSAGE, "Items " + key + " " +(checked
		// ? "checked" : "unchecked") + "!");

		// back to the jsp
		ctx.forwardToInput();
	}

	/**
	 * Updates IUActivities table in ACMI database
	 * 
	 * @param workItemName
	 * @param processId
	 * @param employeeId
	 */
	private void updateIUAActivitiesTable(final String workItemName, final long processId, final Date activeDate, final String employeeId) 
	{
		log.debug("Begin updateIUAActivitiesTable");		
		
		Thread updateThread = new Thread (new Runnable() {
			long waitingTime = INITIAL_WAITING_TIME;
			
			public void run() {
				for (int i = 0; i < MAX_UPDATE_ATTEMPTS; i++)
				{
					try
					{
						int updateCount = doUpdateIUAActivitiesTable(workItemName, processId, activeDate, employeeId);
						//If successful, no need to re-try, so just break out of the loop.
						if (updateCount > 0)
						{
							log.debug("workItemName = '" + workItemName + "'; processId = " + processId + "; activeDate = " + dateFormat.format(activeDate) + "; employeeId = '" + employeeId + "'");
							log.debug("IUAActivities update affected " + updateCount + " records (after attempt # " + (i + 1) + ").");
							break;
						} else if (i == (MAX_UPDATE_ATTEMPTS - 1))
						{
							log.warn("workItemName = '" + workItemName + "'; processId = " + processId + "; activeDate = " + dateFormat.format(activeDate) + "; employeeId = '" + employeeId + "'");
							log.warn("IUAActivities update affected " + updateCount + " records (after attempt # " + (i + 1) + ").");
						}
					} catch (SQLException e) {
						if (i == (MAX_UPDATE_ATTEMPTS - 1))
						{
							log.error("There has been an SQLException thrown while updating IUAActivities table with Work Item Accepted user and date.", e);
							break;
						}
					}
					
					try
					{
						Thread.sleep(waitingTime);
						waitingTime = waitingTime*2;
					} catch (InterruptedException ie)
					{
						//do nothing...
					}				
				}		
			}
		});
		
		updateThread.start();
		log.debug("End updateIUAActivitiesTable");
	}

	private int doUpdateIUAActivitiesTable(String workItemName, long processId, Date activeDate, String employeeId) throws SQLException {		
		Connection conn = Connect.getConnection();
		PreparedStatement psmtUpdate = null;
		try {

			StringBuffer updateSQL = new StringBuffer(" UPDATE IUAActivities SET " + Constants.AcceptDate + " = ? , ");
			updateSQL.append(Constants.EmployeeID + " = ? ");
			updateSQL.append("WHERE " + Constants.PID + " = ? ");
			updateSQL.append(" AND " + Constants.ActivityName + " = ? ");
			updateSQL.append(" AND " + Constants.ActiveDate + " = ? ");
			updateSQL.append(" AND " + Constants.CompleteDate + " IS NULL ");

			psmtUpdate = conn.prepareStatement(updateSQL.toString());

			psmtUpdate.setNull(1, Types.DATE);
			
			if (employeeId != null)
				psmtUpdate.setString(2, employeeId);
			else
				psmtUpdate.setNull(2, Types.VARCHAR);
			
			psmtUpdate.setLong(3, processId);
			psmtUpdate.setString(4, workItemName);
			psmtUpdate.setTimestamp(5, new Timestamp(activeDate.getTime()));
			int updateCount = psmtUpdate.executeUpdate();
			
			if (updateCount == 0)
			{
				SQLWarning warning = psmtUpdate.getWarnings();
				
				if (warning != null)
					printWarnings(warning);
			}
			return updateCount;
		} finally {
			Connect.closePSMT(psmtUpdate);
			Connect.closeConnection(conn);
		}
	}

	private void printWarnings(SQLWarning warning) {
		StringBuffer buff = new StringBuffer();
		SQLWarning currentWarning = warning;
		
		while (currentWarning != null)
		{
			buff.append("Warning: " + currentWarning.getClass().getName() + "; message: " + currentWarning.getMessage() + "; error code: " + currentWarning.getErrorCode());
			
			currentWarning = currentWarning.getNextWarning();
			
			if (currentWarning != null)
				buff.append(System.getProperty("line.separator"));
		}
		
		log.warn(buff.toString());
	}
}