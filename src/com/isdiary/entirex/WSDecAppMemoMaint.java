package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.decappmemomaint.client.DecAppMemoMaintLocator;
import com.softwarag.extirex.webservice.decappmemomaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.decappmemomaint.client.MUDECMWINOUT_PARMS;
import com.softwarag.extirex.webservice.decappmemomaint.client.MUDECMWIN_PARMS;
import com.softwarag.extirex.webservice.decappmemomaint.client.holders.MUDECMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.decappmemomaint.client.holders.MUDECMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.decappmemomaint.client.holders.MUDECMWResponseOUT_PARMSHolder;

public class WSDecAppMemoMaint {

	public static synchronized void fetch(String PolicyNo, String EventId, MUDECMWResponseINOUT_PARMS1Holder inoutparms, MUDECMWResponseMSG_DATAHolder msgInfo, MUDECMWResponseOUT_PARMSHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service  = new DecAppMemoMaintLocator().getIASLIBPort();
		
		MUDECMWIN_PARMS action = new MUDECMWIN_PARMS("D");
		
        MUDECMWINOUT_PARMS inputs =  new MUDECMWINOUT_PARMS();
        
        inputs.setPOLICY_ID(new BigDecimal(PolicyNo));
		inputs.setUSER_ID(new String(""));
		inputs.setEVENT_ID(new BigDecimal(EventId));
		inputs.setSTD_EVENT_ID(new String(""));
		inputs.setSECOND_REQUEST_IND(new String(""));
		inputs.setAGENT(new String(""));
		inputs.setFILLER(new String(""));
		inputs.setAPPLICANT(new String(""));
		inputs.setWITHDRAWN_IND(new String(""));
		inputs.setTEXT(new String[60]);
		inputs.setINCOMPLETE_IND(new String(""));
		inputs.setDECLINED_IND(new String(""));
        inputs.setLOG_COUNTER(new BigDecimal("0"));
	
		service.MUDECMW(action, inputs, msgInfo, inoutparms, outparms);
		
		
	}
	
	public static synchronized void add( MUDECMWINOUT_PARMS inputs , MUDECMWResponseINOUT_PARMS1Holder inoutparms, MUDECMWResponseMSG_DATAHolder msgInfo, MUDECMWResponseOUT_PARMSHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service  = new DecAppMemoMaintLocator().getIASLIBPort();
		
		MUDECMWIN_PARMS action = new MUDECMWIN_PARMS("A");
		
		service.MUDECMW(action, inputs, msgInfo, inoutparms, outparms);
			
	}
	
	public static synchronized void edit(MUDECMWINOUT_PARMS inputs , MUDECMWResponseINOUT_PARMS1Holder inoutparms, MUDECMWResponseMSG_DATAHolder msgInfo, MUDECMWResponseOUT_PARMSHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service  = new DecAppMemoMaintLocator().getIASLIBPort();
		
		MUDECMWIN_PARMS action = new MUDECMWIN_PARMS("M");
		
		service.MUDECMW(action, inputs, msgInfo, inoutparms, outparms);
		
		
	}
}
