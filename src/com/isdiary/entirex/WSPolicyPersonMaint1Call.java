package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.policypersonmaint1.client.ACCPEMWINOUT_PARM;
import com.softwarag.extirex.webservice.policypersonmaint1.client.ACCPEMWIN_PARM;
import com.softwarag.extirex.webservice.policypersonmaint1.client.IASLIBPort;
import com.softwarag.extirex.webservice.policypersonmaint1.client.PolicyPersonMaint1Locator;
import com.softwarag.extirex.webservice.policypersonmaint1.client.holders.ACCPEMWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policypersonmaint1.client.holders.ACCPEMWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.policypersonmaint1.client.holders.ACCPEMWResponseOUT_PARMHolder;

public class WSPolicyPersonMaint1Call {
	
	public static synchronized void fecth(String person_id, String policy_id,  String userid,  ACCPEMWResponseINOUT_PARM1Holder inoutparms, ACCPEMWResponseMSG_INFOHolder msgInfo, ACCPEMWResponseOUT_PARMHolder outparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new PolicyPersonMaint1Locator().getIASLIBPort();
				
		ACCPEMWINOUT_PARM inputs = new ACCPEMWINOUT_PARM();
		
		inputs .setPOLICY_ID(new BigDecimal(policy_id));
		inputs.setCONDITION_CODE(new String[4]);
		inputs.setCONDITION_TEXT(new String[4]);
		inputs.setDISPLAY_DATE(new BigDecimal("0"));
		inputs.setPERSON_ID(new BigDecimal(person_id));
		inputs.setPERSON_STATUS_IND(new String(""));
		
		ACCPEMWIN_PARM action = new ACCPEMWIN_PARM();
		action.setACTION("D");
		action.setUSER_ID(userid);
		
		service.ACCPEMW(action, inputs , msgInfo, inoutparms , outparms);
	}

	
	public static synchronized void update(String userid, ACCPEMWINOUT_PARM inputs, ACCPEMWResponseINOUT_PARM1Holder inoutparms,ACCPEMWResponseMSG_INFOHolder msgInfo, ACCPEMWResponseOUT_PARMHolder outparms, String proformAction) throws ServiceException, RemoteException
	{
		IASLIBPort service = new PolicyPersonMaint1Locator().getIASLIBPort();	
		
		ACCPEMWIN_PARM action = new ACCPEMWIN_PARM();
		action.setACTION(proformAction);
		action.setUSER_ID(userid);
		
		service.ACCPEMW(action, inputs , msgInfo, inoutparms , outparms);
	}

	
}
