package com.epm.acmi.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.hibernate.Query;

import com.cc.acmi.common.User;
import com.cc.framework.adapter.struts.ActionContext;
import com.cc.framework.ui.control.ControlActionContext;
import com.epm.acmi.hbm.BaseHibernateDAO;
import com.epm.acmi.hbm.IuaappDocProcesses;
import com.epm.acmi.hbm.IuaappDocProcessesDAO;
import com.epm.acmi.hbm.IuatelephonicInterview;
import com.epm.acmi.struts.Constants;
import com.fujitsu.iflow.model.workflow.ProcessInstance;
import com.fujitsu.iflow.model.workflow.WFAdminSession;
import com.fujitsu.iflow.model.workflow.WFObjectFactory;
import com.fujitsu.iflow.model.workflow.WFSession;
import com.fujitsu.iflow.model.workflow.WorkItem;

public class MiscellaneousUtils {

	private static final String COMMA = ",";
	private static final String NULL_STRING = "";
	private static final int MAX_ROLE_STR_LENGTH = 35;
	
	private static final String INTERVIEW_BY_PID_SQL = "from IuatelephonicInterview u where u.iuaappDocProcesses in (from IuaappDocProcesses i where i.pid = :pid)";
	private static final String PID_PARAM = "pid";
	
	public static final DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

	private static Logger log = Logger.getLogger(MiscellaneousUtils.class);
	
	/**
	 * A standard implementation of deep clone taken from the internet
	 * 
	 * @param obj
	 * @return
	 */
	public  static Object deepClone(Serializable obj)
	{
		if (obj == null)
			return null;
		
	    try
	    {
	    	ByteArrayOutputStream b = new ByteArrayOutputStream();
	    	ObjectOutputStream out = new ObjectOutputStream(b);
	    	out.writeObject(obj);
	    	ByteArrayInputStream byteIn = new ByteArrayInputStream(b.toByteArray());
	    	ObjectInputStream objIn = new ObjectInputStream(byteIn);
	    	return objIn.readObject();
	    } catch (Exception e)
	    {
	    	log.debug(e.getClass().getName() + " exception thrown while cloning object of type " + obj.getClass().getName(), e);
	    	return null;
	    }
	}
	

	/**
	 * Given a key and a value separated by a token separator, retrieve only the key
	 * 
	 * @param str
	 * @param tokenSeparator
	 * @return
	 */
	public static String getKeyFromPairedKeyValue(
			String str, String tokenSeparator) {
		String result = NULL_STRING;
		StringTokenizer tok = new StringTokenizer(str, tokenSeparator);
		
		if (tok.hasMoreTokens())
			result = tok.nextToken();
		
		return result;
	}
	
	/**
	 * From a key, value, and token separator, retrieve an amalgamated keyvalue string.
	 * 
	 * @param key
	 * @param value
	 * @param tokenSeparator
	 * @return
	 */
	public static String getPairedKeyValueFromKeyAndValue(String key, String value, String tokenSeparator)
	{
		StringBuffer buff = new StringBuffer();
		buff.append(key);
		buff.append(tokenSeparator);
		buff.append(value);
		return buff.toString();
	}
	
	/**
	 * Gets the process attached to a given interview id
	 * 
	 * @param ctx
	 * @param processDAO
	 * @param processIns
	 * @return
	 */
	public static IuaappDocProcesses getProcessForInterview(ActionContext ctx, IuaappDocProcessesDAO processDAO, ProcessInstance processIns) {		
		if (isInterviewPlanName(processIns.getPlanName()))
		{
			IuaappDocProcesses process = processDAO.findById(new Long(processIns.getId()));
						
			String processType = process.getTypeCode();
			
			if ((process != null) && (isInterviewProcessTypeDB(processType)))
			{
				if (!isSubProcessTypeDB(processType))
				{
					return process;
				} else
				{
					process = processDAO.findById(new Long(processIns.getParentProcessId()));
					
					if (process != null)
					{
						processType = process.getTypeCode();
						
						if (!isSubProcessTypeDB(processType))
						{
							return process;
						} else
						{
							log.debug("trying to get the last process in the hierarchy - doc decision --> interview common --> initial or follow up");
							
							try
							{
								ProcessInstance parentProcessIns = findProcessInstance(ctx, processIns.getParentProcessId());
								
								if (parentProcessIns != null)
								{
									process = processDAO.findById(new Long(parentProcessIns.getParentProcessId()));
									return process;
								}
							} catch (Exception e)
							{
								log.debug("Couldn't find process for interview");
							}
						}
					}
				} 
			} 
		} 

		return null;
	}
	
	/**
	 * There are four templates involved in interview workflows
	 * 
	 * @param planName
	 * @return
	 */
	public static boolean isInterviewPlanName(String planName)
	{
		return (planName.equals(Constants.EPM_CODE_INITIAL_INTERVIEW)) ||
			(planName.equals(Constants.EPM_CODE_FOLLOW_UP_INTERVIEW)) ||
			(planName.equals(Constants.EPM_CODE_INTERVIEW_COMMON)) ||
			(planName.equals(Constants.EPM_CODE_DOCUMENT_DECISION));
	}

