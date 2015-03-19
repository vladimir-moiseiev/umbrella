package com.umbrella.model.internal.repository;

import com.umbrella.model.internal.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
