package com.epm.acmi.struts.form;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.cc.framework.adapter.struts.FWActionForm;
import com.cc.framework.ui.model.FormDataModel;
import com.epm.acmi.struts.form.dsp.DocumentTypeList;
import com.epm.acmi.util.ACMICache;

/**
 * Document MetaData Form class
 * 
 * @author Jay Hombal
 */
public class DocumentMetaDataForm extends FWActionForm implements FormDataModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private DocumentTypeList documentTypes = null;
	
	private DocumentTypeList documentTypesUploadable = null;


	private TreeMap productList = null;


	private TreeMap statesList = null;


	private FormFile file = null;


	private String gfid = "";


	private String docType = "";


	private String docCode = "";


	private String scanDate;


	private String annotation = "";


	private String lbid = "";


	private String tempLBID = "";


	private String policyNumber = "";


	private String state = "";


	private String keyAppSSN = "";


	private String keyAppFirstName = "";


	private String keyAppMiddle = "";


	private String keyAppLastName = "";


	private String keyAppSuffix = "";


	private String keyAppDOB;


	private String appFirstName = "";


	private String appMiddle = "";


	private String appLastName = "";


	private String appSuffix = "";


	private String appDOB;


	private String agentNumber = "";


	private String agentName = "";


	private String checkMaker = "";


	private String employer = "";


	private String product = "";


	private String docDescription = "";


	private String cmsReviewed = "";


	private String epmReviewed = "";


	private String cfp = "";


	private String docFlags = "";


	private String URL = "";


	public DocumentMetaDataForm() {

		documentTypes = ACMICache.getDocumentTypeList();
		documentTypesUploadable = ACMICache.getDocumentTypeUploadableList();
		productList = ACMICache.getProductNameMap();
		statesList = ACMICache.getStatesMap();
	}


	public String getURL() {
		return URL;
	}


	public void setURL(String url) {
		URL = url;
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


	public String getAnnotation() {
		return annotation;
	}


	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}


	public String getAppDOB() {
		return appDOB;
	}


	public void setAppDOB(String appDOB) {
		this.appDOB = appDOB;
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


	public String getAppMiddle() {
		return appMiddle;
	}


	public void setAppMiddle(String appMiddle) {
		this.appMiddle = appMiddle;
	}


	public String getAppSuffix() {
		return appSuffix;
	}


	public void setAppSuffix(String appSuffix) {
		this.appSuffix = appSuffix;
	}


	public String getCfp() {
		return cfp;
	}


	public void setCfp(String cfp) {
		this.cfp = cfp;
	}


	public String getCheckMaker() {
		return checkMaker;
	}


	public void setCheckMaker(String checkMaker) {
		this.checkMaker = checkMaker;
	}


	public String getCmsReviewed() {
		return cmsReviewed;
	}


	public void setCmsReviewed(String cmsReviewed) {
		this.cmsReviewed = cmsReviewed;
	}


	public String getDocCode() {
		return docCode;
	}


	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}


	public String getDocDescription() {
		return docDescription;
	}


	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}


	public String getDocFlags() {
		return docFlags;
	}


	public void setDocFlags(String docFlags) {
		this.docFlags = docFlags;
	}


	public String getDocType() {
		return docType;
	}


	public void setDocType(String docType) {
		this.docType = docType;
	}


	public String getEmployer() {
		return employer;
	}


	public void setEmployer(String employer) {
		this.employer = employer;
	}


	public String getEpmReviewed() {
		return epmReviewed;
	}


	public void setEpmReviewed(String epmReviewed) {
		this.epmReviewed = epmReviewed;
	}


	public String getGfid() {
		return gfid;
	}


	public void setGfid(String gfid) {
		this.gfid = gfid;
	}


	public String getKeyAppDOB() {
		return keyAppDOB;
	}


	public void setKeyAppDOB(String keyAppDOB) {
		if (keyAppDOB != null) {
			this.keyAppDOB = keyAppDOB;
		}
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


	public String getKeyAppMiddle() {
		return keyAppMiddle;
	}


	public void setKeyAppMiddle(String keyAppMiddle) {
		this.keyAppMiddle = keyAppMiddle;
	}


	public String getKeyAppSSN() {
		return keyAppSSN;
	}


	public void setKeyAppSSN(String keyAppSSN) {
		this.keyAppSSN = keyAppSSN;
	}


	public String getKeyAppSuffix() {
		return keyAppSuffix;
	}


	public void setKeyAppSuffix(String keyAppSuffix) {
		this.keyAppSuffix = keyAppSuffix;
	}


	public String getLbid() {
		return lbid;
	}


	public void setLbid(String lbid) {
		this.lbid = lbid;
	}


	public String getProduct() {
		if (product != null) {
			product.trim();
		}
		return product;
	}


	public void setProduct(String product) {
		if (this.product != product)
			productList = ACMICache.getProductMapForListBox(product);
		
		this.product = product;
	}


	public String getScanDate() {
		return scanDate;
	}


	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getTempLBID() {
		return tempLBID;
	}


	public void setTempLBID(String tempLBID) {
		this.tempLBID = tempLBID;
	}


	public DocumentTypeList getDocumentTypes() {
		return documentTypes;
	}


	public void setDocumentTypes(DocumentTypeList documentTypes) {
		this.documentTypes = documentTypes;
	}

	public DocumentTypeList getDocumentTypesUploadable() {
		return documentTypesUploadable;
	}


	public void setDocumentTypesUploadable(DocumentTypeList documentTypesFiltered) {
		this.documentTypesUploadable = documentTypesFiltered;
	}


	public FormFile getFile() {
		return file;
	}


	public void setFile(FormFile file) {
		this.file = file;
	}


	public String getPolicyNumber() {
		return policyNumber;
	}


	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public TreeMap getProductList() {
		return productList;
	}


	public void setProductList(TreeMap productList) {
		this.productList = productList;
	}


	public TreeMap getStatesList() {
		return statesList;
	}


	public void setStatesList(TreeMap statesList) {
		this.statesList = statesList;
	}


	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		super.reset(arg0, arg1);
		this.appDOB = "";
		this.appFirstName = "";
		this.appLastName = "";
		this.appMiddle = "";
		this.appSuffix = "";
	}

}