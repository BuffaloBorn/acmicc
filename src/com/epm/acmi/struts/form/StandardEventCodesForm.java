package com.epm.acmi.struts.form;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import com.cc.framework.adapter.struts.FWActionForm;
import com.epm.acmi.bean.StandardEventIdBean;
import com.epm.acmi.util.ACMICache;

public class StandardEventCodesForm extends FWActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String stdEventCode = "";
	private String hiddenfield = "";

	
	public TreeMap getstdEventCodeOptions()
	{
		
		TreeMap localMap = new TreeMap();
		
		TreeMap map = ACMICache.getStdEventCodes();
		 
		String key;
		Set set= map.keySet (  ) ; 
	    Iterator iter = set.iterator (  ) ; 
		
	 
	    while ( iter.hasNext (  )  )  
	    { 
	    	key = iter.next().toString();
	    	
	    	StandardEventIdBean stdEvent = (StandardEventIdBean) map.get(key);
	    	
	    	localMap.put(stdEvent.getCODE(), stdEvent.getCODE() + ": " + stdEvent.getDESCRIPTION());
	    	
	    }
	    
		
		return localMap;
	}
	

	public String getStdEventCode() {
		return stdEventCode;
	}


	public void setStdEventCode(String stdEventCode) {
		this.stdEventCode = stdEventCode;
	}


	public String getHiddenfield() {
		return hiddenfield;
	}


	public void setHiddenfield(String hiddenfield) {
		this.hiddenfield = hiddenfield;
	}
	
	
	
}
