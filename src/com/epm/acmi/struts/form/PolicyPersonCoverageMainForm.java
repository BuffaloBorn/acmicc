/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.form;

import org.apache.struts.action.ActionForm;

import com.cc.framework.adapter.struts.FormActionContext;

/** 
 * MyEclipse Struts
 * Creation date: 04-03-2008
 * 
 * XDoclet definition:
 * @struts.form name="policyPersonCoverageMainForm"
 */
public class PolicyPersonCoverageMainForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String policyid;
	private String policystatus;
	private String person_effective_date;
	private String person_status;
	private String personid;
	private String policy_effective_date;
	private String name;
	private String mode;
	private String display_date;
	private String log_counter="";
	

	public String getPolicyid() {
		return policyid;
	}

	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}

	public String getPolicystatus() {
		return policystatus;
	}

	public void setPolicystatus(String policystatus) {
		this.policystatus = policystatus;
	}

	public String getPerson_effective_date() {
		return person_effective_date;
	}

	public void setPerson_effective_date(String person_effective_date) {
		this.person_effective_date = person_effective_date;
	}

	public String getPerson_status() {
		return person_status;
	}

	public void setPerson_status(String person_status) {
		this.person_status = person_status;
	}

	public String getPersonid() {
		return personid;
	}

	public void setPersonid(String personid) {
		this.personid = personid;
	}

	public String getPolicy_effective_date() {
		return policy_effective_date;
	}

	public void setPolicy_effective_date(String policy_effective_date) {
		this.policy_effective_date = policy_effective_date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getDisplay_date() {
		return display_date;
	}

	public void setDisplay_date(String display_date) {
		this.display_date = display_date;
	}

	public PolicyPersonCoverageMainForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PolicyPersonCoverageMainForm(String policyid, String policystatus,
			String status_effective_date1, String person_status,
			String personid, String status_effective_date2, String name,
			String mode, String display_date) {
		super();
		this.policyid = policyid;
		this.policystatus = policystatus;
		this.person_effective_date = status_effective_date1;
		this.person_status = person_status;
		this.personid = personid;
		this.policy_effective_date = status_effective_date2;
		this.name = name;
		this.mode = mode;
		this.display_date = display_date;
	}

	public void validateForm(FormActionContext ctx) 
	{
		
		
	}

	public String getLog_counter() {
		return log_counter;
	}

	public void setLog_counter(String log_counter) {
		this.log_counter = log_counter;
	}
	
	
}