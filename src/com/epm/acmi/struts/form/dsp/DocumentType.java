package com.epm.acmi.struts.form.dsp;

/**
 * Document Type Definition class
 * @author Jay Hombal
 *
 */
public class DocumentType {

    private String docCode;
    private String docType;
    private boolean isUploadable;
    
    public String getDocCode() {
        return docCode;
    }
    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }
    public String getDocType() {
        return docType;
    }
    public void setDocType(String docType) {
        this.docType = docType;
    }
	public boolean isUploadable() {
		return isUploadable;
	}
	public void setUploadable(boolean isUploadable) {
		this.isUploadable = isUploadable;
	}
    
}
