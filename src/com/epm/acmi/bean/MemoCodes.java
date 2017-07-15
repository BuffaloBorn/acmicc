package com.epm.acmi.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import com.cc.acmi.presentation.dsp.MemoCodesDsp;
import com.epm.acmi.datamodel.MemoCodesDisplayList;
import com.epm.acmi.util.ACMICache;

public final class MemoCodes {

	TreeMap localMemoCodes = ACMICache.getMemoIdCodes();
	
	/**
	 * Returns a List with all DisplayObjects
	 * @return	ManufacturerDisplayList
	 */
	public static synchronized  MemoCodesDisplayList fetch() {
		return fetch(new Filter()) ;
	}

	public static MemoCodesDisplayList fetch(Filter filter) {
			
		ArrayList items = new ArrayList();
		
		Iterator it = ACMICache.getMemoIdCodes().keySet().iterator();

		while (it.hasNext()) {
			
			String key = it.next().toString();
			
			MemoCodesDsp item = (MemoCodesDsp) ACMICache.getMemoIdCodes().get(key);
		      
			
			if (isMatching(item, filter)) {
				items.add(item);
			}
		}
		
		MemoCodesDsp[] dspList = new MemoCodesDsp[items.size()];
		
		return new MemoCodesDisplayList((MemoCodesDsp[]) items.toArray(dspList));
	}
	
	/**
	 * 
	 * @param	item	The Item to compare
	 * @param	filter	The Filter settings
	 * @return	boolean
	 */	
	private static boolean isMatching(MemoCodesDsp item, Filter filter) {

		if (filter.getAttributes().isEmpty()) {
			 return true;
		}
		
		Iterator it = filter.getAttributes().keySet().iterator();
		
		while (it.hasNext()) {
			
			String key = (String) it.next();
			String val = (String) filter.getAttributes().get(key);
			
			if (key.equalsIgnoreCase("MEMO_ID")) {
							
				if (item.getMEMO_ID().startsWith(val)) {
					
					return true;
				}
				
			} 
		}		
		
		return false;
	}
	
}
