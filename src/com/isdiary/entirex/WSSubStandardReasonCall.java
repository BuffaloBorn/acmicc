package com.isdiary.entirex;

import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import javax.xml.rpc.ServiceException;

import com.isdiary.esb.LookupTableServiceClient;

public class WSSubStandardReasonCall {

	public static synchronized void fetch(TreeMap SortedReasonCodeTMap, TreeMap LookupSortReasonCode, TreeMap ReasonCodeToSortLookup)throws ServiceException, RemoteException
	{
		
		//TreeMap localTMap = new TreeMap();
		TreeMap map = LookupTableServiceClient.GetLookupTableMap("Sub-Standard Reason");
	
		NumberFormat f =  new DecimalFormat("#00");
	    
		
		 String key;
		 Set set= map.keySet (  ) ; 
	     Iterator iter = set.iterator (  ) ; 
	     
	     char charAtI;
		 int chType;
		 int order=0; 
	     
		 SortedReasonCodeTMap.put(" ", " ");
		 LookupSortReasonCode.put(" ", " ");
		 ReasonCodeToSortLookup.put(" ", " ");
		
	     while ( iter.hasNext (  )  )  
	     {  
	    	
	    	 key = iter.next().toString();
	    	 
	    	 charAtI = key.charAt(0);
	    	 chType = Character.getType(charAtI);
	    	 
	    	 if (chType == 1)  
	    	  {
	    		 SortedReasonCodeTMap.put(f.format(order).toString(), key+ ": " + map.get ( key  ));
	    		 LookupSortReasonCode.put(f.format(order).toString(), key);
	    		 ReasonCodeToSortLookup.put(key, f.format(order).toString());
	    		 order++;
	    	  }
	      }  


	     iter = set.iterator (  ) ;
	     
	     while ( iter.hasNext (  )  )  
	     {  
	    	
	    	 key = iter.next().toString();
	    	 
	    	 charAtI = key.charAt(0);
	    	 chType = Character.getType(charAtI);
	    	 
	    	 if (chType == 9)  
	    	  {
	    		 SortedReasonCodeTMap.put(Integer.toString(order), key+ ": " + map.get ( key  ));
	    		 LookupSortReasonCode.put(Integer.toString(order), key);
	    		 ReasonCodeToSortLookup.put(key, Integer.toString(order));
	    		 order++;
	    	  }
	      }  


		
	}
	
}
