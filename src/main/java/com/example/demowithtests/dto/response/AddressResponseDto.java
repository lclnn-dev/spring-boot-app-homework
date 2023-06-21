package com.example.demowithtests.dto.response;

public record AddressResponseDto(
        Long id,
        Boolean addressHasActive,
        String country,
        String city,
        String street) {
}
