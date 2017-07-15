package com.epm.acmi.datamodel;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.cc.framework.common.SortOrder;

/**
 * WorkListColumnComparator used for sorting worklist
 * @author usacarl
 *
 */
public class PolicyDiaryListColumnComparator implements Comparator {


	// /**
	// * Special internal class for sorting two string representing dates
	// * @author usapha
	// */
	// private static class DateStringComparator implements Comparator {
	// /**
	// * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	// */
	// private static DateFormat dateParser = DateFormat.getDateInstance();
	//			
	// // public int compare(Object o1, Object o2) {
	// // /* TODO: unfortunately, this reparses the strings each time,
	// // * which is inefficient. Ideally, this sorting type can be
	// // * handled before the date is rendered, when it's still in
	// // * native form.
	// // */
	// //
	// // try {
	// // Date d1 = dateParser.parse(o1.toString());
	// // Date d2 = dateParser.parse(o2.toString());
	// // return d1.compareTo(d2);
	// // }
	// // catch (ParseException pe) {
	// // // if a string can't be parsed as a date, then assume that they're equal
	// // return 0;
	// // }
	// // }
	// }

	/**
	 * Property to compare
	 */
	private String column = "";

	/**
	 * Sortorder
	 */
	private SortOrder direction = SortOrder.NONE;

	/**
	 * Constructor
	 * 
	 * @param column
	 *            Column to Sort
	 * @param direction
	 *            SortDirection
	 */
	public PolicyDiaryListColumnComparator(String column, SortOrder direction) {

		super();
		this.column = column;
		this.direction = direction;
	}

	// /**
	// * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	// */
	// public int compare(Object o1, Object o2) {
	//
	// int result = 0;
	//			
	//
	// try {
	// String str1 = (String) PropertyUtils.getProperty(o1, column);
	// String str2 = (String) PropertyUtils.getProperty(o2, column);
	// if (null != str2) str2.trim();
	// if (null != str1) str1.trim();
	//				
	// // handle the situation of sorting the date column... string comparisons don't work there
	// if (column.equalsIgnoreCase("createdDateString")) {
	// if (direction.equals(SortOrder.ASCENDING)) {
	// result = new DateStringComparator().compare(str1, str2);
	// }
	// else if (direction.equals(SortOrder.DESCENDING)) {
	// result = new DateStringComparator().compare(str2, str1);
	// }
	// }
	// // TODO: add cases for numeric and any other type of "special" sorting
	// // otherwise, handle it as a case-insensitive string
	// else {
	// if (direction.equals(SortOrder.ASCENDING)) {
	// result = str1.compareToIgnoreCase(str2);
	// } else if (direction.equals(SortOrder.DESCENDING)) {
	// result = str2.compareToIgnoreCase(str1);
	// }
	// }
	// } catch (Throwable t) {
	// // No action
	// }
	//			
	//
	// return result;
	// }

	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2) {

		int result = 0;

		// New by Rajeev for secondary sort on date
		// createdDateString

		try {
			if (column.equals("createdDateString")) {
				// String str1 = (String) PropertyUtils.getProperty(o1, "createdDateString");
				// String str2 = (String) PropertyUtils.getProperty(o2, "createdDateString");
				Date d1 = (Date) PropertyUtils.getProperty(o1, "createdDate");
				Date d2 = (Date) PropertyUtils.getProperty(o2, "createdDate");
				if (direction.equals(SortOrder.ASCENDING)) {
					// result = str1.compareTo(str2);
					result = d1.compareTo(d2);
				} else if (direction.equals(SortOrder.DESCENDING)) {
					// result = str2.compareTo(str1);
					result = d2.compareTo(d1);
				}
			} else {
				String str1 = (String) PropertyUtils.getProperty(o1, column);
				String str2 = (String) PropertyUtils.getProperty(o2, column);

				if (direction.equals(SortOrder.ASCENDING)) {
					result = str1.compareToIgnoreCase(str2);
				} else if (direction.equals(SortOrder.DESCENDING)) {
					result = str2.compareToIgnoreCase(str1);
				}
			}
		} catch (Throwable t) {
			// No action
		}

		return result;
	}

}
