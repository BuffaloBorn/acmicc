package com.epm.acmi.util;

import java.util.Vector;


// Created for use with WorkFlowHelper to build complex Queries within iFlow. NOTE: iFlow uses static constants for each
// LISTFIELD_* item defined see iFlow Javadocs API Also iFlow Javadocs for SQLOP_* constants and DataItemRef.TYPE_*
// constants.
 

 // Types of Filters to add as shown below. wfol.addFilter(LISTFIELD_WORKITEM_NAME, SQLOP_EQUALTO, variableValue);
 // wfol.addFilter(java.lang.String udaName, DataItemRef.TYPE_STRING, SQLOP_EQUALTO, java.lang.String variableValue);
 // wfol.addSortOrder(LISTFIELD_WORKITEM_PRIORITY,SORTORDER_DESCENDING);
 
/**
 * This class is used by EPMHelper.
 * @author Rick Perry 
 */
public class WorkFlowFilter {

	private Vector FilterList = new Vector();

	// *** Sorting ***********************************
	// Field Sorting
	private boolean useSortOrder = false;

	private int sortField = -1;

	// UDA Sorting
	private String udaSortColumn = null;

	private String udaTypeAsString = null;

	// Common Sorting constant
	private boolean sortOrder = false;

	public Vector getFilterList() {
		return FilterList;
	}

	public boolean isSortOrderUsed() {
		return useSortOrder;
	}

	public int getSortField() {
		return sortField;
	}

	public boolean getSortOrder() {
		return sortOrder;
	}

	public String udaSortColumnName() {
		return udaSortColumn;
	}

	public String udaSortDataType() {
		return udaTypeAsString;
	}

	public void setSortOrder(int sortFieldConstant, boolean sortOrderConstant) {
		useSortOrder = true;
		sortField = sortFieldConstant;
		sortOrder = sortOrderConstant;
	}

	public void setSortOrder(String udaName, String udaDataType, boolean sortOrderConstant) {
		useSortOrder = true;
		udaSortColumn = udaName;
		udaTypeAsString = udaDataType;
		sortOrder = sortOrderConstant;
	}

	public void addFilter(int fieldConstant, String sqlOp, String value) {

		WorkFlowQueryFilter qFilter = new WorkFlowQueryFilter();
		qFilter.setField(fieldConstant);
		qFilter.setOperator(sqlOp);
		qFilter.setValue("'" + value + "'");

		FilterList.add(qFilter);
	}

	public void addFilter(String uda, String typeConstant, String sqlOpConstant, String value) {

		WorkFlowQueryFilter qFilter = new WorkFlowQueryFilter();
		qFilter.setUdaName(uda);
		qFilter.setDataType(typeConstant);
		qFilter.setOperator(sqlOpConstant);

		// NOT SURE IF I NEED TO MAKE SUREDATATYPE = STRING before quoting ????
		qFilter.setValue("'" + value + "'");

		FilterList.add(qFilter);
	}

}
