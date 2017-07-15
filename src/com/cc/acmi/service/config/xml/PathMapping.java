package com.cc.acmi.service.config.xml;


public class PathMapping
{

    private String virtname;
    private String path;
    private boolean translate;

    public PathMapping()
    {
        translate = false;
    }

    public String getVirtname()
    {
        return virtname;
    }

    public void setVirtname(String virtname)
    {
        this.virtname = virtname;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public boolean isTranslate()
    {
        return translate;
    }

    public void setTranslate(boolean translate)
    {
        this.translate = translate;
    }

    public String toString()
    {
        return virtname + "=" + path;
    }
}
