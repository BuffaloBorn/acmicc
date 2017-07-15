package com.cc.acmi.presentation.dsp;

import com.cc.framework.common.DisplayObject;

public class PolicyPersonCovMainListDsp implements DisplayObject{

	private String ANNUAL_PREM_AMT;
	private String ANNUAL_PERM_EXTRA_PREM_AMT;
	private String COVERAGE_CODE;
	private String SUB_STANDARD_RISK_CODE;
	private String START_DATE;
	private String END_DATE;
	private String TOTAL_PREMIUM;
	
	public String getANNUAL_PREM_AMT() {
		return ANNUAL_PREM_AMT;
	}
	public void setANNUAL_PREM_AMT(String annual_prem_amt) {
		ANNUAL_PREM_AMT = annual_prem_amt;
	}
	public String getANNUAL_PERM_EXTRA_PREM_AMT() {
		return ANNUAL_PERM_EXTRA_PREM_AMT;
	}
	public void setANNUAL_PERM_EXTRA_PREM_AMT(String annual_perm_extra_prem_amt) {
		ANNUAL_PERM_EXTRA_PREM_AMT = annual_perm_extra_prem_amt;
	}
	public String getCOVERAGE_CODE() {
		return COVERAGE_CODE;
	}
	public void setCOVERAGE_CODE(String coverage_code) {
		COVERAGE_CODE = coverage_code;
	}
	public String getSUB_STANDARD_RISK_CODE() {
		return SUB_STANDARD_RISK_CODE;
	}
	public void setSUB_STANDARD_RISK_CODE(String sub_standard_risk_code) {
		SUB_STANDARD_RISK_CODE = sub_standard_risk_code;
	}
	public String getSTART_DATE() {
		return START_DATE;
	}
	public void setSTART_DATE(String start_date) {
		START_DATE = start_date;
	}
	public String getEND_DATE() {
		return END_DATE;
	}
	public void setEND_DATE(String end_date) {
		END_DATE = end_date;
	}
	public String getTOTAL_PREMIUM() {
		return TOTAL_PREMIUM;
	}
	public void setTOTAL_PREMIUM(String total_premium) {
		TOTAL_PREMIUM = total_premium;
	}
	
}
