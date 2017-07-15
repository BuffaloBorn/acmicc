package com.epm.acmi.bean;

public class ConditionCodeBean {

	private String CODE;
    private String DESCRIPTION;
    private String DESCRIPTION_REQUIRED_IND;
	
    
    public String getCODE() {
		return CODE;
	}
	public void setCODE(String code) {
		CODE = code;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String description) {
		DESCRIPTION = description;
	}
	public String getDESCRIPTION_REQUIRED_IND() {
		return DESCRIPTION_REQUIRED_IND;
	}
	public void setDESCRIPTION_REQUIRED_IND(String description_required_ind) {
		DESCRIPTION_REQUIRED_IND = description_required_ind;
	}
	public ConditionCodeBean(String code, String description,
			String description_required_ind) {
		super();
		CODE = code;
		DESCRIPTION = description;
		DESCRIPTION_REQUIRED_IND = description_required_ind;
	}
	
}
