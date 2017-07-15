package com.epm.acmi.struts.form.dsp;

import com.cc.framework.common.DisplayObject;
import com.epm.acmi.struts.Constants;

public class TInterviewDisp implements DisplayObject {

	private long interviewRequestId;
	private String fullName;
	private String firstName;
	private String lastName;
	private String mi;
	private String suffix;
    private String reqDate;
    private String appType;
    private String status;
    private String requestedBy;
    private String applicantID;
    private String requestByID;
    private boolean editable;
    
    public long getInterviewRequestId() {
    	return interviewRequestId;
    }
    public void setInterviewRequestId(long id) {
    	interviewRequestId = id;
    }
	public String getApplicantID() {
		return applicantID;
	}
	public void setApplicantID(String applicantID) {
		this.applicantID = applicantID;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String name){
		fullName = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getRequestByID() {
		return requestByID;
	}
	public void setRequestByID(String reqByID) {
		requestByID = reqByID;
	}
	public String getRequestedBy() {
		return requestedBy; 
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMi() {
		return mi;
	}
	public void setMi(String mi) {
		this.mi = mi;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public boolean getEditable() {
		if (status.equalsIgnoreCase(Constants.INTERVIEW_REQUEST_STATUS_NEW))
			return true;
		
		return false;
	}
	
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
}
