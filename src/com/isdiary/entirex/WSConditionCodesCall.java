package com.isdiary.entirex;

import java.rmi.RemoteException;
import java.util.TreeMap;

import javax.xml.rpc.ServiceException;

import com.isdiary.esb.LookupTableServiceClient;

public class WSConditionCodesCall {

	public static synchronized void fetch(TreeMap conditionCodes, TreeMap conditionCodesExtendedFields)throws ServiceException, RemoteException
	{
		
		conditionCodes.put("", " ");
			
		//map =	LookupTableServiceClient.GetLookupTableMap("Condition");
		
	
		
		LookupTableServiceClient.GetLookupTableMapWithExtendedFields("Condition",conditionCodes,conditionCodesExtendedFields );
		
		 /*String key;
		 Set set= map.keySet (  ) ; 
	     Iterator iter = set.iterator (  ) ; 
	      
	     
	     while ( iter.hasNext (  )  )  
	     {  
	    	
	    	  key = iter.next().toString();
	    	 localTMap.put(key, key+ ": " + map.get ( key  ));
	      }  


		return localTMap;*/	
	   }
}
