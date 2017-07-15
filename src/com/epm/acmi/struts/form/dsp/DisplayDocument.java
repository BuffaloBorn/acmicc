package com.epm.acmi.struts.form.dsp;

import java.io.Serializable;

import com.Optika.message.SystemFields;
import com.cc.framework.common.DisplayObject;

/**
 * DisplayDocument represents Meta Data of the document
 * 
 * @author Jay Hombal
 */
public class DisplayDocument implements DisplayObject, Serializable {

	private static final String NULL_STRING = "";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// System Fields
	private String toolName = "AWVWR";

	private String URL = NULL_STRING;

	private String viewer = "Image";

	private String LUCID = NULL_STRING;

	private String MIMETYPE = NULL_STRING;

	private String SSPROVIDERID = NULL_STRING;

	private String TABLENAME = NULL_STRING;

	private String ROWIDENTIFIER = NULL_STRING;

	private String INDEXPROVIDER = NULL_STRING;

	private String EOF = "1";

	// datafields
	private String docType = NULL_STRING;

	private String docDescription = NULL_STRING;

	private String GFID = NULL_STRING;

	private String appFirstName = NULL_STRING;

	private String appLastName = NULL_STRING;

	private String keyAppFirstName = NULL_STRING;

	private String keyAppLastName = NULL_STRING;

	private String AppMiddle = NULL_STRING;

	private String KeyAppMiddle = NULL_STRING;

	private String KeyAppSuffix = NULL_STRING;

	private String AppSuffix = NULL_STRING;

	private String annotate = NULL_STRING;

	private String CFP = NULL_STRING;

	private String scanDate = NULL_STRING;

	private String docCode = NULL_STRING;

	private String cmslbId = NULL_STRING;

	private String state = NULL_STRING;

	private String policyNumber = NULL_STRING;

	private String reviewed = NULL_STRING;
	
	private String agentName = NULL_STRING;
	
	private String agentNumber = NULL_STRING;
	
	private String product = NULL_STRING;
	
	protected String keyAppFN = keyAppLastName + " " + keyAppFirstName;

	protected String appFN = appLastName + " " + appFirstName;

	private SystemFields systemFields;

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public DisplayDocument() {
		super();
	}

	public String getAnnotate() {
		return annotate;
	}

	public void setAnnotate(String annotate) {
		this.annotate = annotate;
	}

	public String getAppFirstName() {
		return appFirstName;
	}

	public void setAppFirstName(String appFirstName) {
		this.appFirstName = appFirstName;
	}
	
	
	public String getAppLastName() {
		return appLastName;
	}

	public void setAppLastName(String appLastName) {
		this.appLastName = appLastName;
	}

	public String getCFP() {
		return CFP;
	}

	public void setCFP(String cfp) {
		CFP = cfp;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String docReceiptDate) {
		this.scanDate = docReceiptDate;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getEOF() {
		return EOF;
	}

	public void setEOF(String eof) {
		EOF = eof;
	}

	public String getGFID() {
		return GFID;
	}

	public void setGFID(String gfid) {
		GFID = gfid;
	}

	public String getINDEXPROVIDER() {
		return INDEXPROVIDER;
	}

	public void setINDEXPROVIDER(String indexprovider) {
		INDEXPROVIDER = indexprovider;
	}

	public String getKeyAppFirstName() {
		return keyAppFirstName;
	}

	public void setKeyAppFirstName(String keyAppFirstName) {
		this.keyAppFirstName = keyAppFirstName;
	}

	public String getKeyAppLastName() {
		return keyAppLastName;
	}

	public void setKeyAppLastName(String keyAppLastName) {
		this.keyAppLastName = keyAppLastName;
	}

	public String getLUCID() {
		return LUCID;
	}

	public void setLUCID(String lucid) {
		LUCID = lucid;
	}

	public String getMIMETYPE() {
		return MIMETYPE;
	}

	public void setMIMETYPE(String mimetype) {
		MIMETYPE = mimetype;
	}

	public String getROWIDENTIFIER() {
		return ROWIDENTIFIER;
	}

	public void setROWIDENTIFIER(String rowidentifier) {
		ROWIDENTIFIER = rowidentifier;
	}

	public String getSSPROVIDERID() {
		return SSPROVIDERID;
	}

	public void setSSPROVIDERID(String ssproviderid) {
		SSPROVIDERID = ssproviderid;
	}

	public String getTABLENAME() {
		return TABLENAME;
	}

	public void setTABLENAME(String tablename) {
		TABLENAME = tablename;
	}

	public String getToolName() {
		return toolName;
	}

	public void setToolName(String toolName) {
		this.toolName = toolName;
	}

	public String getViewer() {
		return viewer;
	}

	public void setViewer(String viewer) {
		this.viewer = viewer;
	}

	public String getAppMiddle() {
		return AppMiddle;
	}

	public void setAppMiddle(String appMiddle) {
		AppMiddle = appMiddle;
	}

	public String getKeyAppMiddle() {
		return KeyAppMiddle;
	}

	public void setKeyAppMiddle(String keyAppMiddle) {
		KeyAppMiddle = keyAppMiddle;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}

	public SystemFields getSystemFields() {
		return systemFields;
	}

	public void setSystemFields(SystemFields systemFields) {
		this.systemFields = systemFields;
	}

	public String getKeyAppFN() {
		return keyAppLastName + " " + keyAppFirstName;
	}

	public void setKeyAppFN(String keyAppFN) {
		this.keyAppFN = keyAppFN;
	}

	public void setAppFN(String appFN) {
		this.appFN = appFN;
	}
	
	public String getCmslbId() {
		return cmslbId;
	}

	public void setCmslbId(String cmslbId) {
		this.cmslbId = cmslbId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAppSuffix() {
		return AppSuffix;
	}

	public void setAppSuffix(String appSuffix) {
		AppSuffix = appSuffix;
	}

	public String getKeyAppSuffix() {
		return KeyAppSuffix;
	}

	public void setKeyAppSuffix(String keyAppSuffix) {
		KeyAppSuffix = keyAppSuffix;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getAppFN() {
		return appFirstName +" " + appLastName;
	}

	public String getReviewed() {
		return reviewed;
	}

	public void setReviewed(String reviewed) {
		this.reviewed = reviewed;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String getAgentNumber() {
		return agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		this.agentNumber = agentNumber;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

}
