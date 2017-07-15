package com.cc.acmi.common;

import java.io.Serializable;

import com.cc.framework.common.InvalidEnumType;
import com.cc.framework.common.SimpleEnumType2;

/**
 * RegionType class is Common Controls  specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class RegionType
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0xe381f52345719622L;
    public static final RegionType NONE = new RegionType("N", "None");
    public static final RegionType WORLD = new RegionType("W", "World");
    public static final RegionType GROUP;
    public static final RegionType COUNTRY;
    private static final RegionType ALL[];
    private String key;
    private String value;

    public RegionType(String key, String value)
    {
        this.key = "";
        this.value = "";
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

    public boolean equals(Object obj)
    {
        if(obj instanceof RegionType)
        {
            RegionType other = (RegionType)obj;
            return key.equals(other.getKey());
        } else
        {
            return super.equals(obj);
        }
    }

    public static RegionType parse(String code)
        throws InvalidEnumType
    {
        for(int index = 0; index < ALL.length; index++)
        {
            if(ALL[index].getKey().equals(code))
            {
                return ALL[index];
            }
        }

        throw new InvalidEnumType("Invalid RegionType: " + code);
    }

    public String toString()
    {
        return key;
    }

    static 
    {
        GROUP = new RegionType("G", "Group");
        COUNTRY = new RegionType("C", "Country");
        ALL = (new RegionType[] {
            GROUP, COUNTRY
        });
    }
}
