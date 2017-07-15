package com.epm.acmi.cm.et;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * Bean that represents the Stellent DB EPMEvents table
 * 
 * @author Dragos Mandruleanu
 *
 */
public class CMEventDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String GFID;
	private String policyNum;
	private String docType;
	private Date docRecdDate;
	private String status;
	private long recID;
	private long eventID;
	
	public long getEventID() {
		return eventID;
	}
	public void setEventID(long eventID) {
		this.eventID = eventID;
	}
	public long getRecID() {
		return recID;
	}
	public void setRecID(long recID) {
		this.recID = recID;
	}
	public Date getDocRecdDate() {
		return docRecdDate;
	}
	public void setDocRecdDate(Date docRecdDate) {
		this.docRecdDate = docRecdDate;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getGFID() {
		return GFID;
	}
	public void setGFID(String gfid) {
		GFID = gfid;
	}
	public String getPolicyNum() {
		return policyNum;
	}
	public void setPolicyNum(String policyNum) {
		this.policyNum = policyNum;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String toString() {
		return "Rec ID = " + this.recID;
	}

}
