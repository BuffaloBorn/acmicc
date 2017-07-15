package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.ridermaint.client.ACPLPMWINOUT_PARM;
import com.softwarag.extirex.webservice.ridermaint.client.ACPLPMWINOUT_PARMRIDER_INFOsRIDER_INFO;
import com.softwarag.extirex.webservice.ridermaint.client.ACPLPMWIN_PARM;
import com.softwarag.extirex.webservice.ridermaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.ridermaint.client.RiderMaintLocator;
import com.softwarag.extirex.webservice.ridermaint.client.holders.ACPLPMWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.ridermaint.client.holders.ACPLPMWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.ridermaint.client.holders.ACPLPMWResponseOUT_PARMHolder;

public class WSRiderMainCall {

	public static synchronized void fecth(String policyNo, String personId, ACPLPMWResponseMSG_INFOHolder msgInfo, ACPLPMWResponseINOUT_PARM1Holder inoutparms, ACPLPMWResponseOUT_PARMHolder outparms) throws ServiceException, RemoteException
	{
		
		IASLIBPort service = new RiderMaintLocator().getIASLIBPort();

		ACPLPMWIN_PARM action = new ACPLPMWIN_PARM();
		
		action.setUSER_ID(new String(""));
		
		action.setACTION("D");
		
		ACPLPMWINOUT_PARM inputs = new ACPLPMWINOUT_PARM();
		
		inputs.setPOLICY_ID(new BigDecimal(policyNo));
		
		inputs.setPERSON_ID(new BigDecimal(personId));
		
		inputs.setRIDER_FLAG(new String(""));
		
		inputs.setRIDER_INFOs(new ACPLPMWINOUT_PARMRIDER_INFOsRIDER_INFO[5]);
		
		service.ACPLPMW(action , inputs, msgInfo, inoutparms , outparms );
		
	}
	
	public static synchronized void edit(String user, ACPLPMWINOUT_PARM inputs, ACPLPMWResponseMSG_INFOHolder msgInfo, ACPLPMWResponseINOUT_PARM1Holder inoutparms, ACPLPMWResponseOUT_PARMHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new RiderMaintLocator().getIASLIBPort();
		
		ACPLPMWIN_PARM action = new ACPLPMWIN_PARM();
		
		action.setUSER_ID(user);
		action.setACTION("M");
		
		service.ACPLPMW(action , inputs, msgInfo, inoutparms , outparms );
		
	}
	
	public static synchronized void add(String user, ACPLPMWINOUT_PARM inputs, ACPLPMWResponseMSG_INFOHolder msgInfo, ACPLPMWResponseINOUT_PARM1Holder inoutparms, ACPLPMWResponseOUT_PARMHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new RiderMaintLocator().getIASLIBPort();
		
		ACPLPMWIN_PARM action = new ACPLPMWIN_PARM();
	
		action.setUSER_ID(user);
		action.setACTION("A");
		
		service.ACPLPMW(action , inputs, msgInfo, inoutparms , outparms );
		
	}
}
