package com.cc.acmi.common;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionMessages;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.RequestUtils;


import com.cc.framework.adapter.struts.FormActionContext;
import com.epm.acmi.struts.action.FreeTextAction;

public class FieldCheckCustom {

	private static Logger log = Logger.getLogger(FreeTextAction.class);
	
	public FieldCheckCustom() {
	}
	
	public static boolean validateRequired(String field, ActionMessages errors, String fieldname) {
		
		if (CustomValidator.isBlankOrNull(field)) {
			errors.add("", new ActionMessage(DiaryMessages.ERROR_REQUIRED_FIELD, fieldname));
			return false;
		} else {
			return true;
		}
	}
	
	
	public static void validateStartDatePolicyDate(String STATUS_EFF_DATE, String SUB_EFF_DATE,  FormActionContext errors, String fieldname) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy" );
		boolean resultPolicy;
		Date policy_eff_date;
		Date start_date;
		
		try {
			policy_eff_date = sdf.parse( STATUS_EFF_DATE );
			
			start_date = sdf.parse( SUB_EFF_DATE );
			
			resultPolicy = start_date.before(policy_eff_date);
			
			if(resultPolicy)
			{
				errors.addGlobalError(DiaryMessages.POLICY_STARTDATE_ERROR, fieldname);
			}	
			
			
		} catch (ParseException e) {
			
			log.error("Parse Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() );
		}
	}
	
	
	public static void validateStartDatePersonDate( String PERSON_STATUS_EFF_DATE, String SUB_EFF_DATE,  FormActionContext errors, String fieldname) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy" );
		
		boolean resultPerson;
		
		Date person_eff_date;
		Date start_date;
		
		try {
			person_eff_date = sdf.parse( PERSON_STATUS_EFF_DATE );
			
			start_date = sdf.parse( SUB_EFF_DATE );
			
			resultPerson = start_date.before(person_eff_date);
			
			if(resultPerson)
			{
				errors.addGlobalError(DiaryMessages.PERSON_STARTDATE_ERROR, fieldname);
			}	
			
			
		} catch (ParseException e) {
			
			log.error("Parse Exception " + e.getClass().getName() + " caught with message: " + e.getMessage() );
		}
	}
	
	public static void validateRequired(String field, FormActionContext errors, String fieldname) {
		
		if (CustomValidator.isBlankOrNull(field)) {
			errors.addGlobalError(DiaryMessages.ERROR_REQUIRED_FIELD, fieldname);
		}
	}
	
	public static void validateTextBox(String value, FormActionContext errors, String fieldname, String max) {
		
		if (CustomValidator.minLength(value, Integer.parseInt(max))) {
			errors.addGlobalError(DiaryMessages.OVER_MAX_TEXTBOX, fieldname, max);
		}
	}
	
	public static void validateTextBoxLineLimit(String value, FormActionContext errors, String fieldname, String LineLimit, int LastPosition) 
	{
		String wrapwordText = null;
		
    	value = value.replaceAll("[\r\n]", "\n");
    	
    	wrapwordText = StringW.wordWrap(value, LastPosition, "\n", " ");
    	
    	String[] items = wrapwordText.split("\n");
    	
    	if (items.length > LastPosition)
    	{	
    		errors.addGlobalError(DiaryMessages.OVER_LIMIT_TEXTBOX, fieldname, LineLimit);
    	}
    	
		
	}
	

	public static void validateRequiredAgentOrApplicant(String field, FormActionContext errors, String fieldname) {
		
		if (CustomValidator.isBlankOrNull(field)) {
			errors.addGlobalError(DiaryMessages.ERROR_AGENT_OR_APPLICANT_INVAILD, fieldname);
		}
	}
	
	public static void validatePhone(String field, FormActionContext errors, String fieldname) {
		
		if (!CustomValidator.isPhone(field)) 
		{
			errors.addGlobalError(DiaryMessages.ERROR_INVALID_PHONE, fieldname);
		}
	}
	
	public static void validateFax(String field, FormActionContext errors, String fieldname) {
		
		if (!CustomValidator.isPhone(field)) 
		{
			errors.addGlobalError(DiaryMessages.ERROR_INVALID_FAX, fieldname);
		}
	}
	
	public static void validateZipCode(String field, FormActionContext errors, String fieldname) {
		
		if (!CustomValidator.isZipCode(field)) 
		{
			errors.addGlobalError(DiaryMessages.ERROR_INVALID_ZIP_CODE, fieldname);
		}
	}
	
	public static Object validateDate(String fieldname, FormActionContext errors, String datePattern, HttpServletRequest request) {
		Object result = null;
		String value = null;
		
		java.util.Locale locale = RequestUtils.getUserLocale(request, null);

		if (CustomValidator.isBlankOrNull(value))
			return Boolean.TRUE;
		try {
			if (datePattern != null && datePattern.length() > 0)
				result = GenericTypeValidator.formatDate(value, datePattern, false);
			else
				result = GenericTypeValidator.formatDate(value, locale);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (result == null)
			errors.addGlobalError(DiaryMessages.ERROR_INVALID_DATE, fieldname);
		return result != null ? result : Boolean.FALSE;
	}
	
}
