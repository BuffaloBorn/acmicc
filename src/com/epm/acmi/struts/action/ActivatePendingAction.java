package com.epm.acmi.struts.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.io.IOException;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.ui.model.ListDataModel;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.ActivatePendingForm;
import com.epm.acmi.struts.form.dsp.ProcessDisp;
import com.epm.acmi.struts.form.dsp.ProcessList;
import com.epm.acmi.struts.form.dsp.StellentClient;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.EPMHelper;

/**
 * ActivatePendingAction - Handles all actions in Activate Pending Page
 * Actions 
 * 			1. Search
 * 			2. Activate 
 * 			3. Tab click Events
 * 
 * @author Dragos
 */
public class ActivatePendingAction extends MainTabPageBaseAction {

	private static Logger log;

	static {
		log = Logger.getLogger(com.epm.acmi.struts.action.PrevPolicyAction.class);
	}

	/**
	 * @see com.cc.framework.adapter.struts.FWAction#doExecute(ActionContext)
	 */
	/**
	 * This method is called by Controller Servlet. This method is specific to Common Controls API
	 * 
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws IOException, ServletException {

		// ctx.forwardToAction("/main/actpend?ctrl=maintabset&action=TabClick&param=tab2");
	}

	/**
	 * Activate button click event handler
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void activate_onClick(FormActionContext ctx) throws Exception {

		ProcessDisp proc;
		SimpleListControl list;
		ListDataModel data;
		EPMHelper epmHelp;
		long processID;

		log.debug("Entering method activate_onClick()");

		try {

			list = (SimpleListControl) ctx.session().getAttribute("processList");

			if (list != null) {
				data = (ListDataModel) list.getDataModel();

				if (data != null) {

					if (data.size() > 0) {

						epmHelp = new EPMHelper();

						for (int i = 0; i < data.size(); i++) {
							proc = (ProcessDisp) data.getElementAt(i);

							log.debug("process ID " + proc.getPid());
							log.debug("PROCESS TYPE " + proc.getProcessType());
							log.debug("Checked state " + proc.getCheckState());

							if (proc.getCheckState() == true) {
								processID = Long.parseLong(proc.getPid());
								log.debug("Calling BPMHelper with process ID " + processID);
								epmHelp.resumeProcessNew(processID, (String) null, "1");
								proc.setCheckState(false);
							}
						} // end for
					}
				}
			} // end if
		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Error" , ex);
			throw ex;
			
		}

		log.debug("Exiting method activate_onClick()");

		this.search_onClick(ctx);
	}

	/**
	 * Search button click event handler
	 * 
	 * @param ctx
	 * @throws Exception
	 */
	public void search_onClick(FormActionContext ctx) throws Exception {

		ActivatePendingForm form = (ActivatePendingForm) ctx.form();
		String policyNum;
		ProcessList processList = new ProcessList();
		ProcessDisp[] processDispArray = new ProcessDisp[0];
		ArrayList processAL;

		policyNum = form.getPolicyNumber();

		log.debug("Entering method search_onClick()");
		log.debug("Policy Num = " + policyNum);

		try {
			// Get processes from BPM
			processAL = this.PullData(policyNum);
			log.debug("Size of processAL = " + processAL.size());

			if (processAL.size() == 0) {
				processList = null;
			} else {
				processList = new ProcessList();
				log.debug("setting processList data to data from GetSuspendedProcesses");
				processList.setData((ProcessDisp[]) processAL.toArray(processDispArray));
			}
			// Create the ListControl and populate it.
			// with the Data to be displayed
			SimpleListControl processDisp = new SimpleListControl();
			processDisp.setDataModel(processList);
			// Put the ListControl into the Session-Object.
			// Our ListControl is a statefull Object.
			ctx.session().setAttribute("processList", processDisp);
		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError", "");
			log.error("Error" , ex);
			throw ex;
		}

		log.debug("Exiting method search_onClick()");

		ctx.forwardToInput();
	}
	
	
	

