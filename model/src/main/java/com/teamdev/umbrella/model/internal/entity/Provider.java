package com.teamdev.umbrella.model.internal.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
public class Provider extends AbstractPersistable<Long> {
    private String provider;

    public Provider() {
    }

    public Provider(String provider) {
        this.provider = provider;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
