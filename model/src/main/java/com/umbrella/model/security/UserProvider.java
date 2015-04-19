package com.umbrella.model.security;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.umbrella.model.internal.entity.User;
import com.umbrella.model.internal.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class UserProvider {
    public class UserDetails {
        public final long id;
        public final String username;
        public final String password;
        public final boolean isAdmin;

        public UserDetails(long id, String username, String password, boolean isAdmin) {
            this.id = id;
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
        return getUserDetails(byUsername);
    }

    private UserDetails getUserDetails(User byUsername) {
        return new UserDetails(byUsername.getId(), byUsername.getUsername(),byUsername.getPassword(),byUsername.isAdmin());
    }

    public List<UserDetails> getAllUsers() {
        List<User> all = userRepository.findAll();
        return Lists.newArrayList(Lists.transform(all, new Function<User, UserDetails>() {
            @Override
            public UserDetails apply(User input) {
                return getUserDetails(input);
            }
        }));

    }
}
