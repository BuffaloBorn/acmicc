package com.epm.acmi.struts.form.dsp;

import com.cc.framework.ui.model.ListDataModel;

public class TelInterviewList implements ListDataModel {
	
	private TInterviewDisp[] data = new TInterviewDisp[0];
    
    public Object getElementAt(int index) {
        return data[index];
    }

    public int size() {
        return data.length;
    }

    public void setData(TInterviewDisp[] data) {
    	this.data = data;
    }
    
    /**
     * Unique Key for each Row (Object).
     * In this Example our Key only contains the UserId.
     */
    public String getUniqueKey(int index) {
        return String.valueOf(data[index].getInterviewRequestId());
    }

}
