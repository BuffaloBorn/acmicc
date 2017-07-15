package com.cc.acmi.presentation.dsp;

public class PolicyEventsBean {
	
	private String DELIVERY_REQ = "";
	private String KEY_INSURED = "";
	private String KEY_PRODUCT_ID = "";
    private String UNDERW_DESCRIPTION= "";
    private String UNDERWRITER= "";
    private String RIDER_IND= "";
    private String AMENDMENT_IND= "";
    private String NOTES_IND= "";
    private String DECISION_DATE= "";
    private String POLICY_ID= "";
    

   public PolicyEventsBean() {
	   super();
	// TODO Auto-generated constructor stub
   }

public String getDELIVERY_REQ() {
	return DELIVERY_REQ;
}

public void setDELIVERY_REQ(String delivery_req) {
	DELIVERY_REQ = delivery_req;
}

public String getKEY_INSURED() {
	return KEY_INSURED;
}

public void setKEY_INSURED(String key_insured) {
	KEY_INSURED = key_insured;
}

public String getKEY_PRODUCT_ID() {
	return KEY_PRODUCT_ID;
}

public void setKEY_PRODUCT_ID(String key_product_id) {
	KEY_PRODUCT_ID = key_product_id;
}

public String getUNDERW_DESCRIPTION() {
	return UNDERW_DESCRIPTION;
}

public void setUNDERW_DESCRIPTION(String underw_description) {
	UNDERW_DESCRIPTION = underw_description;
}

public String getUNDERWRITER() {
	return UNDERWRITER;
}

public void setUNDERWRITER(String underwriter) {
	UNDERWRITER = underwriter;
}

public String getRIDER_IND() {
	return RIDER_IND;
}

public void setRIDER_IND(String rider_ind) {
	RIDER_IND = rider_ind;
}

public String getAMENDMENT_IND() {
	return AMENDMENT_IND;
}

public void setAMENDMENT_IND(String amendment_ind) {
	AMENDMENT_IND = amendment_ind;
}

public String getNOTES_IND() {
	return NOTES_IND;
}

public void setNOTES_IND(String notes_ind) {
	NOTES_IND = notes_ind;
}

public String getDECISION_DATE() {
	return DECISION_DATE;
}

public void setDECISION_DATE(String decision_date) {
	DECISION_DATE = decision_date;
}


public String getPOLICY_ID() {
	return POLICY_ID;
}

public void setPOLICY_ID(String policy_id) {
	POLICY_ID = policy_id;
}

public PolicyEventsBean(String delivery_req, String key_insured,
		String key_product_id, String underw_description, String underwriter,
		String rider_ind, String amendment_ind, String notes_ind,
		String decision_date, String policy_id) {
	super();
	this.DELIVERY_REQ = delivery_req;
	this.KEY_INSURED = key_insured;
	this.KEY_PRODUCT_ID = key_product_id;
	this.UNDERW_DESCRIPTION = underw_description;
	this.UNDERWRITER = underwriter;
	this.RIDER_IND = rider_ind;
	this.AMENDMENT_IND = amendment_ind;
	this.NOTES_IND = notes_ind;
	this.DECISION_DATE = decision_date;
	this.POLICY_ID = policy_id;
}
    
    
    

}
