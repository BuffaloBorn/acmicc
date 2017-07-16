/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.form;

import java.util.TreeMap;

import org.apache.struts.action.ActionForm;

import com.cc.acmi.common.FieldCheckCustom;
import com.cc.framework.adapter.struts.FormActionContext;

/** 
 * MyEclipse Struts
 * Creation date: 04-02-2008
 * 
 * XDoclet definition:
 * @struts.form name="eventPortamedicForm"
 */
public class EventPortamedicForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String event_id="";
	private String std_event="";
	private String status="";
	private String requested="";
	private String respn_date="";
	private String respn_dd="";
	private String respn_mm="";
	private String respn_yyyy="";
	private String response="";
	private String person_id="";
	private String birthdate="";
	private String birthplace="";
	private String person_name="";
	private String age="";
	private String sex="";
	private String marital="";
	private String drivers_license="";
	private String drivers_license_state="";
	private String ssn="";
	private String employer="";
	private String occ="";
	private String start="";
	private String first_name="";
	private String middle_name="";
	private String last_name="";
	private String address_name="";
	private String address_number="";
	private String city="";
	private String phyician_state="";
	private String phyician_zipCode="";
	private String phyician_phone_number="";
	private String phyician_phone_extentsion="";
	private String phyician_fax_number="";
	private String request_type="";
	private String remarksTextArea="";
	private String policyno="";
	private String log_counter = "";
	
	
	public TreeMap getStatesOptions()
	{
		TreeMap states = new TreeMap();
		
		/*   St                
		St No     Description
		-- -- ---------------
		AK 50 ALASKA      
		AL  1 ALABAMA     
		AR  3 ARKANSAS    
		AZ  2 ARIZONA     
		CA  4 CALIFORNIA  
		CO  5 COLORADO    
		CT  6 CONNECTICUT 
		DC  8 DISTRICT OF COLUMBIA 
		DE  7 DELAWARE             
		FL  9 FLORIDA              
		GA 10 GEORGIA              
		HI 51 HAWAII               
		IA 14 IOWA                 
		ID 11 IDAHO                
		IL 12 ILLINOIS      
		IN 13 INDIANA       
		KS 15 KANSAS        
		KY 16 KENTUCKY      
		LA 17 LOUISIANA     
		MA 20 MASSACHUSETTS 
		MD 19 MARYLAND      
		ME 18 MAINE          
		MI 21 MICHIGAN       
		MN 22 MINNESOTA      
		MO 24 MISSOURI       
		MS 23 MISSISSIPPI    
		MT 25 MONTANA        
		NC 32 NORTH CAROLINA 
		ND 33 NORTH DAKOTA  
		NE 26 NEBRASKA      
		NH 28 NEW HAMPSHIRE 
		NJ 29 NEW JERSEY    
		NM 30 NEW MEXICO    
		NV 27 NEVADA        
		NY 31 NEW YORK      
		OH 34 OHIO           
		OK 35 OKLAHOMA       
		OR 36 OREGON         
		PA 37 PENNSYLVANIA   
		PR 52 PUERTO RICO    
		RI 38 RHODE ISLAND   
		SC 39 SOUTH CAROLINA 
		SD 40 SOUTH DAKOTA   
		TN 41 TENNESSEE      
		TX 42 TEXAS          
		UT 43 UTAH           
		VA 45 VIRGINIA       
		VI 53 VIRGIN ISLANDS 
		VT 44 VERMONT 
		WA 46 WASHINGTON    
		WI 48 WISCONSIN     
		WV 47 WEST VIRGINIA 
		WY 49 WYOMING*/   
		states.put(" ", " "); 
		states.put("AK", "ALASKA");  
		states.put("AL", "ALABAMA");      
		states.put("AR", "ARKANSAS");
		states.put("AZ", "ARIZONA");  
		states.put("CA", "CALIFORNIA");
		states.put("CO", "COLORADO"); 
		states.put("CT", "CONNECTICUT"); 
		states.put("DC", "DISTRICT OF COLUMBIA");
		states.put("DE", "DELAWARE");
		states.put("FL", "FLORIDA");
		states.put("GA", "GEORGIA");
		states.put("HI", "HAWAII");
		states.put("IA", "IOWA");
		states.put("ID", "IDAHO");
		states.put("IL", "ILLINOIS");
		states.put("IN", "INDIANA");
		states.put("KS", "KANSAS");
		states.put("KY", "KENTUCKY");
		states.put("LA", "LOUISIANA");
		states.put("MA", "MASSACHUSETTS");
		states.put("MD", "MARYLAND");
		states.put("ME", "MAINE");
		states.put("MI", "MICHIGAN");
		states.put("MN", "MINNESOTA");
		states.put("MO", "MISSOURI");
		states.put("MS", "MISSISSIPPI");
		states.put("MT", "MONTANA");        
		states.put("NC", "NORTH CAROLINA"); 
		states.put("ND", "NORTH DAKOTA");  
		states.put("NE", "NEBRASKA");      
		states.put("NH", "NEW HAMPSHIRE");
		states.put("NJ", "NEW JERSEY");    
		states.put("NM", "NEW MEXICO"); 
		states.put("NV", "NEVADA");
		states.put("NY", "NEW YORK");      
		states.put("OH", "OHIO");
		states.put("OK", "OKLAHOMA");
		states.put("OR", "OREGON");        
		states.put("PA", "PENNSYLVANIA");
		states.put("PR", "PUERTO RICO"); 
		states.put("RI", "RHODE ISLAND");  
		states.put("SC", "SOUTH CAROLINA");
		states.put("SD", "SOUTH DAKOTA");
		states.put("TN", "TENNESSEE");      
		states.put("TX", "TEXAS");
		states.put("UT", "UTAH");   
		states.put("VA", "VIRGINIA");
		states.put("VI", "VIRGIN ISLANDS");
		states.put("VT", "VERMONT");
		states.put("WA", "WASHINGTON");    
		states.put("WI", "WISCONSIN");
		states.put("WV", "WEST VIRGINIA");
		states.put("WY", "WYOMING");
		
		
		
		return states;
	}
	
	public EventPortamedicForm() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getStd_event() {
		return std_event;
	}

	public void setStd_event(String std_event) {
		this.std_event = std_event;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequest() {
		return requested;
	}

	public void setRequest(String request) {
		this.requested = request;
	}


	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMarital() {
		return marital;
	}

	public void setMarital(String marital) {
		this.marital = marital;
	}

	public String getDrivers_license() {
		return drivers_license;
	}

	public void setDrivers_license(String drivers_license) {
		this.drivers_license = drivers_license;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public String getOcc() {
		return occ;
	}

	public void setOcc(String occ) {
		this.occ = occ;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhyician_state() {
		return phyician_state;
	}

	public void setPhyician_state(String phyician_state) {
		this.phyician_state = phyician_state;
	}


	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}

	public String getRemarksTextArea() {
		return remarksTextArea;
	}

	public void setRemarksTextArea(String remarksTextArea) {
		this.remarksTextArea = remarksTextArea;
	}

	public String getRespn_dd() {
		return respn_dd;
	}

	public void setRespn_dd(String respn_dd) {
		this.respn_dd = respn_dd;
	}

	public String getRespn_mm() {
		return respn_mm;
	}

	public void setRespn_mm(String respn_mm) {
		this.respn_mm = respn_mm;
	}

	public String getRespn_yyyy() {
		return respn_yyyy;
	}

	public void setRespn_yyyy(String respn_yyyy) {
		this.respn_yyyy = respn_yyyy;
	}



	public String getPerson_name() {
		return person_name;
	}

	public void setPerson_name(String person_name) {
		this.person_name = person_name;
	}



	public String getAddress_name() {
		return address_name;
	}



	public void setAddress_name(String address_name) {
		this.address_name = address_name;
	}



	public String getAddress_number() {
		return address_number;
	}



	public void setAddress_number(String address_number) {
		this.address_number = address_number;
	}



	public String getDrivers_license_state() {
		return drivers_license_state;
	}



	public void setDrivers_license_state(String drivers_license_state) {
		this.drivers_license_state = drivers_license_state;
	}


	public String getPhyician_phone_number() {
		return phyician_phone_number;
	}



	public void setPhyician_phone_number(String phyician_phone_number) {
		this.phyician_phone_number = phyician_phone_number;
	}




	public String getPhyician_fax_number() {
		return phyician_fax_number;
	}



	public void setPhyician_fax_number(String phyician_fax_number) {
		this.phyician_fax_number = phyician_fax_number;
	}




	public String getRespn_date() {
		return respn_date;
	}



	public void setRespn_date(String respn_date) {
		this.respn_date = respn_date;
	}



	public String getPolicyno() {
		return policyno;
	}



	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}



	public String getRequested() {
		return requested;
	}



	public void setRequested(String requested) {
		this.requested = requested;
	}
	

	public String getPhyician_zipCode() {
		return phyician_zipCode;
	}



	public void setPhyician_zipCode(String phyician_zipCode) {
		this.phyician_zipCode = phyician_zipCode;
	}
	
	public String getPhyician_phone_extentsion() {
		return phyician_phone_extentsion;
	}



	public void setPhyician_phone_extentsion(String phyician_phone_extentsion) {
		this.phyician_phone_extentsion = phyician_phone_extentsion;
	}

	
	public void clear()
	{
		this.setAddress_name("");
		this.setAddress_number("");
		this.setAge("");
		this.setBirthdate("");
		this.setBirthplace("");
		this.setCity("");
		this.setDrivers_license("");
		this.setDrivers_license_state("");
		this.setEmployer("");
		this.setEvent_id("");
		this.setFirst_name("");
		this.setLast_name("");
		this.setLast_name("");
		this.setMarital("");
		this.setMiddle_name("");
		this.setMiddle_name("");
		this.setOcc("");
		this.setPerson_id("");
		this.setPerson_name("");
		this.setPhyician_fax_number("");
		this.setPhyician_phone_number("");
		this.setPhyician_zipCode("");
		this.setPhyician_state("");
		this.setPolicyno("");
		this.setSex("");
		this.setSsn("");
		this.setStart("");
		this.setStatus("");
		this.setStd_event("");
		this.setRemarksTextArea("");
		this.setRequest("");
		this.setRequest_type("");
		this.setRequested("");
		this.setResponse("");
		this.setPhyician_phone_extentsion("");
		this.setLog_counter("");
		
	}
	
	public void save()
	{
		this.setAddress_name(this.getAddress_name());
		this.setAddress_number(this.getAddress_number());
		this.setAge(this.getAge());
		this.setBirthdate(this.getBirthdate());
		this.setBirthplace(this.getBirthplace());
		this.setCity(this.getCity());
		this.setDrivers_license(this.getDrivers_license());
		this.setDrivers_license_state(this.getDrivers_license_state());
		this.setEmployer(this.getEmployer());
		this.setEvent_id(this.getEvent_id());
		this.setFirst_name(this.getFirst_name());
		this.setLast_name(this.getLast_name());
		this.setMiddle_name(this.getMiddle_name());
		this.setOcc(this.getOcc());
		this.setPerson_id(this.getPerson_id());
		this.setPerson_name(this.getPerson_name());	
		this.setPhyician_fax_number(this.getPhyician_fax_number());
		this.setPhyician_phone_number(this.getPhyician_phone_number());
		this.setPhyician_phone_extentsion(this.getPhyician_phone_extentsion());
		this.setPolicyno(this.getPolicyno());
		this.setSsn(this.getSsn());
		this.setStart(this.getStart());
		this.setStatus(this.getStatus());
		this.setStd_event(this.getStd_event());
		this.setRemarksTextArea(this.getRemarksTextArea());
		this.setRequest(this.getRequest());
		this.setRequest_type(this.getRequest_type());
		this.setRequested(this.getRequested());
		this.setResponse(this.getResponse());
		this.setPhyician_zipCode(this.getPhyician_zipCode());
		
		
	}

	public void validateForm(FormActionContext ctx)
	{
		
		FieldCheckCustom.validateRequired(this.getPerson_id(), ctx, "Person Id");
		
		if (this.getPhyician_phone_number().length() < 0)
			FieldCheckCustom.validatePhone(this.getPhyician_phone_number(), ctx, "Phyician Phone Number");
			
		if (this.getPhyician_fax_number().length() < 0)
			FieldCheckCustom.validateFax(this.getPhyician_fax_number(), ctx, "Phyician Fax Number");
		
		if (this.getPhyician_zipCode().length() < 0)
			FieldCheckCustom.validateZipCode(this.getPhyician_zipCode(), ctx, "Phyician Zip Code");

	}

	public TreeMap getResponseIndOptions()
	{
		
		TreeMap response_ind = new TreeMap();
		response_ind.put("", "");
		response_ind.put("Y", "Yes");
		response_ind.put("N", "No");
		
		return response_ind;
		
	}
	

	public TreeMap getStatusOptions()
	{
		
		TreeMap status = new TreeMap();
			
		/*-- -------------------------------------------------- ---
		I  INCOMPLETE (NO INFORMATION)                         Y 
		O  ORDERED                                             N 
		P  PENDING                                             N 
		R  RECEIVED                                            Y 
		V  VOID                                                Y 
		W  WAIVED/WITHDRAWN                                    Y */
		
		status.put(" ", " ");
		status.put("I", "I: INCOMPLETE (NO INFORMATION)");
		status.put("O", "O: ORDERED");
		status.put("P", "P: PENDING");
		status.put("R", "R: RECEIVED");
		status.put("V", "V: VOID");
		status.put("W", "W: WAIVED/WITHDRAWN");
		
		return status;
		
	}

	public String getLog_counter() {
		return log_counter;
	}

	public void setLog_counter(String log_counter) {
		this.log_counter = log_counter;
	}



	


	
	
}