package com.epm.acmi.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * Medical Records Form
 * @author Dragos
 *
 */
public class MedRecForm extends FWActionForm
{

    private static final long serialVersionUID = 3345435L;
    
    private String firstName;
    private String lastName;
    private String initialMid;
    private String suffix;
    private String applicantType;
    private String requestDate;
    private String physicianName;
    private String physicianLocated;
    private String phiMissing;
    private String applicantID;
       
   
	public String getApplicantID() {
		return applicantID;
	}


	public void setApplicantID(String applicantID) {
		this.applicantID = applicantID;
	}


	public String getApplicantType() {
		return applicantType;
	}


	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}



	public String getPhiMissing() {
		return phiMissing;
	}

	
	public String getPhiMissingForDB() {
		if (this.phiMissing != null && this.phiMissing.equals("on"))
			return "Y";
		else return "N";
	}

	public void setPhiMissing(String phiMissing) {		
		this.phiMissing = phiMissing;
	}
	
	public void setPhiMissingFromDB(String phiMissing) {
		if (phiMissing == null || !phiMissing.equals("Y"))
			this.phiMissing = null;
		else this.phiMissing = "on";
	}

	public String getPhysicianLocated() {
		return physicianLocated;
	}
	
	public String getPhysicianLocatedForDB() {
		if (this.physicianLocated != null && this.physicianLocated.equals("on"))
			return "Y";
		else return "N";	
	}


	public void setPhysicianLocated(String physicianLocated) {
		this.physicianLocated = "";
		this.physicianLocated = physicianLocated;
	}
	
	public void setPhysicianLocatedFromDB(String physicianLocated) {
		if (physicianLocated == null || !physicianLocated.equals("Y"))
			this.physicianLocated = null;
		else this.physicianLocated = "on";
	}


	public String getPhysicianName() {
		return physicianName;
	}


	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}


	public String getRequestDate() {
		return requestDate;
	}


	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}


	public String getSuffix() {
		return suffix;
	}


	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public MedRecForm() {  		
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getInitialMid() {
		return initialMid;
	}


	public void setInitialMid(String initialMid) {
		this.initialMid = initialMid;
	}


	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.phiMissing = "";
		this.physicianLocated = "";
	}


	
	
    
   	
}
