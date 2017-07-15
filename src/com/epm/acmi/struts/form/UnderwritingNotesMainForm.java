/*
 * Generated by MyEclipse Struts
 * Template path: templates/java/JavaClass.vtl
 */
package com.epm.acmi.struts.form;

import org.apache.struts.action.ActionForm;

import com.cc.acmi.common.FieldCheckCustom;
import com.cc.framework.adapter.struts.FormActionContext;

/** 
 * MyEclipse Struts
 * Creation date: 04-07-2008
 * 
 * XDoclet definition:
 * @struts.form name="underwritingNotesMainForm"
 */
public class UnderwritingNotesMainForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String policy_id;
	private String description;
	private String notesArea;
	
	public UnderwritingNotesMainForm() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getPolicy_id() {
		return policy_id;
	}

	public void setPolicy_id(String policy_id) {
		this.policy_id = policy_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNotesArea() {
		return notesArea;
	}

	public void setNotesArea(String notesArea) {
		this.notesArea = notesArea;
	}

	public void validateForm(FormActionContext ctx) 
	{
		FieldCheckCustom.validateRequired(this.getNotesArea(), ctx, "Notes");
		
	}

	public void clear() 
	{
		this.setDescription("");
		this.setNotesArea("");
		this.setPolicy_id("");
	}
	
	
	
	public void save() 
	{
		this.setDescription(getDescription());
		this.setNotesArea(getNotesArea());
		this.setPolicy_id(getPolicy_id());
	}
	
	
}