package com.umbrella.web.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchRequest {
    private String lastName;
    private boolean triolan;
    private boolean ks;
    private boolean volya;
    private String city;

    public SearchRequest() {
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isTriolan() {
        return triolan;
    }

    public void setTriolan(boolean triolan) {
        this.triolan = triolan;
    }

    public boolean isKs() {
        return ks;
    }

    public void setKs(boolean ks) {
        this.ks = ks;
    }

    public boolean isVolya() {
        return volya;
    }

    public void setVolya(boolean volya) {
        this.volya = volya;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
