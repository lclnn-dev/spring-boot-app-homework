package com.example.demowithtests.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record AddressResponseDto(
        @Schema(description = "Id in DB")
        Long id,

        Boolean addressHasActive,
        String country,
        String city,
        String street) {
}
