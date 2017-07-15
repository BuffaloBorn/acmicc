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
	
		service.ACCPEMW1(action, msgInfo, inparms);
		
	}
	


}
