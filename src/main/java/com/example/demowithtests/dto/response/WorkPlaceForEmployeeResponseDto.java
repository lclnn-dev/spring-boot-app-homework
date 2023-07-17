package com.example.demowithtests.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record WorkPlaceForEmployeeResponseDto(

        @Schema(description = "Id in DB")
        Long id,

        String name,
        Boolean airCondition,
        Boolean coffeeMachine,
        Boolean isActive) {
}
