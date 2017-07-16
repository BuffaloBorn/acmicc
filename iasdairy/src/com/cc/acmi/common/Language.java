package com.cc.acmi.common;

import java.io.Serializable;
import java.util.Locale;

import com.cc.framework.common.InvalidEnumType;
import com.cc.framework.common.SimpleEnumType2;

/**
 * Language is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.4 $
 */

public class Language
    implements SimpleEnumType2, Serializable
{

    private static final long serialVersionUID = 0x9a1323b3817398c4L;
    public static final Language NONE = new Language("none", "None");
    public static final Language DEFAULT = new Language("def", "Default Locale (US)");
    public static final Language GERMAN;
    public static final Language ENGLISCH;
    public static final Language ENGLISCH_US;
    public static final Language ENGLISCH_GB;
    public static final Language PRC = new Language("zh_cn", "Chinese");
    public static final Language TAIWAN = new Language("zh_tw", "Taiwan");
    private String key;
    private String value;
    private static final Language ALL[];

    public Language(String key, String value)
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
        if(obj instanceof String)
        {
            String other = (String)obj;
            return key.equals(other);
        } else
        {
            return super.equals(obj);
        }
    }

    public static Language parse(String code)
        throws InvalidEnumType
    {
        for(int index = 0; index < ALL.length; index++)
        {
            if(ALL[index].equals(code))
            {
                return ALL[index];
            }
        }

        throw new InvalidEnumType("Invalid Language: " + code);
    }

    public String toString()
    {
        return key;
    }

    public static Locale toLocale(String language)
    {
        if(language.equals(ENGLISCH.key))
        {
            return Locale.ENGLISH;
        }
        if(language.equals(ENGLISCH_US.key))
        {
            return Locale.US;
        }
        if(language.equals(ENGLISCH_GB.key))
        {
            return Locale.UK;
        }
        if(language.equals(GERMAN.key))
        {
            return Locale.GERMAN;
        }
        if(language.equals(PRC.key))
        {
            return Locale.PRC;
        }
        if(language.equals(TAIWAN.key))
        {
            return Locale.TAIWAN;
        } else
        {
            return Locale.ENGLISH;
        }
    }

    static 
    {
        GERMAN = new Language("de", "German (DE)");
        ENGLISCH = new Language("en", "Englisch (EN)");
        ENGLISCH_US = new Language("en_us", "Englisch (EN_US)");
        ENGLISCH_GB = new Language("en_uk", "Englisch (EN_GB)");
        ALL = (new Language[] {
            GERMAN, ENGLISCH, ENGLISCH_US, ENGLISCH_GB
        });
    }
}
