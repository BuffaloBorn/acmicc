package com.epm.acmi.struts.action;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.adapter.struts.FormActionContext;
import com.cc.framework.common.SortOrder;
import com.cc.framework.ui.control.ControlActionContext;
import com.cc.framework.ui.control.FormControl;
import com.cc.framework.ui.control.SimpleListControl;
import com.cc.framework.util.ListHelp;
import com.epm.acmi.bean.AcmiWorkItemBean;
import com.epm.acmi.datamodel.WorkListList;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.FormLoaderHelper;
import com.epm.acmi.struts.form.SelectedActivity;
import com.epm.acmi.struts.form.WorkListForm;
import com.epm.acmi.util.Connect;
import com.epm.acmi.util.MiscellaneousUtils;
import com.epm.acmi.util.DateUtility;
import com.epm.acmi.util.EPMHelper;
import com.epm.acmi.util.HistoryEvent;
import com.epm.acmi.util.MiscellaneousUtils;
import com.fujitsu.iflow.model.util.ModelInternalException;
import com.fujitsu.iflow.model.util.WFInternalException;
import com.fujitsu.iflow.model.util.WFServerException;
import com.fujitsu.iflow.model.workflow.WFObjectFactory;
import com.fujitsu.iflow.model.workflow.WFSession;
import com.fujitsu.iflow.model.workflow.WorkItem;

/**
 * WorkListAction handles Case Notes page.
 * 
 * @author Rajeev Chachra
 */

/**
 * @author d446
 *
 */
public class WorkListAction extends CCAction {

	//-----------------------------------------------------------
	// --------------------------------------------------------- Instance
	// Variables
	private static Logger log = Logger.getLogger(WorkListAction.class);
	private static String COMMA = ",";
	private static String SPACE = " ";
	private static String SQUOTE = "'";

	private static final int MAX_UPDATE_ATTEMPTS = 5;
	private static final long INITIAL_WAITING_TIME = 100;


	private static Map workingCtxMap =  Collections.synchronizedMap( new HashMap());
	
	private String WORKLISTSQL1 = "select * from worklist WITH (NOLOCK) where responsible =? ";

	public WorkListAction() {
		super();
	}


