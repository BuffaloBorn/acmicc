package com.isdiary.entirex;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.policypersonmaint2.client.ACCPEMW1IN_PARM;
import com.softwarag.extirex.webservice.policypersonmaint2.client.IASLIBPort;
import com.softwarag.extirex.webservice.policypersonmaint2.client.PolicyPersonMaint2Locator;
import com.softwarag.extirex.webservice.policypersonmaint2.client.holders.ACCPEMW1ResponseIN_PARM1Holder;
import com.softwarag.extirex.webservice.policypersonmaint2.client.holders.ACCPEMW1ResponseMSG_INFOHolder;

public class WSPolicyPersonMaint2Call {
	
	
	public static synchronized void update(ACCPEMW1IN_PARM action, ACCPEMW1ResponseIN_PARM1Holder inparms, ACCPEMW1ResponseMSG_INFOHolder msgInfo) throws ServiceException, RemoteException
	{
		IASLIBPort service = new PolicyPersonMaint2Locator().getIASLIBPort();
	
		ACCPEMW1IN_PARM displayAction = new ACCPEMW1IN_PARM();
		
		ACCPEMW1ResponseMSG_INFOHolder displayMsgInfo = new ACCPEMW1ResponseMSG_INFOHolder();
		
		ACCPEMW1ResponseIN_PARM1Holder displayInparms = new ACCPEMW1ResponseIN_PARM1Holder();
		
		displayAction.setACTION("D");
		displayAction.setUSER_ID(action.getUSER_ID());
		displayAction.setPERSON_STATUS_IND(action.getPERSON_STATUS_IND());
		displayAction.setPERSON_ID(action.getPERSON_ID());
		displayAction.setPOLICY_ID(action.getPOLICY_ID());
		displayAction.setPOLICY_PERSON_TYPE_CURRENT(action.getPOLICY_PERSON_TYPE_CURRENT());
		displayAction.setLOG_COUNTER(action.getLOG_COUNTER());
		
		service.ACCPEMW1(displayAction, displayMsgInfo , displayInparms);
		
		action.setLOG_COUNTER(displayAction.getLOG_COUNTER());
		
		service.ACCPEMW1(action, msgInfo, inparms);
		
	}
	


}
