package com.epm.acmi.struts.form;

import org.apache.struts.action.ActionForm;

public class RiderCodesForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String SEARCH_CODE;

	public String getSEARCH_CODE() {
		return SEARCH_CODE;
	}

	public void setSEARCH_CODE(String search_code) {
		SEARCH_CODE = search_code;
	}
	

	public void clear()
	{
		setSEARCH_CODE("");
	}
	
	

	
}
