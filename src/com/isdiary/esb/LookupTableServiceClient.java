package com.isdiary.esb;

import java.rmi.RemoteException;
import javax.xml.rpc.ServiceException;
import java.util.Calendar;
import java.util.TreeMap;

import com.cc.acmi.presentation.dsp.ConditionCodeDsp;
import com.softwarag.esb.webservice.looktables.client.APPLICATION_AREA;
import com.softwarag.esb.webservice.looktables.client.GETLOOKUPTABLES;
import com.softwarag.esb.webservice.looktables.client.LookupCode;
import com.softwarag.esb.webservice.looktables.client.LookupTable;

import com.softwarag.esb.webservice.looktables.client.SENDER;

public class LookupTableServiceClient {

	private final static String ROW_PREFIX = "row_";
	
	//Return of TReemap of codes and descriotion for specificed table
	//	Valid Table names are
	//		Condition
	//		Memo Id
	//		Sub-Standard Reason
	//		Standard Event
	//		Underwriting Status
	public static synchronized TreeMap  GetLookupTableMap(String tableName) throws ServiceException, RemoteException
	{
		TreeMap tableMap = null;
		String[] tableNames = new String[1];
		tableNames[0] = tableName;
		TreeMap tablesMap = GetLookupTablesMap(tableNames);
		if(tablesMap != null)
		{
			tableMap = (TreeMap)tablesMap.get(tableName);
		}
		return tableMap;
	}
	
	
	//Return of TReemap of codes and descriotion for specificed table
	//	Valid Table names are
	//		Condition
	//		Memo Id
	//		Sub-Standard Reason
	//		Standard Event
	//		Underwriting Status
	public static synchronized void GetLookupTableMapWithExtendedFields(String tableName, TreeMap CodeDesc, TreeMap CodeExtendedFeild) throws ServiceException, RemoteException
	{

		String[] tableNames = new String[1];
		tableNames[0] = tableName;
		GetLookupTablesMapWithExtendedFields(tableNames, CodeDesc,CodeExtendedFeild );
	
	}

	//Return of TreeMap of TreeMaps of Tables containing code description
	//Outer Treemap's key is the table name. Inner treep map's key is the code.
	//Valid Table names are
	//	Condition
	//		Memo Id
	//		Sub-Standard Reason
	//		Standard Event
	//		Underwriting Status
	public static synchronized TreeMap  GetLookupTablesMap(String[] tableNames) throws ServiceException, RemoteException 
	{
		com.softwarag.esb.webservice.looktables.client.LookupTableServiceSoapStub binding;
        try {
            binding = (com.softwarag.esb.webservice.looktables.client.LookupTableServiceSoapStub)
                          new com.softwarag.esb.webservice.looktables.client.LookupTableServiceLocator().getLookupTableServiceSoap();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new RemoteException("JAX-RPC ServiceException caught: " + jre);
        }
        if(binding == null)
        	throw new RemoteException("Binding is null");
        	

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        GETLOOKUPTABLES getLookupTables = new com.softwarag.esb.webservice.looktables.client.GETLOOKUPTABLES();
        //Init Application Area;
        getLookupTables.setAPPLICATION_AREA(new APPLICATION_AREA());
        APPLICATION_AREA applicationArea = getLookupTables.getAPPLICATION_AREA();
        applicationArea.setAPI_VERSION("1.0");
        applicationArea.setENVIRONMENT("DEV");
        applicationArea.setSCHEMA_VERSION("1.0");
        SENDER sender = new SENDER();
        sender.setCOMPONENT_ID("IUPS");
        sender.setGUID("");
        sender.setCOMPONENT_ID("GetLookupTable");
        sender.setLANGUAGE("en");
        sender.setLOGICAL_ID("IUPSUSER");
        sender.setREFERENCE_ID("");
        sender.setTASK_ID("");
        sender.setTIMESTAMP(Calendar.getInstance());
        applicationArea.setSENDER(sender);
        
        getLookupTables.setTABLE_NAMES(tableNames);
                
        com.softwarag.esb.webservice.looktables.client.GETLOOKUPTABLESResponse value = null;
        value = binding.getLookupTables(getLookupTables);
        
        LookupTable[] tables = value.getLOOKUP_TABLES();
        TreeMap tablesMap = new TreeMap();
          
        if(value.getLOOKUP_TABLES() != null)
        {
        	for(int i = 0; i < tables.length; i++)
        	{
        		TreeMap tableMap = new TreeMap();
        		LookupTable table = tables[i];
        		LookupCode[] codes = table.getLOOKUP_VALUES();
        		if(codes != null)
        		{
        			for(int k = 0; k < codes.length; k++)
        			{
        				LookupCode code = codes[k];
        				tableMap.put(code.getCODE(), code.getDESCRIPTION());
        				//This code excerpt pulls extended Field Names and Values from the response.
        				for(int j = 0; j < table.getNAMES_COUNT(); j++)
        				{
        					//String extendedFieldName = table.getNAMES()[j].getEXTENDED_FIELD();
        					//String extendedFieldValue = code.getEXTENDED_VALUES()[j];
        				}
        			}
        		}
        		tablesMap.put(table.getNAME(), tableMap);
        	}
        }
        return tablesMap;
    }
	
