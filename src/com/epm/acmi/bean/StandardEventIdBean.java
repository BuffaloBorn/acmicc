package com.epm.acmi.bean;

public class StandardEventIdBean {

	private String CODE="";
    private String DESCRIPTION="";
	private String SCRNAME = "";
	
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
	public String getSCRNAME() {
		return SCRNAME;
	}
	public void setSCRNAME(String scrname) {
		SCRNAME = scrname;
	}
	public StandardEventIdBean(String code, String description, String scrname) {
		super();
		CODE = code;
		DESCRIPTION = description;
		SCRNAME = scrname;
	}
	
	
    
}
