package com.umbrella.model.security;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.umbrella.model.internal.entity.User;
import com.umbrella.model.internal.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service
public class UserProvider {

    public static final String ADMIN = "admin";
    public static final String PWD = "pwd";

    public class UserDetails {

        public final long id;
        public final String username;
        public final String password;
        public final Date validUntil;
        public final boolean isAdmin;
        public UserDetails(long id, String username, String password, Date validUntil, boolean isAdmin) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.validUntil = validUntil;
            this.isAdmin = isAdmin;
        }

    }
    @Inject
    private UserRepository userRepository;

    public long createUser(String username, String password, boolean isAdmin, Date validUntil) {
        if(userRepository.findByUsername(username) == null) {
            return userRepository.save(new User(username, password, isAdmin, validUntil)).getId();
        }
        throw new RuntimeException("User " + username + "already exist.");
    }

    public void setPassword(long id, String password) {
        User user = userRepository.findOne(id);
        if( user == null) {
            throw new RuntimeException("Can't find user with id: " + id);
        }

        user.setPassword(password);
        userRepository.save(user);
    }

    public void setValidUntil(long id, Date validUntil) {
        User user = userRepository.findOne(id);
        if( user == null) {
            throw new RuntimeException("Can't find user with id: " + id);
        }

        user.setValidUntil(validUntil);
        userRepository.save(user);
    }

    public UserDetails getUserDetailsByEmail(String email) {
        final User byUsername = userRepository.findByUsername(email);
        if (byUsername == null) {
            return null;
        }
        return getUserDetails(byUsername);
    }

    private UserDetails getUserDetails(User byUsername) {
        return new UserDetails(byUsername.getId(), byUsername.getUsername(),byUsername.getPassword(), byUsername.getValidUntil(), byUsername.isAdmin());
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

    public void removeUser(long userId) {
        User one = userRepository.findOne(userId);
        if(one != null && one.getUsername().equals(ADMIN)) {
            throw new RuntimeException("Can't remove admin");
        }
        userRepository.delete(userId);

    }
}
