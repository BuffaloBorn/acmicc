package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;


import com.cc.acmi.presentation.dsp.PolicyBrowseDsp;
import com.epm.acmi.datamodel.PolicyBrowseDisplayList;
import com.softwarag.extirex.webservice.browsepolicies.client.BrowsePoliciesLocator;
import com.softwarag.extirex.webservice.browsepolicies.client.IASLIBPort;
import com.softwarag.extirex.webservice.browsepolicies.client.MUPLYBWINOUT_PARM;
import com.softwarag.extirex.webservice.browsepolicies.client.MUPLYBWResponseOUT_PARMsOUT_PARM;
import com.softwarag.extirex.webservice.browsepolicies.client.holders.MUPLYBWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.browsepolicies.client.holders.MUPLYBWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.browsepolicies.client.holders.MUPLYBWResponseOUT_PARMsHolder;

public class WSPolicyBrowseCall {
	
	public static synchronized PolicyBrowseDisplayList initFetch(String keyInsued, MUPLYBWResponseINOUT_PARM1Holder inoutparms ) throws ServiceException, RemoteException
	{
		IASLIBPort service = new BrowsePoliciesLocator().getIASLIBPort();
		
		MUPLYBWINOUT_PARM inputs = new MUPLYBWINOUT_PARM();
		
		inputs.setSTART_KEY(keyInsued);
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
		
		MUPLYBWResponseOUT_PARMsHolder outparms = new MUPLYBWResponseOUT_PARMsHolder();
		
		MUPLYBWResponseMSG_INFOHolder msgInfo = new MUPLYBWResponseMSG_INFOHolder();	
		
		service.MUPLYBW(inputs, msgInfo, inoutparms, outparms);
		
		MUPLYBWResponseOUT_PARMsOUT_PARM[] outArray = outparms.value;
		
		Vector pbrowse = new Vector();
			
		for (int i = 0; i < outArray.length; i++) 
		{
			MUPLYBWResponseOUT_PARMsOUT_PARM item = outArray[i];
			PolicyBrowseDsp PolicyBrowse = new PolicyBrowseDsp();
			
			PolicyBrowse.setAGENT_CODE(item.getAGENT_CODE());		
			PolicyBrowse.setANNUALISED_PREMIUM_PAYABLE(item.getANNUALISED_PREMIUM_PAYABLE());
			PolicyBrowse.setAPPLICATION_FORM_ID(item.getAPPLICATION_FORM_ID());
			PolicyBrowse.setAPPLICATION_INCOMPLETE_IND(item.getAPPLICATION_INCOMPLETE_IND());
			PolicyBrowse.setBILL_CODE(item.getBILL_CODE());		
			PolicyBrowse.setDATE_SIGNED(item.getDATE_SIGNED());	
			PolicyBrowse.setKEY_INSURED_COUNTY(item.getKEY_INSURED_COUNTY());			
			PolicyBrowse.setKEY_INSURED_SEARCH_ADDRESS(item.getKEY_INSURED_SEARCH_ADDRESS());	
			PolicyBrowse.setKEY_INSURED_SEARCH_NAME(item.getKEY_INSURED_SEARCH_NAME());	
			PolicyBrowse.setKEY_INSURED_STATE(item.getKEY_INSURED_STATE());		
			PolicyBrowse.setKEY_PRODUCT_ID(item.getKEY_PRODUCT_ID());
			PolicyBrowse.setKEY_INSURED_ADDRESS_ID(item.getKEY_INSURED_ADDRESS_ID());
			PolicyBrowse.setKEY_INSURED_PERSON_ID(item.getKEY_INSURED_PERSON_ID());
			PolicyBrowse.setLINE_OF_BUSINESS(item.getLINE_OF_BUSINESS());
			PolicyBrowse.setMODE_OF_PAYMENT(item.getMODE_OF_PAYMENT());
			PolicyBrowse.setNEW_LIST_BILL_IND(item.getNEW_LIST_BILL_IND());
			PolicyBrowse.setOLD_POLICY_COMPANY_NAME(item.getOLD_POLICY_COMPANY_NAME());
			PolicyBrowse.setOLD_POLICY_EXISTS_IND(item.getOLD_POLICY_EXISTS_IND());
			PolicyBrowse.setOLD_POLICY_REPLACEMENT_IND(item.getOLD_POLICY_REPLACEMENT_IND());
			PolicyBrowse.setPOLICY_STATUS_CURRENT(item.getPOLICY_STATUS_CURRENT());
			PolicyBrowse.setPOLICY_STATUS_CURRENT_DATE(item.getPOLICY_STATUS_CURRENT_DATE());
			PolicyBrowse.setPREMIUM_PAYABLE(item.getPREMIUM_PAYABLE());
			PolicyBrowse.setPOLICY_ID(item.getPOLICY_ID());
			PolicyBrowse.setSTATE_ISSUED(item.getSTATE_ISSUED());
			PolicyBrowse.setSTATE_SIGNED(item.getSTATE_SIGNED());
			PolicyBrowse.setTENDERED_AMOUNT(item.getTENDERED_AMOUNT());
			PolicyBrowse.setTENDERED_PAYMENT_TYPE(item.getTENDERED_PAYMENT_TYPE());
			
			pbrowse.add(PolicyBrowse);
		}
		
		PolicyBrowseDsp[] result = new PolicyBrowseDsp[pbrowse.size()];
		pbrowse.toArray(result);
		
		
		return new PolicyBrowseDisplayList(result);
	}
	
	
	public static synchronized PolicyBrowseDisplayList forwardFetch(MUPLYBWResponseINOUT_PARM1Holder forwardParms, MUPLYBWResponseINOUT_PARM1Holder inoutparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new BrowsePoliciesLocator().getIASLIBPort();
		
		MUPLYBWINOUT_PARM inputs = new MUPLYBWINOUT_PARM();
				
		inputs.setSTART_KEY(forwardParms.value.getSTART_KEY1());
		String[] BKEY = new String[10];
		
		String[] forwardBKey  = forwardParms.value.getBKEY1();
		
		for (int i = 0; i < forwardBKey.length; i++ )
		{
			BKEY[i] = forwardBKey[i];
			
		}
				
		inputs.setBKEY(forwardBKey );
		
		inputs.setFORWARDCOUNT(forwardParms.value.getFORWARDCOUNT1() );
		
		inputs.setSCROLL(new String("F"));
		
		MUPLYBWResponseOUT_PARMsHolder outparms = new MUPLYBWResponseOUT_PARMsHolder();
		
		MUPLYBWResponseMSG_INFOHolder msgInfo = new MUPLYBWResponseMSG_INFOHolder();	
		
		
		service.MUPLYBW(inputs, msgInfo, inoutparms, outparms);
		
		MUPLYBWResponseOUT_PARMsOUT_PARM[] outArray = outparms.value;
		
		Vector pbrowse = new Vector();
			
		for (int i = 0; i < outArray.length; i++) 
		{
			MUPLYBWResponseOUT_PARMsOUT_PARM item = outArray[i];
			PolicyBrowseDsp PolicyBrowse = new PolicyBrowseDsp();
			
			PolicyBrowse.setAGENT_CODE(item.getAGENT_CODE());		
			PolicyBrowse.setANNUALISED_PREMIUM_PAYABLE(item.getANNUALISED_PREMIUM_PAYABLE());
			PolicyBrowse.setAPPLICATION_FORM_ID(item.getAPPLICATION_FORM_ID());
			PolicyBrowse.setAPPLICATION_INCOMPLETE_IND(item.getAPPLICATION_INCOMPLETE_IND());
			PolicyBrowse.setBILL_CODE(item.getBILL_CODE());		
			PolicyBrowse.setDATE_SIGNED(item.getDATE_SIGNED());	
			PolicyBrowse.setKEY_INSURED_COUNTY(item.getKEY_INSURED_COUNTY());			
			PolicyBrowse.setKEY_INSURED_SEARCH_ADDRESS(item.getKEY_INSURED_SEARCH_ADDRESS());	
			PolicyBrowse.setKEY_INSURED_SEARCH_NAME(item.getKEY_INSURED_SEARCH_NAME());	
			PolicyBrowse.setKEY_INSURED_STATE(item.getKEY_INSURED_STATE());		
			PolicyBrowse.setKEY_PRODUCT_ID(item.getKEY_PRODUCT_ID());
			PolicyBrowse.setKEY_INSURED_ADDRESS_ID(item.getKEY_INSURED_ADDRESS_ID());
			PolicyBrowse.setKEY_INSURED_PERSON_ID(item.getKEY_INSURED_PERSON_ID());
			PolicyBrowse.setLINE_OF_BUSINESS(item.getLINE_OF_BUSINESS());
			PolicyBrowse.setMODE_OF_PAYMENT(item.getMODE_OF_PAYMENT());
			PolicyBrowse.setNEW_LIST_BILL_IND(item.getNEW_LIST_BILL_IND());
			PolicyBrowse.setOLD_POLICY_COMPANY_NAME(item.getOLD_POLICY_COMPANY_NAME());
			PolicyBrowse.setOLD_POLICY_EXISTS_IND(item.getOLD_POLICY_EXISTS_IND());
			PolicyBrowse.setOLD_POLICY_REPLACEMENT_IND(item.getOLD_POLICY_REPLACEMENT_IND());
			PolicyBrowse.setPOLICY_STATUS_CURRENT(item.getPOLICY_STATUS_CURRENT());
			PolicyBrowse.setPOLICY_STATUS_CURRENT_DATE(item.getPOLICY_STATUS_CURRENT_DATE());
			PolicyBrowse.setPREMIUM_PAYABLE(item.getPREMIUM_PAYABLE());
			PolicyBrowse.setPOLICY_ID(item.getPOLICY_ID());
			PolicyBrowse.setSTATE_ISSUED(item.getSTATE_ISSUED());
			PolicyBrowse.setSTATE_SIGNED(item.getSTATE_SIGNED());
			PolicyBrowse.setTENDERED_AMOUNT(item.getTENDERED_AMOUNT());
			PolicyBrowse.setTENDERED_PAYMENT_TYPE(item.getTENDERED_PAYMENT_TYPE());
			
			pbrowse.add(PolicyBrowse);
		}
		
		PolicyBrowseDsp[] result = new PolicyBrowseDsp[pbrowse.size()];
		pbrowse.toArray(result);
		
		
		return new PolicyBrowseDisplayList(result);
	}
	
