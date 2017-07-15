package com.epm.acmi.datamodel;

import com.cc.acmi.presentation.dsp.PolicyEventsDsp;
import com.cc.framework.ui.model.ListDataModel;

public class PolicyEventsDisplayList implements ListDataModel {
	/**
	 * Collection with UserDsp-Objects (=Rows) for our ListControl
	 */
	private PolicyEventsDsp[] data = new PolicyEventsDsp[0];
	
	
	/**
	 * Constructor for UserDisplayList.
	 */
	public PolicyEventsDisplayList() {
		super();
	}
	
	/**
	 * Constructor UserDisplayList
	 * @param	elements	List with Display-Objects
	 */
	public PolicyEventsDisplayList(PolicyEventsDsp[] elements) {
		data = elements;
	}
	
	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return data[index];
	}

	public String getUniqueKey(int index) {
		// TODO Auto-generated method stub
		return data[index].getEVENT_ID().toString();
	}

	public int size() {
		// TODO Auto-generated method stub
		return data.length;
	}

	public PolicyEventsDsp[] getData() {
		return data;
	}

	public void setData(PolicyEventsDsp[] data) {
		this.data = data;
	}

}
