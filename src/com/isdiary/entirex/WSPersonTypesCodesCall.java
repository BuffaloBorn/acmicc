package com.isdiary.entirex;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.rpc.ServiceException;

import com.isdiary.esb.LookupTableServiceClient;

public class WSPersonTypesCodesCall {

	public static synchronized TreeMap fetch()throws ServiceException, RemoteException
	{
		TreeMap localTMap = new TreeMap();
		
		TreeMap map = LookupTableServiceClient.GetLookupTableMap("Person Type");
		
		String key;
		 Set set= map.keySet (  ) ; 
	     Iterator iter = set.iterator (  ) ; 
	     
	     localTMap.put(" ", " ");
	     while ( iter.hasNext (  )  )  
	     {  
	    	
	    	  key = iter.next().toString();
	    	 localTMap.put(key, key);
	      }  
		
		
		return localTMap;
	}
}
