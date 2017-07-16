package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.amendmaint.client.AmendMaintLocator;
import com.softwarag.extirex.webservice.amendmaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.amendmaint.client.MUAMNMWINOUT_PARMS;
import com.softwarag.extirex.webservice.amendmaint.client.MUAMNMWIN_PARMS;
import com.softwarag.extirex.webservice.amendmaint.client.holders.MUAMNMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.amendmaint.client.holders.MUAMNMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.amendmaint.client.holders.MUAMNMWResponseOUT_PARMSHolder;

public class WSAmendMaintCall {
	
	public static synchronized void fetch(String user,String PolicyNo, MUAMNMWResponseINOUT_PARMS1Holder inoutparms, MUAMNMWResponseOUT_PARMSHolder outparms, MUAMNMWResponseMSG_DATAHolder msgInfo) throws ServiceException, RemoteException
	{
		IASLIBPort service = new AmendMaintLocator().getIASLIBPort();

		MUAMNMWIN_PARMS action = new MUAMNMWIN_PARMS();
		action.setACTION("D");
		action.setUSER_ID(user);
		
		MUAMNMWINOUT_PARMS inputs = new MUAMNMWINOUT_PARMS();
		
		inputs.setPOLICY_ID(new BigDecimal(PolicyNo));
		inputs.setDESCRIPTION(new String(""));
		inputs.setTEXT(new String[191]);
		inputs.setTRUST_IND(new String(""));
		inputs.setPROXY_IND(new String(""));
		inputs.setLOG_COUNTER(new BigDecimal("0"));
		
		service.MUAMNMW(action, inputs, msgInfo, inoutparms, outparms);
	}
	
	public static synchronized void edit(String user,MUAMNMWINOUT_PARMS inputs , MUAMNMWResponseINOUT_PARMS1Holder inoutparms, MUAMNMWResponseOUT_PARMSHolder outparms, MUAMNMWResponseMSG_DATAHolder msgInfo) throws ServiceException, RemoteException
	{
		IASLIBPort service = new AmendMaintLocator().getIASLIBPort();

		MUAMNMWIN_PARMS action = new MUAMNMWIN_PARMS();
		action.setACTION("M");
		action.setUSER_ID(user);
		
		service.MUAMNMW(action, inputs, msgInfo, inoutparms, outparms);
	}

	public static void add(String user, MUAMNMWINOUT_PARMS inputs, MUAMNMWResponseINOUT_PARMS1Holder inoutparms,	MUAMNMWResponseOUT_PARMSHolder outparms,	MUAMNMWResponseMSG_DATAHolder msgInfo) throws ServiceException, RemoteException 
	{
		IASLIBPort service = new AmendMaintLocator().getIASLIBPort();

		MUAMNMWIN_PARMS action = new MUAMNMWIN_PARMS();
		action.setACTION("A");
		action.setUSER_ID(user);
		
		service.MUAMNMW(action, inputs, msgInfo, inoutparms, outparms);
		
	}

}
