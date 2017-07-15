package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.presentation.dsp.BrowseRiderCodesDsp;
import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.datamodel.BrowseRiderCodesDisplatList;
import com.softwarag.extirex.webservice.ridercodes.client.ACRIDBWINOUT_PARM;
import com.softwarag.extirex.webservice.ridercodes.client.ACRIDBWPDA_FIELDS;
import com.softwarag.extirex.webservice.ridercodes.client.ACRIDBWResponseOUT_PARMSsOUT_PARMS;
import com.softwarag.extirex.webservice.ridercodes.client.ACSLIBPort;
import com.softwarag.extirex.webservice.ridercodes.client.RiderCodesLocator;
import com.softwarag.extirex.webservice.ridercodes.client.holders.ACRIDBWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.ridercodes.client.holders.ACRIDBWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.ridercodes.client.holders.ACRIDBWResponseOUT_PARMSsHolder;

public class WSRiderCodesCall {

	public static synchronized BrowseRiderCodesDisplatList initFetch(String initRiderCode, ACRIDBWResponseINOUT_PARM1Holder inoutparams, ACRIDBWPDA_FIELDS pdafields) throws ServiceException, RemoteException 
	{
		
		ACSLIBPort service = new RiderCodesLocator().getACSLIBPort();

		ACRIDBWINOUT_PARM inputs = new ACRIDBWINOUT_PARM();
		
		inputs.setSTART_KEY(initRiderCode);
		String[] BKEY = new String[10];
		
		BKEY[0] = new String("");
		BKEY[1] = new String("");
		BKEY[2] = new String("");
		BKEY[3] = new String("");
		BKEY[4] = new String("");
		BKEY[5] = new String("");
		BKEY[6] = new String("");
		BKEY[7] = new String("");
		BKEY[8] = new String("");
		BKEY[9] = new String("");
		
		inputs.setBKEY(BKEY );
		
		inputs.setFORWARDCOUNT(new BigDecimal("0"));
		inputs.setSCROLL(new String());
		
		ACRIDBWResponseOUT_PARMSsHolder outparms = new ACRIDBWResponseOUT_PARMSsHolder();
		ACRIDBWResponseMSG_INFOHolder msgInfo = new ACRIDBWResponseMSG_INFOHolder();
		
		service.ACRIDBW(pdafields, inputs , msgInfo, inoutparams , outparms);
		
		Vector riderCodes = new Vector();
		
		ACRIDBWResponseOUT_PARMSsOUT_PARMS[] outArray = outparms.value;

		for (int i = 0; i < outArray.length; i++)
		{
			ACRIDBWResponseOUT_PARMSsOUT_PARMS item = outArray[i];
			BrowseRiderCodesDsp riderCode = new BrowseRiderCodesDsp();
			
			riderCode.setDESCRIPTION(item.getDESCRIPTION());
			riderCode.setEFF_END_DATE(item.getEFF_END_DATE().toString());
			riderCode.setRIDER_CODE( item.getRIDER_CODE());
			riderCode.setSTATE_CODE(item.getSTATE_CODE());
			riderCode.setSTATE_IND(item.getSTATE_IND());
			riderCode.setEFF_END_DATE(TextProcessing.dateFormat(item.getEFF_END_DATE().toString()));
			riderCode.setEFF_START_DATE( TextProcessing.dateFormat(item.getEFF_START_DATE().toString()));
			riderCode.setRIDER_TEXT(TextProcessing.formatText(item.getRIDER_TEXT(), 60));		
			if (item.getTEXT_REQUIRED().equalsIgnoreCase("Y"))
				riderCode.setTEXT_REQUIRED("Y");
			else
				riderCode.setTEXT_REQUIRED("N");
			riderCodes.add(riderCode);
		}
		
		BrowseRiderCodesDsp[] result = new BrowseRiderCodesDsp[riderCodes.size()];
		riderCodes.toArray(result);
		
		
		return new BrowseRiderCodesDisplatList(result);
	}
	
