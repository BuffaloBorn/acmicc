package com.epm.acmi.struts.form.dsp;

public class ApplicationVO {
	public static final String NULL_STRING = "";
	private String gfid = NULL_STRING;
	private String policyNumber = NULL_STRING;
	private String keyApplicantFirstName = NULL_STRING;
	private String keyApplicantLastName = NULL_STRING;
	private String keyApplicantMI = NULL_STRING;
	private String keyApplicantSuffix = NULL_STRING;
	private String agentName = NULL_STRING;
	private String agentNumber = NULL_STRING;
	private String listBill = NULL_STRING;
	private String productName = NULL_STRING;
	private String state = NULL_STRING;
	
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentNumber() {
		return agentNumber;
	}
	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}
	public String getKeyApplicantFirstName() {
		return keyApplicantFirstName;
	}
	public void setKeyApplicantFirstName(String keyApplicantFirstName) {
		this.keyApplicantFirstName = keyApplicantFirstName;
	}
	public String getKeyApplicantLastName() {
		return keyApplicantLastName;
	}
	public void setKeyApplicantLastName(String keyApplicantLastName) {
		this.keyApplicantLastName = keyApplicantLastName;
	}
	public String getKeyApplicantMI() {
		return keyApplicantMI;
	}
	public void setKeyApplicantMI(String keyApplicantMI) {
		this.keyApplicantMI = keyApplicantMI;
	}
	public String getKeyApplicantSuffix() {
		return keyApplicantSuffix;
	}
	public void setKeyApplicantSuffix(String keyApplicantSuffix) {
		this.keyApplicantSuffix = keyApplicantSuffix;
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
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getGfid() {
		return gfid;
	}
	public void setGfid(String gfid) {
		this.gfid = gfid;
	}
	
	public String toString()
	{		return "gfid = " + gfid + 
	", policyNumber = " + policyNumber + 
	", keyApplicantFirstName " + keyApplicantFirstName +  
	", keyApplicantLastName = " + keyApplicantLastName  + 
	", keyApplicantMI = " + keyApplicantMI + 
	", keyApplicantSuffix = " + keyApplicantSuffix + 
	", agentName = " + agentName + 
	", agentNumber = " + agentNumber + 
	", listBill = " + listBill + 
	", productName = " + productName +
	", state = " + state;
	}
}