	//Return of TreeMap of TreeMaps of Tables containing code description
	//Outer Treemap's key is the table name. Inner treep map's key is the code.
	//Valid Table names are
	//	Condition
	//		Memo Id
	//		Sub-Standard Reason
	//		Standard Event
	//		Underwriting Status
	public static synchronized void GetLookupTablesMapWithExtendedFields(String[] tableNames, TreeMap CodeDesc, TreeMap CodeExtendedFeild) throws ServiceException, RemoteException 
	{
		com.softwarag.esb.webservice.looktables.client.LookupTableServiceSoapStub binding;
        try {
            binding = (com.softwarag.esb.webservice.looktables.client.LookupTableServiceSoapStub)
                          new com.softwarag.esb.webservice.looktables.client.LookupTableServiceLocator().getLookupTableServiceSoap();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new RemoteException("JAX-RPC ServiceException caught: " + jre);
        }
        if(binding == null)
        	throw new RemoteException("Binding is null");
        	

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
        GETLOOKUPTABLES getLookupTables = new com.softwarag.esb.webservice.looktables.client.GETLOOKUPTABLES();
        //Init Application Area;
        getLookupTables.setAPPLICATION_AREA(new APPLICATION_AREA());
        APPLICATION_AREA applicationArea = getLookupTables.getAPPLICATION_AREA();
        applicationArea.setAPI_VERSION("1.0");
        applicationArea.setENVIRONMENT("DEV");
        applicationArea.setSCHEMA_VERSION("1.0");
        SENDER sender = new SENDER();
        sender.setCOMPONENT_ID("IUPS");
        sender.setGUID("");
        sender.setCOMPONENT_ID("GetLookupTable");
        sender.setLANGUAGE("en");
        sender.setLOGICAL_ID("IUPSUSER");
        sender.setREFERENCE_ID("");
        sender.setTASK_ID("");
        sender.setTIMESTAMP(Calendar.getInstance());
        applicationArea.setSENDER(sender);
        
        getLookupTables.setTABLE_NAMES(tableNames);
                
        com.softwarag.esb.webservice.looktables.client.GETLOOKUPTABLESResponse value = null;
        value = binding.getLookupTables(getLookupTables);
        
        LookupTable[] tables = value.getLOOKUP_TABLES();
        //TreeMap tablesMap = new TreeMap();
          
        if(value.getLOOKUP_TABLES() != null)
        {
        	for(int i = 0; i < tables.length; i++)
        	{
        		//TreeMap tableMap = new TreeMap();
        		LookupTable table = tables[i];
        		LookupCode[] codes = table.getLOOKUP_VALUES();
        		if(codes != null)
        		{
        			for(int k = 0; k < codes.length; k++)
        			{
        				LookupCode code = codes[k];
        				CodeDesc.put(code.getCODE(), code.getCODE() + ": " +code.getDESCRIPTION());
        				//This code excerpt pulls extended Field Names and Values from the response.
        				for(int j = 0; j < table.getNAMES_COUNT(); j++)
        				{
        					//String extendedFieldName = table.getNAMES()[j].getEXTENDED_FIELD();
        					String extendedFieldValue = code.getEXTENDED_VALUES()[j];
        					CodeExtendedFeild.put(code.getCODE(), new ConditionCodeDsp(code.getCODE(), code.getDESCRIPTION(), extendedFieldValue, ROW_PREFIX + Integer.toString(k+1)));
        				}
        			}
        		}
        		//CodeDesc.put(table.getNAME(), tableMap);
        	}
        }
        
    }
}
