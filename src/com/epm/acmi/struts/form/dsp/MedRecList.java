package com.epm.acmi.struts.form.dsp;


import com.cc.framework.ui.model.ListDataModel;

/**
 * Collection with RoleDsp-Objects
 * Author: Dragos
 */
public class MedRecList implements ListDataModel {

    private MedRecDisp[] data = new MedRecDisp[0];
         
    public MedRecList() {    
    }

    public Object getElementAt(int index) {
        return data[index];
    }

    public int size() {
        return data.length;
    }

    public void setData(MedRecDisp[] data) {
    	this.data = data;
    }
    
    /**
     * Unique Key for each Row (Object).
     * In this Example our Key only contains the UserId.
     */
    public String getUniqueKey(int index) {
        return data[index].getApplicantID();
    }
}

