package com.umbrella.model.internal.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Street extends AbstractPersistable<Long> {
    @Column(unique = true)
    private String street;

    public Street() {
    }

    public Street(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //if (!super.equals(o)) return false;

        Street street1 = (Street) o;

        if (street != null ? !street.equals(street1.street) : street1.street != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;//super.hashCode();
        result = 31 * result + (street != null ? street.hashCode() : 0);
        return result;
    }
}
