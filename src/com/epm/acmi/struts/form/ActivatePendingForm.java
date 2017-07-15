package com.epm.acmi.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * Activate Pending Process Form class
 * @author Dragos
 *
 */
public class ActivatePendingForm extends FWActionForm
{

    private static final long serialVersionUID = 3345435L;
    
    private String policyNumber;
    public void reset(ActionMapping actionMapping, HttpServletRequest request) {
    	setPolicyNumber(null);
    }
	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
    
    
   	
}
