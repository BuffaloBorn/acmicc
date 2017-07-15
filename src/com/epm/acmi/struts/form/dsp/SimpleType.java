package com.epm.acmi.struts.form.dsp;

/**
 * Common Control specific class, can be used to build list control
 * @author Jay Hombal
 *
 */
public class SimpleType {

    private String key;
    private String value;
    /**
     * 
     */
    public SimpleType() {
        super();
    }
    
    public SimpleType(String key, String value) {
        super();
        this.key=key;
        this.value=value;
     }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    
    
    
}
