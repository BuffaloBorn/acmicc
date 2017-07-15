package com.cc.acmi.common;

import java.io.Serializable;

import com.cc.framework.common.SimpleEnumType2;
/**
 * Color class is used as Color standards by IU&A application.
 * 
 * @author Jay Hombal
 * @version $Revision: 1.5 $
 */
public class Color
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0xb0bb83cb9d19211fL;
    public static final Color NONE = new Color("none", "None");
    public static final Color WHITE;
    public static final Color RED;
    public static final Color BLACK;
    private static final Color ALL[];
    private String key;
    private String value;

    public Color(String key, String value)
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
        WHITE = new Color("white", "White");
        RED = new Color("red", "Red");
        BLACK = new Color("black", "Black");
        ALL = (new Color[] {
            WHITE, RED, BLACK
        });
    }
}
