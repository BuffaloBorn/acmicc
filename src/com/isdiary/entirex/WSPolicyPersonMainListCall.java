package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import java.util.Enumeration;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import com.cc.acmi.common.TextProcessing;
import com.cc.acmi.presentation.dsp.PolicyPersonMainListDsp;
import com.cc.framework.adapter.struts.ActionContext;
import com.epm.acmi.bean.PolicyPersonMainBean;
import com.epm.acmi.datamodel.PolicyPersonMainDisplayList;
import com.epm.acmi.struts.Constants;
import com.softwarag.extirex.webservice.policypersonmainlist.client.ACCPEBWINOUT_PARM;
import com.softwarag.extirex.webservice.policypersonmainlist.client.ACCPEBWINOUT_PARMLINEsLINE;
import com.softwarag.extirex.webservice.policypersonmainlist.client.ACCPEBWResponseINOUT_PARM1LINEsLINE1;
import com.softwarag.extirex.webservice.policypersonmainlist.client.IASLIBPort;
import com.softwarag.extirex.webservice.policypersonmainlist.client.POlicyPersonMaintListLocator;
import com.softwarag.extirex.webservice.policypersonmainlist.client.holders.ACCPEBWResponseINOUT_PARM1Holder;
import com.softwarag.extirex.webservice.policypersonmainlist.client.holders.ACCPEBWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.policypersonmainlist.client.holders.ACCPEBWResponseOUT_PARMHolder;

public class WSPolicyPersonMainListCall {

	public static synchronized PolicyPersonMainDisplayList fetch(String policyNo,  ACCPEBWResponseMSG_INFOHolder msgInfo, ACCPEBWResponseINOUT_PARM1Holder inoutparms, ACCPEBWResponseOUT_PARMHolder outparms, ActionContext ctx ) throws ServiceException, RemoteException  
	{
		IASLIBPort service = new POlicyPersonMaintListLocator().getIASLIBPort();

		String IASModify = (String)ctx.session().getAttribute(Constants.IASModify);
		
		ACCPEBWINOUT_PARM inputs = new ACCPEBWINOUT_PARM();
		
		inputs.setPOLICY_ID(new BigDecimal(policyNo));
		inputs.setSTART_PERSON_ID(new BigDecimal("0"));
		inputs.setMORE_DATA(new String(""));
		inputs.setPP_STARTDATE(new String(""));
		
		ACCPEBWINOUT_PARMLINEsLINE[] LINEs = new ACCPEBWINOUT_PARMLINEsLINE[5]; 
		
		inputs.setLINEs(LINEs);
			
		service.ACCPEBW(inputs , msgInfo , inoutparms , outparms);
		
		BigDecimal[] PERSON_ID = outparms.value.getPERSON_ID();
		
		String[] PERSON_LEVEL_IND = outparms.value.getPERSON_LEVEL_IND();
		
		String[] PERSON_SEARCH_NAME = outparms.value.getPERSON_SEARCH_NAME();
		
		String[] PERSON_STATUS = outparms.value.getPERSON_STATUS();
		
		String[] PERSON_STATUS_START_DATE = outparms.value.getPERSON_STATUS_START_DATE();
		
		String[] RIDER_IND = outparms.value.getRIDER_IND();
		
		ACCPEBWResponseINOUT_PARM1LINEsLINE1[] outArray = inoutparms.value.getLINEs();
		
		Vector pperson = new Vector();
		
		for (int i = 0; i < outArray.length; i++) 
		{
			ACCPEBWResponseINOUT_PARM1LINEsLINE1 item = outArray[i];
			PolicyPersonMainListDsp PolicyPerson = new  PolicyPersonMainListDsp();
			
			if (item.getPERSON_STATUS_IND1().trim().length() != 0) 
			{
//				System.out.print("\n");
//				System.out.println("Line:"  + i);
					
				String[] POLICY_PERSON_TYPE_CURRENT = item.getPOLICY_PERSON_TYPE_CURRENT1();
				
				PolicyPerson.setTone(POLICY_PERSON_TYPE_CURRENT[0]);
				PolicyPerson.setTtwo(POLICY_PERSON_TYPE_CURRENT[1]);
				PolicyPerson.setTthree(POLICY_PERSON_TYPE_CURRENT[2]);
				PolicyPerson.setTfour(POLICY_PERSON_TYPE_CURRENT[3]);
				PolicyPerson.setTfive(POLICY_PERSON_TYPE_CURRENT[4]);
				PolicyPerson.setPERSON_STATUS_IND(item.getPERSON_STATUS_IND1());	
				PolicyPerson.setSMOKER_IND(item.getSMOKER_IND1());
				PolicyPerson.setPERSON_ID(PERSON_ID[i].toString());
				PolicyPerson.setPERSON_LEVEL_IND(PERSON_LEVEL_IND[i].toString());
				PolicyPerson.setPERSON_SEARCH_NAME(PERSON_SEARCH_NAME[i].toString());
				PolicyPerson.setPERSON_STATUS(PERSON_STATUS[i].toString());
				PolicyPerson.setPERSON_STATUS_START_DATE(TextProcessing.plainDateFormat(PERSON_STATUS_START_DATE[i].toString()));
				
				
				if(RIDER_IND[i].toString().equalsIgnoreCase("Y"))
				{
					PolicyPerson.setColumnMode(true);
					PolicyPerson.setRIDER_IND("Yes");
					PolicyPerson.setRIDER_SHOW("Open");
				}
				else
				{
					PolicyPerson.setColumnMode(false);
					PolicyPerson.setRIDER_IND("No");
					if(PERSON_STATUS[i].toString().equalsIgnoreCase("d"))
						PolicyPerson.setRIDER_SHOW("");
					else
					{
						if (IASModify.equalsIgnoreCase("display"))
						{
							PolicyPerson.setRIDER_SHOW("");
						}
						else
						{
							PolicyPerson.setRIDER_SHOW("Create");
							
						}
					}
				}
				pperson.add(PolicyPerson);
				
			}
		
		}
		
		PolicyPersonMainBean[] items = new PolicyPersonMainBean[pperson.size()];
		//PolicyPersonMainListDsp[] result = new PolicyPersonMainListDsp[pperson.size()];
		//pperson.toArray(items);
		int j = 0;
		
		Enumeration e = pperson.elements();
		
		while (e.hasMoreElements()) {
			PolicyPersonMainListDsp data = (PolicyPersonMainListDsp) e.nextElement();
			
			items[j++] = new PolicyPersonMainBean(data);
		}

	
		return new PolicyPersonMainDisplayList(items);
	 
	}

}
