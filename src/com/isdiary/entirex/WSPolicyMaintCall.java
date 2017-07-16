package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.policymaint.client.ACPLYMWINOUT_PARM;
import com.softwarag.extirex.webservice.policymaint.client.ACPLYMWIN_PARM;
import com.softwarag.extirex.webservice.policymaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.policymaint.client.PolicyMaintLocator;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseIN_PARM1Holder;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.policymaint.client.holders.ACPLYMWResponseOUT_PARMHolder;

public class WSPolicyMaintCall {
	
	public static synchronized void fetch(String policyNo, String userId, ACPLYMWResponseIN_PARM1Holder inparms, ACPLYMWResponseINOUT_PARM1Holder inoutparms, ACPLYMWResponseOUT_PARMHolder outparms, ACPLYMWResponseMSG_INFOHolder msgInfo) throws ServiceException, RemoteException
	{
		IASLIBPort service = new PolicyMaintLocator().getIASLIBPort();

		ACPLYMWIN_PARM action = new ACPLYMWIN_PARM();
		action.setACTION("D");

		ACPLYMWINOUT_PARM inputs = new ACPLYMWINOUT_PARM();
		
		inputs.setPOLICY_ID(new BigDecimal(policyNo));
		inputs.setUNDERW_STATUS_CURRENT(new String(""));
		inputs.setUSER_ID(userId);
		inputs.setLOG_COUNTER(new BigDecimal("0"));
		
		service.ACPLYMW(action, inputs, msgInfo, inparms, inoutparms, outparms);
		
	}

	public static synchronized void updateUnderwriterStatus(String policyNo, String userId, String underWriterStatus, ACPLYMWResponseMSG_INFOHolder msgInfo, String logCounter)throws ServiceException, RemoteException
	{
		IASLIBPort service = new PolicyMaintLocator().getIASLIBPort();

		ACPLYMWResponseIN_PARM1Holder inparms = new ACPLYMWResponseIN_PARM1Holder();
		ACPLYMWResponseOUT_PARMHolder outparms = new ACPLYMWResponseOUT_PARMHolder();
		ACPLYMWResponseINOUT_PARM1Holder inoutparms = new ACPLYMWResponseINOUT_PARM1Holder();
		
		
		ACPLYMWIN_PARM action = new ACPLYMWIN_PARM();
		action.setACTION("M");

		ACPLYMWINOUT_PARM inputs = new ACPLYMWINOUT_PARM();
		
		inputs.setPOLICY_ID(new BigDecimal(policyNo));
		inputs.setUNDERW_STATUS_CURRENT(underWriterStatus);
		inputs.setUSER_ID(userId);
		inputs.setLOG_COUNTER(new BigDecimal(logCounter));
		
		
		service.ACPLYMW(action, inputs, msgInfo, inparms, inoutparms, outparms);
		
	}
	
	
}
