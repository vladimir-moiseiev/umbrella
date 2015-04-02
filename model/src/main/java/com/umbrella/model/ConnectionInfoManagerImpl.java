package com.umbrella.model;


import com.umbrella.model.internal.entity.Comment;
import com.umbrella.model.internal.entity.ConnectionInfo;
import com.umbrella.model.internal.entity.User;
import com.umbrella.model.internal.repository.CommentRepository;
import com.umbrella.model.internal.repository.ConnectionInfoRepository;
import com.umbrella.model.internal.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ConnectionInfoManagerImpl implements ConnectionInfoManager {

    @Inject
    private ConnectionInfoRepository connectionInfoRepository;

    @Inject
    private CommentRepository commentRepository;

    @Inject
    private UserRepository userRepository;

    @Override
    public void addComment(long userId, long id, String text) {
        ConnectionInfo one = connectionInfoRepository.findOne(id);
        if(one == null) {
            throw new RuntimeException("Can't find record with id " + id);
        }
        User user = userRepository.findOne(userId);
        if(user == null) {
            throw new RuntimeException("Can't find user with id " + userId);
        }
        Comment newComment = commentRepository.save(new Comment(user, new Date(), text));

        final List<Comment> comments = one.getComments();
        comments.add(newComment);
        one.setComments(comments);
        connectionInfoRepository.save(one);
    }
}
