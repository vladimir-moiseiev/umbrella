package com.umbrella.model.dto;


import java.util.Date;

public class CommentDTO {
    private long id;
    private String user;
    private Date date;
    private String text;

    public CommentDTO() {
    }

    public CommentDTO(long id, String user, Date date, String text) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
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
