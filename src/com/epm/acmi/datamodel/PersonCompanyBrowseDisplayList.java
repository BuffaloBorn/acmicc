package com.epm.acmi.datamodel;

import com.cc.acmi.presentation.dsp.PersonCompanyBrowseDsp;
import com.cc.framework.ui.model.ListDataModel;

public class PersonCompanyBrowseDisplayList implements ListDataModel {

	
	PersonCompanyBrowseDsp[] data = new PersonCompanyBrowseDsp[0];
	
	public PersonCompanyBrowseDisplayList(PersonCompanyBrowseDsp[] elements)
	{
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

	public PersonCompanyBrowseDisplayList() {
		super();
	}

	public PersonCompanyBrowseDsp[] getData() {
		return data;
	}

	public void setData(PersonCompanyBrowseDsp[] data) {
		this.data = data;
	}

	
	
}
