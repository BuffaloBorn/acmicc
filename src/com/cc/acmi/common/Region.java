package com.cc.acmi.common;

/**
 * Region class is Common Controls specific class 
 * 
 * @author Jay Hombal
 * @version $Revision: 1.3 $
 */

public class Region
    implements BusinessObject
{

    private String id;
    private String parent;
    private String name;
    private RegionType type;

    public Region(String id)
    {
        this.id = null;
        parent = null;
        name = null;
        type = RegionType.NONE;
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getParent()
    {
        return parent;
    }

    public RegionType getType()
    {
        return type;
    }

    public void setName(String string)
    {
        name = string;
    }

    public void setParent(String string)
    {
        parent = string;
    }

    public void setType(RegionType type)
    {
        this.type = type;
    }
}
