package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import com.cc.acmi.common.ButtonMode;
import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.presentation.dsp.PolicyEventsDsp;
import com.softwarag.extirex.webservice.policyevents.client.IADIABWINOUT_PARM;
import com.softwarag.extirex.webservice.policyevents.client.IADIABWResponseOUT_PARMOUT_ARRAYsOUT_ARRAY;
import com.softwarag.extirex.webservice.policyevents.client.IASLIBPort;
import com.softwarag.extirex.webservice.policyevents.client.PolicyEventsLocator;
import com.softwarag.extirex.webservice.policyevents.client.holders.IADIABWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policyevents.client.holders.IADIABWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.policyevents.client.holders.IADIABWResponseOUT_PARMHolder;
import com.epm.acmi.bean.StandardEventIdBean;
import com.epm.acmi.datamodel.PolicyEventsDisplayList;
import com.epm.acmi.util.ACMICache;


public class WSPolicyEventsCall
{
	
	public static synchronized void fetch(PolicyEventsDisplayList events,  String policyNo ) throws ServiceException, RemoteException 
	{
		IASLIBPort service = new PolicyEventsLocator().getIASLIBPort();
	
		IADIABWResponseMSG_INFOHolder msgInfo = new IADIABWResponseMSG_INFOHolder();
		IADIABWResponseINOUT_PARM1Holder inputOutParm1 = new IADIABWResponseINOUT_PARM1Holder();
		IADIABWResponseOUT_PARMHolder outparm = new IADIABWResponseOUT_PARMHolder();

		BigDecimal POLICY_ID;
		String MORE_EVENTS = "";
		String scrname = "";
		byte[] LAST_KEY = new byte[39];

		POLICY_ID = new BigDecimal(policyNo);

		IADIABWINOUT_PARM input = new IADIABWINOUT_PARM(POLICY_ID, MORE_EVENTS, LAST_KEY);

		service.IADIABW(input, msgInfo, inputOutParm1, outparm);
		
		IADIABWResponseOUT_PARMOUT_ARRAYsOUT_ARRAY[]  outArray = outparm.value.getOUT_ARRAYs();

		Vector pevents = new Vector();
		
//		displayBean.setPOLICY_ID(inputOutParm1.value.getPOLICY_ID1().toString());
//		displayBean.setAMENDMENT_IND(outparm.value.getAMENDMENT_IND());
//		displayBean.setDECISION_DATE(outparm.value.getDECISION_DATE());
//		displayBean.setDELIVERY_REQ(outparm.value.getDELIVERY_REQ());
//		displayBean.setKEY_INSURED(outparm.value.getKEY_INSURED());
//		displayBean.setKEY_PRODUCT_ID(outparm.value.getKEY_PRODUCT_ID());
//		displayBean.setNOTES_IND(outparm.value.getNOTES_IND());
//		displayBean.setRIDER_IND(outparm.value.getRIDER_IND());
//		displayBean.setUNDERW_DESCRIPTION(outparm.value.getUNDERW_DESCRIPTION());
//		displayBean.setUNDERWRITER(outparm.value.getUNDERWRITER());
		
		for (int i = 0; i < outArray.length; i++) 
		{
			
			IADIABWResponseOUT_PARMOUT_ARRAYsOUT_ARRAY item = outArray[i];
			
			if (item.getDATE_COMPLETED().length() != 0)
			{
				PolicyEventsDsp PolicyEvents = new PolicyEventsDsp();
				PolicyEvents.setDATE_COMPLETED(TextProcessing.dateFormat(item.getDATE_COMPLETED()));
				PolicyEvents.setDATE_CREATED(TextProcessing.dateFormat(item.getDATE_CREATED()));
				PolicyEvents.setEVENT_DESCRIPTION(item.getEVENT_DESCRIPTION());
				PolicyEvents.setEVENT_ID(item.getEVENT_ID());
				PolicyEvents.setEVENT_PERSON(TextProcessing.recipientFormat(item.getEVENT_PERSON()));
				PolicyEvents.setSTD_EVENT_ID(item.getSTD_EVENT_ID());
				PolicyEvents.setSTD_EVENT_STATUS(item.getSTD_EVENT_STATUS());
				PolicyEvents.setUSER_ID(item.getUSER_ID());
				
				
				if (ACMICache.getStdEventCodes().containsKey(item.getSTD_EVENT_ID()))
				{	
				
					StandardEventIdBean stdEvent = (StandardEventIdBean) ACMICache.getStdEventCodes().get(item.getSTD_EVENT_ID());
					
					scrname = stdEvent.getSCRNAME(); 
					
					PolicyEvents.setSCRNAME(scrname);
					PolicyEvents.setColumnMode(ButtonMode.ENABLED);
					PolicyEvents.setEditMode(ButtonMode.ENABLED);
				}
			     else
			     {	 
			    	 PolicyEvents.setSCRNAME("");
			    	 PolicyEvents.setColumnMode(ButtonMode.HIDDEN);
			    	 PolicyEvents.setEditMode(ButtonMode.HIDDEN);
			     }
				pevents.add(PolicyEvents);
			}
		}
		
		PolicyEventsDsp[] result = new PolicyEventsDsp[pevents.size()];
		pevents.toArray(result);
	
		events.setData(result);
	}
}

