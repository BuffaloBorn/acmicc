package com.cc.acmi.common;

import java.io.Serializable;

import com.cc.framework.common.SimpleEnumType2;

/**
 * GuiType is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class GuiType
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0xbc6cfcef2367f0a1L;
    public static final GuiType NONE = new GuiType("none", "None");
    public static final GuiType DEFAULT;
    public static final GuiType NORMAL;
    public static final GuiType LITE;
    private static final GuiType ALL[];
    private String key;
    private String value;

    public GuiType(String key, String value)
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
        DEFAULT = new GuiType("DEF", "User Profile Settings");
        NORMAL = new GuiType("NORMAL", "Rich (high bandwidth)");
        LITE = new GuiType("LITE", "Lite (small bandwidth)");
        ALL = (new GuiType[] {
            DEFAULT, NORMAL, LITE
        });
    }
}
