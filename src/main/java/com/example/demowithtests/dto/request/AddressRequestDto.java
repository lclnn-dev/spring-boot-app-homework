package com.example.demowithtests.dto.request;

import com.example.demowithtests.util.annotation.dto.CountryRightFormed;
import io.swagger.v3.oas.annotations.media.Schema;

public record AddressRequestDto(

        @Schema(description = "Flag of active address")
        Boolean addressHasActive,

        @CountryRightFormed
        @Schema(description = "Name of the country. Country must be a 2 characters length and uppercase.", example = "EN")
        String country,

        @Schema(description = "Name of the city.", example = "Kyiv")
        String city,

        @Schema(description = "Name of the street.", example = "Cental")
        String street) {

    public AddressRequestDto(Boolean addressHasActive, String country, String city, String street) {
        this.addressHasActive = addressHasActive != null ? addressHasActive : Boolean.TRUE;
        this.country = country;
        this.city = city;
        this.street = street;
    }
}
