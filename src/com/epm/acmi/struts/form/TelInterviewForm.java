package com.epm.acmi.struts.form;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.cc.framework.adapter.struts.FWActionForm;
import org.apache.struts.action.ActionMapping;

public class TelInterviewForm extends FWActionForm {
	

	private static final long serialVersionUID = 3345908L;
	private String interviewRequestId;
    private String fullName;
    private String firstName;
    private String lastName;
    private String mi;
    private String suffix;
    private String applicantType;
    private String requestDate;
    private String applicantID;
    private Object uwList;
    private boolean showNewInterviewRequest;
    private boolean createNewInterviewRequest = true;
    
    private String uwIDName;

    public String getInterviewRequestId() {
    	return interviewRequestId;
    }
    
    public void setInterviewRequestId(String id) {
    	interviewRequestId = id;
    }
    
	public String getApplicantID() {
		return applicantID;
	}

	public void setApplicantID(String applicantID) {
		this.applicantID = applicantID;
	}

	public String getApplicantType() {
		return applicantType;
	}

	public void setApplicantType(String applicantType) {
		this.applicantType = applicantType;
	}

	public String getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String name) {
		fullName = name;
	}
	
	public Object getUwList() {
		return uwList;
	}
	
	public void setUwList(Object list) {
		uwList = list;
	}
	
	public boolean getShowNewInterviewRequest() {
		return showNewInterviewRequest;
	}
	
	public void setShowNewInterviewRequest(boolean showNewScreen) {
		showNewInterviewRequest = showNewScreen;
	}

	public String getUwIDName() {
		return uwIDName;
	}
	
	public void setUwIDName(String name) {
		uwIDName = name;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest req) {
		showNewInterviewRequest = false;
		setUwIDName(null);
//		setUwList(null);
		setFullName(null);
		setApplicantType(null);
		setRequestDate(null);
		setInterviewRequestId(null);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMi() {
		return mi;
	}

	public void setMi(String mi) {
		this.mi = mi;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public boolean getCreateNewInterviewRequest() {
		return createNewInterviewRequest;
	}

	public void setCreateNewInterviewRequest(boolean createNewInterviewRequest) {
		this.createNewInterviewRequest = createNewInterviewRequest;
	}
}
