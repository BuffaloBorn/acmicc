package com.cc.acmi.presentation.dsp;

import com.cc.framework.common.DisplayObject;

public class PolicyDetailsDsp implements DisplayObject {
	
	private String eventid = "";

    private String eventst = "";

    private String policyapplicant = "";
 
    private String policydesc = "";

    private String policyid = "";

    private String usercode= "";
    
    /**
	 * Default Constructor for UserDsp.
	 */
	public PolicyDetailsDsp() {
		super();
	}
	
	/**
	 * Constructor for UserDsp.
	 * @param	userId	The UserId
	 * @param	firstName	The Firstname
	 * @param	lastName	The Lastname
	 * @param	role	The UserRole (Admin, Guest, ...)
	 */
	
    
	public String getEventid() {
		return eventid;
	}

	public void setEventid(String eventid) {
		this.eventid = eventid;
	}

	public String getEventst() {
		return eventst;
	}

	public void setEventst(String eventst) {
		this.eventst = eventst;
	}

	public String getPolicyapplicant() {
		return policyapplicant;
	}

	public void setPolicyapplicant(String policyapplicant) {
		this.policyapplicant = policyapplicant;
	}

	public String getPolicydesc() {
		return policydesc;
	}

	public void setPolicydesc(String policydesc) {
		this.policydesc = policydesc;
	}

	public String getPolicyid() {
		return policyid;
	}

	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}

	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public PolicyDetailsDsp(String eventid, String eventst, String policyapplicant, String policydesc, String policyid, String usercode) {
		super();
		this.eventid = eventid;
		this.eventst = eventst;
		this.policyapplicant = policyapplicant;
		this.policydesc = policydesc;
		this.policyid = policyid;
		this.usercode = usercode;
	}


}
