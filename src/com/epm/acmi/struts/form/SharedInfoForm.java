package com.epm.acmi.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * Shared Info Form
 * @author usaho
 *
 */
public class SharedInfoForm extends FWActionForm
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public SharedInfoForm()
    {
    }


    public void reset(ActionMapping actionMapping, HttpServletRequest request)
    {
    	checkboxRCA = false;
    	checkboxADEC = false;
    	checkboxDNP = false;
    	checkboxRPR = false;
    	checkboxSIOT = false;
    }

    protected boolean checkboxRCA;
    protected boolean checkboxADEC;
    protected boolean checkboxDNP;
    protected boolean checkboxRPR;
    protected boolean checkboxSIOT;
    
    protected String  rca;
    protected String  adec;
    protected String dnp;
    protected String rpr;
    protected String siot;
    
    
	public String getAdec() {
		return adec;
	}


	public void setAdec(String adec) {
		this.adec = adec;
		if (!adec.trim().equals("")) {
			this.setCheckboxADEC(true);		
		} 
		else
		{
			this.setCheckboxADEC(false);
		}
		//		Fix to issue #67 cmontero 03/28/2006
		if (adec.trim().equals("isChecked")) {
			this.adec="";
		}
		//		End of Fix
	}


	public boolean isCheckboxADEC() {
		return checkboxADEC;
	}


	public void setCheckboxADEC(boolean checkboxADEC) {
		this.checkboxADEC = checkboxADEC;
	}


	public boolean isCheckboxDNP() {
	//	System.err.println("HELLO HELLO FROM setCheckboxDNP to:" + checkboxDNP);
		return checkboxDNP;
	}


	public void setCheckboxDNP(boolean checkboxDNP) {
		this.checkboxDNP = checkboxDNP;
	}


	public boolean isCheckboxRCA() {
		return checkboxRCA;
	}


	public void setCheckboxRCA(boolean checkboxRCA) {
	//	System.err.println("HELLO HELLO FROM setCheckboxRCA to:" + checkboxRCA);
		this.checkboxRCA = checkboxRCA;
	}


	public boolean isCheckboxRPR() {
		return checkboxRPR;
	}


	public void setCheckboxRPR(boolean checkboxRPR) {
		this.checkboxRPR = checkboxRPR;
	}


	public boolean isCheckboxSIOT() {
		return checkboxSIOT;
	}


	public void setCheckboxSIOT(boolean checkboxSIOT) {
		this.checkboxSIOT = checkboxSIOT;
	}


	public String getDnp() {
		return dnp;
	}


	public void setDnp(String dnp) {
	//	System.err.println("setDNP: '"+dnp+"'");
		this.dnp = dnp;

		if (!dnp.trim().equals("")) {
			this.setCheckboxDNP(true);		
		} 
		
		//		Fix to issue #67 cmontero 03/28/2006		
		if (dnp.trim().equals("isChecked")) {
			this.dnp="";
		}
		//		End of Fix
	}


	public String getRca() {
		return rca;
	}


	public void setRca(String rca) {
	//System.err.println("setRCA: '"+rca+"'");
		this.rca = rca;

		if (!rca.trim().equals("")) {
			this.setCheckboxRCA(true);		
		} 
		else
		{
			this.setCheckboxRCA(false);
		}
		//		Fix to issue #67 cmontero 03/28/2006		
		if (rca.trim().equals("isChecked")) {
			this.rca="";
		}
		//		End of Fix
	}


	public String getRpr() {
		return rpr;
	}


	public void setRpr(String rpr) {
		this.rpr = rpr;

		if (!rpr.trim().equals("")) {

			this.setCheckboxRPR(true);		
		} 
		else
		{
			this.setCheckboxRPR(false);
		}
		//		Fix to issue #67 cmontero 03/28/2006
		if (rpr.trim().equals("isChecked")) {
			this.rpr="";
		}
		//		End of Fix
	}


	public String getSiot() {
		return siot;
	}


	public void setSiot(String siot) {
		this.siot = siot;

		if (!siot.trim().equals("")) {
			this.setCheckboxSIOT(true);		
		} 
		
		//		Fix to issue #67 cmontero 03/28/2006		
		if (siot.trim().equals("isChecked")) {
			this.siot="";
		}
		//		End of Fix
	}	
	
}