package com.epm.acmi.datamodel;

import com.cc.acmi.presentation.dsp.ConditionCodeDsp;
import com.cc.framework.ui.model.ListDataModel;
import com.cc.framework.util.StringHelp;

public class ConditionCodesDisplayList implements ListDataModel {

	private ConditionCodeDsp[] data = new ConditionCodeDsp[0];
	
	public ConditionCodesDisplayList(ConditionCodeDsp[] elements) {
		
		this.data = elements;
	}
	
	public Object getElementAt(int index) {
	
		return data[index];
	}

	public String getUniqueKey (int index) {
		
		return data[index].getPOSTION();
	}

	public int size() {

		return data.length;
	}

	public ConditionCodeDsp[] getData() {
		return data;
	}

	public ConditionCodeDsp getPositionAt(String position)
	{
		int index = getIndex(position);
		return data[index];
	}
	
	public void setData(ConditionCodeDsp[] data) {
		this.data = data;
	}

	public ConditionCodesDisplayList() {
		super();
	}

	public int getIndex(String uniqueKey) {
		String index = StringHelp.split(uniqueKey, "_")[1];
		return Integer.parseInt(index)-1; 
	}

	
}
