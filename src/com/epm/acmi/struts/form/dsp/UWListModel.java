package com.epm.acmi.struts.form.dsp;

import java.io.Serializable;
import com.epm.acmi.util.LDAPUser;
import com.cc.framework.ui.model.OptionListDataModel;

public class UWListModel implements OptionListDataModel, Serializable {

	Object[] value;
	
	public Object getKey(int arg0) {
		if (value != null) {
			LDAPUser user = (LDAPUser)value[arg0];
			return user.getUserId() + "|" + user.getFullName();
		}
		return null;
	}

	public String getTooltip(int arg0) {
		LDAPUser user = (LDAPUser)value[arg0];
		return user.getFullName();
	}

	public Object getValue(int arg0) {
		if (value != null) {
			LDAPUser user = (LDAPUser)value[arg0];
			return user.getFullName();
		}
		return null;
	}

	public int size() {
		if (value != null){
			return value.length;
		}
		return 0;
	}
	
	public void setValues(Object[] values) {
		value = values;
	}
}
