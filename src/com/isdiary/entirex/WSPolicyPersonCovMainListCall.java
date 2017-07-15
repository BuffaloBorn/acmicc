package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.presentation.dsp.PolicyPersonCovMainListDsp;
import com.epm.acmi.datamodel.PolicyPersonCovMainDisplayList;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.IAPPCBWINOUT_PARM;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.IASLIBPort;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.PolicyPersonCovMaintListLocator;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.holders.IAPPCBWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.holders.IAPPCBWResponseMESSAGE_INFOHolder;
import com.softwarag.extirex.webservice.policypersoncovmaintlist.client.holders.IAPPCBWResponseOUT_PARMHolder;

public class WSPolicyPersonCovMainListCall {

	public static synchronized void fetch(PolicyPersonCovMainDisplayList policyCoverages ,String policyNo, String userId, String personId,IAPPCBWResponseMESSAGE_INFOHolder msgInfo, IAPPCBWResponseINOUT_PARM1Holder inoutparms, IAPPCBWResponseOUT_PARMHolder outparms ) throws ServiceException, RemoteException
	{
		
		IASLIBPort service = new PolicyPersonCovMaintListLocator().getIASLIBPort();
		
		IAPPCBWINOUT_PARM inputs = new IAPPCBWINOUT_PARM();
	
		inputs.setPOLICY_ID(new BigDecimal(policyNo));
		inputs.setPERSON_ID(new BigDecimal(personId));
		
		inputs.setMORE_DATA(new String(""));
		inputs.setSTART_COVERAGE_CODE(new String(""));
		inputs.setDISPLAY_DATE(new String(""));
		
		
		inputs.setANNUAL_PERM_EXTRA_PREM_AMT(new BigDecimal[20]);
		inputs.setANNUAL_PREM_AMT( new BigDecimal[20]);
		inputs.setCOVERAGE_CODE(new String[20]);
		inputs.setSUB_STANDARD_RISK_CODE(new String[20]);
		
		inputs.setSTART_DATE(new String[20]);
		inputs.setEND_DATE(new String[20]);

		
	
		//IAPPCBWResponseINOUT_PARM1Holder inoutparms = new IAPPCBWResponseINOUT_PARM1Holder();
		//IAPPCBWResponseOUT_PARMHolder outparms = new IAPPCBWResponseOUT_PARMHolder();
		
		service.IAPPCBW(inputs, msgInfo, inoutparms, outparms );
		
		BigDecimal[] ANNUAL_PREM_AMT = inoutparms.value.getANNUAL_PREM_AMT1();
		BigDecimal[] ANNUAL_PERM_EXTRA_PREM_AMT = inoutparms.value.getANNUAL_PERM_EXTRA_PREM_AMT1();
		String[] COVERAGE_CODE = inoutparms.value.getCOVERAGE_CODE1();
		String[] SUB_STANDARD_RISK_CODE = inoutparms.value.getSUB_STANDARD_RISK_CODE1();
		String[] START_DATE = inoutparms.value.getSTART_DATE1();
		String[] END_DATE = inoutparms.value.getEND_DATE1();
		BigDecimal[] TOTAL_PREMIUM = outparms.value.getTOTAL_PREMIUM();
		
		Vector pcoverage = new Vector();
		
		for(int i=0; i < COVERAGE_CODE.length; i++)
		{
			PolicyPersonCovMainListDsp PolicyCoverage = new  PolicyPersonCovMainListDsp();
			
			if (COVERAGE_CODE[i].trim().length() != 0) 
			{
				PolicyCoverage.setCOVERAGE_CODE(COVERAGE_CODE[i].toString());
				PolicyCoverage.setANNUAL_PERM_EXTRA_PREM_AMT(ANNUAL_PERM_EXTRA_PREM_AMT[i].toString());
				PolicyCoverage.setANNUAL_PREM_AMT(ANNUAL_PREM_AMT[i].toString());
				PolicyCoverage.setSUB_STANDARD_RISK_CODE(SUB_STANDARD_RISK_CODE[i].toString());
				PolicyCoverage.setSTART_DATE(TextProcessing.dateFormat(START_DATE[i].toString()));
				PolicyCoverage.setEND_DATE(TextProcessing.dateFormat(END_DATE[i].toString()));
				PolicyCoverage.setTOTAL_PREMIUM(TOTAL_PREMIUM[i].toString());
				pcoverage.add(PolicyCoverage);
			}
		}
		PolicyPersonCovMainListDsp[] results = new PolicyPersonCovMainListDsp[pcoverage.size()];
		pcoverage.toArray(results);
		
		policyCoverages.setData(results);
		
	}
	
	public static synchronized void updateDisplayDate(PolicyPersonCovMainDisplayList policyCoverages, IAPPCBWINOUT_PARM inputs,IAPPCBWResponseMESSAGE_INFOHolder msgInfo, IAPPCBWResponseINOUT_PARM1Holder inoutparms, IAPPCBWResponseOUT_PARMHolder outparms ) throws ServiceException, RemoteException
	{
		
		IASLIBPort service = new PolicyPersonCovMaintListLocator().getIASLIBPort();
				
		service.IAPPCBW(inputs, msgInfo, inoutparms, outparms );
		
		BigDecimal[] ANNUAL_PREM_AMT = inoutparms.value.getANNUAL_PREM_AMT1();
		BigDecimal[] ANNUAL_PERM_EXTRA_PREM_AMT = inoutparms.value.getANNUAL_PERM_EXTRA_PREM_AMT1();
		String[] COVERAGE_CODE = inoutparms.value.getCOVERAGE_CODE1();
		String[] SUB_STANDARD_RISK_CODE = inoutparms.value.getSUB_STANDARD_RISK_CODE1();
		String[] START_DATE = inoutparms.value.getSTART_DATE1();
		String[] END_DATE = inoutparms.value.getEND_DATE1();
		BigDecimal[] TOTAL_PREMIUM = outparms.value.getTOTAL_PREMIUM();
		
		Vector pcoverage = new Vector();
		
		for(int i=0; i < COVERAGE_CODE.length; i++)
		{
			PolicyPersonCovMainListDsp PolicyCoverage = new  PolicyPersonCovMainListDsp();
			
			if (COVERAGE_CODE[i].trim().length() != 0) 
			{
				PolicyCoverage.setCOVERAGE_CODE(COVERAGE_CODE[i].toString());
				PolicyCoverage.setANNUAL_PERM_EXTRA_PREM_AMT(ANNUAL_PERM_EXTRA_PREM_AMT[i].toString());
				PolicyCoverage.setANNUAL_PREM_AMT(ANNUAL_PREM_AMT[i].toString());
				PolicyCoverage.setSUB_STANDARD_RISK_CODE(SUB_STANDARD_RISK_CODE[i].toString());
				PolicyCoverage.setSTART_DATE(TextProcessing.dateFormat(START_DATE[i].toString()));
				PolicyCoverage.setEND_DATE(TextProcessing.dateFormat(END_DATE[i].toString()));
				PolicyCoverage.setTOTAL_PREMIUM(TOTAL_PREMIUM[i].toString());
				pcoverage.add(PolicyCoverage);
			}
		}
		PolicyPersonCovMainListDsp[] results = new PolicyPersonCovMainListDsp[pcoverage.size()];
		pcoverage.toArray(results);
		
		policyCoverages.setData(results);
		
	}
	
}
