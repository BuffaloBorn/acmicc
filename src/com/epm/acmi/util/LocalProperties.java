package com.epm.acmi.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

/**
 * Helper file to read properties from Local.Properties
 * @author usaho
 *
 */
public class LocalProperties {

	public static final ResourceBundle appResource = ResourceBundle.getBundle("Local");


	/**
	 * Return Property by key name
	 * @param key
	 * @return String
	 */
	public static String getProperty(String key) {
		return appResource.getString(key);
	}


	/**
	 * Return properties object
	 * @return Properties
	 */
	public static Properties getProperties() {
		Properties props = new Properties();
		Enumeration APropKeys = appResource.getKeys();
		String key = "", value = "";
		while (APropKeys.hasMoreElements()) {
			key = (String) APropKeys.nextElement();
			value = appResource.getString(key);
			props.setProperty(key, value);
		}
		return props;
	}

	/**
	 * Breaks a string of the form value1,value2,...,valueN into a list of Strings. There can be white-spave between values, as in value1, value2,
	 * value3, etc...
	 *
	 * @param str
	 * @return
	 */
	public static List separateString(String str)
	{
		return separateString(str, ",");
	}
	
	/**
	 * Breaks a string of the form value1{separator}value2{separator}...{separator}valueN into a list of Strings. There can be white-spave between values, as in value1, value2,
	 * value3, etc...
	 *
	 * @param str
	 * @param separator
	 * @return
	 */
	public static List separateString(String str, String separator)
	{
		List values = new ArrayList();

		if (str == null)
		{
			return values;
		}

		StringTokenizer token = new StringTokenizer(str, separator);

		while (token.hasMoreTokens())
			values.add(token.nextToken().trim());

		return values;
	}


}