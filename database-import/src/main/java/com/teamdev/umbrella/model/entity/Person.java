package com.teamdev.umbrella.model.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Entity;

@Entity
public class Person extends AbstractPersistable<Long> {
}
