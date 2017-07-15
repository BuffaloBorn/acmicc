package com.epm.acmi.struts.form;

import java.math.BigDecimal;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.epm.acmi.util.ACMICache;


/**
 * Activate Pending Process Form class
 * @author Dragos
 *
 */
public class ActivateIASForm extends ActionForm
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Instance Variables
	
	
	private String KEY_INSURED = "";
	private String KEY_PRODUCT_ID = "";
    private String UNDERW_DESCRIPTION= "";
    private String UNDERWRITER= "";
    private String UNDERWRITER_OLD= "";
    private String DECISION_DATE= "";
    private String POLICY_ID="";
	private String udwselectedItem="";
	private String WARNING="";
	private String USER="";
	private BigDecimal NUMOFRIDER = new BigDecimal("0.0");
	private String ISSUED_PLACE_FLAG = "";
	private String POLICY_TRANS_TYPE="";
	private String POLICY_TRANS_TYPE_COND="";
	private String RIDER_IND ="";
	private String AMENDMENT_IND ="";

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		return null;
	}

	public TreeMap getWarningOptions()
	{
		
		TreeMap status = new TreeMap();
		
		status.put(" ", " ");
		status.put("Y", "Yes");
		status.put("N", "No");
		
		return status;
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


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	
	public TreeMap getUnderwriterOptions() 
	{
		return ACMICache.getUnderWriterStatusCodes();
	}


	public String getUdwselectedItem() {
		return udwselectedItem;
	}


	public void setUdwselectedItem(String udwselectedItem) {
		this.udwselectedItem = udwselectedItem;
	}


	public String getWARNING() {
		return WARNING;
	}


	public void setWARNING(String warning) {
		WARNING = warning;
	}

	public String getUSER() {
		return USER;
	}

	public void setUSER(String user) {
		USER = user;
	}

	public String getUNDERWRITER_OLD() {
		return UNDERWRITER_OLD;
	}

	public void setUNDERWRITER_OLD(String underwriter_old) {
		UNDERWRITER_OLD = underwriter_old;
	}

	public String getISSUED_PLACE_FLAG() {
		return ISSUED_PLACE_FLAG;
	}

	public void setISSUED_PLACE_FLAG(String issued_place_flag) {
		ISSUED_PLACE_FLAG = issued_place_flag;
	}

	public String getPOLICY_TRANS_TYPE() {
		return POLICY_TRANS_TYPE;
	}

	public void setPOLICY_TRANS_TYPE(String policy_trans_type) {
		POLICY_TRANS_TYPE = policy_trans_type;
	}

	public String getPOLICY_TRANS_TYPE_COND() {
		return POLICY_TRANS_TYPE_COND;
	}

	public void setPOLICY_TRANS_TYPE_COND(String policy_trans_type_cond) {
		POLICY_TRANS_TYPE_COND = policy_trans_type_cond;
	}

	public BigDecimal getNUMOFRIDER() {
		return NUMOFRIDER;
	}

	public void setNUMOFRIDER(BigDecimal numofrider) {
		NUMOFRIDER = numofrider;
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

	
}
