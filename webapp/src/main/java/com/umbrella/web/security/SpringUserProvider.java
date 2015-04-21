package com.umbrella.web.security;

import com.google.common.collect.Lists;
import com.umbrella.model.security.UserProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Date;

import static com.umbrella.model.security.UserProvider.ADMIN;
import static com.umbrella.model.security.UserProvider.PWD;

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
        if(userProvider.getUserDetailsByEmail(ADMIN) == null)
            userProvider.createUser(ADMIN, bCryptPasswordEncoder.encode(PWD), true, null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProvider.UserDetails user = userProvider.getUserDetailsByEmail(username);
        if(user != null){
            if(user.validUntil != null && user.validUntil.before(new Date()) && !user.isAdmin) {
                throw new UsernameNotFoundException("User expired.");
            }
            return new ExtendedUser(user.username,user.password,user.isAdmin ? Lists.newArrayList(ADMIN_ROLES) : Lists.newArrayList(USER_ROLES),user.id);
        }
        throw new UsernameNotFoundException("Can't find user " + username);
    }
}
