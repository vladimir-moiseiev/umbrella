package com.umbrella.model;

import org.springframework.transaction.annotation.Transactional;

//@Transactional
public interface ConnectionInfoManager {
    void addComment(long userId, long id, String text);
}
