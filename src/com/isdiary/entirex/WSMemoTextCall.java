package com.isdiary.entirex;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import com.softwarag.extirex.webservice.memotext.client.IASLIBPort;
import com.softwarag.extirex.webservice.memotext.client.MUMEMMWIN_PARM;
import com.softwarag.extirex.webservice.memotext.client.MemoTextLocator;
import com.softwarag.extirex.webservice.memotext.client.holders.MUMEMMWResponseMSG_DATAHolder;
import com.softwarag.extirex.webservice.memotext.client.holders.MUMEMMWResponseOUT_PARMHolder;

public class WSMemoTextCall {

	public static synchronized void fetch(String memocode, MUMEMMWResponseMSG_DATAHolder msgInfo, MUMEMMWResponseOUT_PARMHolder outparms) throws ServiceException, RemoteException
	{
		
		IASLIBPort service  = new MemoTextLocator().getIASLIBPort();
		
		
		MUMEMMWIN_PARM inputs = new MUMEMMWIN_PARM();
		
		inputs.setGET_MEMO_ID(memocode);
		
		service.MUMEMMW(inputs, msgInfo , outparms);
		
	}
	
}
