package com.epm.acmi.datamodel;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.cc.framework.common.SortOrder;

/**
 * ReassignTaskColumnComparator used for sorting reassign task list
 * @author usaho
 *
 */
public class ReassignTaskColumnComparator implements Comparator  {

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
		 * @param	column	Column to Sort
		 * @param	direction	SortDirection
		 */
		public ReassignTaskColumnComparator(String column, SortOrder direction) {
			super();

			this.column = column;
			this.direction = direction;
		}

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
