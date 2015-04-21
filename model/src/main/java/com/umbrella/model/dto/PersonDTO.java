package com.umbrella.model.dto;


import java.util.Date;
import java.util.List;
import java.util.Set;

public class PersonDTO {
    private long id;
    private String lastName;
    private String firstName;
    private String secondName;

    private Set<String> phones;
    private String identificationNumber;

    private String city;
    private String street;
    private String building;
    private String apartment;

    private String provider;

    private List<CommentDTO> comments;

    private Date createdDate;

    public PersonDTO() {
    }

    public PersonDTO(long id, String lastName, String firstName, String secondName, Set<String> phones,
                     String identificationNumber, String city, String street, String building, String apartment,
                     String provider, List<CommentDTO> comments, Date createdDate) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.secondName = secondName;
        this.phones = phones;
        this.identificationNumber = identificationNumber;
        this.city = city;
        this.street = street;
        this.building = building;
        this.apartment = apartment;
        this.provider = provider;
        this.comments = comments;
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
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

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
