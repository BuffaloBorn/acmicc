package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.epm.acmi.struts.action.EventPortamedicAction;
import com.softwarag.extirex.webservice.eventportrequest.client.EventPortRequestLocator;
import com.softwarag.extirex.webservice.eventportrequest.client.IASLIBPort;
import com.softwarag.extirex.webservice.eventportrequest.client.MUPRTMWINOUT_PARMS;
import com.softwarag.extirex.webservice.eventportrequest.client.MUPRTMWIN_PARMS;
import com.softwarag.extirex.webservice.eventportrequest.client.holders.MUPRTMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.eventportrequest.client.holders.MUPRTMWResponseMSG_INFOHolder;


public class WSEventPortRequest {
	
	private static String classAction = "Event Portamedic Request Data";
	
	private static Logger log = Logger.getLogger(EventPortamedicAction.class);
	
	public static synchronized void fetch(String PolicyNo, String EventId, MUPRTMWResponseINOUT_PARMS1Holder inoutparms, MUPRTMWResponseMSG_INFOHolder msgInfo) throws ServiceException, RemoteException
	{
		log.debug("Fetching " + classAction);
		
		IASLIBPort service = new EventPortRequestLocator().getIASLIBPort();
		
		MUPRTMWIN_PARMS action = new MUPRTMWIN_PARMS();
		
		action.setACTION("D");
		
		MUPRTMWINOUT_PARMS inputs = new MUPRTMWINOUT_PARMS();
		
		inputs.setUSER_ID(new String(""));
		
		//=============System Information =======================
		inputs.setEVENT_STATUS_CURRENT(new String(""));
		inputs.setPOLICY_ID(new BigDecimal(PolicyNo));
		inputs.setDATE_COMPLETED_YYYYMMDD(new BigDecimal("0"));
		inputs.setRESPONSE_INDICATOR(new String(""));
		inputs.setSTD_EVENT_ID(new String(""));
		inputs.setEVENT_ID(new BigDecimal(EventId));
		inputs.setDATE_CREATED(new String(""));
		inputs.setLOG_COUNTER(new BigDecimal("0"));
		
		//=============General Information =======================
		inputs.setEQUIF_SUBJECT_PERSON_ID(new BigDecimal("0"));
		inputs.setSUBJECT_PERSON_SEARCH_NAME(new String(""));
		inputs.setBIRTH_DATE_YYYYMMDD(new BigDecimal("0"));
		inputs.setMIB_BIRTH_PLACE(new String(""));
		inputs.setAGE(new BigDecimal("0") );
		inputs.setSEX(new String(""));
		inputs.setMARITAL_STATUS(new String(""));
		inputs.setDRIVERS_LICENCE_ID(new String(""));
		inputs.setDRIVER_LICENCE_STATE(new String(""));
		inputs.setSOCIAL_SECURITY_NUMBER(new String(""));
		inputs.setEMPLOYER(new String(""));
		inputs.setOCCUPATION(new String(""));
		inputs.setEMP_START_DATE_YY_MM(new BigDecimal("0"));
		
		
		//=============Phyician Information =======================
		inputs.setDOC_FIRST_NAME(new String(""));
		inputs.setDOC_MIDDLE_NAME(new String(""));
		inputs.setHOSPDOC_NAME(new String(""));
		inputs.setHOSPDOC_ADDR_STREET_NAME(new String(""));
		inputs.setHOSPDOC_ADDR_STREET_NUMBER(new String(""));
		inputs.setHOSPDOC_ADDR_CITY(new String(""));
		inputs.setHOSPDOC_ADDR_STATE(new String(""));
		inputs.setHOSPDOC_ADDR_ZIP_4(new String(""));
		inputs.setHOSPDOC_ADDR_ZIP_5(new String(""));
		inputs.setHOSPDOC_PHONE_EXTENSION(new String(""));
		inputs.setHOSPDOC_FAX_AREA(new String(""));
		inputs.setHOSPDOC_FAX_EXCHANGE(new String(""));
		inputs.setHOSPDOC_FAX_NUMBER(new String(""));
		inputs.setHOSPDOC_PHONE_AREA(new String(""));
		inputs.setHOSPDOC_PHONE_EXCHANGE(new String(""));
		inputs.setHOSPDOC_PHONE_NUMBER(new String(""));
		inputs.setEQUIF_REQUEST_TYPE(new String(""));
		inputs.setATTENTION_TEXT(new String[5]);
		
		service.MUPRTMW(action, inputs, msgInfo, inoutparms);
		
		log.debug("Finish....fetching " + classAction);
	}
	
	public static synchronized void add(MUPRTMWINOUT_PARMS inputs, MUPRTMWResponseINOUT_PARMS1Holder inoutparms, MUPRTMWResponseMSG_INFOHolder msgInfo) throws ServiceException, RemoteException
	{

		log.debug("Adding " + classAction);
		
		IASLIBPort service = new EventPortRequestLocator().getIASLIBPort();
		MUPRTMWIN_PARMS action = new MUPRTMWIN_PARMS();
		action.setACTION("A");
		
		service.MUPRTMW(action, inputs, msgInfo, inoutparms);
		
		log.debug("Finish adding.... " + classAction);
	}
	
	public static synchronized void edit(MUPRTMWINOUT_PARMS inputs, MUPRTMWResponseINOUT_PARMS1Holder inoutparms, MUPRTMWResponseMSG_INFOHolder msgInfo) throws ServiceException, RemoteException
	{

		log.debug("Editing " + classAction);
		
		IASLIBPort service = new EventPortRequestLocator().getIASLIBPort();
		MUPRTMWIN_PARMS action = new MUPRTMWIN_PARMS();
		action.setACTION("M");
		
		service.MUPRTMW(action, inputs, msgInfo, inoutparms);
		
		log.debug("Finish editing.... " + classAction);
	}
	
	
	
}
