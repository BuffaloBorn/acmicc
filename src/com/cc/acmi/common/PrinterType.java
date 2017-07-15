package com.cc.acmi.common;

import java.io.Serializable;

import com.cc.framework.common.SimpleEnumType2;

/**
 * PrinterType class is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class PrinterType
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0x90856f2232bcb2c0L;
    public static final PrinterType NONE = new PrinterType("none", "None");
    public static final PrinterType HP4M;
    private static final PrinterType ALL[];
    private String key;
    private String value;

    public PrinterType(String key, String value)
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
        HP4M = new PrinterType("HP4M", "HP LaserJet 4M");
        ALL = (new PrinterType[] {
            HP4M
        });
    }
}
