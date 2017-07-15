package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.policypersoncovmaint.client.IAPPCMWINOUT_PARM;
import com.softwarag.extirex.webservice.policypersoncovmaint.client.IAPPCMWIN_PARM;
import com.softwarag.extirex.webservice.policypersoncovmaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.policypersoncovmaint.client.PolicyPersonCovMaintLocator;
import com.softwarag.extirex.webservice.policypersoncovmaint.client.holders.IAPPCMWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policypersoncovmaint.client.holders.IAPPCMWResponseMESSAGE_INFOHolder;
import com.softwarag.extirex.webservice.policypersoncovmaint.client.holders.IAPPCMWResponseOUT_PARMHolder;

public class WSPolicyPersonCovMainCall {

	public static synchronized void fetch( String policyNo, String userId, String personId, String coveragecode,String displaydate,IAPPCMWResponseMESSAGE_INFOHolder msgInfo, IAPPCMWResponseINOUT_PARM1Holder inoutparms, IAPPCMWResponseOUT_PARMHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new PolicyPersonCovMaintLocator().getIASLIBPort();
		
		IAPPCMWIN_PARM action = new IAPPCMWIN_PARM();
	
		action.setACTION("D");
		action.setEND_DATE("");
		action.setSTART_DATE("");
		
		IAPPCMWINOUT_PARM inputs = new IAPPCMWINOUT_PARM();
	
		inputs.setUSER_ID(userId);
		inputs .setPOLICY_ID(new BigDecimal(policyNo));
		inputs.setCOVERAGE_CODE(coveragecode);
		inputs.setPERSON_ID(new BigDecimal(personId));
		inputs.setSS_CCODE_TIME_PERIOD(new String(""));
		inputs.setSUB_EFF_DATE(new BigDecimal("0"));
		inputs.setCONDITION_CODE( new String[4]);
		inputs.setCONDITION_TEXT( new String[4]);
		inputs.setSUB_STANDARD_RISK_CODE( new String(""));
		inputs.setDISPLAY_DATE(displaydate);
		
		service.IAPPCMW(action, inputs, msgInfo , inoutparms, outparms);
	
	}
	
	public static synchronized void update( IAPPCMWINOUT_PARM inputs,IAPPCMWResponseMESSAGE_INFOHolder msgInfo, IAPPCMWResponseINOUT_PARM1Holder inoutparms, IAPPCMWResponseOUT_PARMHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new PolicyPersonCovMaintLocator().getIASLIBPort();
		
		IAPPCMWIN_PARM action = new IAPPCMWIN_PARM();
	
		action.setACTION("M");
		action.setEND_DATE("");
		action.setSTART_DATE("");
		
//		IAPPCMWINOUT_PARM inputs = new IAPPCMWINOUT_PARM();
	
//		inputs.setUSER_ID(userId);
//		inputs .setPOLICY_ID(new BigDecimal(policyNo));
//		inputs.setCOVERAGE_CODE(coveragecode);
//		inputs.setPERSON_ID(new BigDecimal(personId));
//		inputs.setSS_CCODE_TIME_PERIOD(new String(""));
//		inputs.setSUB_EFF_DATE(new BigDecimal("0"));
//		inputs.setCONDITION_CODE( new String[4]);
//		inputs.setCONDITION_TEXT( new String[4]);
//		inputs.setSUB_STANDARD_RISK_CODE( new String(""));
		
		service.IAPPCMW(action, inputs, msgInfo , inoutparms, outparms);
	
	}
}
