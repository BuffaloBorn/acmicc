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

		ACPCYMWIN_PARM displayAction = new ACPCYMWIN_PARM();
	
		ACPCYMWINOUT_PARM displayInputs = new ACPCYMWINOUT_PARM();
		
		ACPCYMWResponseMSG_INFOHolder displayMsgInfo = new ACPCYMWResponseMSG_INFOHolder();
		
		ACPCYMWResponseINOUT_PARM1Holder displayOutparms = new ACPCYMWResponseINOUT_PARM1Holder();
		
		
		displayAction.setACTION("D");
		displayAction.setPERSON_ID(new BigDecimal(person_id));
		displayAction.setUSER_ID(user_id);
		
		displayInputs.setSMOKER_IND(new String(""));
		displayInputs.setLOG_COUNTER(new BigDecimal("0"));
		
		service.ACPCYMW(displayAction, displayInputs, displayMsgInfo , displayOutparms );
		
		ACPCYMWIN_PARM action = new ACPCYMWIN_PARM();
		
		action.setACTION("M");
		action.setPERSON_ID(new BigDecimal(person_id));
		action.setUSER_ID(user_id);
		
		ACPCYMWINOUT_PARM inputs = new ACPCYMWINOUT_PARM();
		
		inputs.setSMOKER_IND(smoker_ind);
		inputs.setLOG_COUNTER(displayOutparms.value.getLOG_COUNTER1());
		
		ACPCYMWResponseINOUT_PARM1Holder outparms = new ACPCYMWResponseINOUT_PARM1Holder();
	
		service.ACPCYMW(action, inputs, msgInfo, outparms);
	}
}
