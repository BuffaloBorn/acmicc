package com.cc.acmi.presentation.dsp;

import java.math.BigDecimal;

import com.cc.framework.common.DisplayObject;

public class PersonCompanyBrowseDsp implements DisplayObject {

	private BigDecimal PERSON_ID;

    private String SEARCH_NAME;

    private String PERSON_TYPE;

    private String TITLE;

    private String FIRST_NAME;

    private String MIDDLE_NAME;

    private String FAMILY_NAME;

    private String HONORIFICS;

    private String NAME_SUFFIX;

    private String SEX;

    private String SMOKER_IND;

    private String BIRTH_DATE;

    private String MIB_BIRTH_PLACE;

    private String SSN_NUMBER;

    private String DRIVERS_LICENCE_ID;

    private String DRIVER_LICENCE_STATE;

    private String HEIGHT_FEET_INCHES;

    private String WEIGHT_POUNDS;

    private String OCCUPATION;

    private String EMPLOYER;

    private String EMPLOYMENT_START;

    private String MONTHLY_INCOME;

    private String BUSINESS_PHONE_NUMBER;

    private String RESIDENTIAL_PHONE;

    private String RESIDENTIAL_SEARCH_ADDRESS;

	public BigDecimal getPERSON_ID() {
		return PERSON_ID;
	}

	public void setPERSON_ID(BigDecimal person_id) {
		PERSON_ID = person_id;
	}

	public String getSEARCH_NAME() {
		return SEARCH_NAME;
	}

	public void setSEARCH_NAME(String search_name) {
		SEARCH_NAME = search_name;
	}

	public String getPERSON_TYPE() {
		return PERSON_TYPE;
	}

	public void setPERSON_TYPE(String person_type) {
		PERSON_TYPE = person_type;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String title) {
		TITLE = title;
	}

	public String getFIRST_NAME() {
		return FIRST_NAME;
	}

	public void setFIRST_NAME(String first_name) {
		FIRST_NAME = first_name;
	}

	public String getMIDDLE_NAME() {
		return MIDDLE_NAME;
	}

	public void setMIDDLE_NAME(String middle_name) {
		MIDDLE_NAME = middle_name;
	}

	public String getFAMILY_NAME() {
		return FAMILY_NAME;
	}

	public void setFAMILY_NAME(String family_name) {
		FAMILY_NAME = family_name;
	}

	public String getHONORIFICS() {
		return HONORIFICS;
	}

	public void setHONORIFICS(String honorifics) {
		HONORIFICS = honorifics;
	}

	public String getNAME_SUFFIX() {
		return NAME_SUFFIX;
	}

	public void setNAME_SUFFIX(String name_suffix) {
		NAME_SUFFIX = name_suffix;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sex) {
		SEX = sex;
	}

	public String getSMOKER_IND() {
		return SMOKER_IND;
	}

	public void setSMOKER_IND(String smoker_ind) {
		SMOKER_IND = smoker_ind;
	}

	public String getBIRTH_DATE() {
		return BIRTH_DATE;
	}

	public void setBIRTH_DATE(String birth_date) {
		BIRTH_DATE = birth_date;
	}

	public String getMIB_BIRTH_PLACE() {
		return MIB_BIRTH_PLACE;
	}

	public void setMIB_BIRTH_PLACE(String mib_birth_place) {
		MIB_BIRTH_PLACE = mib_birth_place;
	}

	public String getSSN_NUMBER() {
		return SSN_NUMBER;
	}

	public void setSSN_NUMBER(String ssn_number) {
		SSN_NUMBER = ssn_number;
	}

	public String getDRIVERS_LICENCE_ID() {
		return DRIVERS_LICENCE_ID;
	}

	public void setDRIVERS_LICENCE_ID(String drivers_licence_id) {
		DRIVERS_LICENCE_ID = drivers_licence_id;
	}

	public String getDRIVER_LICENCE_STATE() {
		return DRIVER_LICENCE_STATE;
	}

