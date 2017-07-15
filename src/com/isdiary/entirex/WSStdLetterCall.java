package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.softwarag.extirex.webservice.stdletter.client.IASLIBPort;
import com.softwarag.extirex.webservice.stdletter.client.LetterLocator;
import com.softwarag.extirex.webservice.stdletter.client.MUELTMWINOUT_PARMS;
import com.softwarag.extirex.webservice.stdletter.client.MUELTMWIN_PARMS;
import com.softwarag.extirex.webservice.stdletter.client.holders.MUELTMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.stdletter.client.holders.MUELTMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.stdletter.client.holders.MUELTMWResponseOUT_PARMSHolder;

public class WSStdLetterCall {

	private static Logger log = Logger.getLogger(WSStdLetterCall.class);
	private static String classAction = "Standard Letter Maintance Data";
	
	public static synchronized void fetch(String PolicyNo, String EventId, MUELTMWResponseINOUT_PARMS1Holder inoutparms, MUELTMWResponseMSG_DATAHolder msgInfo, MUELTMWResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		IASLIBPort service = new LetterLocator().getIASLIBPort();
		
		MUELTMWIN_PARMS action = new MUELTMWIN_PARMS();
		action.setACTION("D");
		
		MUELTMWINOUT_PARMS inputs = new MUELTMWINOUT_PARMS();
		
		inputs.setATTACHMENT_IND(new String(""));
		//inputs.setDATE_COMPLETED_YYYYMMDD(new String(""));
		inputs.setEVENT_ID(new BigDecimal(EventId));
		inputs.setEVENT_STATUS_CURRENT(new String(""));
		inputs.setFREE_DESCRIPTION(new String(""));
		inputs.setMEMO_ID(new String(""));
		inputs.setPOLICY_ID(new BigDecimal(PolicyNo));
		//inputs.setRECIPIENT_PERSON_ID(new String(""));
		inputs.setRECIPIENT_SEARCH_NAME(new String(""));
		inputs.setSECOND_REQUEST_IND(new String(""));
		inputs.setSTD_EVENT_ID(new String(""));
		inputs.setTEXT(new String[60]);
		inputs.setUSER_ID(new String(""));
		
		service.MUELTMW(action, inputs, msgInfo, inoutparms, outparms);
		
		
	}
	
	public static synchronized void add(MUELTMWINOUT_PARMS inputs, MUELTMWResponseINOUT_PARMS1Holder inoutparms, MUELTMWResponseMSG_DATAHolder msgInfo, MUELTMWResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		log.debug("Adding " + classAction);
		IASLIBPort service = new LetterLocator().getIASLIBPort();
		
		MUELTMWIN_PARMS action = new MUELTMWIN_PARMS();
		action.setACTION("A");
		
		service.MUELTMW(action, inputs, msgInfo, inoutparms, outparms);
		
		log.debug("Finish addng.... " + classAction);
	}
	
	public static synchronized void edit(MUELTMWINOUT_PARMS inputs, MUELTMWResponseINOUT_PARMS1Holder inoutparms, MUELTMWResponseMSG_DATAHolder msgInfo, MUELTMWResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException	
	{
		log.debug("Editing " + classAction);
		
		IASLIBPort service = new LetterLocator().getIASLIBPort();
		
		MUELTMWIN_PARMS action = new MUELTMWIN_PARMS();
		action.setACTION("M");
		
	
		service.MUELTMW(action, inputs, msgInfo, inoutparms, outparms);
			
		log.debug("Finish editing.... " + classAction);
	}
}
