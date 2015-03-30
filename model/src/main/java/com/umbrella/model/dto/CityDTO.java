package com.umbrella.model.dto;

public class CityDTO {
    private final long id;
    private final String city;

    public CityDTO(long id, String city) {
        this.id = id;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }
}
