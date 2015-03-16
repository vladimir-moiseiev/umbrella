package com.teamdev.umbrella.model.internal.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

@Entity
public class Person extends AbstractPersistable<Long> {
    private String lastName;
    private String firstName;
    private String secondName; //otchestvo

    private String phone;
    private String identificationNumber;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST} )
    private City city;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.PERSIST} )
    private Street street;

    private String building;
    private String apartment;

    public Person() {
    }

    public Person(String lastName, String firstName, String secondName, String phone, String identificationNumber,
                  City city, Street street, String building, String apartment) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.identificationNumber = identificationNumber;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        //if (!super.equals(o)) return false;

        Person person = (Person) o;

        if (apartment != null ? !apartment.equals(person.apartment) : person.apartment != null) return false;
        if (building != null ? !building.equals(person.building) : person.building != null) return false;
        if (city != null ? !city.equals(person.city) : person.city != null) return false;
        if (firstName != null ? !firstName.equals(person.firstName) : person.firstName != null) return false;
        if (identificationNumber != null ? !identificationNumber.equals(person.identificationNumber) : person.identificationNumber != null)
            return false;
        if (lastName != null ? !lastName.equals(person.lastName) : person.lastName != null) return false;
        if (phone != null ? !phone.equals(person.phone) : person.phone != null) return false;
        if (secondName != null ? !secondName.equals(person.secondName) : person.secondName != null) return false;
        if (street != null ? !street.equals(person.street) : person.street != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;//super.hashCode();
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (identificationNumber != null ? identificationNumber.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + (apartment != null ? apartment.hashCode() : 0);
        return result;
    }
}
