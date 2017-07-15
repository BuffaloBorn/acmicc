package com.cc.acmi.service.security;


public class Grant
{

    private String code;
    private boolean granted;

    public Grant()
    {
        granted = true;
    }

    public Grant(String code, boolean granted)
    {
        this.granted = true;
        this.code = code;
        this.granted = granted;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public boolean isGranted()
    {
        return granted;
    }

    public void setGranted(boolean granted)
    {
        this.granted = granted;
    }

    public String toString()
    {
        return "Grant: " + code + (granted ? " IS GRANTED" : "NOT GRANTED");
    }
}
