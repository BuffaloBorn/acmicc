package com.epm.acmi.struts.form;

import com.cc.framework.adapter.struts.FWActionForm;


public class AdminForm extends FWActionForm {
	
	private String refreshAcmic, refreshEPM, refreshIAS;
	private static final long serialVersionUID = 0L;
	
	public void setRefreshAcmic(String acmic) {
		refreshAcmic = acmic;
	}
	
	public String getRefreshAcmic() {
		return refreshAcmic;
	}

	public void setRefreshEPM(String epm) {
		refreshEPM = epm;
	}
	
	public String getRefreshEPM() {
		return refreshEPM;
	}

	public String getRefreshIAS() {
		return refreshIAS;
	}

	public void setRefreshIAS(String refreshIAS) {
		this.refreshIAS = refreshIAS;
	}
}
