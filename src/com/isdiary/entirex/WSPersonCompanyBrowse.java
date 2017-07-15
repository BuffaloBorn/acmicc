package com.isdiary.entirex;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.xml.rpc.ServiceException;

import com.cc.acmi.presentation.dsp.PersonCompanyBrowseDsp;
import com.epm.acmi.datamodel.PersonCompanyBrowseDisplayList;
import com.softwarag.extirex.webservice.browseperscomp.client.ACPCYBWMORE;
import com.softwarag.extirex.webservice.browseperscomp.client.ACPCYBWResponseOUT_PARMSsOUT_PARMS;
import com.softwarag.extirex.webservice.browseperscomp.client.ACSLIBPort;
import com.softwarag.extirex.webservice.browseperscomp.client.BrowsePersCompLocator;
import com.softwarag.extirex.webservice.browseperscomp.client.holders.ACPCYBWResponseMORE1Holder;
import com.softwarag.extirex.webservice.browseperscomp.client.holders.ACPCYBWResponseMSG_INFOHolder;
import com.softwarag.extirex.webservice.browseperscomp.client.holders.ACPCYBWResponseOUT_PARMSsHolder;

public class WSPersonCompanyBrowse {

	public static synchronized PersonCompanyBrowseDisplayList fetch(String PolicyNo, String START_PERSON_ID,  String MORE_REC ) throws ServiceException, RemoteException
	{
		
		ACSLIBPort service = new BrowsePersCompLocator().getACSLIBPort();
		
		ACPCYBWMORE more = new ACPCYBWMORE();
		ACPCYBWResponseMSG_INFOHolder msgInfo = new ACPCYBWResponseMSG_INFOHolder();
		ACPCYBWResponseMORE1Holder more1 = new ACPCYBWResponseMORE1Holder(); 
	
		ACPCYBWResponseOUT_PARMSsHolder outparms = new ACPCYBWResponseOUT_PARMSsHolder();
		
		Vector pPersonCompanyBrowse = new Vector();
		
		more.setSTART_PERSON_ID(new BigDecimal(PolicyNo));
		more.setMORE_RECS(new String(""));
		
		
		service.ACPCYBW(more,  msgInfo, more1, outparms );
		
		ACPCYBWResponseOUT_PARMSsOUT_PARMS[] outArray = outparms.value;
		
		PersonCompanyBrowseDsp PersonCompanyBrowse = new PersonCompanyBrowseDsp();
		
		MORE_REC = more1.value.getMORE_RECS1();
		START_PERSON_ID = more1.value.getSTART_PERSON_ID1().toString();
		
		for (int i = 0; i < outArray.length; i++)
		{
			ACPCYBWResponseOUT_PARMSsOUT_PARMS item = outArray[i];
			
			PersonCompanyBrowse.setBIRTH_DATE(item.getBIRTH_DATE());
			PersonCompanyBrowse.setBUSINESS_PHONE_NUMBER(item.getBUSINESS_PHONE_NUMBER());
			PersonCompanyBrowse.setDRIVER_LICENCE_STATE(item.getDRIVER_LICENCE_STATE());
			PersonCompanyBrowse.setDRIVERS_LICENCE_ID(item.getDRIVERS_LICENCE_ID());
			PersonCompanyBrowse.setEMPLOYER(item.getEMPLOYER());
			PersonCompanyBrowse.setEMPLOYMENT_START(item.getEMPLOYMENT_START());
			PersonCompanyBrowse.setFAMILY_NAME(item.getFAMILY_NAME());
			PersonCompanyBrowse.setFIRST_NAME(item.getFIRST_NAME());
			PersonCompanyBrowse.setHEIGHT_FEET_INCHES(item.getHEIGHT_FEET_INCHES());
			PersonCompanyBrowse.setHONORIFICS(item.getHONORIFICS());
			PersonCompanyBrowse.setMIB_BIRTH_PLACE(item.getMIB_BIRTH_PLACE());
			PersonCompanyBrowse.setMIDDLE_NAME(item.getMIDDLE_NAME());
			PersonCompanyBrowse.setMONTHLY_INCOME(item.getMONTHLY_INCOME());
			PersonCompanyBrowse.setNAME_SUFFIX(item.getNAME_SUFFIX());
			PersonCompanyBrowse.setOCCUPATION(item.getOCCUPATION());
			PersonCompanyBrowse.setPERSON_ID(item.getPERSON_ID());
			PersonCompanyBrowse.setPERSON_TYPE(item.getPERSON_TYPE());
			PersonCompanyBrowse.setRESIDENTIAL_PHONE(item.getRESIDENTIAL_PHONE());
			PersonCompanyBrowse.setRESIDENTIAL_SEARCH_ADDRESS(item.getRESIDENTIAL_SEARCH_ADDRESS());
			PersonCompanyBrowse.setSEARCH_NAME(item.getSEARCH_NAME());
			PersonCompanyBrowse.setSEX(item.getSEX());
			PersonCompanyBrowse.setSMOKER_IND(item.getSMOKER_IND());
			PersonCompanyBrowse.setSSN_NUMBER(item.getSSN_NUMBER());
			PersonCompanyBrowse.setTITLE(item.getTITLE());
			PersonCompanyBrowse.setWEIGHT_POUNDS(item.getWEIGHT_POUNDS());
			
			
			pPersonCompanyBrowse.add(PersonCompanyBrowse);
			
		}
		
		PersonCompanyBrowseDsp[] result = new PersonCompanyBrowseDsp[pPersonCompanyBrowse.size()];
		return new PersonCompanyBrowseDisplayList(result);
		
	}
}
