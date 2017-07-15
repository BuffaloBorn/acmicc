package com.epm.acmi.datamodel;

import com.cc.acmi.presentation.dsp.BrowseRiderCodesDsp;
import com.cc.framework.ui.model.ListDataModel;

public class BrowseRiderCodesDisplatList implements ListDataModel {

	private BrowseRiderCodesDsp[] data = new BrowseRiderCodesDsp[0];
	
	public BrowseRiderCodesDisplatList(BrowseRiderCodesDsp[] elements) {
		super();
		this.data = elements;
	}
	
	public Object getElementAt(int index) 
	{	
		return data[index];
	}

	public String getUniqueKey(int index) 
	{
		return data[index].getRIDER_CODE();
	}

	public int size() 
	{	
		return data.length;
	}

	public BrowseRiderCodesDisplatList() {
		super();
	}

	public BrowseRiderCodesDsp[] getData() {
		return data;
	}

	public void setData(BrowseRiderCodesDsp[] data) {
		this.data = data;
	}

	

}
