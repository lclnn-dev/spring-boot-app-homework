package com.example.demowithtests.dto.request;

public record AddressRequestDto(
        Boolean addressHasActive,
        String country,
        String city,
        String street) {

    public AddressRequestDto(Boolean addressHasActive, String country, String city, String street) {
        this.addressHasActive = addressHasActive != null ? addressHasActive : Boolean.TRUE;
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