	public void setDRIVER_LICENCE_STATE(String driver_licence_state) {
		DRIVER_LICENCE_STATE = driver_licence_state;
	}

	public String getHEIGHT_FEET_INCHES() {
		return HEIGHT_FEET_INCHES;
	}

	public void setHEIGHT_FEET_INCHES(String height_feet_inches) {
		HEIGHT_FEET_INCHES = height_feet_inches;
	}

	public String getWEIGHT_POUNDS() {
		return WEIGHT_POUNDS;
	}

	public void setWEIGHT_POUNDS(String weight_pounds) {
		WEIGHT_POUNDS = weight_pounds;
	}

	public String getOCCUPATION() {
		return OCCUPATION;
	}

	public void setOCCUPATION(String occupation) {
		OCCUPATION = occupation;
	}

	public String getEMPLOYER() {
		return EMPLOYER;
	}

	public void setEMPLOYER(String employer) {
		EMPLOYER = employer;
	}

	public String getEMPLOYMENT_START() {
		return EMPLOYMENT_START;
	}

	public void setEMPLOYMENT_START(String employment_start) {
		EMPLOYMENT_START = employment_start;
	}

	public String getMONTHLY_INCOME() {
		return MONTHLY_INCOME;
	}

	public void setMONTHLY_INCOME(String monthly_income) {
		MONTHLY_INCOME = monthly_income;
	}

	public String getBUSINESS_PHONE_NUMBER() {
		return BUSINESS_PHONE_NUMBER;
	}

	public void setBUSINESS_PHONE_NUMBER(String business_phone_number) {
		BUSINESS_PHONE_NUMBER = business_phone_number;
	}

	public String getRESIDENTIAL_PHONE() {
		return RESIDENTIAL_PHONE;
	}

	public void setRESIDENTIAL_PHONE(String residential_phone) {
		RESIDENTIAL_PHONE = residential_phone;
	}

	public String getRESIDENTIAL_SEARCH_ADDRESS() {
		return RESIDENTIAL_SEARCH_ADDRESS;
	}

	public void setRESIDENTIAL_SEARCH_ADDRESS(
			String residential_search_address) {
		RESIDENTIAL_SEARCH_ADDRESS = residential_search_address;
	}

	public PersonCompanyBrowseDsp(BigDecimal person_id, String search_name,
			String person_type, String title, String first_name,
			String middle_name, String family_name, String honorifics,
			String name_suffix, String sex, String smoker_ind,
			String birth_date, String mib_birth_place, String ssn_number,
			String drivers_licence_id, String driver_licence_state,
			String height_feet_inches, String weight_pounds, String occupation,
			String employer, String employment_start, String monthly_income,
			String business_phone_number, String residential_phone,
			String residential_search_address) {
		super();
		PERSON_ID = person_id;
		SEARCH_NAME = search_name;
		PERSON_TYPE = person_type;
		TITLE = title;
		FIRST_NAME = first_name;
		MIDDLE_NAME = middle_name;
		FAMILY_NAME = family_name;
		HONORIFICS = honorifics;
		NAME_SUFFIX = name_suffix;
		SEX = sex;
		SMOKER_IND = smoker_ind;
		BIRTH_DATE = birth_date;
		MIB_BIRTH_PLACE = mib_birth_place;
		SSN_NUMBER = ssn_number;
		DRIVERS_LICENCE_ID = drivers_licence_id;
		DRIVER_LICENCE_STATE = driver_licence_state;
		HEIGHT_FEET_INCHES = height_feet_inches;
		WEIGHT_POUNDS = weight_pounds;
		OCCUPATION = occupation;
		EMPLOYER = employer;
		EMPLOYMENT_START = employment_start;
		MONTHLY_INCOME = monthly_income;
		BUSINESS_PHONE_NUMBER = business_phone_number;
		RESIDENTIAL_PHONE = residential_phone;
		RESIDENTIAL_SEARCH_ADDRESS = residential_search_address;
	}

	public PersonCompanyBrowseDsp() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
