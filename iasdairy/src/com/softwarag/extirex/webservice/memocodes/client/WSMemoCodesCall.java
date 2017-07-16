package com.softwarag.extirex.webservice.memocodes.client;


import java.io.IOException;
import java.util.Vector;

import com.cc.sample.presentation.dsp.MemoCodesDsp;
import com.csvreader.CsvReader;
import com.epm.acmi.datamodel.MemoCodesDisplayList;



public class WSMemoCodesCall {

	public static synchronized MemoCodesDisplayList fetch() throws  IOException
	{
	
		CsvReader reader = new CsvReader("memoids.csv");

		reader.readHeaders();

		Vector pMemoCodes = new Vector();
		
		while (reader.readRecord())
		{
			String MEMO_ID = reader.get("MEMO_ID");
			String DESCRIPTION = reader.get("DESCRIPTION");
			String EFF_DT_REQ_IND = reader.get("EFF_DT_REQ_IND");
			String ATTACHMENT_IND = reader.get("ATTACHMENT_IND");
			
			
			if (MEMO_ID.length() != 0 )
			{
				
				MemoCodesDsp MemoCodes = new MemoCodesDsp();
				
				MemoCodes.setMEMO_ID(MEMO_ID);
				MemoCodes.setDESCRIPTION(DESCRIPTION);
				MemoCodes.setEFF_DT_REQ_IND(EFF_DT_REQ_IND);
				MemoCodes.setATTACHMENT_IND(ATTACHMENT_IND);
				
		
				pMemoCodes.add(MemoCodes);
			}


		}

		reader.close();
		
		MemoCodesDsp[] result = new MemoCodesDsp[pMemoCodes.size()];
		pMemoCodes.toArray(result);
		
		return new MemoCodesDisplayList(result);

		
	}

	
	
}
