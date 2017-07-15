package com.cc.acmi.service.config;

import com.cc.acmi.service.security.UserRole;

public interface SecurityConfig
{

    public static final String GUEST_ROLE = "guest";

    public abstract UserRole[] getRoles();

    public abstract UserRole parseRole(String s)
        throws IllegalArgumentException;

    public abstract UserRole getGuestRole();
}
