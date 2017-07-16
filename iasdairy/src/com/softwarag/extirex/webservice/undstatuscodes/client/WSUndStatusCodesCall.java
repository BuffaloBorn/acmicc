package com.softwarag.extirex.webservice.undstatuscodes.client;


import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;



/*import com.softwarag.extirex.webservice.undstatuscodes.client.ACSLIBPort;
import com.softwarag.extirex.webservice.undstatuscodes.client.ACUSTWS2MORE;
import com.softwarag.extirex.webservice.undstatuscodes.client.ACUSTWS2ResponseOUT_PARMSsOUT_PARMS;
import com.softwarag.extirex.webservice.undstatuscodes.client.UndStatusCodesLocator;
import com.softwarag.extirex.webservice.undstatuscodes.client.holders.ACUSTWS2ResponseOUT_PARMSsHolder;
import com.softwarag.extirex.webservice.undstatuscodes.client.holders.ACUSTWS2ResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.undstatuscodes.client.holders.ACUSTWS2ResponseMORE1Holder;*/

public class WSUndStatusCodesCall {

	public static synchronized TreeMap fetch()//throws ServiceException, RemoteException
	{
		TreeMap localTMap = new TreeMap();
		
		/*ACSLIBPort service = new UndStatusCodesLocator().getACSLIBPort();
		
		ACUSTWS2ResponseMORE1Holder more1 = new ACUSTWS2ResponseMORE1Holder();
		ACUSTWS2ResponseOUT_PARMSsHolder outparms = new ACUSTWS2ResponseOUT_PARMSsHolder();
		ACUSTWS2ResponseMSG_INFOHolder msginfo = new ACUSTWS2ResponseMSG_INFOHolder();
		ACUSTWS2MORE more = new ACUSTWS2MORE();
		
		
		more.setMORE_CODES(new String(""));
		more.setLAST_KEY(new String(""));
		
		service.ACUSTWS2(more, msginfo, more1, outparms);
		
		ACUSTWS2ResponseOUT_PARMSsOUT_PARMS[] outArray =  outparms.value;
		
		for (int i = 0; i < outArray.length; i++)
		{
			ACUSTWS2ResponseOUT_PARMSsOUT_PARMS item = outArray[i];
			localTMap.put(item.getUNDERW_STATUS(), item.getUNDERW_STATUS() + ": " + item.getDESCRIPTION());
		
		}*/
		
		TreeMap map = new TreeMap();
		map.put("A", "CS-DEP/S ADD ON/S APPROVED");
		map.put("B", "APPROVED WITH DELETE");
		map.put("C", "CS - SUBMITTED");
		map.put("D", "DECLINED");
		map.put("E", "CS - APPR. WITH RIDER (S)");
		map.put("F", "CS - DECLINE(S) - SEE COMM.");
		map.put("G", "PENDING GROUP DECISION");
		map.put("H", "HOLDING FOR UNDERWRITING");
		map.put("I", "INCOMPLETE");
		map.put("J", "CS - HL BENEFIT APPROVED");
		map.put("K", "APPROVED STANDARD");
		map.put("L", "APPROVED PREFERRED");
		map.put("M", "APPR. RIDER(S) AND/OR DECLINE(S)");
		map.put("N", "APPR KEY-STD./SPOUSE-PREF.");
		map.put("O", "APPR KEY-PREF./SPOUSE-STD.");
		map.put("P", "CS - RIDER(S) REMOVAL APPR.");
		map.put("Q", "CS - SEE COMMENTS");
		map.put("R", "CS - REINST. APPROVED");
		map.put("S", "SUBMITTED APPLICATION");
		map.put("T", "TRANSFERRED POLICY");
		map.put("U", "UNLOAD POLICY");
		map.put("V", "APPR OTHER THAN APPL'D FOR");
		map.put("W", "WITHDRAWN");
		map.put("X", "APPROVE WITH ONE EXCLUSION RIDER");
		map.put("Y", "APPROVED WITH ONE OR MORE DECLINES");
		map.put("Z", "APPROVED AS APPLIED FOR");

		 String key;
		 Set set= map.keySet (  ) ; 
	     Iterator iter = set.iterator (  ) ; 
	      
	     while ( iter.hasNext (  )  )  
	     {  
	    	
	    	  key = iter.next().toString();
	    	 localTMap.put(key, key+ ": " + map.get ( key  ));
	      }  
		
		
		return localTMap;
		
	}
	
}
