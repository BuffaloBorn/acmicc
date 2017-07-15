package com.cc.acmi.presentation.dsp;

import com.cc.framework.common.DisplayObject;

public class MemoCodesDsp implements DisplayObject {
	
	private java.lang.String MEMO_ID;

    private java.lang.String DESCRIPTION;

    private java.lang.String ATTACHMENT_IND;

    private java.lang.String APPLICATION_FORM_ID;

    private java.lang.String EFF_DT_REQ_IND;

	public java.lang.String getMEMO_ID() {
		return MEMO_ID;
	}

	public void setMEMO_ID(java.lang.String memo_id) {
		MEMO_ID = memo_id;
	}

	public java.lang.String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public void setDESCRIPTION(java.lang.String description) {
		DESCRIPTION = description;
	}

	public java.lang.String getATTACHMENT_IND() {
		return ATTACHMENT_IND;
	}

	public void setATTACHMENT_IND(java.lang.String attachment_ind) {
		ATTACHMENT_IND = attachment_ind;
	}

	public java.lang.String getAPPLICATION_FORM_ID() {
		return APPLICATION_FORM_ID;
	}

	public void setAPPLICATION_FORM_ID(java.lang.String application_form_id) {
		APPLICATION_FORM_ID = application_form_id;
	}

	public java.lang.String getEFF_DT_REQ_IND() {
		return EFF_DT_REQ_IND;
	}

	public void setEFF_DT_REQ_IND(java.lang.String eff_dt_req_ind) {
		EFF_DT_REQ_IND = eff_dt_req_ind;
	}

	public MemoCodesDsp() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemoCodesDsp(String memo_id, String description,	String attachment_ind, String application_form_id,	String eff_dt_req_ind) 
	{
		super();
		MEMO_ID = memo_id;
		DESCRIPTION = description;
		ATTACHMENT_IND = attachment_ind;
		APPLICATION_FORM_ID = application_form_id;
		EFF_DT_REQ_IND = eff_dt_req_ind;
	}

}
