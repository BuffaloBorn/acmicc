package com.epm.acmi.util;
 //* @author : Rick Perry
 //* To change this template use Options | File Templates.
 //* NOTE: iFlow uses static constants for each LISTFIELD_* item defined see iFlow Javadocs API
 //* Also iFlow Javadocs for SQLOP_* constants and DataItemRef.TYPE_* constants.
 
/*
    Types of Filters to add as shown below.
    wfol.addFilter(LISTFIELD_WORKITEM_NAME, SQLOP_EQUALTO, variableValue);
    wfol.addFilter(java.lang.String udaName, DataItemRef.TYPE_STRING, SQLOP_EQUALTO, java.lang.String variableValue);
    wfol.addSortOrder(LISTFIELD_WORKITEM_PRIORITY,SORTORDER_DESCENDING);
*/

/**
 * This class is used by EPMHelper.
 * @author Rick Perry 
 */
public class WorkFlowQueryFilter {

    int field = -1;
    String sqlOperator = null;
    String comparisonValue = null;
    // These parms are used for Queries where udaName = value
    String udaName = null;
    String dataType = null;

    public void setField(int fieldConstant) { field             = fieldConstant; }
    public void setOperator(String sqlOp)   { sqlOperator       = sqlOp; }
    public void setValue(String val)        { comparisonValue   = val; }
    public void setUdaName(String uda)      { udaName           = uda; }
    public void setDataType(String type)    { dataType          = type; }

    public int getField()       { return field; }
    public String getOperator() { return sqlOperator; }
    public String getValue()    { return comparisonValue; }
    public String getUdaName()  { return udaName; }
    public String getDataType() { return dataType; }

}