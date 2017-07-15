/**
 * $Header: d:/repository/cvs/cc-samples-ext/source/com/cc/sample/dbaccess/Filter.java,v 1.1 2003/11/21 15:35:58 P001001 Exp $
 * $Revision: 1.1 $
 * $Date: 2003/11/21 15:35:58 $
 *
 * ==========================================================================
 *
 * Copyright (c) 2002-2003 SCC Informationssysteme GmbH. All rights reserved.
 * www.scc-gmbh.com
 *
 * ==========================================================================
 */
package com.epm.acmi.bean;

import java.util.TreeMap;

/**
 * Defines a filter
 * 
 * @author   <a href="mailto:gschulz@scc-gmbh.com">Gernot Schulz</a>
 * @version  $Revision: 1.1 $
 */
public class Filter {

	/**
	 * Collection with columns and values to filter
	 */
	private TreeMap attributes = new TreeMap();

	// -------------------------------------------------
	//                    Methods
	// -------------------------------------------------

	/**
	 * Default Constructor
	 */
	public Filter() {
		super();
	}

	/**
	 * Sets the filter attributes.
	 * @param	id	ColumnId
	 * @param	value	value to filter
	 */
	public void setAttribute(String id, String value) {
		
		if (null == id || id.equals("") || null == value || value.equals("")) {
			return;
		} 

		attributes.put(id, value);
	}

	/**
	 * Returns the attributes.
	 * @return Hashtable
	 */
	public TreeMap getAttributes() {
		return attributes;
	}

}
