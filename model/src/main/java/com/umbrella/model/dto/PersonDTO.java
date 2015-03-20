package com.umbrella.model.dto;


public class PersonDTO {
    private String lastName;
    private String firstName;
    private String secondName;

    private String phone;
    private String identificationNumber;

    private String city;
    private String street;
    private String building;
    private String apartment;

    private String provider;

    public PersonDTO() {
    }

    public PersonDTO(String lastName, String firstName, String secondName, String phone, String identificationNumber, String city, String street, String building, String apartment, String provider) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.identificationNumber = identificationNumber;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.provider = provider;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
