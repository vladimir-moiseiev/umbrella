package com.umbrella.model;


import com.umbrella.model.internal.entity.Comment;
import com.umbrella.model.internal.entity.ConnectionInfo;
import com.umbrella.model.internal.entity.User;
import com.umbrella.model.internal.repository.ConnectionInfoRepository;
import com.umbrella.model.internal.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Date;

@Service
public class ConnectionInfoManager {

    @Inject
    private ConnectionInfoRepository connectionInfoRepository;

    @Inject
    private UserRepository userRepository;

    public void addComment(long userId, long id, String text) {
        ConnectionInfo one = connectionInfoRepository.findOne(id);
        if(one == null) {
            throw new RuntimeException("Can't find record with id " + id);
        }
        User user = userRepository.findOne(userId);
        if(user == null) {
            throw new RuntimeException("Can't find user with id " + userId);
        }
        one.getComments().add(new Comment(user,new Date(), text));
        connectionInfoRepository.save(one);
    }
}
