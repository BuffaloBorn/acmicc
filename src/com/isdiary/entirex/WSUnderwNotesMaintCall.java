package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.underwnotesmaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.underwnotesmaint.client.MUNOTMWINOUT_PARMS;
import com.softwarag.extirex.webservice.underwnotesmaint.client.MUNOTMWIN_PARMS;
import com.softwarag.extirex.webservice.underwnotesmaint.client.UnderwNotesMaintLocator;
import com.softwarag.extirex.webservice.underwnotesmaint.client.holders.MUNOTMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.underwnotesmaint.client.holders.MUNOTMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.underwnotesmaint.client.holders.MUNOTMWResponseOUT_PARMSHolder;

public class WSUnderwNotesMaintCall {

	public static synchronized void fetch(String user, MUNOTMWResponseOUT_PARMSHolder outparms, MUNOTMWResponseINOUT_PARMS1Holder inoutparms, String PolicyNo, MUNOTMWResponseMSG_DATAHolder msgInfo) throws RemoteException, ServiceException
	{
		IASLIBPort service = new UnderwNotesMaintLocator().getIASLIBPort();
	
		MUNOTMWIN_PARMS action = new MUNOTMWIN_PARMS();
		action.setACTION("D");
		action.setUSER_ID(user);

		MUNOTMWINOUT_PARMS inputs = new MUNOTMWINOUT_PARMS();
		
		
		inputs.setPOLICY_ID(new BigDecimal(PolicyNo));
		inputs.setDESCRIPTION(new String(""));
		inputs.setTEXT(new String[191]);
		inputs.setLOG_COUNTER(new BigDecimal("0"));

	
		service.MUNOTMW(action, inputs, msgInfo, inoutparms, outparms);
		
	}
	
	public static synchronized void edit(String user, MUNOTMWINOUT_PARMS inputs , MUNOTMWResponseOUT_PARMSHolder outparms, MUNOTMWResponseINOUT_PARMS1Holder inoutparms, MUNOTMWResponseMSG_DATAHolder msgInfo) throws RemoteException, ServiceException
	{
		
		IASLIBPort service = new UnderwNotesMaintLocator().getIASLIBPort();
		
		MUNOTMWIN_PARMS action = new MUNOTMWIN_PARMS();
		action.setUSER_ID(user);
		action.setACTION("M");
		
		service.MUNOTMW(action, inputs, msgInfo, inoutparms, outparms);
		
	}

	public static void add(String user, MUNOTMWINOUT_PARMS inputs,MUNOTMWResponseOUT_PARMSHolder outparms, MUNOTMWResponseINOUT_PARMS1Holder inoutparms,	MUNOTMWResponseMSG_DATAHolder msgInfo)  throws RemoteException, ServiceException 
	{
		IASLIBPort service = new UnderwNotesMaintLocator().getIASLIBPort();
		
		MUNOTMWIN_PARMS action = new MUNOTMWIN_PARMS();
		action.setUSER_ID(user);
		action.setACTION("A");
		
		service.MUNOTMW(action, inputs, msgInfo, inoutparms, outparms);
		
	}
	
}
