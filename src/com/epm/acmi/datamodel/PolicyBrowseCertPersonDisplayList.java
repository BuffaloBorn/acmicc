package com.epm.acmi.datamodel;

import com.cc.acmi.presentation.dsp.PolicyBrowseCertPersonDsp;
import com.cc.framework.ui.model.ListDataModel;

public class PolicyBrowseCertPersonDisplayList implements ListDataModel {

	private PolicyBrowseCertPersonDsp[] data = new PolicyBrowseCertPersonDsp[0];
	
	public PolicyBrowseCertPersonDisplayList(PolicyBrowseCertPersonDsp[] elements) {
		this.data = elements;
	}
	
	public Object getElementAt(int index) {
		return data[index];
	}

	public String getUniqueKey(int index) {
		return data[index].getPERSON_ID().toString();
	}

	public int size() {
		return data.length;
	}

	public PolicyBrowseCertPersonDisplayList() {
		super();
	}

	public PolicyBrowseCertPersonDsp[] getData() {
		return data;
	}

	public void setData(PolicyBrowseCertPersonDsp[] data) {
		this.data = data;
	}

	

}
