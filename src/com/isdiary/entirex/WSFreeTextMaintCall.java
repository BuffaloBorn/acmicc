package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.softwarag.extirex.webservice.freetextmaint.client.FreeTextMaintLocator;
import com.softwarag.extirex.webservice.freetextmaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.freetextmaint.client.MUEFMMWINOUT_PARMS;
import com.softwarag.extirex.webservice.freetextmaint.client.MUEFMMWIN_PARMS;
import com.softwarag.extirex.webservice.freetextmaint.client.holders.MUEFMMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.freetextmaint.client.holders.MUEFMMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.freetextmaint.client.holders.MUEFMMWResponseOUT_PARMSHolder;

public class WSFreeTextMaintCall {

	private static Logger log = Logger.getLogger(WSFreeTextMaintCall.class);
	private static String classAction = "Free Text Maintance Data";
	
	public static synchronized void fetch(String PolicyNo, String EventId, MUEFMMWResponseINOUT_PARMS1Holder inoutparms, MUEFMMWResponseMSG_DATAHolder msgInfo, MUEFMMWResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		log.debug("Fetching " + classAction);
		
		IASLIBPort service = new FreeTextMaintLocator().getIASLIBPort();
		MUEFMMWIN_PARMS action = new MUEFMMWIN_PARMS();
		action.setACTION("D");
		
		MUEFMMWINOUT_PARMS inputs = new MUEFMMWINOUT_PARMS();
		
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
		inputs.setMEMO_IND(new String(""));
		inputs.setFREE_FORM_IND(new String(""));
		
		
		service.MUEFMMW(action, inputs, msgInfo, inoutparms, outparms);
		
		log.debug("Finish....fetching " + classAction);
	}
	
	public static synchronized void add(MUEFMMWINOUT_PARMS inputs,  MUEFMMWResponseINOUT_PARMS1Holder inoutparms, MUEFMMWResponseMSG_DATAHolder msgInfo, MUEFMMWResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		log.debug("Adding " + classAction);
		
		IASLIBPort service = new FreeTextMaintLocator().getIASLIBPort();
		MUEFMMWIN_PARMS action = new MUEFMMWIN_PARMS();
		action.setACTION("A");
		
		
		service.MUEFMMW(action, inputs, msgInfo, inoutparms, outparms);
		
		log.debug("Finish addng.... " + classAction);
	}
	
	
	public static synchronized void edit( MUEFMMWINOUT_PARMS inputs, MUEFMMWResponseINOUT_PARMS1Holder inoutparms, MUEFMMWResponseMSG_DATAHolder msgInfo, MUEFMMWResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		log.debug("Editing " + classAction);
		
		IASLIBPort service = new FreeTextMaintLocator().getIASLIBPort();
		MUEFMMWIN_PARMS action = new MUEFMMWIN_PARMS();
		action.setACTION("M");
		
		service.MUEFMMW(action, inputs, msgInfo, inoutparms, outparms);
		
		log.debug("Finish editing.... " + classAction);
		
	}
}
