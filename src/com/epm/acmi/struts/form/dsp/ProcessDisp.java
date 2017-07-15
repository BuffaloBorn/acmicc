package com.epm.acmi.struts.form.dsp;

import com.cc.framework.common.DisplayObject;

/**
 * Role DisplayObject (ViewHelper)
 * Author: Dragos
 */
public class ProcessDisp implements DisplayObject {

	private String pid;
    private String processType;
    private String suspendDate;
    private boolean checkState;
   
	public boolean getCheckState() {
		return checkState;
	}

	public void setCheckState(boolean checkState) {
		this.checkState = checkState;
	}

	public ProcessDisp()
    {
    }

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getSuspendDate() {
		return suspendDate;
	}

	public void setSuspendDate(String suspendDate) {
		this.suspendDate = suspendDate;
	}
	
	

}
