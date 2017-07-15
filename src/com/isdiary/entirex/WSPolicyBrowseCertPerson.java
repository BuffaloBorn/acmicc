package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.presentation.dsp.PolicyBrowseCertPersonDsp;
import com.epm.acmi.datamodel.PolicyBrowseCertPersonDisplayList;
import com.softwarag.extirex.webservice.browsepolcertperson.client.BrowsePolCertPersLocator;
import com.softwarag.extirex.webservice.browsepolcertperson.client.IASLIBPort;
import com.softwarag.extirex.webservice.browsepolcertperson.client.MUCPEBWINOUT_PARM;
import com.softwarag.extirex.webservice.browsepolcertperson.client.MUCPEBWResponseOUT_PARMsOUT_PARM;
import com.softwarag.extirex.webservice.browsepolcertperson.client.holders.MUCPEBWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.browsepolcertperson.client.holders.MUCPEBWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.browsepolcertperson.client.holders.MUCPEBWResponseOUT_PARMsHolder;

public class WSPolicyBrowseCertPerson {
	
	public static synchronized PolicyBrowseCertPersonDisplayList fetch(String PolicyNo) throws ServiceException, RemoteException
	{
		
		IASLIBPort service = new BrowsePolCertPersLocator().getIASLIBPort();
		
		BigDecimal POLICY_ID;
		POLICY_ID = new BigDecimal(PolicyNo);
		
		MUCPEBWINOUT_PARM inputs = new MUCPEBWINOUT_PARM(POLICY_ID);
		MUCPEBWResponseOUT_PARMsHolder outparms = new MUCPEBWResponseOUT_PARMsHolder();
		MUCPEBWResponseINOUT_PARM1Holder inoutparms = new MUCPEBWResponseINOUT_PARM1Holder();
		MUCPEBWResponseMSG_INFOHolder msgInfo = new MUCPEBWResponseMSG_INFOHolder();
		
		service.MUCPEBW(inputs, msgInfo, inoutparms, outparms);
		MUCPEBWResponseOUT_PARMsOUT_PARM[] outArray = outparms.value;
		
		Vector pbrowsePerson = new Vector();
		
		
		
		for (int i = 0; i < outArray.length; i++)
		{
			MUCPEBWResponseOUT_PARMsOUT_PARM item = outArray[i];
			PolicyBrowseCertPersonDsp PolicyBrowsePerson = new PolicyBrowseCertPersonDsp();
			
			
			if 	(item.getPERSON_SEARCH_ADDRESS().length() != 0 ) 
			{
				PolicyBrowsePerson.setPOLICY_ID(PolicyNo);
				PolicyBrowsePerson.setBILLED_TO_DATE(item.getBILLED_TO_DATE());
				PolicyBrowsePerson.setEFFECTIVE_DATE(item.getEFFECTIVE_DATE());
				PolicyBrowsePerson.setHIRE_DATE(item.getHIRE_DATE());
				PolicyBrowsePerson.setPERSON_ID(item.getPERSON_ID());
				PolicyBrowsePerson.setPERSON_SEARCH_ADDRESS(item.getPERSON_SEARCH_ADDRESS());
				PolicyBrowsePerson.setPAID_TO_DATE(item.getPAID_TO_DATE());
				
				String personserch[] = item.getPERSON_SEARCH_NAME();
				
				PolicyBrowsePerson.setPERSON_SEARCH_NAME1(TextProcessing.recipientFormat(personserch[0].toString()));
				PolicyBrowsePerson.setPERSON_SEARCH_NAME2(TextProcessing.recipientFormat(personserch[1].toString()));
				PolicyBrowsePerson.setPERSON_SEARCH_NAME3(TextProcessing.recipientFormat(personserch[2].toString()));
				PolicyBrowsePerson.setPERSON_SEARCH_NAME4(TextProcessing.recipientFormat(personserch[3].toString()));
				PolicyBrowsePerson.setPERSON_SEARCH_NAME5(TextProcessing.recipientFormat(personserch[4].toString()));
						
				String specialdate[] = item.getSPECIAL_ACTION_DATE();
				
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE1(specialdate[0].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE2(specialdate[1].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE3(specialdate[2].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE4(specialdate[3].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE5(specialdate[4].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE6(specialdate[5].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE7(specialdate[6].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE8(specialdate[7].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE9(specialdate[8].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE10(specialdate[9].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE11(specialdate[10].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE12(specialdate[11].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE13(specialdate[12].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE14(specialdate[13].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE15(specialdate[14].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE16(specialdate[15].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE17(specialdate[16].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE18(specialdate[17].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE19(specialdate[18].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_DATE20(specialdate[19].toString());
				
				String specialaction[] = item.getSPECIAL_ACTION_REASON_CODE();
				
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE1(specialaction[0].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE2(specialaction[1].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE3(specialaction[2].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE4(specialaction[3].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE5(specialaction[4].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE6(specialaction[5].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE7(specialaction[6].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE8(specialaction[7].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE9(specialaction[8].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE10(specialaction[9].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE11(specialaction[10].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE12(specialaction[11].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE13(specialaction[12].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE14(specialaction[13].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE15(specialaction[14].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE16(specialaction[15].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE17(specialaction[16].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE18(specialaction[17].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE19(specialaction[18].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE20(specialaction[19].toString());
				
				
				PolicyBrowsePerson.setSPECIAL_ACTION_REASON_CODE1(specialaction[0].toString());
				
				String specialactiontype[] = item.getSPECIAL_ACTION_TYPE();
				
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE1(specialactiontype[0].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE2(specialactiontype[1].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE3(specialactiontype[2].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE4(specialactiontype[3].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE5(specialactiontype[4].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE6(specialactiontype[5].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE7(specialactiontype[6].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE8(specialactiontype[7].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE9(specialactiontype[8].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE10(specialactiontype[9].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE11(specialactiontype[10].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE12(specialactiontype[11].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE13(specialactiontype[12].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE14(specialactiontype[13].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE15(specialactiontype[14].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE16(specialactiontype[15].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE17(specialactiontype[16].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE18(specialactiontype[17].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE19(specialactiontype[18].toString());
				PolicyBrowsePerson.setSPECIAL_ACTION_TYPE20(specialactiontype[19].toString());
				
				
				pbrowsePerson.add(PolicyBrowsePerson);
			}
		}	
		
		PolicyBrowseCertPersonDsp[] result = new PolicyBrowseCertPersonDsp[pbrowsePerson.size()];
		pbrowsePerson.toArray(result);
		
		return new PolicyBrowseCertPersonDisplayList(result);
		
	}
	
	
}