	/**
	 * The interview id is mapped to the top-level process in the interview process. When you are in a 
	 * subprocess, you have to check if the interview id matches with the parent process pid or the 
	 * parent's parent process id.
	 * 
	 * @param wi
	 * @param wfSession
	 * @return
	 * @throws Exception
	 */
	public static boolean comesFromATelephonicInterviewProcess(WorkItem wi, WFSession wfSession) throws Exception{
		if (isInterviewPlanName(wi.getPlanName()))
			return true;
		else
		{
			long parentProcessId = wi.getProcessInstance().getParentProcessId();
			
			if (parentProcessId > -1)
			{
				ProcessInstance parentProcess = WFObjectFactory.getProcessInstance(parentProcessId, wfSession);			
				boolean isParentFromInterview = isInterviewPlanName(parentProcess.getPlanName());
				
				if (isParentFromInterview == true)
					return true;
				else
				{				
					ProcessInstance grandParentProcess = WFObjectFactory.getProcessInstance(parentProcess.getId(), wfSession);
					boolean isGrandParentFromInterview = isInterviewPlanName(grandParentProcess.getPlanName());
					return isGrandParentFromInterview;
				}
			} else
				return false;
		}
	}

	/**
	 * There are four templates involved in interview workflows
	 * @param processName
	 * @return
	 */
	public static boolean isInterviewProcessName(String processName)
	{
		return (processName.indexOf(Constants.EPM_CODE_INITIAL_INTERVIEW) > -1)
		|| (processName.indexOf(Constants.EPM_CODE_FOLLOW_UP_INTERVIEW) > -1)
		|| (processName.indexOf(Constants.EPM_CODE_INTERVIEW_COMMON) > -1)
		|| (processName.indexOf(Constants.EPM_CODE_DOCUMENT_DECISION) > -1);
	}

	/**
	 * Tests whether the activity name is one of the Nurse Consultant activities.
	 * @param activity
	 * @return
	 */
	public static boolean isNurseConsultantActivity(String activity)
	{
		return (activity.indexOf(Constants.EPM_ACTIVITY_CONDUCT_INITIAL_INTERVIEW) > -1)
		|| (activity.indexOf(Constants.EPM_ACTIVITY_CONDUCT_FOLLOW_UP_INTERVIEW) > -1)
		|| (activity.indexOf(Constants.EPM_ACTIVITY_REVISE_INTERVIEW) > -1);
	}

	public static String getWorkItemIdRole(ControlActionContext ctx) throws Exception {
		String workIdString = (String) ctx.request().getSession().getAttribute(com.epm.acmi.struts.Constants.workItemId);
		long workItemId = Long.parseLong(workIdString);
		User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);

		EPMHelper epmHelper = null;
		WFSession wfSession = null;
		WorkItem wi = null;
		
		try
		{
			epmHelper = new EPMHelper();
			wfSession = epmHelper.connectAsUser(user.getUserId(), user.getPassword());
			
			if (workItemId != -1) {
				wi = WFObjectFactory.getWorkItem(workItemId, wfSession);
				
				if (wi != null)
				{
					String userRole = wi.getNodeInstance().getRole(); 
					return userRole;
				}
			}		
		} finally
		{
			if (epmHelper != null)
				epmHelper.disconnect(wfSession);
		}
		
