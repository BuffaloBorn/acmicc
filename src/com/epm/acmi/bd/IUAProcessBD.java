package com.epm.acmi.bd;

import java.util.*;

import com.epm.acmi.hbm.*;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.dsp.*;
import com.epm.acmi.util.IUAUtils;
import com.cc.framework.common.DisplayObject;
import com.cc.framework.ui.model.ListDataModel;

public class IUAProcessBD {

	private static final String IUAAPPLICATION = "iuaapplication";


	/**
	 * Retrieves a list of interview requests from a GFID
	 * @param gfid
	 * @return
	 */
	public TelInterviewList getTelInterviewRequests(String gfid) {

		TelInterviewList interList = new TelInterviewList();
		IuainterviewRequestDAO interviewReqDAO = new IuainterviewRequestDAO();
		IuaapplicationDAO appDAO = new IuaapplicationDAO();
		Iuaapplication app = appDAO.findById(gfid);
		IuaapplicationDAO.close();

		List reqList = interviewReqDAO.findByProperty(IUAAPPLICATION, app);

		if (reqList != null) {

			Iterator inteviewReqItr = reqList.iterator();

			ArrayList list = new ArrayList();
			while (inteviewReqItr.hasNext()) {
				IuainterviewRequest interviewReq = (IuainterviewRequest)inteviewReqItr.next();
				DisplayObject obj = getInterviewDisplayObject(interviewReq);
				list.add(obj);
			}

			if (list.size() > 0) {
				Object[] dispList = list.toArray(new TInterviewDisp[list.size()]);
				interList.setData((TInterviewDisp[])dispList);
			}
			else {
				TInterviewDisp[] dispList = new TInterviewDisp[0];
				interList.setData(dispList);
			}
			IuainterviewRequestDAO.close();
			return interList;
		}
		return null;
	}

	/**
	 * Retrieves the display object for an interview request
	 * @param interviewRequest
	 * @return
	 */
	public TInterviewDisp getInterviewDisplayObject(IuainterviewRequest interviewRequest) {

		TInterviewDisp interviewDisp = null;

		if (interviewRequest != null) {
			interviewDisp = new TInterviewDisp();
			interviewDisp.setInterviewRequestId(interviewRequest.getInterviewRequestId().longValue());
			interviewDisp.setFullName(interviewRequest.getApplicantName());
			interviewDisp.setFirstName(interviewRequest.getApplicantFirstName());
			interviewDisp.setLastName(interviewRequest.getApplicantLastName());
			interviewDisp.setMi(interviewRequest.getApplicantMi());
			interviewDisp.setSuffix(interviewRequest.getApplicantSuffix());	
			
			if (interviewRequest.getRequestedDate() != null)
				interviewDisp.setReqDate(IUAUtils.convertDateToString(interviewRequest.getRequestedDate()));
			
			interviewDisp.setAppType(IUAUtils.getAppStatusStringfromCode(interviewRequest.getApplicantType()));
			Iuauser user = interviewRequest.getIuauserByInitialInterviewUw();
			
			if (user != null)
			{
				interviewDisp.setRequestByID(user.getEmployeeid());
				interviewDisp.setRequestedBy(user.getEmployeename());
			}
			
			interviewDisp.setStatus(interviewRequest.getInterviewRequestStatus());
		}
		return interviewDisp;
	}

	/**
	 * Edits an interview request in the DB from interview request id and data.
	 * 
	 * @param interviewRequestId
	 * @param appName
	 * @param firstName
	 * @param lastName
	 * @param mi
	 * @param suffix
	 * @param uwName
	 * @param reqDate
	 * @param type
	 */
	public IuainterviewRequest edit(long interviewRequestId, String appName, String firstName, String lastName, String mi, String suffix, String uwName, String reqDate, String type) {

		IuainterviewRequestDAO requestDAO = new IuainterviewRequestDAO();
		IuainterviewRequest request = requestDAO.findById(new Long(interviewRequestId));

		if (request != null)
		{
			request.setApplicantName(appName);
			request.setApplicantFirstName(firstName);
			request.setApplicantLastName(lastName);
			request.setApplicantMi(mi);
			request.setApplicantSuffix(suffix);
			request.setApplicantType(type);

			try {
				request.setRequestedDate(IUAUtils.convertStringToDate(reqDate));
			}catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			
			//uwName is in the form of userid|fullName
			//need to create Iuauser objects
			Iuauser user = IUAUtils.getUserFromIDName(uwName);
			request.setIuauserByInitialInterviewUw(user);

			//Update the object
			BaseHibernateDAO.getSession().update(request);
			BaseHibernateDAO.getSession().flush();
			IuainterviewRequestDAO.close();
		}

		return request;
	}
	
