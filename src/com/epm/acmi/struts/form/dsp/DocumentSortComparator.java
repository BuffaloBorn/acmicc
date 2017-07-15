package com.epm.acmi.struts.form.dsp;

import java.util.Comparator;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.cc.framework.common.SortOrder;
import com.epm.acmi.util.DateUtility;

/**
 * Comparator for our book list
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class DocumentSortComparator implements Comparator {


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
	public DocumentSortComparator(String column, SortOrder direction)
	{
		super();

		this.column = column;
		this.direction = direction;
	}


	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object o1, Object o2)
	{

		int result = 0;

		try
		{
			String str1 = (String) PropertyUtils.getProperty(o1, column);
			String str2 = (String) PropertyUtils.getProperty(o2, column);
			if (null != str2) str2.trim();
			if (null != str1) str1.trim();
			if (column.toLowerCase().indexOf("date") >= 0)
			{
				Date d1 = DateUtility.convertStringToDate(str1);
				Date d2 = DateUtility.convertStringToDate(str2);
				
				if (direction.equals(SortOrder.ASCENDING))
				{
					result = d1.compareTo(d2);
				}
				else if (direction.equals(SortOrder.DESCENDING))
				{
					result = d2.compareTo(d1);
				}
			} else {
		
				if (direction.equals(SortOrder.ASCENDING))
				{
					result = str1.compareTo(str2);
				}
				else if (direction.equals(SortOrder.DESCENDING))
				{
					result = str2.compareTo(str1);
				}
			}
		}
		catch (Throwable t)
		{
			// No action
		}

		return result;
	}
}
