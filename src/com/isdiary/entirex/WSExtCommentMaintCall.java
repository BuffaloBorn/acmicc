package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.extcommmaint.client.ExtCommMaintLocator;
import com.softwarag.extirex.webservice.extcommmaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.extcommmaint.client.MUTXTMWINOUT_PARMS;
import com.softwarag.extirex.webservice.extcommmaint.client.MUTXTMWIN_PARMS;
import com.softwarag.extirex.webservice.extcommmaint.client.holders.MUTXTMWResponseINOUT_PARMS1Holder;
import com.softwarag.extirex.webservice.extcommmaint.client.holders.MUTXTMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.extcommmaint.client.holders.MUTXTMWResponseOUT_PARMSHolder;

public class WSExtCommentMaintCall {

	public static synchronized void fetch(String user, String PolicyNo,  MUTXTMWResponseINOUT_PARMS1Holder inoutparms, MUTXTMWResponseMSG_DATAHolder msgInfo, MUTXTMWResponseOUT_PARMSHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new ExtCommMaintLocator().getIASLIBPort();
		
		MUTXTMWIN_PARMS inparams = new MUTXTMWIN_PARMS();
		inparams.setUPDATE_FLAG(new String("N"));
		inparams.setUSER_ID(user);
		
		MUTXTMWINOUT_PARMS inputs = new MUTXTMWINOUT_PARMS();
		
		inputs.setPOLICY_ID(new BigDecimal(PolicyNo));
		inputs.setDESCRIPTION(new String(""));
		inputs.setTEXT(new String[60]);
			
	
		service.MUTXTMW(inparams, inputs, msgInfo, inoutparms, outparms);
	}
	
	
	public static synchronized void edit(String user, MUTXTMWINOUT_PARMS inputs,  MUTXTMWResponseINOUT_PARMS1Holder inoutparms, MUTXTMWResponseMSG_DATAHolder msgInfo, MUTXTMWResponseOUT_PARMSHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new ExtCommMaintLocator().getIASLIBPort();
		
		MUTXTMWIN_PARMS inparams = new MUTXTMWIN_PARMS();
		inparams.setUPDATE_FLAG(new String("Y"));
		inparams.setUSER_ID(user);

		service.MUTXTMW(inparams, inputs, msgInfo, inoutparms, outparms);
	}


	public static void add(String user, MUTXTMWINOUT_PARMS inputs,MUTXTMWResponseINOUT_PARMS1Holder inoutparms,MUTXTMWResponseMSG_DATAHolder msgInfo,MUTXTMWResponseOUT_PARMSHolder outparms) throws ServiceException, RemoteException 
	{
		IASLIBPort service = new ExtCommMaintLocator().getIASLIBPort();
		
		MUTXTMWIN_PARMS inparams = new MUTXTMWIN_PARMS();
		inparams.setUSER_ID(user);
		inparams.setUPDATE_FLAG(new String("Y"));

		service.MUTXTMW(inparams, inputs, msgInfo, inoutparms, outparms);
		
	}
	
}
