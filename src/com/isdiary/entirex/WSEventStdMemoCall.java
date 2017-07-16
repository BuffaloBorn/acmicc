package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.evtstdmemomaint.client.EvtStdMemoMaintLocator;
import com.softwarag.extirex.webservice.evtstdmemomaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.evtstdmemomaint.client.MUESMMWINOUT_PARMS;
import com.softwarag.extirex.webservice.evtstdmemomaint.client.MUESMMWIN_PARMS;
import com.softwarag.extirex.webservice.evtstdmemomaint.client.holders.MUESMMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.evtstdmemomaint.client.holders.MUESMMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.evtstdmemomaint.client.holders.MUESMMWResponseOUT_PARMSHolder;

public class WSEventStdMemoCall {
	

	public static void fetch(String policyNo, String eventId,MUESMMWResponseINOUT_PARMS1Holder inoutparms,	MUESMMWResponseMSG_DATAHolder msgInfo,		MUESMMWResponseOUT_PARMSHolder outparms) throws RemoteException, ServiceException 
	{
		
		IASLIBPort service = new EvtStdMemoMaintLocator().getIASLIBPort();

		
		MUESMMWIN_PARMS action = new MUESMMWIN_PARMS();
		action.setACTION("D");
		
		MUESMMWINOUT_PARMS inputs = new MUESMMWINOUT_PARMS();
		
		//inputs.setDATE_COMPLETED_YYYYMMDD(new String(""));
		inputs.setEVENT_ID(new BigDecimal(eventId));
		inputs.setEVENT_STATUS_CURRENT(new String(""));
		inputs.setPOLICY_ID(new BigDecimal(policyNo));
		//inputs.setRECIPIENT_PERSON_ID(new String(""));
		inputs.setRECIPIENT_SEARCH_NAME(new String(""));
		inputs.setSECOND_REQUEST_IND(new String(""));
		inputs.setSTD_EVENT_ID(new String(""));
		
		inputs.setUSER_ID(new String(""));
		inputs.setLOG_COUNTER(new BigDecimal("0"));
		
		service.MUESMMW(action, inputs, msgInfo, inoutparms, outparms);
			
	}
	
	
	public static void add(MUESMMWINOUT_PARMS inputs,MUESMMWResponseINOUT_PARMS1Holder inoutparms,	MUESMMWResponseMSG_DATAHolder msgInfo,		MUESMMWResponseOUT_PARMSHolder outparms) throws RemoteException, ServiceException 
	{
		
		IASLIBPort service = new EvtStdMemoMaintLocator().getIASLIBPort();

		
		MUESMMWIN_PARMS action = new MUESMMWIN_PARMS();
		
		action.setACTION("A");
				
		service.MUESMMW(action, inputs, msgInfo, inoutparms, outparms);
			
	}
	
	public static void edit(MUESMMWINOUT_PARMS inputs,MUESMMWResponseINOUT_PARMS1Holder inoutparms,	MUESMMWResponseMSG_DATAHolder msgInfo,		MUESMMWResponseOUT_PARMSHolder outparms) throws RemoteException, ServiceException 
	{
		
		IASLIBPort service = new EvtStdMemoMaintLocator().getIASLIBPort();

		
		MUESMMWIN_PARMS action = new MUESMMWIN_PARMS();
		
		action.setACTION("M");
				
		service.MUESMMW(action, inputs, msgInfo, inoutparms, outparms);
			
	}
	
}
