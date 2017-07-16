package com.epm.acmi.struts.form;

import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.cc.acmi.common.FieldCheckCustom;
import com.cc.framework.adapter.struts.FormActionContext;

public class StandardLetterForm extends ActionForm {

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
		private String letterTextArea="";
		private String second_request="";
		private String recipient_name="";
		private String policyno="";
		private String log_counter="";
	
		
		public StandardLetterForm() {
			super();
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
		
		public TreeMap getAttachmentOptions()
		{
			
			TreeMap attachment = new TreeMap();
			
			attachment.put(" ", " ");
			attachment.put("Y", "Yes");
			attachment.put("N", "No");
			
			return attachment;
			
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

		public String getSecond_request() {
			return second_request;
		}

		public void setSecond_request(String second_request) {
			this.second_request = second_request;
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

		public String getPolicyno() {
			return policyno;
		}


		public void setPolicyno(String policyno) {
			this.policyno = policyno;
		}
		
		public String getLetterTextArea() {
			return letterTextArea;
		}


		public void setLetterTextArea(String letterTextArea) {
			this.letterTextArea = letterTextArea;
		}
		
		public void clear(){
			this.setDescription("");
			this.setEvent_id("");
			this.setMemoid("");
			this.setRecipient_id("");
			this.setRecipient_name("");
			this.setRequested("");
			this.setRespn_date("");
			this.setSecond_request("");
			this.setStatus("");
			this.setAttachment("");
			this.setPolicyno("");
			this.setLetterTextArea("");
			this.setStd_event("");
			this.setLog_counter("");
		}


		public void save()
		{
			this.setAttachment(this.getAttachment());
			this.setDescription(this.getDescription());
			this.setEvent_id(this.getEvent_id());
			this.setMemoid(this.getMemoid());
			this.setPolicyno(this.getPolicyno());
			this.setRecipient_id(this.getRecipient_id());
			this.setRecipient_name(this.getRecipient_name());
			this.setRequested(this.getRequested());
			this.setRespn_date(this.getRespn_date());
			this.setSecond_request(this.getSecond_request());
			this.setStatus(this.getStatus());
			this.setStd_event(this.getStd_event());
			this.setLetterTextArea(this.getLetterTextArea());
			this.setLog_counter(this.getLog_counter());
		}
		
		
		public ActionErrors validateForm(ActionMapping mapping, HttpServletRequest request )	
		{
			
			ActionErrors errors = null;
			
			
			
			return errors;
					
		}
		
		
		public void validateForm(FormActionContext ctx)
		{
			
			FieldCheckCustom.validateRequired(this.getDescription(), ctx, "Description");
			FieldCheckCustom.validateRequired(this.getLetterTextArea(), ctx, "Letter Text");
		}


		public String getLog_counter() {
			return log_counter;
		}


		public void setLog_counter(String log_counter) {
			this.log_counter = log_counter;
		}


}
