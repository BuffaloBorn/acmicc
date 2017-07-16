package com.epm.acmi.util;

import java.io.IOException;
/*import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;*/

import org.apache.log4j.Logger;

import java.util.TreeMap;


import com.epm.acmi.datamodel.MemoCodesDisplayList;
import com.softwarag.extirex.webservice.memocodes.client.WSMemoCodesCall;
import com.softwarag.extirex.webservice.seventcodes.client.WSStdEventCodesCall;
import com.softwarag.extirex.webservice.undstatuscodes.client.WSUndStatusCodesCall;


import edu.emory.mathcs.backport.java.util.concurrent.locks.ReentrantReadWriteLock;

public class ACMICache {

	private static TreeMap StdEventCodes;
	private static TreeMap UnderWriterStatusCodes;
	private static MemoCodesDisplayList MemoIdCodes;
	
	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Logger log = Logger.getLogger(ACMICache.class);
	
	/**
	 * Load all Standard Event Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDUnderWriterStatusCodes()
	{
		//String service = "Under Writer Status Codes";
		
//		try {
			UnderWriterStatusCodes = WSUndStatusCodesCall.fetch();
//		} catch (RemoteException e) {
//			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
//			
//		} catch (ServiceException e) {
//			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
//		}
	}
	
	/**
	 * Load all Standard Event Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDStdEventCodes()
	{
		
		
		
//		String service = "Std Event Codes";
//		try {
			StdEventCodes = WSStdEventCodesCall.fetch();
//		} catch (RemoteException e) 
//		{
//			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
//			
//		} catch (ServiceException e) {
//			log.error("Service Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
//		}
	}

	/**
	 * Load all Standard Event Codes  from WSacmi db into ACMICache
	 */
	public static synchronized void loaDMemoIdCodes()
	{
		String service = "Memo Id Codes";
		try {
			MemoIdCodes = WSMemoCodesCall.fetch();
//		} catch (RemoteException e) {
//			log.error("Remote Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		} catch (IOException e) {
			log.error("IOException Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() +" Web Service: " + service );
		} 
	}

	public static TreeMap getStdEventCodes() {
		rwl.readLock().lock();
		try {
			return StdEventCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}

	public static TreeMap getUnderWriterStatusCodes() {
		rwl.readLock().lock();
		try {
			return UnderWriterStatusCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}
	
	public static MemoCodesDisplayList getMemoIdCodes() {
		rwl.readLock().lock();
		try {
			return MemoIdCodes;
		} finally {
			rwl.readLock().unlock();
		}
	}

}
