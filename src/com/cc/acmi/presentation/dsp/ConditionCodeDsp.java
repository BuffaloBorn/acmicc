package com.cc.acmi.presentation.dsp;

import com.cc.framework.common.DisplayObject;

public class ConditionCodeDsp implements DisplayObject {

	private String CODE="";
    private String DESCRIPTION="";
    private String DESCRIPTION_REQUIRED_IND="";
	private String POSTION="";
    
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
	
	public ConditionCodeDsp() {
		super();
	}
	
	public String getPOSTION() {
		return POSTION;
	}
	public void setPOSTION(String postion) {
		POSTION = postion;
	}
	
	public ConditionCodeDsp(String code, String description,
			String description_required_ind, String postion) {
		super();
		CODE = code;
		DESCRIPTION = description;
		DESCRIPTION_REQUIRED_IND = description_required_ind;
		POSTION = postion;
	}
    
}
