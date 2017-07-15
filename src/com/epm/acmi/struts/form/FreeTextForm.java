/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.form;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cc.acmi.common.FieldCheckCustom;
import com.cc.framework.adapter.struts.FormActionContext;

import java.util.TreeMap;

/** 
 * MyEclipse Struts
 * Creation date: 04-01-2008
 * 
 * XDoclet definition:
 * @struts.form name="freeTextForm"
 */
public class FreeTextForm extends ActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String event_id="";
	private String std_event="";
	private String description="";
	private String recipient_id="";
	private String attachment="";
	private String status="";
	private String requested="";
	private String respn_yyyy="";
	private String respn_dd="";
	private String respn_date="";
	private String respn_mm="";
	private String memoid="";
	private String freeTextArea="";
	private String second_request="";
	private String application_formid="";
	private String recipient_name="";
	private String memoind="";
	private String freeformind="";
	private String policyno="";
	private String lineCount="";
	private String remLines ="";
	private String remChars="";
	private String hiddenfreeTextArea="";
	private String autoSave ="";
	
	public FreeTextForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	
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

	public TreeMap getFreeformIndOptions()
	{
		
		TreeMap freeform = new TreeMap();
		
		freeform.put(" ", " ");
		freeform.put("Y", "Yes");
		freeform.put("N", "No");
		
		return freeform;
		
	}
	
	public TreeMap getAttachmentOptions()
	{
		
		TreeMap attachment = new TreeMap();
		
		attachment.put(" ", " ");
		attachment.put("Y", "Yes");
		attachment.put("N", "No");
		
		return attachment;
		
	}
	
	public TreeMap getMemoindOptions()
	{
		
		TreeMap memoid = new TreeMap();
		
		memoid.put(" ", " ");
		memoid.put("Y", "Yes");
		memoid.put("N", "No");
		
		return memoid;
		
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRequested() {
		return requested;
	}

	public void setRequested(String requested) {
		this.requested = requested;
	}

	public String getMemoid() {
		return memoid;
	}

	public void setMemoid(String memoid) {
		this.memoid = memoid;
	}

	public String getFreeTextArea() {
		return freeTextArea;
	}

	public void setFreeTextArea(String freeTextArea) {
		this.freeTextArea = freeTextArea;
	}

	public String getSecond_request() {
		return second_request;
	}

	public void setSecond_request(String second_request) {
		this.second_request = second_request;
	}


	public String getApplication_formid() {
		return application_formid;
	}


	public void setApplication_formid(String application_formid) {
		this.application_formid = application_formid;
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

	
	public String getRespn_yyyy() {
		return respn_yyyy;
	}


	public void setRespn_yyyy(String respn_yyyy) {
		this.respn_yyyy = respn_yyyy;
	}


	public String getRespn_dd() {
		return respn_dd;
	}

	public void setRespn_dd(String respn_dd) {
		this.respn_dd = respn_dd;
	}


	public String getRespn_mm() {
		return respn_mm;
	}


	public void setRespn_mm(String respn_mm) {
		this.respn_mm = respn_mm;
	}
	
	public String getRespn_date() {
		return respn_date;
	}

	public void setRespn_date(String respn_date) {
		this.respn_date = respn_date;
	}

	public String getAttachment() {
		return attachment;
	}


	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

	public String getMemoind() {
		return memoind;
	}


	public void setMemoind(String memoind) {
		this.memoind = memoind;
	}


	public String getFreeformind() {
		return freeformind;
	}


	public void setFreeformind(String freeformind) {
		this.freeformind = freeformind;
	}

	public String getPolicyno() {
		return policyno;
	}


	public void setPolicyno(String policyno) {
		this.policyno = policyno;
	}
	
	public String getAutoSave() {
		return autoSave;
	}

	public void setAutoSave(String autoSave) {
		this.autoSave = autoSave;
	}

	public void clear(){
		this.setDescription("");
		this.setEvent_id("");
		this.setApplication_formid("");
		this.setFreeTextArea("");
		this.setMemoid("");
		this.setRecipient_id("");
		this.setRecipient_name("");
		this.setRequested("");
		this.setRespn_date("");
		this.setSecond_request("");
		this.setSecond_request("");
		this.setStatus("");
		this.setAttachment("");
		this.setFreeformind("");
		this.setMemoind("");
		this.setPolicyno("");
		this.setAutoSave("");
	}


	public void save()
	{
		this.setApplication_formid(this.getApplication_formid());
		this.setAttachment(this.getAttachment());
		this.setDescription(this.getDescription());
		this.setEvent_id(this.getEvent_id());
		this.setFreeformind(this.getFreeformind());
		this.setFreeTextArea(this.getFreeTextArea());
		this.setMemoid(this.getMemoid());
		this.setMemoind(this.getMemoind());
		this.setPolicyno(this.getPolicyno());
		this.setRecipient_id(this.getRecipient_id());
		this.setRecipient_name(this.getRecipient_name());
		this.setRequested(this.getRequested());
		this.setRespn_date(this.getRespn_date());
		this.setSecond_request(this.getSecond_request());
		this.setStatus(this.getStatus());
		this.setStd_event(this.getStd_event());
		this.setAutoSave("false");
	}
	
	
	public ActionErrors validateForm(ActionMapping mapping, HttpServletRequest request )	
	{
		
		ActionErrors errors = null;

		return errors;
				
	}
	
	
	public void validateForm(FormActionContext ctx)
	{
		FieldCheckCustom.validateRequired(this.getDescription(), ctx, "Description");
	}

	public String getLineCount() {
		return lineCount;
	}

	public void setLineCount(String lineCount) {
		this.lineCount = lineCount;
	}

	public String getRemLines() {
		return remLines;
	}

	public void setRemLines(String remLines) {
		this.remLines = remLines;
	}

	public String getRemChars() {
		return remChars;
	}

	public void setRemChars(String remChars) {
		this.remChars = remChars;
	}

	public String getHiddenfreeTextArea() {
		return hiddenfreeTextArea;
	}

	public void setHiddenfreeTextArea(String hiddenfreeTextArea) {
		this.hiddenfreeTextArea = hiddenfreeTextArea;
	}

}