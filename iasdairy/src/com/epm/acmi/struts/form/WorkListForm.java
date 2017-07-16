// Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.0/xslt/JavaClass.xsl

package com.epm.acmi.struts.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * MyEclipse Struts Creation date: 09-09-2005 XDoclet definition:
 * 
 * @author Rajeev chachra
 */
public class WorkListForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Instance Variables
	protected List acmiWIBList;

	// Methods

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

	public List getAcmiWINBList() {
		return acmiWIBList;
	}

	public void setAcmiWIBList(List acmiWIList) {
		this.acmiWIBList = acmiWIList;
	}

}
