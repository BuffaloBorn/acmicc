package com.epm.acmi.util;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;


import com.cc.framework.ui.model.OptionListDataModel;
import com.epm.acmi.hbm.Iuauser;
import com.epm.acmi.struts.Constants;
import com.epm.acmi.struts.form.dsp.UWListModel;

public class IUAUtils {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 

	public static String getAppStatusStringfromCode(String appTypeCode) {
		if (appTypeCode != null) {
			if (appTypeCode.equals(Constants.KEY_APPLICANT_CODE)) 
				return Constants.KEY_APPLICANT;
			if (appTypeCode.equals(Constants.SPOUCE_APPLICANT_CODE)) 
				return Constants.SPOUCE_APPLICANT;
			if (appTypeCode.equals(Constants.DEPENDANT_APPLICANT_CODE)) 
				return Constants.DEPENDANT_APPLICANT;
		}
		return "";
	}
	
	public static String getAppStatusCodeFromString(String appTypeString) {
		if (appTypeString != null) {
			if (appTypeString.equals(Constants.KEY_APPLICANT)) 
				return Constants.KEY_APPLICANT_CODE;
			if (appTypeString.equals(Constants.SPOUCE_APPLICANT)) 
				return Constants.SPOUCE_APPLICANT_CODE;
			if (appTypeString.equals(Constants.DEPENDANT_APPLICANT)) 
				return Constants.DEPENDANT_APPLICANT_CODE;
		}
		return "";
	}
	
	public static Iuauser getUserFromIDName(String name) {
		
		try {
			if (name != null) {
				StringTokenizer token = new StringTokenizer(name, "|");
				String userid   = token.nextToken();
				String userName = token.nextToken();
				
				return new Iuauser(userid, userName);
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNameFromIDName(String idName) {
		try {
			if (idName != null) {
				StringTokenizer token = new StringTokenizer(idName, "|");
				token.nextToken();
				String userName = token.nextToken();
				
				return userName;
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getIdFromIDName(String idName) {
		try {
			if (idName != null) {
				StringTokenizer token = new StringTokenizer(idName, "|");
				String userid = token.nextToken();
				
				return userid;
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static OptionListDataModel getUsersByRole(String role) {
		
		ArrayList list = null;
		
		if (role != null) {
			list = ACMICache.getUsersByRole(role);
		}
		else { //Get all users.
			list = ACMICache.getUserGroups();
		}
		Collections.sort(list);
		//Convert this list to OptionsListDataModel object for UI.
		return transformUserList(list);
	}

	public static OptionListDataModel transformUserList(ArrayList list) {
		
		if (list != null) {
			
			//Add the Not Selected to the top of the list
			list.add(0, getNotSelectedUser());
			UWListModel model = new UWListModel();		
			model.setValues(list.toArray());
			
			return model;
		}
		return null;
	}
	
	private static LDAPUser getNotSelectedUser() {
		LDAPUser user = new LDAPUser();
		user.setUserId("");
		user.setFirstName(Constants.NOT);
		user.setLastName(Constants.SELECTED);
		return user;
	}
	
	public static String convertDateToString(Date date) {

		Calendar cal = Calendar.getInstance();
		String retVal;

		if (date == null)
			retVal = "";
		else {
			cal.setTime(date);
			retVal = "" + (cal.get(Calendar.MONTH) + 1);
			retVal += "/";
			retVal += cal.get(Calendar.DATE);
			retVal += "/";
			retVal += cal.get(Calendar.YEAR);
		}
		return retVal;
	}
	
	public static Date convertStringToDate(String date) throws ParseException {
		 
		if (date != null) {
			Date convertedDate = dateFormat.parse(date); 
			return convertedDate;
		} else
			return null;
	}
}
