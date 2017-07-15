package com.cc.acmi.service.security;


public interface UserRole
{

    public abstract boolean hasRight(String s);

    public abstract String getName();

    public abstract String getCode();
}
