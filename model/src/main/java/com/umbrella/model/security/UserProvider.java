package com.umbrella.model.security;

import com.umbrella.model.internal.entity.User;
import com.umbrella.model.internal.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserProvider {
    public class UserDetails {
        public final String username;
        public final String password;
        public final boolean isAdmin;

        public UserDetails(String username, String password, boolean isAdmin) {
            this.username = username;
            this.password = password;
            this.isAdmin = isAdmin;
        }
    }

    @Inject
    private UserRepository userRepository;

    public void createUser(String username, String password, boolean isAdmin) {
        if(userRepository.findByUsername(username) == null) {
            userRepository.save(new User(username, password, isAdmin));
        }
    }

    public UserDetails getUserDetailsByEmail(String email) {
        final User byUsername = userRepository.findByUsername(email);
        if (byUsername == null) {
            return null;
        }
        return new UserDetails(byUsername.getUsername(),byUsername.getPassword(),byUsername.isAdmin());
    }
}