/**
 * PolicyDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.epm.acmi.bean;

import java.io.Serializable;
import com.cc.framework.common.DisplayObject;

public class PolicyDetail  implements DisplayObject, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String eventid;

    private java.lang.String eventst;

    private java.lang.String policyapplicant;

    private java.lang.String policydesc;

    private java.lang.String policyid;

    private java.lang.String usercode;

    public PolicyDetail() {
    }

    public PolicyDetail(
           java.lang.String eventid,
           java.lang.String eventst,
           java.lang.String policyapplicant,
           java.lang.String policydesc,
           java.lang.String policyid,
           java.lang.String usercode) {
           this.eventid = eventid;
           this.eventst = eventst;
           this.policyapplicant = policyapplicant;
           this.policydesc = policydesc;
           this.policyid = policyid;
           this.usercode = usercode;
    }


    /**
     * Gets the eventid value for this PolicyDetail.
     * 
     * @return eventid
     */
    public java.lang.String getEventid() {
        return eventid;
    }


    /**
     * Sets the eventid value for this PolicyDetail.
     * 
     * @param eventid
     */
    public void setEventid(java.lang.String eventid) {
        this.eventid = eventid;
    }


    /**
     * Gets the eventst value for this PolicyDetail.
     * 
     * @return eventst
     */
    public java.lang.String getEventst() {
        return eventst;
    }


    /**
     * Sets the eventst value for this PolicyDetail.
     * 
     * @param eventst
     */
    public void setEventst(java.lang.String eventst) {
        this.eventst = eventst;
    }


    /**
     * Gets the policyapplicant value for this PolicyDetail.
     * 
     * @return policyapplicant
     */
    public java.lang.String getPolicyapplicant() {
        return policyapplicant;
    }


    /**
     * Sets the policyapplicant value for this PolicyDetail.
     * 
     * @param policyapplicant
     */
    public void setPolicyapplicant(java.lang.String policyapplicant) {
        this.policyapplicant = policyapplicant;
    }


    /**
     * Gets the policydesc value for this PolicyDetail.
     * 
     * @return policydesc
     */
    public java.lang.String getPolicydesc() {
        return policydesc;
    }


    /**
     * Sets the policydesc value for this PolicyDetail.
     * 
     * @param policydesc
     */
    public void setPolicydesc(java.lang.String policydesc) {
        this.policydesc = policydesc;
    }


    /**
     * Gets the policyid value for this PolicyDetail.
     * 
     * @return policyid
     */
    public java.lang.String getPolicyid() {
        return policyid;
    }


    /**
     * Sets the policyid value for this PolicyDetail.
     * 
     * @param policyid
     */
    public void setPolicyid(java.lang.String policyid) {
        this.policyid = policyid;
    }


    /**
     * Gets the usercode value for this PolicyDetail.
     * 
     * @return usercode
     */
    public java.lang.String getUsercode() {
        return usercode;
    }


    /**
     * Sets the usercode value for this PolicyDetail.
     * 
     * @param usercode
     */
    public void setUsercode(java.lang.String usercode) {
        this.usercode = usercode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PolicyDetail)) return false;
        PolicyDetail other = (PolicyDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.eventid==null && other.getEventid()==null) || 
             (this.eventid!=null &&
              this.eventid.equals(other.getEventid()))) &&
            ((this.eventst==null && other.getEventst()==null) || 
             (this.eventst!=null &&
              this.eventst.equals(other.getEventst()))) &&
            ((this.policyapplicant==null && other.getPolicyapplicant()==null) || 
             (this.policyapplicant!=null &&
              this.policyapplicant.equals(other.getPolicyapplicant()))) &&
            ((this.policydesc==null && other.getPolicydesc()==null) || 
             (this.policydesc!=null &&
              this.policydesc.equals(other.getPolicydesc()))) &&
            ((this.policyid==null && other.getPolicyid()==null) || 
             (this.policyid!=null &&
              this.policyid.equals(other.getPolicyid()))) &&
            ((this.usercode==null && other.getUsercode()==null) || 
             (this.usercode!=null &&
              this.usercode.equals(other.getUsercode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getEventid() != null) {
            _hashCode += getEventid().hashCode();
        }
        if (getEventst() != null) {
            _hashCode += getEventst().hashCode();
        }
        if (getPolicyapplicant() != null) {
            _hashCode += getPolicyapplicant().hashCode();
        }
        if (getPolicydesc() != null) {
            _hashCode += getPolicydesc().hashCode();
        }
        if (getPolicyid() != null) {
            _hashCode += getPolicyid().hashCode();
        }
        if (getUsercode() != null) {
            _hashCode += getUsercode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PolicyDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://webservice.acmi.com", "PolicyDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservice.acmi.com", "eventid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("eventst");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservice.acmi.com", "eventst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyapplicant");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservice.acmi.com", "policyapplicant"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policydesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservice.acmi.com", "policydesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservice.acmi.com", "policyid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("usercode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://webservice.acmi.com", "usercode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
