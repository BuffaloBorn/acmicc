package com.epm.acmi.bean;

import java.util.Date;

import com.cc.framework.common.DisplayObject;

public class ReassignTaskBean implements DisplayObject {
	
	protected String taskName;
	protected long taskId;
	protected String keyApplicantName;
	protected String state;
	protected String processName;
	protected String status;
	protected Date createdDate;
	protected String assignee;
	protected String underWriter;
	protected String policyNumber;
	protected String createdDateString;
	protected String userId;
	protected String role;
	protected boolean checkState;

	public ReassignTaskBean() {
		super();
		
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(boolean checkState) {
		this.checkState = checkState;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		this.createdDateString = createdDate.toLocaleString();
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public String getKeyApplicantName() {
		return keyApplicantName;
	}

	public void setKeyApplicantName(String keyApplicantName) {
		this.keyApplicantName = keyApplicantName;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getUnderWriter() {
		return underWriter;
	}

	public void setUnderWriter(String underWriter) {
		this.underWriter = underWriter;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
