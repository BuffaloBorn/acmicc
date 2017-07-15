package com.epm.acmi.struts.form;

import java.util.TreeMap;

import com.cc.framework.adapter.struts.FWActionForm;
import com.epm.acmi.util.ACMICache;

public class StandardEventCodesForm extends FWActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String stdEventCode = "";
	private String hiddenfield = "";

	
	public TreeMap getstdEventCodeOptions()
	{
		return ACMICache.getStdEventCodes();
	}
	

	public String getStdEventCode() {
		return stdEventCode;
	}


	public void setStdEventCode(String stdEventCode) {
		this.stdEventCode = stdEventCode;
	}


	public String getHiddenfield() {
		return hiddenfield;
	}


	public void setHiddenfield(String hiddenfield) {
		this.hiddenfield = hiddenfield;
	}
	
	
	
}
