package com.epm.acmi.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * DateUtility.java
 * A collection of utility methods for performing frequently require tasks.
 * @author Jay Hombal
 */
public class DateUtility {

	String theDate = null;

	String theTimeZone = null;

	boolean debug = false;

	/**
	 * Converts a datetime string into and instance of java.util.Date using the date format: yyyy-MM-ddTHH:mm:ss.SSSZ.
	 * Follows Postel's Law (lenient about what it accepts, as long as it's sensible)
	 * 
	 * @param dateTime
	 *            A datetime string
	 * @return Corresponding instance of java.util.Date (returns null if dateTime string argument is empty string or
	 *         null)
	 */
	public static Date convertStringToDate(String dateTime) {
		return parseDateAsUTC(dateTime);
	}

	/**
	 * Converts an instance of java.util.Date into a String using the date format: yyyy-MM-ddTHH:mm:ss.
	 * 
	 * @param date
	 *            Instance of java.util.Date.
	 * @return ISO 8601 String representation (yyyy-MM-ddTHH:mm:ss.SSSZ) of the Date argument or null if the Date
	 *         argument is null.
	 */
	public static String convertDateToString(Date date) {
		if (date == null) {
			return null;
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			// df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df.format(date);
		}
	}

	/**
	 * Converts an instance of java.util.Date into a String using the date format: yyyy-MM-ddZ.
	 * 
	 * @param date
	 *            Instance of java.util.Date.
	 * @return Corresponding date string (returns null if Date argument is null).
	 */
	public static String convertDateToDateString(Date date) {
		if (date == null) {
			return null;
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'Z'");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df.format(date);
		}
	}

