package com.cc.acmi.presentation.dsp;

public class PolicyPersonMainListDsp {

	protected String tone = "";
	protected String ttwo = "";
	protected String tthree = "";
	protected String tfour = "";
	protected String tfive = "";
	protected String PERSON_STATUS_IND = "";
	protected String SMOKER_IND = "";
	protected String PERSON_ID = "";
	protected String PERSON_LEVEL_IND = "";
	protected String PERSON_SEARCH_NAME = "";
	protected String PERSON_STATUS = "";
	protected String PERSON_STATUS_START_DATE = "";
	protected String RIDER_IND = "";
	protected String RIDER_SHOW = "";
	protected boolean columnMode;
	
	public String getTone() {
		return tone;
	}
	public void setTone(String tone) {
		this.tone = tone;
	}
	public String getTtwo() {
		return ttwo;
	}
	public void setTtwo(String ttwo) {
		this.ttwo = ttwo;
	}
	public String getTthree() {
		return tthree;
	}
	public void setTthree(String tthree) {
		this.tthree = tthree;
	}
	public String getTfour() {
		return tfour;
	}
	public void setTfour(String tfour) {
		this.tfour = tfour;
	}
	public String getTfive() {
		return tfive;
	}
	public void setTfive(String tfive) {
		this.tfive = tfive;
	}
	public String getPERSON_STATUS_IND() {
		return PERSON_STATUS_IND;
	}
	public void setPERSON_STATUS_IND(String person_status_ind) {
		PERSON_STATUS_IND = person_status_ind;
	}
	public String getSMOKER_IND() {
		return SMOKER_IND;
	}
	public void setSMOKER_IND(String smoker_ind) {
		SMOKER_IND = smoker_ind;
	}
	public String getPERSON_ID() {
		return PERSON_ID;
	}
	public void setPERSON_ID(String person_id) {
		PERSON_ID = person_id;
	}
	public String getPERSON_LEVEL_IND() {
		return PERSON_LEVEL_IND;
	}
	public void setPERSON_LEVEL_IND(String person_level_ind) {
		PERSON_LEVEL_IND = person_level_ind;
	}
	public String getPERSON_SEARCH_NAME() {
		return PERSON_SEARCH_NAME;
	}
	public void setPERSON_SEARCH_NAME(String person_search_name) {
		PERSON_SEARCH_NAME = person_search_name;
	}
	public String getPERSON_STATUS() {
		return PERSON_STATUS;
	}
	public void setPERSON_STATUS(String person_status) {
		PERSON_STATUS = person_status;
	}
	public String getPERSON_STATUS_START_DATE() {
		return PERSON_STATUS_START_DATE;
	}
	public void setPERSON_STATUS_START_DATE(String person_status_start_date) {
		PERSON_STATUS_START_DATE = person_status_start_date;
	}
	public String getRIDER_IND() {
		return RIDER_IND;
	}
	public void setRIDER_IND(String rider_ind) {
		RIDER_IND = rider_ind;
	}
	
	public boolean getColumnMode() {
		return columnMode;
	}
	
	public boolean isColumnMode() {
		return columnMode;
	}
	
	public void setColumnMode(boolean columnMode) {
		this.columnMode = columnMode;
	}
	
	public String getRIDER_SHOW() {
		return RIDER_SHOW;
	}
	public void setRIDER_SHOW(String rider_show) {
		RIDER_SHOW = rider_show;
	}
	
	
	public PolicyPersonMainListDsp(String tone, String ttwo, String tthree,
			String tfour, String tfive, String person_status_ind,
			String smoker_ind, String person_id, String person_level_ind,
			String person_search_name, String person_status,
			String person_status_start_date, String rider_ind,
			boolean columnMode, String rider_show) {
		super();
		this.tone = tone;
		this.ttwo = ttwo;
		this.tthree = tthree;
		this.tfour = tfour;
		this.tfive = tfive;
		PERSON_STATUS_IND = person_status_ind;
		SMOKER_IND = smoker_ind;
		PERSON_ID = person_id;
		PERSON_LEVEL_IND = person_level_ind;
		PERSON_SEARCH_NAME = person_search_name;
		PERSON_STATUS = person_status;
		PERSON_STATUS_START_DATE = person_status_start_date;
		RIDER_IND = rider_ind;
		this.columnMode = columnMode;
		RIDER_SHOW = rider_show;
		
	}
	
	public PolicyPersonMainListDsp() {
		super();
		// TODO Auto-generated constructor stub
	}


	
	
	
}
