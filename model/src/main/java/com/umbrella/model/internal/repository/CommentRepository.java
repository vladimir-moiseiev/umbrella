package com.umbrella.model.internal.repository;


import com.umbrella.model.internal.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
