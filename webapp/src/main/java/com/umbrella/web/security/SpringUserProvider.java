package com.umbrella.web.security;

import com.google.common.collect.Lists;
import com.umbrella.model.security.UserProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@Component
public class SpringUserProvider implements UserDetailsService{
    private static final SimpleGrantedAuthority[] ADMIN_ROLES = new SimpleGrantedAuthority[]
            {new SimpleGrantedAuthority("admin"), new SimpleGrantedAuthority("user")};
    private static final SimpleGrantedAuthority[] USER_ROLES = new SimpleGrantedAuthority[]{new SimpleGrantedAuthority("user")};

    @Inject
    private UserProvider userProvider;

    @Inject
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initAdmin() {
        userProvider.createUser("admin", bCryptPasswordEncoder.encode("pwd"), true);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProvider.UserDetails user = userProvider.getUserDetailsByEmail(username);
        return new User(user.username,user.password,user.isAdmin ? Lists.newArrayList(ADMIN_ROLES) : Lists.newArrayList(USER_ROLES));
    }
}
