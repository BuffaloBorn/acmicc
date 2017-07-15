package com.epm.acmi.struts.form.dsp;

import com.cc.framework.ui.model.ListDataModel;

/**
 * Collection with RoleDsp-Objects
 * Author: Dragos
 */
public class ProcessList implements ListDataModel {

    private ProcessDisp[] data = new ProcessDisp[0];
         
    public ProcessList() {  
//    	this.data = new ProcessDisp[10];
//    	
//    	ProcessDisp a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("1432");
//    	a.setProcessType("MEMO");
//    	a.setSuspendDate("12/13/05");
//    	this.data[0] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("21231");
//    	a.setProcessType("DMV");
//    	a.setSuspendDate("5/20/05");
//    	this.data[1] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("3145");
//    	a.setProcessType("MEDREC");
//    	a.setSuspendDate("9/21/05");
//    	this.data[2] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("2423");
//    	a.setProcessType("MEDREC");
//    	a.setSuspendDate("9/21/05");
//    	this.data[3] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("3134234245");
//    	a.setProcessType("DMV");
//    	a.setSuspendDate("9/21/05");
//    	this.data[4] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("312345");
//    	a.setProcessType("MEMO");
//    	a.setSuspendDate("9/21/05");
//    	this.data[5] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("32346");
//    	a.setProcessType("DMV");
//    	a.setSuspendDate("9/21/05");
//    	this.data[6] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("8788");
//    	a.setProcessType("MEMO");
//    	a.setSuspendDate("9/21/05");
//    	this.data[7] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("8766");
//    	a.setProcessType("DMV");
//    	a.setSuspendDate("9/21/05");
//    	this.data[8] = a;
//    	
//    	a = new ProcessDisp();
//    	a.setPid("5678");
//    	a.setProcessType("MEDREC");
//    	a.setSuspendDate("9/21/05");
//    	this.data[9] = a;
    	
    }

    public Object getElementAt(int index) {
        return data[index];
    }

    public int size() {
        return data.length;
    }

    public void setData(ProcessDisp[] data) {
    	this.data = data;
    }
    
    /**
     * Unique Key for each Row (Object).
     * In this Example our Key only contains the UserId.
     */
    public String getUniqueKey(int index) {
        return data[index].getPid();
    }
}