	public static synchronized BrowseRiderCodesDisplatList forwardFetch(ACRIDBWResponseINOUT_PARM1Holder forwardParms, ACRIDBWResponseINOUT_PARM1Holder inoutparams, FormActionContext ctx, ACRIDBWPDA_FIELDS pdafields) throws ServiceException, RemoteException 
	{
		
		ACSLIBPort service = new RiderCodesLocator().getACSLIBPort();

		ACRIDBWINOUT_PARM inputs = new ACRIDBWINOUT_PARM();
		
		inputs.setSTART_KEY(forwardParms.value.getSTART_KEY1());
		String[] BKEY = new String[10];
		
		String[] forwardBKey  = forwardParms.value.getBKEY1();
		
		for (int i = 0; i < forwardBKey.length; i++ )
		{
			BKEY[i] = forwardBKey[i];
			
		}
		
		inputs.setBKEY(BKEY );
		
		inputs.setFORWARDCOUNT(forwardParms.value.getFORWARDCOUNT1());
		inputs.setSCROLL(new String("F"));
		
		ACRIDBWResponseOUT_PARMSsHolder outparms = new ACRIDBWResponseOUT_PARMSsHolder();
		ACRIDBWResponseMSG_INFOHolder msgInfo = new ACRIDBWResponseMSG_INFOHolder();
		service.ACRIDBW(pdafields, inputs , msgInfo, inoutparams , outparms);
		
		if (msgInfo.value.getRETURN_CODE().equalsIgnoreCase("I"))
		{	
			ctx.session().setAttribute("msgInfoRiderCodesResponse", msgInfo);
			inoutparams = forwardParms;	
			return null;
		}
		
		Vector riderCodes = new Vector();
		
		ACRIDBWResponseOUT_PARMSsOUT_PARMS[] outArray = outparms.value;

		for (int i = 0; i < outArray.length; i++)
		{
			ACRIDBWResponseOUT_PARMSsOUT_PARMS item = outArray[i];
			BrowseRiderCodesDsp riderCode = new BrowseRiderCodesDsp();
			
			riderCode.setDESCRIPTION(item.getDESCRIPTION());
			riderCode.setEFF_END_DATE(item.getEFF_END_DATE().toString());
			riderCode.setRIDER_CODE( item.getRIDER_CODE());
			riderCode.setSTATE_CODE(item.getSTATE_CODE());
			riderCode.setSTATE_IND(item.getSTATE_IND());
			riderCode.setEFF_END_DATE(TextProcessing.dateFormat(item.getEFF_END_DATE().toString()));
			riderCode.setEFF_START_DATE( TextProcessing.dateFormat(item.getEFF_START_DATE().toString()));
			riderCode.setRIDER_TEXT(TextProcessing.formatText(item.getRIDER_TEXT(), 59));
			if (item.getTEXT_REQUIRED().equalsIgnoreCase("Y"))
				riderCode.setTEXT_REQUIRED("Y");
			else
				riderCode.setTEXT_REQUIRED("N");
			riderCodes.add(riderCode);
		}
		
		BrowseRiderCodesDsp[] result = new BrowseRiderCodesDsp[riderCodes.size()];
		riderCodes.toArray(result);
		
		
		return new BrowseRiderCodesDisplatList(result);
	}
	
	public static synchronized BrowseRiderCodesDisplatList backwardFetch(ACRIDBWResponseINOUT_PARM1Holder forwardParms, ACRIDBWResponseINOUT_PARM1Holder inoutparams, ACRIDBWPDA_FIELDS pdafields) throws ServiceException, RemoteException 
	{
		
		ACSLIBPort service = new RiderCodesLocator().getACSLIBPort();

		ACRIDBWINOUT_PARM inputs = new ACRIDBWINOUT_PARM();
		
		inputs.setSTART_KEY(forwardParms.value.getSTART_KEY1());
		String[] BKEY = new String[10];
		
		String[] forwardBKey  = forwardParms.value.getBKEY1();
		
		for (int i = 0; i < forwardBKey.length; i++ )
		{
			BKEY[i] = forwardBKey[i];
			
		}
		
		inputs.setBKEY(BKEY );
		
		inputs.setFORWARDCOUNT(forwardParms.value.getFORWARDCOUNT1());
		inputs.setSCROLL(new String("B"));
		
		ACRIDBWResponseOUT_PARMSsHolder outparms = new ACRIDBWResponseOUT_PARMSsHolder();
		ACRIDBWResponseMSG_INFOHolder msgInfo = new ACRIDBWResponseMSG_INFOHolder();
		
	
		service.ACRIDBW(pdafields, inputs , msgInfo, inoutparams , outparms);
		
		
		Vector riderCodes = new Vector();
		
		ACRIDBWResponseOUT_PARMSsOUT_PARMS[] outArray = outparms.value;

		for (int i = 0; i < outArray.length; i++)
		{
			ACRIDBWResponseOUT_PARMSsOUT_PARMS item = outArray[i];
			BrowseRiderCodesDsp riderCode = new BrowseRiderCodesDsp();
			
			riderCode.setDESCRIPTION(item.getDESCRIPTION());
			riderCode.setEFF_END_DATE(item.getEFF_END_DATE().toString());
			riderCode.setRIDER_CODE( item.getRIDER_CODE());
			riderCode.setSTATE_CODE(item.getSTATE_CODE());
			riderCode.setSTATE_IND(item.getSTATE_IND());
			riderCode.setEFF_END_DATE(TextProcessing.dateFormat(item.getEFF_END_DATE().toString()));
			riderCode.setEFF_START_DATE( TextProcessing.dateFormat(item.getEFF_START_DATE().toString()));
			riderCode.setRIDER_TEXT(TextProcessing.formatText(item.getRIDER_TEXT(), 59));
			if (item.getTEXT_REQUIRED().equalsIgnoreCase("Y"))
				riderCode.setTEXT_REQUIRED("Y");
			else
				riderCode.setTEXT_REQUIRED("N");
			riderCodes.add(riderCode);
		}
		
		BrowseRiderCodesDsp[] result = new BrowseRiderCodesDsp[riderCodes.size()];
		riderCodes.toArray(result);
		
		
		return new BrowseRiderCodesDisplatList(result);
	}
	
}
