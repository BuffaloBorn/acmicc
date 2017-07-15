package com.epm.acmi.struts.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * CaseNotes Form class
 * @author Rajeev Chachra
 *
 */
public class CaseNotesForm extends FWActionForm
{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public CaseNotesForm()
    {
    }


    public void reset(ActionMapping actionMapping, HttpServletRequest request)
    {
      //  	    
    }
    
    protected String  previousNotes;
    protected String  subject;
    protected String  message;
    
    
	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public String getPreviousNotes() {
		return previousNotes;
	}


	public void setPreviousNotes(String previousNotes) {
		this.previousNotes = previousNotes;
	}
 
    
}