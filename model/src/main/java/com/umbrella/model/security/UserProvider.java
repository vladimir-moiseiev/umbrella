package com.umbrella.model.security;

import com.umbrella.model.internal.entity.User;
import com.umbrella.model.internal.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class UserProvider {
    public class UserDetails {
        public final String username;
        public final boolean isAdmin;

        public UserDetails(String username, boolean isAdmin) {
            this.username = username;
            this.isAdmin = isAdmin;
        }
    }

    @Inject
    private UserRepository userRepository;

    UserDetails getUserDetailsByEmail(String email) {
        final User byUsername = userRepository.findByUsername(email);
        if (byUsername == null) {
            return null;
        }
        return new UserDetails(byUsername.getUsername(),byUsername.isAdmin());
    }
}