		return NULL_STRING;
	}

	/**
	 * Retrieves a process instance given a context and a process instance id/
	 * 
	 * @param ctx
	 * @param processInstanceId
	 * @return
	 * @throws Exception
	 */
	public static ProcessInstance findProcessInstance(ActionContext ctx, long processInstanceId) throws Exception {
		User user = (User) ctx.session().getAttribute(com.epm.acmi.struts.Constants.loggedUser);

		EPMHelper epmHelper = null;
		WFSession wfSession = null;
		ProcessInstance pi = null;
		
		try
		{
			epmHelper = new EPMHelper();
			wfSession = epmHelper.connectAsUser(user.getUserId(), user.getPassword());
			
			if (processInstanceId != -1)
				pi = WFObjectFactory.getProcessInstance(processInstanceId, wfSession);	
		} finally
		{
			if (epmHelper != null)
				epmHelper.disconnect(wfSession);
		}
		
		return pi;
	}

	public static boolean isNullString(String str) {		
		return str == null || str.length() == 0;
	}
	

	public static boolean isNullCollection(Collection coll)
	{
		return coll == null || coll.size() == 0;
	}

	public static boolean isInterviewProcessTypeDB(String processTypeCode) {
		return Constants.DB_CODE_INITIAL_INTERVIEW.equals(processTypeCode) ||
		Constants.DB_CODE_FOLLOW_UP_INTERVIEW.equals(processTypeCode) ||
		Constants.DB_CODE_INTERVIEW_COMMON.equals(processTypeCode) ||
		Constants.DB_CODE_DOCUMENT_DECISION.equals(processTypeCode);
	}

	public static boolean isSubProcessTypeDB(String processTypeCode) {
		return Constants.DB_CODE_INTERVIEW_COMMON.equals(processTypeCode) ||
		Constants.DB_CODE_DOCUMENT_DECISION.equals(processTypeCode) ||
		Constants.DB_CODE_ACMI_QUALITY_ASSURANCE.equals(processTypeCode) ||
		Constants.DB_CODE_ACMI_MANUAL_ACTIVATE.equals(processTypeCode);
	}
	

    /**
     * This method gets the top-level process id from one of the sub-processes process id.
     * 
     * @param processInstanceId
     * @param bpmHelper
     * @return
     * @throws Exception
     */
    public static long getTopInterviewProcessId(long processInstanceId, WFAdminSession wfAdminSession) throws Exception {	
    	EPMHelper epmHelper = new EPMHelper();
        
        try {
			ProcessInstance procInstance = epmHelper.getProcessInstance(processInstanceId, wfAdminSession);
			
			if (procInstance == null)
				return -1;
			else
			{		
				String templateName = procInstance.getPlanName();
				
				if (!isInterviewProcess(templateName))
					return -1;
				else if (isInterviewTopProcess(templateName))
					return procInstance.getId();
				else { //it is a telephonic-interview sub-process...
				   long parentProcessInstanceId = procInstance.getParentProcessId();
				   return getTopInterviewProcessId(parentProcessInstanceId, wfAdminSession);
				}
			}
        } catch (Exception e)
        {
        	return -1;
        }
	}
    

	public static IuatelephonicInterview getInterviewForPID(long pid) {
		log.debug("getInterviewForPID(" + pid + ") - Start");
		Query query = BaseHibernateDAO.getSession().createQuery(INTERVIEW_BY_PID_SQL);
		query.setLong(PID_PARAM, pid);
		List results = query.list();		
		log.debug("got " + results.size() + " interview requests");
		log.debug("getInterviewForPID() - End");
		
		if (!isNullCollection(results))
		{
			IuatelephonicInterview interview = (IuatelephonicInterview)results.get(0);
			return interview;
		} else
			return null;
	}
	/**
	 * Returns true if the process might belong to an interview process,
	 * or one of its sub-processes.
	 * 
	 * There are four templates that might be an interview process template
	 * 
	 * @param planName
	 * @return
	 */
	public static boolean isInterviewProcess(String planName)
	{
		if (planName == null)
			return false;
		else
			return (planName.equals(Constants.EPM_CODE_INITIAL_INTERVIEW)) ||
			(planName.equals(Constants.EPM_CODE_FOLLOW_UP_INTERVIEW)) ||
			(planName.equals(Constants.EPM_CODE_INTERVIEW_COMMON)) ||
			(planName.equals(Constants.EPM_CODE_DOCUMENT_DECISION));
	}
	
	public static boolean isInterviewTopProcess(String planName)
	{
		if (planName == null)
			return false;
		else
			return (planName.equals(Constants.EPM_CODE_INITIAL_INTERVIEW)) ||
			(planName.equals(Constants.EPM_CODE_FOLLOW_UP_INTERVIEW));
	}
	
	public static String[] getRoleStrings(String roles)
	{
		log.debug(roles);
		String[] result = new String[2];	
		result[0] = NULL_STRING;
		result[1] = NULL_STRING;
		
		StringTokenizer token = new StringTokenizer(roles, COMMA);
		
		int currentIndex = 0;
		int currentLength = 0;
		
		//Get a new Buffer for the first string
		StringBuffer buff = new StringBuffer();
		
		while (token.hasMoreTokens())
		{
			String tok = token.nextToken();
			currentLength = currentLength + tok.length() + 1;
			
			if ((currentLength > MAX_ROLE_STR_LENGTH) && (currentIndex == 0))
			{
				//Get a new buffer for the second string...
				result[currentIndex] = buff.toString();
				buff = new StringBuffer();
				currentIndex++;
			}
			
			buff.append(tok);
			
			if (token.hasMoreTokens())
				buff.append(COMMA);
		}
		
		result[currentIndex] = buff.toString();
		return result;
	}
	
	public static boolean isPastDate(Date myDate) throws ParseException
	{	
		long nowJustDateMillis = getJustDateMillis(new Date());
		long myDateJustDateMillis = getJustDateMillis(myDate);		
		return myDateJustDateMillis < nowJustDateMillis;
	}
		
	public static long getJustDateMillis(Date myDate) throws ParseException	{
		String myDateJustDate = dateFormat.format(myDate);
		return dateFormat.parse(myDateJustDate).getTime();		
	}
	
	public static void main(String[] args) throws ParseException
	{
		Date longAgo = dateFormat.parse("7/16/2000");
		Date yesterday = dateFormat.parse("7/15/2008");
		Date today = dateFormat.parse("7/16/2008");
		Date tomorrow = dateFormat.parse("7/17/2008");
		Date longAfter = dateFormat.parse("7/16/2058");

		System.out.println(isPastDate(longAgo));
		System.out.println(isPastDate(yesterday));
		System.out.println(isPastDate(today));
		System.out.println(isPastDate(tomorrow));
		System.out.println(isPastDate(longAfter));
	}
	
	public static Logger getIASLogger() {
		
		return Logger.getLogger("IASLog");
	}
}
