package com.umbrella.web.security;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;
import java.util.Collection;

public class ExtendedUser extends User {
    private long userId;

    public ExtendedUser(String username, String password, Collection<? extends GrantedAuthority> authorities, long userId) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public ExtendedUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, long userId) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public static long getUserId(Principal principal) {
        return get(principal).getUserId();
    }

    public static ExtendedUser get(Principal principal) {
        if(principal == null) throw new AccessDeniedException("User is unauthorized");
        return (ExtendedUser) ((Authentication) principal).getPrincipal();
    }
}
