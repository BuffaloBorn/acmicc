package com.epm.acmi.struts.form;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * Previous Policy Form class
 * @author Dragos
 *
 */
public class PrevPolicyForm extends FWActionForm
{

    private static final long serialVersionUID = 3254322345435L;
    
    private String insuredName;
    private String previousPolicyID;
    private String status;
    private String ptd;
    private String effDate;
    private String shortTermPolicy;  
    
    
   	public String getShortTermPolicy() {
		return shortTermPolicy;
	}

	public void setShortTermPolicy(String shortTermPolicy) {
		this.shortTermPolicy = shortTermPolicy;
	}		

	public String getInsuredName() {
		return insuredName;
	}

	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}

	public String getPreviousPolicyID() {
		return previousPolicyID;
	}

	public void setPreviousPolicyID(String previousPolicyID) {
		this.previousPolicyID = previousPolicyID;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getPtd() {
		return ptd;
	}

	public void setPtd(String ptd) {
		this.ptd = ptd;
	}

	public String getStatus() {
		return status; 
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public PrevPolicyForm()
    {		
    }

}
