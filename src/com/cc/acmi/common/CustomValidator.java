package com.cc.acmi.common;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.DateValidator;


public class CustomValidator {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CustomValidator() {
	}

	public static boolean isBlankOrNull(String value) {
		return value == null || value.trim().length() == 0;
	}

	public static boolean maxLength(String value, int max) {
		return value.length() <= max;
	}

	public static boolean minLength(String value, int min) {
		return value.length() >= min;
	}
	
	public static boolean isInRange(byte value, byte min, byte max) {
		return value >= min && value <= max;
	}

	public static boolean isInRange(int value, int min, int max) {
		return value >= min && value <= max;
	}

	public static boolean isInRange(float value, float min, float max) {
		return value >= min && value <= max;
	}

	public static boolean isInRange(short value, short min, short max) {
		return value >= min && value <= max;
	}

	public static boolean isInRange(long value, long min, long max) {
		return value >= min && value <= max;
	}

	public static boolean isInRange(double value, double min, double max) {
		return value >= min && value <= max;
	}
	
	public static boolean isDate(String value, Locale locale) {
		return DateValidator.getInstance().isValid(value, locale);
	}

	public static boolean isDate(String value, String datePattern, boolean strict) {
		return DateValidator.getInstance().isValid(value, datePattern, strict);
	}
	
	public static boolean isPhone(String phoneNumber)
	{
		boolean isValid = false;
		
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		
		if(matcher.matches())
		{
			isValid = true;
		}
	
		return isValid;
	}

	public static boolean isZipCode(String phoneNumber)
	{
		boolean isValid = false;
		
		
		String expression =  "^\\d{5}([\\-]\\d{4})?$";
		CharSequence inputStr = phoneNumber;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		
		if(matcher.matches())
		{
			isValid = true;
		}
	
		return isValid;
	}
	
}
