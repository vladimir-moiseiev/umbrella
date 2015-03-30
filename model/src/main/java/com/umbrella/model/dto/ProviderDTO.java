package com.umbrella.model.dto;


public class ProviderDTO {
    private final long id;
    private final String provider;

    public ProviderDTO( long id,String provider) {
        this.provider = provider;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getProvider() {
        return provider;
    }
}