	/**
	 * Saves an interview request to DB based on attribute data
	 * 
	 * @param gfid
	 * @param appName
	 * @param firstName
	 * @param lastName
	 * @param mi
	 * @param suffix
	 * @param uwName
	 * @param reqDate
	 * @param type
	 */
	public IuainterviewRequest save(String gfid, String appName, String firstName, String lastName, String mi, String suffix, String uwName, String reqDate, String type) {

		IuaapplicationDAO appDAO = new IuaapplicationDAO();
		Iuaapplication app = appDAO.findById(gfid);
		IuaapplicationDAO.close();

		IuainterviewRequest request = new IuainterviewRequest();
		request.setApplicantName(appName);
		request.setApplicantFirstName(firstName);
		request.setApplicantLastName(lastName);
		request.setApplicantMi(mi);
		request.setApplicantSuffix(suffix);
		request.setApplicantType(type);

		try {
			request.setRequestedDate(IUAUtils.convertStringToDate(reqDate));
		}catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		request.setInterviewRequestStatus(Constants.INTERVIEW_REQUEST_STATUS_NEW);
		request.setIuaapplication(app);

		//uwName is in the form of userid|fullName
		//need to create Iuauser objects
		Iuauser user = IUAUtils.getUserFromIDName(uwName);
		request.setIuauserByInitialInterviewUw(user);

		//Save the object
		IuainterviewRequestDAO dao = new IuainterviewRequestDAO();
		dao.save(request);
		IuainterviewRequestDAO.close();
		return request;
	}

	/**
	 * Deletes an interview request from an interview request id
	 * 
	 * @param requestIdStr
	 */
	public void deleteRequest(String requestIdStr) {

		if (requestIdStr != null) {
			//check if it's numeric
			Long requestId = null;
			
			try {
				requestId = new Long(requestIdStr);
			}
			catch (NumberFormatException e) {
				requestId = new Long(0);
				e.printStackTrace();
			}

			IuainterviewRequest request = getInterviewRequest(requestId);
			IuainterviewRequestDAO interviewDAO = new IuainterviewRequestDAO();
			interviewDAO.delete(request);
			IuainterviewRequestDAO.close();
		}

	}

	/**
	 * Edits
	 * @param requestIdStr
	 * @return
	 */
	public IuainterviewRequest getInterviewRequestForEdit(String requestIdStr) {
		if (requestIdStr != null) {
			//check if it's numeric
			Long requestId = new Long(0);
			try {
				requestId = new Long(requestIdStr);
			}
			catch (NumberFormatException e) {
				e.printStackTrace();
			}

			IuainterviewRequest request = getInterviewRequest(requestId);
			return request;
		}

		return null;
	}

	/**
	 * Retrieves an interview request object from an interview request id.
	 * 
	 * @param requestId
	 * @return
	 */
	public IuainterviewRequest getInterviewRequest(Long requestId) {

		IuainterviewRequestDAO requestDAO = null;

		try {
			requestDAO = new IuainterviewRequestDAO();
			IuainterviewRequest request = requestDAO.findById(requestId);
			return request;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			IuainterviewRequestDAO.close();
		}

		return null;
	}

	/**
	 * Gets the telephonic interview request display object corresponding to a request id
	 * from the model list
	 * @param intList
	 * @param requestId
	 * @return
	 */
	public TInterviewDisp getInterviewDisp(TelInterviewList intList, String requestId) {

		if (requestId == null || intList == null) {
			return null;
		}

		for (int i=0; i < intList.size(); i++) {
			String key = intList.getUniqueKey(i);
			if (key.equalsIgnoreCase(requestId)) {
				return (TInterviewDisp)intList.getElementAt(i);
			}
		}
		return null;
	}

	/**
	 * Checks for pending requests by checking the request status for the 
	 * display object that has the same name as the appName parameter.
	 * 
	 * @param appName
	 * @param model
	 * @return
	 */
	public boolean checkForPendingRequests(String appName, ListDataModel model) {

		if (model != null && appName != null) {
			for (int i=0; i < model.size(); i++) {
				TInterviewDisp disp = (TInterviewDisp)model.getElementAt(i);
				String applicantName = disp.getFullName();
				
				if (applicantName.equalsIgnoreCase(appName)) {
					//check for request status
					String status = disp.getStatus();
					if (!status.equalsIgnoreCase(Constants.INTERVIEW_REQUEST_STATUS_FINISHED)) {
						return true;
					}
					else {
						return false;
					}

				}
			}
		}
		return false;
	}


	/**
	 * Using the fact that there is at most one interview attached to an interview request not having a 
	 * process attached to it, we go through the interviewes referencing the interview request,  and we 
	 * retrieve that interview, if there is any.
	 * 
	 * @param interviewRequest
	 * @return
	 */
	public static IuatelephonicInterview getExistingFollowUpInterview(IuainterviewRequest interviewRequest) {
		IuatelephonicInterview followUpInterview = null;

		Iterator interviews = interviewRequest.getIuatelephonicInterviews().iterator();

		while (interviews.hasNext())
		{
			IuatelephonicInterview nextInterview = (IuatelephonicInterview)interviews.next();

			if (nextInterview.getIuaappDocProcesses() == null)
			{
				followUpInterview = nextInterview;
				break;
			}
		}

		return followUpInterview;
	}
}
