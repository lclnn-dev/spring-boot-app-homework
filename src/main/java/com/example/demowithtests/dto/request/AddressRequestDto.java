package com.example.demowithtests.dto.request;

import com.example.demowithtests.util.annotation.dto.CountryRightFormed;

public record AddressRequestDto(
        Boolean addressHasActive,

        @CountryRightFormed
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
