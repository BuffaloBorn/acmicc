/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.form;

import java.util.TreeMap;

import org.apache.struts.action.ActionForm;

import com.cc.framework.ui.control.SimpleListControl;

import com.epm.acmi.util.ACMICache;
/** 
 * MyEclipse Struts
 * Creation date: 04-03-2008
 * 
 * XDoclet definition:
 * @struts.form name="policyPersonMainForm"
 */
public class PolicyPersonMainForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7714404851132730372L;
	
	private String policyid;
	private String personid;
	private String effective_date;
	
	private SimpleListControl policyPersonList = new SimpleListControl();
	
	public TreeMap getTransactionsOptions()
	{
		
//		TreeMap status = new TreeMap();
			
		/*--- --------------------
		 I  INSURED             
		 K  KEY INSURED         
		 N  IN-CARE-OF          
		 O  OWNER               
		 P  PAYEE               
		 T  ATTENTION LINE      
		 W  WITHDRAWN APPLICANT 
		 Z  DEP NOT ELIG FOR MSA 
		 						*/
//		status.put(" ", " ");
//		status.put("*", "*");
//		status.put("I", "I");
//		status.put("K", "K");
//		status.put("N", "N");
//		status.put("O", "R");
//		status.put("P", "P");
//		status.put("T", "T");
//		status.put("W", "W");
//		status.put("Z", "Z");
		
		
		return ACMICache.getPersonTypesCodes();
		
	}
	
	public TreeMap getPersonStatusHelpOptions()
	{
	
		//TreeMap status = new TreeMap();
		
	   /*A    Y  APPROVED ADDITION                        
		 B    Y  APPROVED WITH DELETE                     
		 C    N  CANCELLED                                
		 D    N  DECLINED                                 
		 G    N  PENDING GROUP DECISION                   
		 I    N  INCOMPLETE                               
		 M    Y  APPROVED WITH RIDER(S) AND/OR DECLINE(S) 
		 Q    Y  SEE COMMENTS                             
		 S    N  SUBMITTED APPLICATION                 
		 V    Y  APPROVE OTHER THAN APPLIED FOR        
		 W    N  WITHDRAWN                             
		 X    Y  APPROVED WITH ONE EXCLUSION RIDER     
		 Y    Y  APPROVED WITH ONE OR MORE DECLINE(S)  
		 Z    Y  APPROVED AS APPLIED FOR       */     
		
//		status.put(" ", " ");
//		status.put("A", "A: APPROVED ADDITION");
//		status.put("B", "B: APPROVED WITH DELETE");
//		status.put("C", "C: CANCELLED");
//		status.put("D", "D: DECLINED");
//		status.put("G", "G: PENDING GROUP DECISION");
//		status.put("I", "I: INCOMPLETE");
//		status.put("M", "M: APPROVED WITH RIDER(S) AND/OR DECLINE(S)");
//		status.put("Q", "Q: SEE COMMENTS");
//		status.put("S", "S: SUBMITTED APPLICATION");
//		status.put("V", "V: APPROVE OTHER THAN APPLIED FOR");
//		status.put("W", "W: WITHDRAWN");
//		status.put("X", "X: APPROVED WITH ONE EXCLUSION RIDER");
//		status.put("Y", "Y: APPROVED WITH ONE OR MORE DECLINE(S)");
//		status.put("Z", "Z: APPROVED AS APPLIED FOR");
		
		
		return ACMICache.getPersonStatusCodes();	
	}
	
	public TreeMap getSmokerOptions()
	{
		
		TreeMap status = new TreeMap();
		
		status.put("Y", "Yes");
		status.put("N", "No");
		
		return status;
	}
		
	public String getPolicyid() {
		return policyid;
	}
	public void setPolicyid(String policyid) {
		this.policyid = policyid;
	}
	public String getPersonid() {
		return personid;
	}
	public void setPersonid(String personid) {
		this.personid = personid;
	}
	public String getEffective_date() {
		return effective_date;
	}
	public void setEffective_date(String effective_date) {
		this.effective_date = effective_date;
	}

	public SimpleListControl getPolicyPersonList() {
		return policyPersonList;
	}

	public void setPolicyPersonList(SimpleListControl control) {
		this.policyPersonList = control;
	}
	
}