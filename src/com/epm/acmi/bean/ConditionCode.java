package com.epm.acmi.bean;

import java.util.Vector;

import com.cc.acmi.presentation.dsp.ConditionCodeDsp;
import com.epm.acmi.datamodel.ConditionCodesDisplayList;
import com.epm.acmi.util.ACMICache;

public final class ConditionCode {

	private final static String ROW_PREFIX = "row_";
	
	public static ConditionCodesDisplayList loadList(String[] CONDITION_CODE, String[] CONDITION_TEXT )
	{
		Vector conditions = new Vector();
		String required_text = "";
		
		for (int i = 0; i < CONDITION_CODE.length; i++) 
		{
		
			if(CONDITION_CODE[i]!= null && CONDITION_CODE[i].toString().trim().length() != 0 )
			{
				ConditionCodeDsp item = (ConditionCodeDsp) ACMICache.getConditionCodesExtendedFields().get(CONDITION_CODE[i].toString());
				
				if (item.getDESCRIPTION_REQUIRED_IND() == null)
					required_text = "";
				else	
					required_text  = item.getDESCRIPTION_REQUIRED_IND();
			}
			else
				required_text = "";
			
			conditions.add(new ConditionCodeDsp(CONDITION_CODE[i].toString(), CONDITION_TEXT[i].toString(), required_text, ROW_PREFIX + Integer.toString(i+1)));
		}
		
		ConditionCodeDsp[] dspList = new ConditionCodeDsp[conditions.size()];
		return  new ConditionCodesDisplayList((ConditionCodeDsp[]) conditions.toArray(dspList));
	}
	
}
