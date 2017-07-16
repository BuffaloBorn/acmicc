package com.epm.acmi.struts.form;

import java.util.TreeMap;


import org.apache.struts.action.ActionForm;



import com.cc.acmi.common.FieldCheckCustom;
import com.cc.framework.adapter.struts.FormActionContext;

public class LetterForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String by_agent_applicant="";
	private String suspense_date="";
	private String suspense_amt="";
	private String event_id="";
	private String std_event="";
	private String recipient_id="";
	private String recipient_name="";
	private String status="";
	private String requested="";
	private String letter_withdrawn="";
	private String letter_incomplete="";
	private String letter_declined="";
	private String attach="";
	private String second_request="";
	private String letter_text="";
	private boolean letter_declined_value = false;
	private String policyno;
	private String log_counter="";
	
	public TreeMap getStatusOptions()
	{
		
		TreeMap status = new TreeMap();
			
		/*-- -------------------------------------------------- ---
		I  INCOMPLETE (NO INFORMATION)                         Y 
		O  ORDERED                                             N 
		P  PENDING                                             N 
		R  RECEIVED                                            Y 
		V  VOID                                                Y 
		W  WAIVED/WITHDRAWN                                    Y */
		
		status.put(" ", " ");
		status.put("I", "I: INCOMPLETE (NO INFORMATION)");
		status.put("O", "O: ORDERED");
		status.put("P", "P: PENDING");
		status.put("R", "R: RECEIVED");
		status.put("V", "V: VOID");
		status.put("W", "W: WAIVED/WITHDRAWN");
		
		return status;
		
	}
	
	public TreeMap getSecondRequestOptions()
	{
		
		TreeMap secondrequest = new TreeMap();
		
		secondrequest.put(" ", " ");
		secondrequest.put("Y", "Yes");
		secondrequest.put("N", "No");
		
		return secondrequest;
		
	}

	
	public TreeMap getAttachmentOptions()
	{
		
		TreeMap attachment = new TreeMap();
		
		attachment.put(" ", " ");
		attachment.put("Y", "Yes");
		attachment.put("N", "No");
		
		return attachment;
		
	}


	public String getBy_agent_applicant() {
		return by_agent_applicant;
	}
	public void setBy_agent_applicant(String by_agent_applicant) {
		this.by_agent_applicant = by_agent_applicant;
	}
	public String getSuspense_date() {
		return suspense_date;
	}
	public void setSuspense_date(String suspense_date) {
		this.suspense_date = suspense_date;
	}
	public String getSuspense_amt() {
		return suspense_amt;
	}
	public void setSuspense_amt(String suspense_amt) {
		this.suspense_amt = suspense_amt;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getStd_event() {
		return std_event;
	}
	public void setStd_event(String std_event) {
		this.std_event = std_event;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		if(status.equalsIgnoreCase(" "))
		{
			status = "";
		}
		
		this.status = status;
	}

	public String getRequested() {
		return requested;
	}
	public void setRequested(String requested) {
		this.requested = requested;
	}
	public String getLetter_withdrawn() {
		return letter_withdrawn;
	}
	public void setLetter_withdrawn(String letter_withdrawn) {
		this.letter_withdrawn = letter_withdrawn;
	}
	
	public String getLetter_incomplete() {
		return letter_incomplete;
	}
	public void setLetter_incomplete(String letter_incomplete) {
		this.letter_incomplete = letter_incomplete;
	}
	
	public String getLetter_declined() {
		return letter_declined;
	}
	public void setLetter_declined(String letter_declined) {
		this.letter_declined = letter_declined;
	}
	
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		if(attach.equalsIgnoreCase(" "))
		{
			attach = "";
		}
		
		this.attach = attach;
	}


	public String getSecond_request() {
		return second_request;
	}


	public void setSecond_request(String second_request) {
		
		if(second_request.equalsIgnoreCase(" "))
		{
			second_request = "";
		}
		
		this.second_request = second_request;
	}


	public String getLetter_text() {
		return letter_text;
	}


	public void setLetter_text(String letter_text) {
		this.letter_text = letter_text;
	}


	public String getRecipient_id() {
		return recipient_id;
	}


	public void setRecipient_id(String recipient_id) {
		this.recipient_id = recipient_id;
	}


	public String getRecipient_name() {
		return recipient_name;
	}


	public void setRecipient_name(String recipient_name) {
		this.recipient_name = recipient_name;
	}


	public String getPolicyno() {
		return policyno;
	}


	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	
	public void save()
	{
	
		this.setAttach(getAttach());
		this.setBy_agent_applicant(getBy_agent_applicant());
		this.setEvent_id(getEvent_id());
		this.setLetter_declined(getLetter_declined());
		this.setLetter_incomplete(getLetter_incomplete());
		this.setLetter_text(getLetter_text());
		this.setLetter_withdrawn(getLetter_withdrawn());
		this.setPolicyno(getPolicyno());
		this.setRecipient_id(getRecipient_id());
		this.setRecipient_name(getRecipient_name());
		this.setRequested(getRequested());
		this.setSecond_request(getSecond_request());
		this.setStatus(getStatus());
		this.setStd_event(getStd_event());
		this.setSuspense_amt(getSuspense_amt());
		this.setSuspense_date(getSuspense_date());
		this.setLetter_declined_value(isLetter_declined_value());
		
	}
	
	public void clear()
	{
		this.setAttach("");
		this.setBy_agent_applicant("");
		this.setEvent_id("");
		this.setLetter_declined("");
		this.setLetter_incomplete("");
		this.setLetter_text("");
		this.setLetter_withdrawn("");
		this.setPolicyno("");
		this.setRecipient_id("");
		this.setRecipient_name("");
		this.setRequested("");
		this.setSecond_request("");
		this.setStatus("");
		this.setStd_event("");
		this.setSuspense_amt("");
		this.setSuspense_date("");
		this.setLetter_declined_value(false);
		this.setLog_counter("");
	}
	
	public void validateForm(FormActionContext ctx)
	{
		if(this.getLetter_text().equalsIgnoreCase("0"))
		{
			FieldCheckCustom.validateRequiredAgentOrApplicant(this.getBy_agent_applicant(), ctx, "By Agent or By Applicant");
		}
	}

	public boolean isLetter_declined_value() {
		return letter_declined_value;
	}

	public void setLetter_declined_value(boolean letter_declined_value) {
		this.letter_declined_value = letter_declined_value;
	}

	public String getLog_counter() {
		return log_counter;
	}

	public void setLog_counter(String log_counter) {
		this.log_counter = log_counter;
	}

	

}