	/**
	 * Retrive data from Process Specific information from IUAAppDocProcesses in ACMI database for a given policy number
	 * 
	 * @param policyNumber
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList PullData(String policyNumber) throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		ArrayList suspendedProcessesAL = new ArrayList();
		ProcessDisp proc;
		PreparedStatement psmt = null;
		String appStatus = null;
		String gfId = null;
		String sql = "Select distinct pid, suspendDate, description ";
		sql += "from IUAAppDocProcesses docProc WITH (NOLOCK), IUAProcessTypes procType WITH (NOLOCK) ";
		sql += "where docProc.gfid = ? ";
		sql += "and upper(docProc.status) = 'SUSP' ";
		sql += "and upper(procType.code) = upper(docProc.TypeCode) ";
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
		String pid;
		String typecode;
		Timestamp suspendDate;

		log.debug("Entering method PullData()");

		try {
			
			conn = Connect.getConnection();

			psmt = conn.prepareStatement("Select gfid, status from IUAApplication WITH (NOLOCK) where policyNo = ?");
			psmt.setString(1, policyNumber);

			rs = psmt.executeQuery();
			if (rs.next()) {
				gfId = rs.getString("gfid");
				appStatus = rs.getString("status");
			}
			log.debug("gfId = " + gfId);
			log.debug("AppStatus = " + appStatus);

			log.debug("Pulling suspended processes from IUAAppDocProcesses table");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, gfId);

			rs = psmt.executeQuery();

			while (rs.next()) {
				pid = rs.getString("pid");
				typecode = rs.getString("description");
				suspendDate = rs.getTimestamp("suspendDate");

				// ISSUE #92 CMONTERO 04/18/2006
				// Check if MAIN
				// if (typecode != null && typecode.equalsIgnoreCase("MAIN")) {
				//					
				// log.debug("MAIN process");
				//					
				// // If MAIN and INCOMPLETE show process
				// if (appStatus.indexOf("INCP") > -1){
				// log.debug("INCOMPLETE status. Showing MAIN process");
				// proc = new ProcessDisp();
				// proc.setPid(pid);
				// proc.setProcessType(typecode);
				// proc.setSuspendDate(sdf.format(suspendDate));
				//
				// suspendedProcessesAL.add(proc);
				// }
				// }
				// else {
				// log.debug("NOT MAIN process - adding process");
				log.debug("Activate Pending - adding process");
				proc = new ProcessDisp();
				proc.setPid(pid);
				proc.setProcessType(typecode);
				proc.setSuspendDate(sdf.format(suspendDate));

				suspendedProcessesAL.add(proc);
				// }

			}

		} catch (Exception e) {
			log.error("Error" , e);
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (psmt != null)
				psmt.close();
			if (conn != null)
				conn.close();
		}

		log.debug("Exiting method PullData()");

		return suspendedProcessesAL;
	}

	/**
	 * Constructor
	 */
	public ActivatePendingAction() {
		super();
	}

	/**
	 * @see com.cc.sampleapp.tabset.sample401.action.TabPageBaseAction#getTabPageId()
	 */

	/**
	 * Returns tab page Id
	 */
	public String getTabPageId() {
		return TABPAGE2;
	}

	/**
	 * This Method is called, when the Tabpage is clicked
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 */
	public void maintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {
		log.debug("Begin method maintabset_onTabClick");
		ctx.session().setAttribute("processList", null);
//		if (seltab.equals("tab2")) {
//			// Clear Stellent Session Id when user tabs into document exceptions
//			User user = (User) ctx.session().getAttribute(Constants.loggedUser);
//			String AAID = user.getAAID();
//			if (AAID != null) {
//				StellentClient.logout(AAID);
//				user.setAAID(null);
//			}
//		}
		ctx.control().execute(ctx, seltab);
		// ctx.forwardToAction("/main/actpend?ctrl=maintabset&action=TabClick&param=tab2");
		log.debug("End method maintabset_onTabClick");

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
		log.debug("Begin method secondarymaintabset_onTabClick");
		ctx.session().setAttribute("processList", null);
//		if (seltab.equals("tab2")) {
//
//			// Clear Stellent Session Id when user tabs into document exceptions
//			User user = (User) ctx.session().getAttribute(Constants.loggedUser);
//			String AAID = user.getAAID();
//			if (AAID != null) {
//				StellentClient.logout(AAID);
//				user.setAAID(null);
//			}
//		}
		ctx.control().execute(ctx, seltab);
		// ctx.forwardToAction("/secondarymain/actpend?ctrl=secondarymaintabset&action=TabClick&param=tab2");
		log.debug("End method secondarymaintabset_onTabClick");

	}

}