	/**
	 * Converts an instance of java.util.Date into a String using the date format: HH:mm:ss.SSSZ.
	 * </p>
	 * 
	 * @param date
	 *            Instance of java.util.Date.
	 * @return Corresponding time string (returns null if Date argument is null).
	 */
	public static String convertDateToTimeString(Date date) {
		if (date == null) {
			return null;
		} else {
			DateFormat df = new SimpleDateFormat("HH:mm:ss.SSS'Z'");
			df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df.format(date);
		}
	}

	
	public static String convertDateToLocalTimeString(Date date) {
		if (date == null) {
			return null;
		} else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			//df.setTimeZone(TimeZone.getTimeZone("UTC"));
			return df.format(date);
		}
	}
	/**
	 * Attempt to parse the given string of form: yyyy-MM-dd[THH:mm:ss[.SSS][Z]] as a Date. If the string is not of that
	 * form, return null.
	 * 
	 * @param dateString
	 *            the date string to parse
	 * @return Date the date, if parse was successful; null otherwise
	 */
	public static Date parseDateAsUTC(String dateString) {
		if (dateString == null || dateString.length() == 0) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat();
		// formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		if (dateString.endsWith("Z")) {
			if (dateString.length() == 11) {
				formatter.applyPattern("yyyy-MM-dd'Z'");
			} else if (dateString.length() == 20) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
			} else if (dateString.length() == 22) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss.S'Z'");
			} else if (dateString.length() == 23) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss.SS'Z'");
			} else if (dateString.length() == 24) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			}
		} else if (dateString.indexOf("T") > -1){
			if (dateString.length() == 10) {
				formatter.applyPattern("yyyy-MM-dd");
			}
			if (dateString.length() == 10) {
				formatter.applyPattern("MM/dd/yyyy");
			} else if (dateString.length() == 19) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss");
			} else if (dateString.length() == 21) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss.S");
			} else if (dateString.length() == 22) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
			} else if (dateString.length() == 23) {
				formatter.applyPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
			} else if (dateString.endsWith("GMT") || dateString.endsWith("UTC")) {
				formatter.applyPattern("EEE, dd MMMM yyyyy HH:mm:ss z");
			}
		} else
		{
			if (dateString.length() == 10) {
				formatter.applyPattern("MM/dd/yyyy");
			} else if (dateString.length() == 19) {
				formatter.applyPattern("yyyy-MM-dd HH:mm:ss");
			} else if (dateString.length() == 21) {
				formatter.applyPattern("yyyy-MM-dd HH:mm:ss.S");
			} else if (dateString.length() == 22) {
				formatter.applyPattern("yyyy-MM-dd HH:mm:ss.SS");
			} else if (dateString.length() == 23) {
				formatter.applyPattern("yyyy-MM-dd HH:mm:ss.SSS");
			} else if (dateString.endsWith("GMT") || dateString.endsWith("UTC")) {
				formatter.applyPattern("EEE, dd MMMM yyyyy HH:mm:ss z");
			}
		}
		try {

			return formatter.parse(dateString);
			// return DateFormat.getDateTimeInstance().parse(dt.toString());

		} catch (ParseException e) {
			return null;
		}
	}

	public static void main(String[] args) {
		String dateTimeString = "2002-08-22T13:58:06";
		Date date = convertStringToDate(dateTimeString);
		System.out.println("\nDateString: " + dateTimeString + "\nConvertDateToString: " + convertDateToString(date));
		System.out.println("\nDate: " + convertDateToString(date) + "\nConvertDateToString: " + convertDateToString(date));
		System.out.println("\nDate: " + convertDateToDateString(date) + "\nConvertDateToDateString: "
				+ convertDateToDateString(date));
		System.out.println("\nDate: " + convertDateToTimeString(date) + "\nConvertDateToTimeString: "
				+ convertDateToTimeString(date));
	}

	public String getTheDate() {
		return theDate;
	}

	public String getTimeZone() {
		return theTimeZone;
	}

	public String calculateDate() throws Exception {
		TimeZone TZ = calculateLocalTimeZone();
		String dtFormat = "MM/dd/yyyy HH:mm:ss";

		Calendar today = new GregorianCalendar(TZ);
		long mil = (today.getTime()).getTime();
		java.util.Date dt = new java.util.Date(mil);

		SimpleDateFormat fm = new SimpleDateFormat(dtFormat);
		theDate = fm.format(dt);
		return theDate;
	}

	public String calculateDate(String dtFormat) throws Exception {
		TimeZone TZ = calculateLocalTimeZone();
		if (dtFormat == null)
			dtFormat = "MM/dd/yyyy HH:mm:ss";

		Calendar today = new GregorianCalendar(TZ);
		long mil = (today.getTime()).getTime();
		java.util.Date dt = new java.util.Date(mil);

		SimpleDateFormat fm = new SimpleDateFormat(dtFormat);
		theDate = fm.format(dt);
		return theDate;
	}

	public TimeZone calculateLocalTimeZone() throws Exception {
		Calendar today = new GregorianCalendar();
		TimeZone t = today.getTimeZone();
		// boolean DST = false;
		// if ( today.get(Calendar.DST_OFFSET) != 0 ) DST = true;
		// String localTimeZone = t.getDisplayName(DST, TimeZone.SHORT);
		return t;
	}

	public String calculateTimeZone(Integer gmt_OffSet) throws Exception {
		int gmtOffSet = 0;

		if (gmt_OffSet.intValue() >= -12 && gmt_OffSet.intValue() <= 12) {
			// gmt_OffSet given in hours
			gmtOffSet = gmt_OffSet.intValue() * 60 * 60 * 1000;
		} else if (gmt_OffSet.intValue() >= -720 && gmt_OffSet.intValue() <= 720) {
			// gmt_OffSet given in minutes
			gmtOffSet = gmt_OffSet.intValue() * 60 * 1000;
		} else if (gmt_OffSet.intValue() >= -43200 && gmt_OffSet.intValue() <= 43200) {
			// gmt_OffSet given in seconds
			gmtOffSet = gmt_OffSet.intValue() * 1000;
		} else {
			// gmt_OffSet given in milliseconds
		}

		String[] ids = TimeZone.getAvailableIDs(gmtOffSet);
		if (ids.length == 0)
			throw new Exception("An Error occurred while calculating the TimeZone. Verify your GMTOffSet");
		for (int t = 0; t < ids.length; t++) {
			if (ids[t].length() == 3)
				theTimeZone = ids[t];
		}
		SimpleTimeZone stz = new SimpleTimeZone(gmtOffSet, ids[0]);
		Calendar today = new GregorianCalendar(stz);
		if (today.get(Calendar.DST_OFFSET) != 0) {
			theTimeZone = theTimeZone.substring(0, 0) + "DT";
		}
		return theTimeZone;
	}

	public String computeDate(String startDate, Integer numOfDays) {
		// startDate myust be in MM/DD/YYYY format
		// numOfDays must include sign

		int mmonth = new Integer(startDate.substring(0, 2)).intValue();
		int dday = new Integer(startDate.substring(3, 5)).intValue();
		int yyear = new Integer(startDate.substring(6, 10)).intValue();

		Calendar today = new GregorianCalendar(yyear, (mmonth - 1), dday);
		today.add(Calendar.DAY_OF_MONTH, numOfDays.intValue());
		java.util.Date dt = today.getTime();

		SimpleDateFormat fm = new SimpleDateFormat("MM/dd/yyyy");
		String theDate = fm.format(dt);
		return theDate;
	}

	public long getTzOffset(String tZ) {
		TimeZone tz = TimeZone.getTimeZone(tZ);
		tz.setID(tZ);
		Calendar today = new GregorianCalendar(tz);
		long tZNum = new Long(new Integer(today.get(Calendar.ZONE_OFFSET)).toString()).longValue();
		return tZNum;
	}

	public String convertFromGmtToLocalDate(String dateStr, String inFormat, String outFormat) {
		try {
			// log.debug("DateHelper::convertFromGmtToLocalDate() - Date passed in = "+dateStr);

			TimeZone TZ = calculateLocalTimeZone();
			// log.debug("DateHelper::convertFromGmtToLocalDate() - Local Time Zone = "+TZ);

			if (inFormat == null)
				throw new Exception("You must specify the inbound date format");
			if (outFormat == null)
				outFormat = "MM/dd/yyyy HH:mm:ss";

			SimpleDateFormat sdfInput = new SimpleDateFormat(inFormat);
			SimpleDateFormat sdfOutput = new SimpleDateFormat(outFormat);
			int offset = TZ.getRawOffset();

			// log.debug("DateHelper::convertFromGmtToLocalDate() - TimeZone.getTimeZone("+TZ+") = "+ timeZone);
			sdfInput.setTimeZone(TZ);
			java.util.Date date = sdfInput.parse(dateStr);
			long mil = date.getTime() + offset;
			date = new Date(mil);
			boolean inDST = TZ.inDaylightTime(date);
			if (inDST == true) {
				int DSTSavings = TZ.getDSTSavings();
				// log.debug("DateHelper::convertFromGmtToLocalDate() - DSTSavings = "+DSTSavings);
				mil = date.getTime() + DSTSavings;
				date = new Date(mil);
			}
			dateStr = sdfOutput.format(date);
			// log.debug("DateHelper::convertFromGmtToLocalDate() - Date converted from GMT to Local = "+dateStr);

		} catch (Exception e) {
			// log.error("DateHelper::convertFromGmtToLocalDate() - Exception Caught: "+e.toString());
		}
		return dateStr;
	}
}
