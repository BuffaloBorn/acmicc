package com.isdiary.entirex;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

/*import com.softwarag.extirex.webservice.seventcodes.client.holders.MUEVNWSResponseMORE1Holder;
import com.softwarag.extirex.webservice.seventcodes.client.holders.MUEVNWSResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.seventcodes.client.holders.MUEVNWSResponseOUT_PARMSsHolder;*/


import java.util.TreeMap;

public class WSStdEventCodesNoCall {

	public static synchronized TreeMap fetch()throws ServiceException, RemoteException
	{
		
		TreeMap map = new TreeMap();
		//TreeMap localTMap = new TreeMap();
		
//		map.put("A QUES", "ALCOHOL QUESTIONNAIRE KEY APPLICANT");
//		map.put("A QUES-B", "ALCOHOL QUESTIONNAIRE ON KEY APPLICANT & SPOUSE");
//		map.put("A QUES-S", "ALCOHOL QUESTIONNAIRE ON SPOUSE");
//		map.put("AA QUES", "ASTHMA/ALLERGY QUESTIONNAIRE");
//		map.put("AD QUE", "ALCOHOL & DRUG QUESTIONNAIRES ON KEY APPLICANT");
//		map.put("AD QUE-B", "ALCOHOL & DRUG QUESTIONNAIRES ON KEY APP & SPOUSE");
//		map.put("AD QUE-S", "ALCOHOL & DRUG QUESTIONNAIRES ON SPOUSE");
//		map.put("ADDONDEC", "ADD-ON REQUEST DECLINED");
//		map.put("AI", "APPLICATION INFORMATION");
//		map.put("AMEND", "LETTER PRINTED ON STANDARD ACMIC LETTERHEAD");
//		map.put("AMEND-A", "ADD ON AMENDMENT");
//		map.put("AMEND-P", "POST ISSUE SUB-STANDARD RATING AMENDMENT");
//		map.put("AMEND-R", "REINSTATEMENT AMENDMENT");
//		map.put("APP", "LETTER TO APPLICANT NOTIFYING RELEASE OF RECORDS");
//		map.put("APS", "ATTENDING PHYSICIAN STATEMENT");
//		map.put("AT", "AUTOMATICE TRANSFER ENDORSEMENT LETTER");
//		map.put("AUDIT", "PREMIUM AUDIT");
//		map.put("AV QUES", "AVIATION/AVOCATION QUESTIONNAIRE");
		map.put("BLD", "BLOOD PROFILE");
//		map.put("BLOOD", "BLOOD PROFILE");
//		map.put("CHG/POL", "CHANGE RIDER TO RATING");
//		map.put("CODE DET", "REQUEST FOR CODE DETAILS");
//		map.put("CONF", "CONFIRMATION NOTICE");
//		map.put("CORRES", "CORRES");
//		map.put("CSREQDEC", "CUSTOMER SERVICE REQUEST DECLINED");
//		map.put("D QUES", "DRUG QUESTIONNAIRE ON KEY APPLICANT");
//		map.put("D QUES-B", "DRUG QUESTIONNAIRE KEY APPLICANT & SPOUSE");
//		map.put("D QUES-S", "DRUG QUESTIONNAIRE ON SPOUSE");
//		map.put("DAC", "MOTOR VEHICLE RECORD");
//		map.put("DLN-A", "DRIVER'S LICENSE NUMBER ON KEY APPLICANT");
//		map.put("DLN-B", "DRIVERS' LICENSE NUMBERS ON KEY APP & SPOUSE");
//		map.put("DLN-S", "DRIVER'S LICENSE NUMBER ON SPOUSE");
//		map.put("DLR", "MOTOR VEHICLE RECORD");
//		map.put("DR-A", "DEL REQ - AMENDMENT");
//		map.put("DR-EX", "DEL REQ - EXCLUSION RIDER");
//		map.put("DR-F", "DEL REQ - FORMS");
//		map.put("DR-LC", "DEL REQ - LOAD CONVERSION");
//		map.put("DR-P", "DEL REQ - PREMIUM");
//		map.put("DR-S", "DEL REQ - SIGNATURE(S)");
//		map.put("DR-1", "LETTER TO DOCTOR EXPLAINING THE DECLINE");
//		map.put("DR-2", "CONTINUATION OF DOCTOR LETTER");
		map.put("ECG", "EKG");
//		map.put("EKG", "EKG");
//		map.put("EXAM", "PARAMEDICAL EXAM");
//		map.put("EXAM-BL", "PARAMEDICAL EXAM AND BLOOD PROFILE");
		map.put("EXM", "PARAMEDICAL EXAM");
		map.put("EXM-BLD", "PARAMEDICAL EXAM AND BLOOD PROFILE");
//		map.put("F/", "ALCOHOL & DRUG QUESTIONNAIRES ON SPOUSE");
//		map.put("FR QUES", "FOREIGN RESIDENCE QUESTIONNAIRE");
		map.put("FREE TX", "FREE TEXT");
//		map.put("H-7B", "HEALTH QUESTION 7B");
		map.put("HIPAA", "HIPAA COVERAGE");
		map.put("HIPAA-MI", "HIPAA COVERAGE - MI");
		map.put("HIPAA-OH", "HIPAA COVERAGE (OHIO)");
//		map.put("HIV", "HIV CONSENT FORM");
		map.put("HIV-H", "HIV CONSENT FORM - HEALTH");
//		map.put("HOS", "HOME OFFICE URINE SPECIMEN");
//		map.put("HW", "HEIGHT(S) AND WEIGHT(S)");
//		map.put("IHCS-LET", "IND CUSTOMER SERVICE LETTER");
//		map.put("INSP-BB", "INSPECTION - BUSINESS INSURANCE REPORT");
//		map.put("INSP-HTH", "INSPECTION REPORT - MEDICAL EXPENSE FASTCOM");
//		map.put("INSP-LI", "INSPECTION REPORT LIFE FASTCOM");
//		map.put("INSP-SL", "INSPECTION - SPECIAL LIFE SPECIFIC RATE");
//		map.put("INSP-SS", "INSPECTION - SPECIAL SERVICE");
//		map.put("INSPEC", "INSPECTION - SPECIAL REQUEST");
		map.put("LETTER", "DECLINE, INCOMPLETE, WITHDRAWN LETTER");
//		map.put("LEVEL-1", "LIFE REQUIREMENTS");
//		map.put("LEVEL-10", "LIFE REQUIREMENTS");
//		map.put("LEVEL-11", "LIFE REQ. - 2 ADD'L HOS");
//		map.put("LEVEL-2", "LIFE REQUIREMENTS");
//		map.put("LEVEL-3", "LIFE REQUIREMENTS");
//		map.put("LEVEL-4", "LIFE REQUIREMENTS");
//		map.put("LEVEL-5", "LIFE REQUIREMENTS");
//		map.put("LEVEL-6", "LIFE REQUIREMENTS");
//		map.put("LEVEL-7", "LIFE REQUIREMENTS");
//		map.put("LEVEL-8", "LIFE REQUIREMENTS");
//		map.put("LEVEL-9", "LIFE REQUIREMENTS");
//		map.put("LIFE-3", "LIFE REQUIREMENTS");
//		map.put("LQ", "SECTION 11 - HEALTH QUESTIONS");
//		map.put("MEDREC", "MEDICAL RECORD REQUEST");
//		map.put("MF", "MICRO-FILM FILE(S) FOR UNDERWRITING");
//		map.put("MIB", "MIB INQUIRY REQUEST");
//		map.put("MIB CODE", "MIB REPORT CODES");
//		map.put("MIB-H1", "MIB HEALTH DECLINE 1ST PART");
//		map.put("MIB-L1", "MIB LIFE DECLINE 1ST PART");
//		map.put("MIB-2", "MIB 2ND PT. FOR DECLINE OF HEALTH/LIFE");
//		map.put("MVR", "MOTOR VEHICLE RECORD");
//		map.put("NOTICE", "NOTICE OF UNINSURABLE APPLICANT");
//		map.put("NOTIF", "NOTIFICATION");
		map.put("OFFER-IN", "RIDER OFFER OF COVERAGE LETTER (IN ONLY)");
		map.put("PHONE", "TELEPHONIC INTERVIEW");
		map.put("PROP", "PROPOSAL SUBMITTED WITH THE APPLICATION");
//		map.put("QT-ND", "QUOTE FOR REPLACEMENT POLICY");
		map.put("QUOTE", "QUOTE");
		map.put("RE-OPEN", "RE-OPEN REQUEST FOR NEW APPLICATION");
//		map.put("RECORDS", "ATTENDING PHYSICIAN STATEMENT");
//		map.put("REM RID", "REMOVE RIDER AFTER ISSUE");
//		map.put("REMNOT", "REMINDER NOTICE");
//		map.put("REPL", "REPLACEMENT INFORMATION");
//		map.put("REPL-LI", "REPLACEMENT INFORMATION FOR LIFE");
//		map.put("REPL-OH", "REPLACEMENT INFORMATION");
//		map.put("REQ-DR", "REQUEST FOR DOCTOR'S NAME AND ADDRESS");
//		map.put("RESC", "RESCIND AFTER ISSUE - 1ST PT.");
//		map.put("RESC AGT", "RESCIND AFTER ISSUE - AGENT COPY");
//		map.put("RESC NCL", "RESCIND WITH NO CLAIMS - 2ND PART");
//		map.put("RESC WCL", "RESCIND WITH CLAIMS - 2ND PART");
//		map.put("RESC 1", "RESCIND ONE PERSON - 2ND PART");
//		map.put("RESC/AGT", "RESCIND AFTER ISSUE - AGENT COPY");
//		map.put("RESC/NCL", "RESCIND WITH NO CLAIMS - 2ND PART");
//		map.put("RESC/WCL", "RESCIND WITH CLAIMS - 2ND PART");
//		map.put("RID", "ADDING RIDER AFTER ISSUE - 1ST PT.");
//		map.put("RID ADD", "ADDING RIDER AFTER ISSUED - 2ND PT.");
//		map.put("RID RESC", "RESCIND - RIDER NOT RETURNED");
//		map.put("RID/AGT", "RIDER AFTER ISSUE - AGENT COPY");
//		map.put("RIDER-A", "ADD-ON EXCLUSION RIDER");
//		map.put("RIDER-P", "POST ISSUE EXCLUSION RIDER");
//		map.put("RIDER-R", "REINSTATEMENT EXCLUSION RIDER");
		map.put("SA", "SPECIAL AUTHORIZATION");
//		map.put("SS&DL-A", "SS# AND DL# ON APPLICANT");
//		map.put("SS&DL-B", "SS# AND DL# ON APPLICANT & SPOUSE");
//		map.put("SS&DL-S", "SS# AND DL# ON SPOUSE");
//		map.put("SS-A", "SOCIAL SECURITY NUMBER ON KEY APPLICANT");
//		map.put("SS-B", "SOCIAL SECURITY NUMBER ON KEY APPLICANT & SPOUSE");
//		map.put("SS-S", "SOCIAL SECURITY NUMBER ON SPOUSE");
		map.put("STDLET", "LETTER PRINTED ON STANDARD ACMIC LETTERHEAD");
		map.put("URNSPEC", "HOME OFFICE URINE SPECIMEN");

		
		
		/*IASLIBPort service = new SEventCodesLocator().getIASLIBPort();
		
		MUEVNWSResponseMORE1Holder more1 = new MUEVNWSResponseMORE1Holder();
		MUEVNWSResponseOUT_PARMSsHolder outparms = new MUEVNWSResponseOUT_PARMSsHolder();
		MUEVNWSResponseMSG_INFOHolder msginfo = new MUEVNWSResponseMSG_INFOHolder();
		MUEVNWSMORE more = new MUEVNWSMORE();
		
		
		more.setMORE_CODES(new String(""));
		more.setLAST_KEY(new String(""));
		
		service.MUEVNWS(more, msginfo, more1, outparms);
		
		MUEVNWSResponseOUT_PARMSsOUT_PARMS[] outArray =  outparms.value;
		
		for (int i = 0; i < outArray.length; i++)
		{
			MUEVNWSResponseOUT_PARMSsOUT_PARMS item = outArray[i];
			localTMap.put(item.getSTD_EVENT_ID(), item.getSTD_EVENT_ID()+ ": " + item.getDESCRIPTION());
			
		}*/
		
//		 String key;
//		 Set set= map.keySet (  ) ; 
//	     Iterator iter = set.iterator (  ) ; 
//	      
//	     while ( iter.hasNext (  )  )  
//	     {  
//	    	
//	    	  key = iter.next().toString();
//	    	 localTMap.put(key, key+ ": " + map.get ( key  ));
//	      }  


		return map;
	}
	
}
