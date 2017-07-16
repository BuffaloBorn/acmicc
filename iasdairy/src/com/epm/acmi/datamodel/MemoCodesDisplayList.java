package com.epm.acmi.datamodel;

import com.cc.framework.ui.model.ListDataModel;
import com.cc.sample.presentation.dsp.MemoCodesDsp;


public class MemoCodesDisplayList implements ListDataModel {

	private MemoCodesDsp[] data = new MemoCodesDsp[0];
	
	public MemoCodesDisplayList(MemoCodesDsp[] elements) {
		
		this.data = elements;
	}

	public Object getElementAt(int index) {
		// TODO Auto-generated method stub
		return data[index];
	}

	public String getUniqueKey (int index) {
		// TODO Auto-generated method stub
		return data[index].getMEMO_ID();
	}

	public int size() {
		// TODO Auto-generated method stub
		return data.length;
	}

	public MemoCodesDisplayList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemoCodesDsp[] getData() {
		return data;
	}

	public void setData(MemoCodesDsp[] data) {
		this.data = data;
	}

}