	public static synchronized PolicyBrowseDisplayList backwardFetch(MUPLYBWResponseINOUT_PARM1Holder backwardParms, MUPLYBWResponseINOUT_PARM1Holder inoutparms) throws ServiceException, RemoteException
	{
		IASLIBPort service = new BrowsePoliciesLocator().getIASLIBPort();
		
		MUPLYBWINOUT_PARM inputs = new MUPLYBWINOUT_PARM();
		
		inputs.setSTART_KEY(backwardParms.value.getSTART_KEY1());
		String[] BKEY = new String[10];
		
		String[] forwardBKey  = backwardParms.value.getBKEY1();
		
		for (int i = 0; i < forwardBKey.length; i++ )
		{
			BKEY[i] = forwardBKey[i];
			
		}
				
		inputs.setBKEY(forwardBKey );
		
		inputs.setFORWARDCOUNT(backwardParms.value.getFORWARDCOUNT1() );
		
		inputs.setSCROLL(new String("B"));
		
		MUPLYBWResponseOUT_PARMsHolder outparms = new MUPLYBWResponseOUT_PARMsHolder();
		
		MUPLYBWResponseMSG_INFOHolder msgInfo = new MUPLYBWResponseMSG_INFOHolder();	
		
		service.MUPLYBW(inputs, msgInfo, inoutparms, outparms);
		
		MUPLYBWResponseOUT_PARMsOUT_PARM[] outArray = outparms.value;
		
		Vector pbrowse = new Vector();
			
		for (int i = 0; i < outArray.length; i++) 
		{
			MUPLYBWResponseOUT_PARMsOUT_PARM item = outArray[i];
			PolicyBrowseDsp PolicyBrowse = new PolicyBrowseDsp();
			
			PolicyBrowse.setAGENT_CODE(item.getAGENT_CODE());		
			PolicyBrowse.setANNUALISED_PREMIUM_PAYABLE(item.getANNUALISED_PREMIUM_PAYABLE());
			PolicyBrowse.setAPPLICATION_FORM_ID(item.getAPPLICATION_FORM_ID());
			PolicyBrowse.setAPPLICATION_INCOMPLETE_IND(item.getAPPLICATION_INCOMPLETE_IND());
			PolicyBrowse.setBILL_CODE(item.getBILL_CODE());		
			PolicyBrowse.setDATE_SIGNED(item.getDATE_SIGNED());	
			PolicyBrowse.setKEY_INSURED_COUNTY(item.getKEY_INSURED_COUNTY());			
			PolicyBrowse.setKEY_INSURED_SEARCH_ADDRESS(item.getKEY_INSURED_SEARCH_ADDRESS());	
			PolicyBrowse.setKEY_INSURED_SEARCH_NAME(item.getKEY_INSURED_SEARCH_NAME());	
			PolicyBrowse.setKEY_INSURED_STATE(item.getKEY_INSURED_STATE());		
			PolicyBrowse.setKEY_PRODUCT_ID(item.getKEY_PRODUCT_ID());
			PolicyBrowse.setKEY_INSURED_ADDRESS_ID(item.getKEY_INSURED_ADDRESS_ID());
			PolicyBrowse.setKEY_INSURED_PERSON_ID(item.getKEY_INSURED_PERSON_ID());
			PolicyBrowse.setLINE_OF_BUSINESS(item.getLINE_OF_BUSINESS());
			PolicyBrowse.setMODE_OF_PAYMENT(item.getMODE_OF_PAYMENT());
			PolicyBrowse.setNEW_LIST_BILL_IND(item.getNEW_LIST_BILL_IND());
			PolicyBrowse.setOLD_POLICY_COMPANY_NAME(item.getOLD_POLICY_COMPANY_NAME());
			PolicyBrowse.setOLD_POLICY_EXISTS_IND(item.getOLD_POLICY_EXISTS_IND());
			PolicyBrowse.setOLD_POLICY_REPLACEMENT_IND(item.getOLD_POLICY_REPLACEMENT_IND());
			PolicyBrowse.setPOLICY_STATUS_CURRENT(item.getPOLICY_STATUS_CURRENT());
			PolicyBrowse.setPOLICY_STATUS_CURRENT_DATE(item.getPOLICY_STATUS_CURRENT_DATE());
			PolicyBrowse.setPREMIUM_PAYABLE(item.getPREMIUM_PAYABLE());
			PolicyBrowse.setPOLICY_ID(item.getPOLICY_ID());
			PolicyBrowse.setSTATE_ISSUED(item.getSTATE_ISSUED());
			PolicyBrowse.setSTATE_SIGNED(item.getSTATE_SIGNED());
			PolicyBrowse.setTENDERED_AMOUNT(item.getTENDERED_AMOUNT());
			PolicyBrowse.setTENDERED_PAYMENT_TYPE(item.getTENDERED_PAYMENT_TYPE());
			
			pbrowse.add(PolicyBrowse);
		}
		
		PolicyBrowseDsp[] result = new PolicyBrowseDsp[pbrowse.size()];
		pbrowse.toArray(result);
		
		
		return new PolicyBrowseDisplayList(result);
	}

}
