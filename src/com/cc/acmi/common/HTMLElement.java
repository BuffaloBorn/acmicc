package com.cc.acmi.common;

import java.io.Serializable;

import com.cc.framework.common.InvalidEnumType;
import com.cc.framework.common.SimpleEnumType2;

/**
 * HTMLElement is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */
public class HTMLElement
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0x325ac2cf0098a075L;
    public static final HTMLElement NONE = new HTMLElement(0, "None");
    public static final HTMLElement PLAINTEXT;
    public static final HTMLElement TEXT;
    public static final HTMLElement CALENDAR;
    public static final HTMLElement TEXTAREA;
    public static final HTMLElement TEXTPOPUP;
    public static final HTMLElement COLORPICKER;
    public static final HTMLElement SWAPSELECT;
    public static final HTMLElement SELECT_SINGLE;
    public static final HTMLElement SELECT_MULTIPLE;
    public static final HTMLElement SPIN;
    public static final HTMLElement FILE;
    public static final HTMLElement PASSWORD;
    private static final HTMLElement ALL[];
    private int key;
    private String value;

    public HTMLElement(int key, String value)
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

    public static HTMLElement parse(String code)
        throws InvalidEnumType
    {
        for(int index = 0; index < ALL.length; index++)
        {
            if(ALL[index].equals(code))
            {
                return ALL[index];
            }
        }

        throw new InvalidEnumType("Invalid HTMLElement: " + code);
    }

    public String toString()
    {
        return getKey();
    }

    static 
    {
        PLAINTEXT = new HTMLElement(1, "Plaintext");
        TEXT = new HTMLElement(2, "Text");
        CALENDAR = new HTMLElement(3, "Calendar");
        TEXTAREA = new HTMLElement(4, "Textarea");
        TEXTPOPUP = new HTMLElement(5, "TextPopup");
        COLORPICKER = new HTMLElement(6, "Colorpicker");
        SWAPSELECT = new HTMLElement(7, "SwapSelect");
        SELECT_SINGLE = new HTMLElement(8, "Select-Single");
        SELECT_MULTIPLE = new HTMLElement(9, "<b style='color:red;'>Select-Multiple</b>");
        SPIN = new HTMLElement(10, "Spin");
        FILE = new HTMLElement(11, "File");
        PASSWORD = new HTMLElement(12, "Password");
        ALL = (new HTMLElement[] {
            PLAINTEXT, TEXT, CALENDAR, TEXTAREA, TEXTPOPUP, COLORPICKER, SWAPSELECT, SELECT_SINGLE, SELECT_MULTIPLE, SPIN, 
            FILE, PASSWORD
        });
    }
}
