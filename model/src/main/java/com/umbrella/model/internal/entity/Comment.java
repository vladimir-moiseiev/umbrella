package com.umbrella.model.internal.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Comment extends AbstractPersistable<Long> {

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private User user;
    private Date date;
    private String text;

    public Comment() {
    }

    public Comment(User user, Date date, String text) {
        this.user = user;
        this.date = date;
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