	/**
	 * Retrives work list items and build work list to display on work list page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param ctx
	 * @return
	 * @throws Exception
	 */
	public AcmiWorkItemBean[] execute1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, ActionContext ctx) throws Exception {
		log.debug("Begin execute1()");
		WorkListForm workListForm = (WorkListForm) form;
		AcmiWorkItemBean[] itemsFirDisplay = null;
		long t = System.currentTimeMillis();
		try {
			// newly selected Filter
			String selActivity = request.getParameter("selectedActivity");
			String filterVal = request.getParameter("filter");

			HttpSession session = request.getSession();
			SelectedActivity sa = (SelectedActivity) session.getAttribute("SelectedActivity");

			if (selActivity == null && sa != null) {
				selActivity = sa.getSelectedActivity();
				filterVal = sa.getFilter();

			} else if (selActivity == null && sa == null) {
				selActivity = "AllActivities";
			}

			// Set filter value = null when it is not user
			if (selActivity.endsWith("AllActivities") || selActivity.endsWith("MyActiveActivities")
					|| selActivity.endsWith("MyAcceptedActivities") || selActivity.endsWith("MyDeclinedActivities")) {
				filterVal = null;
			}

			log.debug("Current Selected Filter " + selActivity);

			if (sa == null) {
				sa = new SelectedActivity();
			}

			sa.setSelectedActivity(selActivity);

			// set filter as null value is also OK.
			sa.setFilter(filterVal);
			// upon reading and setting the value from UI pub back the bean in
			// session
			session.setAttribute("SelectedActivity", sa);
			List acmiWIBList = null;
			log.debug("Get worklist items from EPM database");

			if (sa.getSelectedActivity() != null) {
				if (sa.getSelectedActivity().equals("MyActiveActivities")) {
					acmiWIBList = getWLNew1(request, session, response, 2, ctx);
				} else if (sa.getSelectedActivity().equals("MyAcceptedActivities")) {
					acmiWIBList = getWLNew1(request, session, response, 3, ctx);
				} else if (sa.getSelectedActivity().equals("AllActivities")) {
					acmiWIBList = getWLNew1(request, session, response, 1, ctx);
				} else if (sa.getSelectedActivity().equals("ActivityByUW")) {
					acmiWIBList = getWLNew1(request, session, response, 4, ctx);
				} else if (selActivity.equals("ActivityByState")) {
					acmiWIBList = getWLNew1(request, session, response, 5, ctx);
				} else if (selActivity.equals("PolicyNumber")) {
					acmiWIBList = getWLNew1(request, session, response, 6, ctx);
				} 
				// USR 8399-1 
				else if (selActivity.equals("AgentNumber")) {
					acmiWIBList = getWLNew1(request, session, response,7, ctx);
				} // End of usr 8399-1 changes.  
				else {
					acmiWIBList = getWLNew1(request, session, response, 1, ctx);
				}
			} else {
				acmiWIBList = getWLNew1(request, session, response, 1, ctx);
			}

			log.debug("Get worklist items from EPM .. Done");

			// Bulding display list for common controls
			int i = 0;

			itemsFirDisplay = new AcmiWorkItemBean[acmiWIBList.size()];
			Iterator iter = acmiWIBList.iterator();

			while (iter.hasNext()) {
				itemsFirDisplay[i++] = (AcmiWorkItemBean) iter.next();
			}

			// End Bulding display list for common controls
			workListForm.setAcmiWIBList(acmiWIBList);
			request.setAttribute("acmiWIBList", acmiWIBList);
			if (acmiWIBList.isEmpty()) {
				session.setAttribute("acmiWIBListIsEmpty", "true");
			} else {
				session.setAttribute("acmiWIBListIsEmpty", "false");
			}
			
			long t1 = System.currentTimeMillis();
			t1 = t1 - t;
			log.info("WL Time " + t1);
			log.debug("End execute1()");
		} catch (Exception ex) {
			log.error("execute1() Failed ", ex);
			throw ex;
		}

		return itemsFirDisplay;
	}

	private static String getSQLList(String filter)
	{
		List values = getList(filter);
		StringBuffer buf = new StringBuffer();
		
		for (int i = 0; i < values.size(); i++)
		{
			buf.append(SQUOTE);
			buf.append(values.get(i));
			buf.append(SQUOTE);
			
			if (i < (values.size() - 1)) {
				buf.append(COMMA);
				buf.append(SPACE);
			}
		}
		
		return buf.toString();
	}
	
	private static List getList(String filter)
	{
		StringTokenizer tok = new StringTokenizer(filter, COMMA);
		List result = new ArrayList();
		
		while (tok.hasMoreTokens())
		{
			String value = tok.nextToken();
			result.add(value.trim());
		}
		
		return result;		
	}
	
	/**
	 * Retrieve workitems from EPM
	 * 
	 * @param tasks
	 * @param request
	 * @param response
	 * @param session
	 * @param onlyaccptandactivetasks
	 * @return
	 * @throws Exception
	 */	
	private List getWLNew1(HttpServletRequest request, HttpSession session, HttpServletResponse response,
			int status, ActionContext ctx) {
		List acmiWIBList = new ArrayList();
		String filter = null;
		String tmpGFID = null;
		SelectedActivity sa = (SelectedActivity) session.getAttribute("SelectedActivity");
		User user = (User) session.getAttribute(Constants.loggedUser);
		if (sa == null) {
			filter = request.getParameter("filter");
		} else {
			filter = sa.getFilter();
		}

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rst = null;
		String sql = "";

		try
		{
			session.getCreationTime();
		}
		catch (IllegalStateException ise) {
			//This replaces the EPM session expiration. 
			//We do not keep an EPM session alive for the duration of the HTTP session anymore,
			//since that leads to memory leaks on the EPM Server.
			log.error("Your session has expired ", ise);
			ctx.addGlobalError("message.nosession");
			ctx.forwardToAction("/login");
			return acmiWIBList;
		}
		
		try {

			String keyAppFirstName = null;
			String keyAppLastName = null;

			conn = Connect.getConnection();
			// status = 1 All accepted and active activities
			if (status == 1) {
				psmt = conn.prepareStatement(WORKLISTSQL1);
				psmt.setString(1, user.getUserId());
			}
			// status =2 all active activities
			else if (status == 3) {
				sql = WORKLISTSQL1 + "  and status=8";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, user.getUserId());
			}
			// status =3 all accepted activities
			else if (status == 2) {
				sql = WORKLISTSQL1 + "  and status=3";
				psmt = conn.prepareStatement(sql);
				psmt.setString(1, user.getUserId());
			}
			// status =4 all active and accepted activities filter by underwriter
			else if (status == 4) {
				if (null != filter && !filter.equals("")) {
					sql = WORKLISTSQL1 + "  and uwinitial in (" + getSQLList(filter) + ")";
					psmt = conn.prepareStatement(sql);
				} else {
					psmt = conn.prepareStatement(WORKLISTSQL1);
				}

				psmt.setString(1, user.getUserId());
			}
			// status =5 all active and accepted activities filter by state
			else if (status == 5) {
				if (null != filter && !filter.equals("")) {
					sql = WORKLISTSQL1 + "  and state in (" + getSQLList(filter) + ")";
					psmt = conn.prepareStatement(sql);
				} else {
					psmt = conn.prepareStatement(WORKLISTSQL1);
				}
				psmt.setString(1, user.getUserId());
			}

			else if (status == 6) {
				// check policy number
				try {
					if (!MiscellaneousUtils.isNullString(filter))
						Long.parseLong(filter);
				} catch (NumberFormatException ne) {
					ctx.addGlobalError("worklist.invalidPolicyNo");
					log.warn("Policy Number Format Exception ", ne);

					// Note: Returning value of empty workitems as another error
					// line was in the sequence was trapped and returned a fatal error message
					return acmiWIBList;

				}
				if (null != filter && !filter.equals("")) {
					sql = WORKLISTSQL1 + "  and PolicyNo='" + filter + "'";
					psmt = conn.prepareStatement(sql);
				} else {
					psmt = conn.prepareStatement(WORKLISTSQL1);
				}
				psmt.setString(1, user.getUserId());
			}
			/*
			 *  Begins USR 8399-1 changes 
			 *  NagRathna Hiriyurkar  01/25/2008
			 *  sTATUS 7 = By AgentNumber and My activites by AgentNumber.
			 */
			else if (status == 7) {
				if (null != filter && !filter.equals("")) {
					//log.debug("Agent Number filter value : " + getSQLList(filter));
					log.debug("Agent Number filter value : " + getSQLList(filter));
					sql = WORKLISTSQL1 + "  and AgentNumber in (" + getSQLList(filter) + ")";
					psmt = conn.prepareStatement(sql);
				} else {
					psmt = conn.prepareStatement(WORKLISTSQL1);
				}
				psmt.setString(1, user.getUserId());
			}
			// End USR 8399-1 CHANGES.
	
			rst = psmt.executeQuery();
			
			while (rst.next()) {

				AcmiWorkItemBean acmiWIB = new AcmiWorkItemBean();

				String pl = rst.getString("PolicyNo");
				if (null != pl) {
					if (pl.trim().length() > 0) {
						acmiWIB.setPolicyNumber(pl);
					}
				}
				String uw = rst.getString("UWInitial");
				if (null != uw) {
					if (uw.trim().length() > 0) {
						acmiWIB.setUnderWriter(uw);
					}
				}
				String lb = rst.getString("CMListBill");
				if (null != lb) {
					if (lb.trim().length() > 0) {
						acmiWIB.setListBill(lb);
					}
				}
				String st = rst.getString("State");
				if (null != st) {
					if (st.trim().length() > 0) {
						acmiWIB.setState(st);
					}
				}

				keyAppFirstName = rst.getString("KeyAppFirstName");
				keyAppLastName = rst.getString("KeyAppLastName");
				if (keyAppLastName != null) {
					acmiWIB.setKeyApplicantName(keyAppLastName + " " + keyAppFirstName);
				} else {
					acmiWIB.setKeyApplicantName(keyAppFirstName);
				}
				int wistatus = rst.getInt("status");
				String actStatus = "";
				if (wistatus == WorkItem.STATE_ACTIVE) {
					actStatus = "Active";
				} else if (wistatus == WorkItem.STATE_ACCEPTED) {
					actStatus = "Accepted";
				} else if (wistatus == WorkItem.STATE_DECLINED) {
					actStatus = "Declined";
				} else if (wistatus == WorkItem.STATE_DEACTIVE) {
					actStatus = "DeActive";
				}
				acmiWIB.setTaskId(rst.getLong("workitemid"));
				acmiWIB.setTaskName(rst.getString("label"));
				acmiWIB.setCreatedDate(rst.getTimestamp("ActiveDate"));
				acmiWIB.setAssignee(rst.getString("responsible"));
				acmiWIB.setStatus(actStatus);
				/*USR 7930 
				 * Asmita - 02/09/2007
				 */
				
				if(rst.getString("gfid").startsWith("E")||rst.getString("gfid").startsWith("e")){
					acmiWIB.setTaskEapp("E");
				}
				//End USR 7930 Change
				
				/* USR 8399-1 
				 * NagRathna Hiriyurkar - 01/21/2008 
				*/
				String ag = rst.getString("Agentnumber");
				if (null != ag) {
					if (ag.trim().length() > 0) {
						acmiWIB.setTaskAgent(ag);
					}
				}
				// USR 8399-1 changes Ends.
				
				tmpGFID = rst.getString("gfid"); 
				String taskURL = "WorkItem.jsp?taskId=" + String.valueOf(rst.getLong("workitemid"));
				taskURL = response.encodeURL(taskURL);
				acmiWIBList.add(acmiWIB);
			}
		} catch (Exception ex) {
			ctx.addGlobalError("error.mainError");
			log.error("Work list creation failed, GFID =" + tmpGFID , ex);
			ex.printStackTrace();
		} finally {
			Connect.closeResultSet(rst);
			Connect.closePSMT(psmt);
			Connect.closeConnection(conn);

		}
		return acmiWIBList;
	}



	/**
	 * @see com.cc.framework.adapter.struts.FrameworkAction#doExecute(com.cc.framework.adapter.struts.ActionContext)
	 *      This Method is called when the work List Page is loaded
	 * @param ctx
	 *            ActionContext
	 * @throws java.lang.Exception
	 */
	public void doExecute(ActionContext ctx) throws IOException, ServletException {

		try {

			log.debug("Begin doExecute()");

			User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);
			AcmiWorkItemBean[] itemsFirDisplay = execute1(ctx.mapping(), ctx.form(), ctx.request(), ctx.response(), 
					ctx);
			SimpleListControl theWorkList = new SimpleListControl();

			WorkListList modelWorkList = new WorkListList(itemsFirDisplay);
			modelWorkList.sortByColumn("createdDateString", SortOrder.ASCENDING);
			theWorkList.setDataModel(modelWorkList);
			ctx.session().setAttribute("WLList", theWorkList);
			log.debug("End doExecute()");
		} catch (Exception ex) {
			log.error("doExecute Failed", ex);
			ctx.addGlobalError("error.mainError", "");
			ctx.forwardByName("failure");
		}
		ctx.forwardToInput();

	}


	// ----------------------------------------------
	// TabSet-Callback Methods
	// ----------------------------------------------

	/**
	 * This Method is called, when the Tabpage is clicked In our Example we instantiate the BookListControl.
	 * 
	 * @param ctx
	 *            The ControlActionContext
	 * @param seltab
	 *            The selected TabPage
	 */
	public void maintabset_onTabClick(ControlActionContext ctx, String seltab) throws Exception {

		try {
			ctx.session().setAttribute("acmiWIBList", ctx.request().getAttribute("acmiWIBList"));
		} catch (Throwable t) {
			log.error("Error retrieve BookList", t);
			ctx.addGlobalError("error.mainError", "");
			// t.printStackTrace();
		}

		// default processing. This includes:
		// - tabpage selection
		// - forward to the specified jsp

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
	 */
	public void WLList_onSort(ControlActionContext ctx, String column, SortOrder direction) throws Exception {
		log.debug("Begin WLList_onSort()");
		log.debug("column=" + column + " direction=" + direction);
		WorkListList dataModel = (WorkListList) ctx.control().getDataModel();
		dataModel.sortByColumn(column, direction);
		ctx.control().execute(ctx, column, direction);
		log.debug("Begin WLList_onSort()");
	}


	/**
	 * This Method is called when the Usere Accepts a Task by click on the 'Accept' link in work list
	 * 
	 * @param ctx
	 *            ControlActionContext
	 * @param key
	 *            key (Task Id selected)
	 */
	// fix for issue #63 - cmontero - 03/28/2006
	// public void WLList_onDrilldown(ControlActionContext ctx, String key) {
	public void WLList_onDrilldown(ControlActionContext ctx, String key) {
		log.debug("Begin WLList_onDrilldown()");
		EPMHelper epmh = null;
		WFSession mywfSession = null;
		String theGFID = null;
		long wIdLong = new Long(key).longValue();
		
		synchronized (ctx)
		{
			FormActionContext testCtx = (FormActionContext)workingCtxMap.get(ctx);
			
			//If context is being worked on, we clicked twice, don't process again...
			if (testCtx != null)
				return;
			
			//Mark the fact we are working on this context
			workingCtxMap.put(ctx, ctx);
		}
		
		try {
			
			
			User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);

			//Remove any leftover doctCode from the UploadDocument Screen
			ctx.session().removeAttribute("docCode");
			ctx.session().removeAttribute("refreshList");
			
			epmh = new EPMHelper();
			mywfSession = epmh.connectAsUser(user.getUserId(), user.getPassword());
			WorkItem wi = WFObjectFactory.getWorkItem(wIdLong, mywfSession);
			if (null != wi) {
				com.fujitsu.iflow.model.workflow.DataItem[] di = wi.getWorklistDataItems();
				for (int j = 0; j < di.length; j++) {
					if (di[j].getName().equalsIgnoreCase("gfId")) {
						theGFID = di[j].getValue();
						ctx.session().setAttribute(com.epm.acmi.struts.Constants.gfid, theGFID);
						ctx.session().setAttribute(com.epm.acmi.struts.Constants.workItemId, key);
						// set it also in request object
						ctx.request().setAttribute(com.epm.acmi.struts.Constants.workItemId, key);
						ctx.request().setAttribute(com.epm.acmi.struts.Constants.gfid, theGFID);
					}
				}
				long t = System.currentTimeMillis();

				wi.accept();
				long t1 = System.currentTimeMillis();
				log.info("WortItem Accept time for " + wi.getName() + " is: " + (t1 - t) + " ms.");
				updateActivitiesTable(ctx, key, theGFID, user, wi);		
				
				String workItemRole = wi.getNodeInstance().getRole(); 
				
				if (Constants.IUPS_UW_ROLE.equalsIgnoreCase(workItemRole) && !Constants.MAKE_UNDERWRITING_DECISION.equals(wi.getName()))
					ctx.session().setAttribute(Constants.IS_INTERVIEW_TAB_READ_ONLY, Constants.FALSE);
				else
					ctx.session().setAttribute(Constants.IS_INTERVIEW_TAB_READ_ONLY, Constants.TRUE);	
			}
			try {
				ctx.request().getSession().setAttribute("wid", key);
				FormLoaderHelper formLoader = new FormLoaderHelper();
				formLoader.loadNBWSDetailsForm(ctx);
				FormControl control = new FormControl();
				control.setDesignModel(NBWSDetailsAction.getDesignModel(ctx.request()));
				ctx.session().setAttribute("ActivityActions", control);
			} catch (Exception ex) {
				ctx.addGlobalError("error.mainError", "");
				log.error("WorkItem Accept Failed", ex);
			}
			

		} catch (Exception e) {
			log.warn("There has been an unknown exception while in Work Item Accepted");
			ctx.addGlobalError("error.assignError", e.getMessage());
			log.warn("Work Item is stale (selected work item already accepted by some one else)");
			log.debug("Because of error forward to work list");
			ctx.forwardToAction("/worklist");
			return;
		} finally
		{
			try
			{
				if ((epmh != null) && (mywfSession != null))
					epmh.disconnect(mywfSession);
			} catch (Exception ex)
			{
				log.error("Exception while disconnecting from EPM Server", ex);
			}
			
			synchronized (ctx)
			{
				workingCtxMap.remove(ctx);
			}
		}
		log.debug("End WLList_onDrilldown()");
		ctx.forwardToAction("main/iuauser?ctrl=maintabset&action=TabClick&param=tab1");

	}

	private void updateActivitiesTable(final ControlActionContext ctx, final String key,
			final String theGFID, final User user, final WorkItem wi) throws WFInternalException,
			WFServerException, ModelInternalException, Exception {
		log.debug("Begin updateActivitiesTable");		
		
		Thread updateThread = new Thread (new Runnable() {
			long waitingTime = INITIAL_WAITING_TIME;
			
			public void run() {
				for (int i = 0; i < MAX_UPDATE_ATTEMPTS; i++)
				{
					try
					{
						int updateCount = doUpdateActivitiesTable(ctx, key, theGFID, user, wi);
						//If successful, no need to re-try, so just break out of the loop.
						if (updateCount > 0)
						{
							log.debug("gfid = " + theGFID);
							log.debug("IUAActivities update affected " + updateCount + " records (after attempt # " + (i + 1) + ").");
							break;
						} else if (i == (MAX_UPDATE_ATTEMPTS - 1))
						{
							log.warn("gfid = " + theGFID);
							log.warn("IUAActivities update affected " + updateCount + " records (after attempt # " + (i + 1) + ").");
						}
					} catch (Exception e) {
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
		log.debug("End updateActivitiesTable");
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

	private int doUpdateActivitiesTable(ControlActionContext ctx, String key,
			String theGFID, User user, WorkItem wi) throws WFInternalException,
			WFServerException, ModelInternalException, Exception {
		//Adding - Cesar...
		Date acceptDate = retrieveEventCreateDateFromHistory(wi);
		log.info("WorkItem - work item id: " + wi.getId());
		log.info("WorkItem - process instance id: " + wi.getProcessInstance().getId());
		log.info("WorkItem - Last accept timestamp: " + DateUtility.convertDateToTimeString(acceptDate));
		//End Adding - Cesar
		// set the current Role from the Work Item Id
		// end setting current role for the Work Item Id
		// Update the IUAActivities Table
		long processId = wi.getProcessInstance().getId();
		Connection conn = Connect.getConnection();
		PreparedStatement psmtUpdate = null;
		
		WorkListList dataModel = (WorkListList) ctx.control().getDataModel();
		AcmiWorkItemBean acmiWorkItemBean = (AcmiWorkItemBean) ListHelp.getLineFromKey(dataModel, key);
		log.debug("AcmiWorkItemBean -- Accept Label = " + acmiWorkItemBean.getAcceptLabel());
		log.debug("AcmiWorkItemBean -- Active Date = " + acmiWorkItemBean.getCreatedDateString());
		log.debug("IUAActivities update - AcceptDate: " + acceptDate.getTime() + ", EmployeeID = " + user.getUserId() + ", PID = " + processId +
				", ActivityName = " + wi.getName() + ", ActiveDate = " + acmiWorkItemBean.getCreatedDateString());
		
		try {
			//Started Modifications - Cesar
			String updateSQL = " update IUAActivities set AcceptDate = ?, EmployeeID = ? where PID = ? and ActivityName = ? and ActiveDate = ? ";
			psmtUpdate = conn.prepareStatement(updateSQL);
			psmtUpdate.setTimestamp(1, new Timestamp(acceptDate.getTime()));
			psmtUpdate.setString(2, user.getUserId());
			psmtUpdate.setLong(3, processId);
			psmtUpdate.setString(4, wi.getName());
			psmtUpdate.setTimestamp(5, new Timestamp(acmiWorkItemBean.getCreatedDate().getTime()));
			//Ended Modifications - Cesar
			int updateCount = psmtUpdate.executeUpdate();
			log.debug("IUAActivities update affected " + updateCount + " records.");

			if (updateCount == 0)
			{
				SQLWarning warning = psmtUpdate.getWarnings();
				
				if (warning != null)
					printWarnings(warning);
			}
			return updateCount;
		} catch (Exception e) {
			log.error("There has been a database exception while updating IUAActivities table with Work Item Accepted user and date. GFID = " + theGFID, e);
			ctx.addGlobalError("error.mainError", "");
			return 0;
		} finally {
			Connect.closePSMT(psmtUpdate);
			Connect.closeConnection(conn);
		}
	}
	
	/**
	 * Retrieves the accept date for a work item from history. 
	 * 
	 * @param wi - The work item we want to find out the accept date for
	 * @return - A date representing the accept date-time.
	 * @throws Exception
	 */
	private Date retrieveEventCreateDateFromHistory(WorkItem wi) throws WFInternalException, WFServerException, ModelInternalException
	{
		try
		{
			HistoryEvent workItemEvent = EPMHelper.retrieveWorkItemEventFromHistoryTimestamp(wi);
			
			//Retrieve timestamp from last "Accept" event and convert to Date...
			if (workItemEvent == null)
			{
				String exString = "History event list for process instance id" + wi.getProcessInstanceId() + " does not contain and event for work item id" + wi.getId();
				throw new ModelInternalException(0, exString);
			}
			else
			{			
				//From the last event, retrieve the event creation timestamp, and return the corresponding date.
				long lastAcceptedTimestamp = workItemEvent.getTimestamp();
				return new Date(lastAcceptedTimestamp);
			}		
		} catch (Exception e)
		{
			log.warn("Unable to retrieve accepted date from history for process id " + wi.getProcessInstanceId());
			return new Date();
		}
	}
}
