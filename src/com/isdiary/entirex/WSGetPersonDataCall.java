package com.isdiary.entirex;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.epm.acmi.util.MiscellaneousUtils;
import com.softwarag.extirex.webservice.getpersondata.client.ACPCYMW1IN_PARMS;
import com.softwarag.extirex.webservice.getpersondata.client.GetPersonDataLocator;
import com.softwarag.extirex.webservice.getpersondata.client.IASLIBPort;
import com.softwarag.extirex.webservice.getpersondata.client.holders.ACPCYMW1ResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.getpersondata.client.holders.ACPCYMW1ResponseOUT_PARMSHolder;

public class WSGetPersonDataCall {

	private static Logger log = MiscellaneousUtils.getIASLogger();
	private static String classAction = "Get Person Data Data";
	
	public static synchronized void fetch(ACPCYMW1IN_PARMS inputs, ACPCYMW1ResponseMSG_INFOHolder msgInfo, ACPCYMW1ResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		log.debug("Fetching " + classAction);
		
		IASLIBPort service = new GetPersonDataLocator().getIASLIBPort();
		
		service.ACPCYMW1(inputs, msgInfo, outparms);
		
		log.debug("Finish....fetching " + classAction);
	}
	
	public static synchronized void add(ACPCYMW1IN_PARMS inputs, ACPCYMW1ResponseMSG_INFOHolder msgInfo, ACPCYMW1ResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		log.debug("Fetching " + classAction);
		
		IASLIBPort service = new GetPersonDataLocator().getIASLIBPort();
		
		service.ACPCYMW1(inputs, msgInfo, outparms);
		
		log.debug("Finish....fetching " + classAction);
	}
		
	
	public static synchronized void edit(ACPCYMW1IN_PARMS inputs, ACPCYMW1ResponseMSG_INFOHolder msgInfo, ACPCYMW1ResponseOUT_PARMSHolder outparms ) throws ServiceException, RemoteException
	{
		log.debug("Fetching " + classAction);
		
		IASLIBPort service = new GetPersonDataLocator().getIASLIBPort();
		
		service.ACPCYMW1(inputs, msgInfo, outparms);
		
		log.debug("Finish....fetching " + classAction);
	}
}
