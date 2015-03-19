package com.umbrella.web.security;

import com.umbrella.model.security.UserProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class SpringUserProvider implements UserDetailsService{
    private static final SimpleGrantedAuthority[] ADMIN_ROLES = new SimpleGrantedAuthority[]
            {new SimpleGrantedAuthority("admin"), new SimpleGrantedAuthority("user")};
    private static final SimpleGrantedAuthority[] USER_ROLES = new SimpleGrantedAuthority[]{new SimpleGrantedAuthority("user")};

    @Inject
    private UserProvider userProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
