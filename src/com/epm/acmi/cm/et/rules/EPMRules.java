package com.epm.acmi.cm.et.rules;

public interface EPMRules {

	public String isProcessExists(String GFID, String DocType) throws Exception;

	public String isProcessRunning(String GFID, String DocType) throws Exception;

	public String isProcessSuspended(String GFID, String DocType) throws Exception;

	public String CreateProcess(String GFID, String policyNum, String DocType, String recId) throws Exception;

	public boolean resumeProcess(String GFID, String DocType, String mauallystarted) throws Exception;
	
	public String isEPMProcessSuspended(String gfId, String epmDocType) throws Exception;
	
	public boolean isApplicationClosed(String gfId, String policyNo, String docType) throws Exception;
	
	public boolean doesGFIDExist(String gfId) throws Exception;
}
