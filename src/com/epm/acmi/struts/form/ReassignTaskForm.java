// Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.0/xslt/JavaClass.xsl

package com.epm.acmi.struts.form;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.cc.framework.adapter.struts.FWActionForm;

/**
 * ReassignTaskForm class used as struts ActionForm in Reassign Page
 * 
 */
public class ReassignTaskForm extends FWActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	// Instance Variables
	// protected List reassignWIList;
	protected String item;


	private String roleDropdown;


	protected Map map;


	private TreeMap roleValues;


	// Methods

	public String getRoleDropdown() {
		return roleDropdown;
	}


	public void setRoleDropdown(String roleDropdown) {
		this.roleDropdown = roleDropdown;
	}


	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		return null;
	}


	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}


	public TreeMap getRoleValues() {
		return roleValues;
	}


	public void setRoleValues(TreeMap roleValues) {
		this.roleValues = roleValues;
	}


	public String getItem() {
		return item;
	}


	public void setItem(String item) {
		this.item = item;
	}


	public Map getMap() {
		return map;
	}


	public void setMap(Map map) {
		this.map = map;
	}

}
