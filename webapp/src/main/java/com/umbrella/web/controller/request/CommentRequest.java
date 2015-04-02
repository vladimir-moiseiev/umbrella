package com.umbrella.web.controller.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentRequest {
    private long record;
    private String text;

    public CommentRequest() {
    }

    public long getRecord() {
        return record;
    }

    public void setRecord(long record) {
        this.record = record;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
