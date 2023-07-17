package com.example.demowithtests.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

public record WorkPlaceResponseDto(

        @Schema(description = "Id in DB")
        Long id,

        String name,
        Boolean airCondition,
        Boolean coffeeMachine,

        Set<EmployeeWorkPlaceResponseDto> employees
) {
}
