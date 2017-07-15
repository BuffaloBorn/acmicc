package com.isdiary.entirex;

import java.rmi.RemoteException;
import java.util.TreeMap;

import javax.xml.rpc.ServiceException;

import com.cc.acmi.presentation.dsp.MemoCodesDsp;
import com.softwarag.extirex.webservice.memocodes.client.IASLIBPort;
import com.softwarag.extirex.webservice.memocodes.client.MUMEMBWMORE;
import com.softwarag.extirex.webservice.memocodes.client.MUMEMBWResponseOUT_PARMSsOUT_PARMS;
import com.softwarag.extirex.webservice.memocodes.client.MemoCodesLocator;
import com.softwarag.extirex.webservice.memocodes.client.holders.MUMEMBWResponseMORE1Holder;
import com.softwarag.extirex.webservice.memocodes.client.holders.MUMEMBWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.memocodes.client.holders.MUMEMBWResponseOUT_PARMSsHolder;


public class WSMemoCodesCall {

	public static synchronized TreeMap fetch() throws ServiceException, RemoteException
	{
		
		IASLIBPort service  = new MemoCodesLocator().getIASLIBPort();
		
		
		MUMEMBWMORE inputs = new MUMEMBWMORE();
		

		MUMEMBWResponseMSG_INFOHolder msgInfo = new MUMEMBWResponseMSG_INFOHolder();
		MUMEMBWResponseMORE1Holder more = new MUMEMBWResponseMORE1Holder();	
		MUMEMBWResponseOUT_PARMSsHolder outparms = new MUMEMBWResponseOUT_PARMSsHolder();
		
		TreeMap pMemoCodes = new TreeMap();
		
		boolean more_codes = true;
		String lastKey = new String(""); 
		String moreCodes = new String("");
		
		do 
		{
			inputs.setLAST_KEY(lastKey);
			inputs.setMORE_CODES(moreCodes);
			
			service.MUMEMBW(inputs , msgInfo , more , outparms);
			
			MUMEMBWResponseOUT_PARMSsOUT_PARMS[] outArray = outparms.value;
			
			for (int i = 0; i < outArray.length; i++)
			{
				MUMEMBWResponseOUT_PARMSsOUT_PARMS item = outArray[i];
								
				if (item.getMEMO_ID().length() != 0 )
				{	
					pMemoCodes.put(item.getMEMO_ID(), new MemoCodesDsp(item.getMEMO_ID(),item.getDESCRIPTION(), item.getATTACHMENT_IND(), item.getAPPLICATION_FORM_ID(), item.getEFF_DT_REQ_IND()) );
				
				}
			}
		
			if (more.value.getMORE_CODES1().equalsIgnoreCase("y"))
			{
				more_codes = true;
				moreCodes = more.value.getMORE_CODES1();
				lastKey = more.value.getLAST_KEY1();
			}
			else
			{
				more_codes = false;
			}
			
		}
		while (more_codes);
		
		
		return pMemoCodes;
		
	}

	
	
}
