package com.epm.acmi.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import com.cc.framework.common.DisplayObject;

public class AcmiWorkItemBean implements DisplayObject ,Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	protected String taskName="";

	protected long taskId;

	protected String keyApplicantName;

	protected String state="";

	protected Date ApplicationDate;

	protected String processName="";

	protected String status="";

	protected Date createdDate;

	protected Date dueDate;

	protected String assignee="";

	protected String underWriter="";

	protected String listBill="";

	protected String policyNumber="";

	protected String createdDateString;

	protected String acceptLabel = new String("Select");

	//USR 7930
	protected String taskEapp="";
	//End USR7930
	
	//USR 8399-1
	protected String taskAgent="";
	//End USR8399-1


	public AcmiWorkItemBean() {
		super();

	}

	public Date getApplicationDate() {
		return ApplicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		ApplicationDate = applicationDate;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
		this.createdDateString = DateFormat.getTimeInstance().format(createdDate);
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getKeyApplicantName() {
		return keyApplicantName;
	}

	public void setKeyApplicantName(String keyApplicantName) {
		this.keyApplicantName = keyApplicantName;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getUnderWriter() {
		return underWriter;
	}

	public void setUnderWriter(String underWriter) {
		this.underWriter = underWriter;
	}

	public String getListBill() {
		return listBill;
	}

	public void setListBill(String listBill) {
		this.listBill = listBill;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getCreatedDateString() {
		return createdDateString;
	}

	public void setCreatedDateString(String createdDateString) {
		this.createdDateString = createdDateString;
	}

	public String getAcceptLabel() {
		return acceptLabel;
	}

	public void setAcceptLabel(String acceptLabel) {
		this.acceptLabel = acceptLabel;
	}


	//USR 7930
	public String getTaskEapp() {
		return taskEapp;
	}

	public void setTaskEapp(String taskEapp) {
		this.taskEapp = taskEapp;
	}
	
	//End USR 7930
   
	//	USR 8399-1
	public String getTaskAgent() {
		return taskAgent;
	}

	public void setTaskAgent(String taskAgent) {
		this.taskAgent = taskAgent;
	}
	//End of 8399-1
}
