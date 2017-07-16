package com.epm.acmi.struts.form.dsp;

import com.cc.framework.common.DisplayObject;
import com.epm.acmi.util.IUAUtils;

/**
 * Role DisplayObject (ViewHelper)
 * author: Dragos 
 */
public class MedRecDisp implements DisplayObject {

	private String fullName;
    private String reqDate;
    private String appType;
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
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = IUAUtils.getAppStatusStringfromCode(appType);
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String firstName, String middleInitial, String lastName, String suffix) {
		this.fullName = firstName + " " + middleInitial + " " + lastName + " " + suffix;
	}
	public String getPhiMissing() {
		return phiMissing;
	}
	public void setPhiMissing(String phiMissing) {
		this.phiMissing = phiMissing;
	}
	public String getPhysicianLocated() {
		return physicianLocated;
	}
	public void setPhysicianLocated(String physicianLocated) {
		this.physicianLocated = physicianLocated;
	}
	public String getPhysicianName() {
		return physicianName;
	}
	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
    
    
}
