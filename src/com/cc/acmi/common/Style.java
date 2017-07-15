package com.cc.acmi.common;

import java.io.Serializable;

import com.cc.framework.common.InvalidEnumType;
import com.cc.framework.common.SimpleEnumType2;

/**
 * Style class is Common Controls  class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class Style
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0xee0b9a04cc1044c8L;
    public static final Style NONE = new Style(0, "None");
    public static final Style ENABLED;
    public static final Style DISABLED;
    public static final Style READONLY;
    private static final Style ALL[];
    private int key;
    private String value;

    public Style(int key, String value)
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
        return Integer.toString(key);
    }

    public String getValue()
    {
        return value;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof String)
        {
            String other = (String)obj;
            return getKey().equals(other);
        } else
        {
            return super.equals(obj);
        }
    }

    public static Style parse(String code)
        throws InvalidEnumType
    {
        for(int index = 0; index < ALL.length; index++)
        {
            if(ALL[index].equals(code))
            {
                return ALL[index];
            }
        }

        throw new InvalidEnumType("Invalid Style: " + code);
    }

    public String toString()
    {
        return getKey();
    }

    static 
    {
        ENABLED = new Style(2, "enabled");
        DISABLED = new Style(3, "disabled");
        READONLY = new Style(4, "readonly");
        ALL = (new Style[] {
            ENABLED, DISABLED, READONLY
        });
    }
}
