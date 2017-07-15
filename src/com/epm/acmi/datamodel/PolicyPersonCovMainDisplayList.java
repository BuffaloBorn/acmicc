package com.epm.acmi.datamodel;

import com.cc.acmi.presentation.dsp.PolicyPersonCovMainListDsp;
import com.cc.framework.ui.model.ListDataModel;

public class PolicyPersonCovMainDisplayList implements ListDataModel {

	private PolicyPersonCovMainListDsp[] data = new PolicyPersonCovMainListDsp[0]; 
	
	public PolicyPersonCovMainDisplayList() {
		super();
	}
	
	public PolicyPersonCovMainDisplayList(PolicyPersonCovMainListDsp[] data) {
		this.data = data;
	}

	public Object getElementAt(int index) {
		return data[index];
	}

	public String getUniqueKey(int index) {
		return data[index].getCOVERAGE_CODE() ;
	}

	public int size() {
		return data.length;
	}

	public PolicyPersonCovMainListDsp[] getData() {
		return data;
	}

	public void setData(PolicyPersonCovMainListDsp[] data) {
		this.data = data;
	}

	

}
