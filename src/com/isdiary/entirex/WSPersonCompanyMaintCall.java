package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.personcompanymaint.client.ACPCYMWINOUT_PARM;
import com.softwarag.extirex.webservice.personcompanymaint.client.ACPCYMWIN_PARM;
import com.softwarag.extirex.webservice.personcompanymaint.client.IASLIBPort;
import com.softwarag.extirex.webservice.personcompanymaint.client.PersonCompanyMaintLocator;
import com.softwarag.extirex.webservice.personcompanymaint.client.holders.ACPCYMWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.personcompanymaint.client.holders.ACPCYMWResponseMSG_INFOHolder;

public class WSPersonCompanyMaintCall {

	public static synchronized void update(String person_id, String smoker_ind, String user_id,ACPCYMWResponseMSG_INFOHolder msgInfo) throws ServiceException, RemoteException
	{
		IASLIBPort service = new PersonCompanyMaintLocator().getIASLIBPort();

		ACPCYMWIN_PARM action = new ACPCYMWIN_PARM();
		
	
		action.setACTION("M");
		action.setPERSON_ID(new BigDecimal(person_id));
		action.setUSER_ID(user_id);
		
		ACPCYMWINOUT_PARM inputs = new ACPCYMWINOUT_PARM();
		
		inputs.setSMOKER_IND(smoker_ind);
		
		ACPCYMWResponseINOUT_PARM1Holder outparms = new ACPCYMWResponseINOUT_PARM1Holder();
	
		service.ACPCYMW(action, inputs, msgInfo, outparms);
	}
}
