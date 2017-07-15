package com.epm.acmi.datamodel;

import com.cc.acmi.presentation.dsp.PolicyBrowseDsp;
import com.cc.framework.ui.model.ListDataModel;

public class PolicyBrowseDisplayList implements ListDataModel {

	private PolicyBrowseDsp[] data = new PolicyBrowseDsp[0];
		
	public PolicyBrowseDisplayList(PolicyBrowseDsp[] elements) {
		data = elements;
	}

	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return data[index];
	}

	public String getUniqueKey(int index) {
		// TODO Auto-generated method stub
		return data[index].getPOLICY_ID().toString();
	}

	public int size() {
		// TODO Auto-generated method stub
		return data.length;
	}

	public PolicyBrowseDisplayList() {
		super();
		// TODO Auto-generated constructor stub
	}

}
