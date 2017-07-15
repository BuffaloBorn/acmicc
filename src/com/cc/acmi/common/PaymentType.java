package com.cc.acmi.common;

import java.io.Serializable;

import com.cc.framework.common.SimpleEnumType2;

/**
 * PaymentType class is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class PaymentType
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0x4b24f1076e9d9be7L;
    public static final PaymentType NONE = new PaymentType("none", "None");
    public static final PaymentType VISA;
    public static final PaymentType MASTER;
    public static final PaymentType AMEX;
    public static final PaymentType INVOICE;
    private static final PaymentType ALL[];
    private String key;
    private String value;

    public PaymentType(String key, String value)
    {
        this.key = key;
        this.value = value;
    }

    public SimpleEnumType2[] elements()
    {
        return ALL;
    }

    public String getKey()
    {
        return key;
    }

    public String getValue()
    {
        return value;
    }

    static 
    {
        VISA = new PaymentType("visa", "Visa Card");
        MASTER = new PaymentType("master", "Master Card");
        AMEX = new PaymentType("amex", "American Express");
        INVOICE = new PaymentType("invoice", "Invoice");
        ALL = (new PaymentType[] {
            VISA, MASTER, AMEX, INVOICE
        });
    }
}
