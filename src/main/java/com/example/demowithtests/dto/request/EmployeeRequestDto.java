package com.example.demowithtests.dto.request;

import com.example.demowithtests.domain.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record EmployeeRequestDto(
        @NotNull
        @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long.")
        @Schema(description = "Name of an employee.", example = "Billy",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String name,

        @Schema(description = "Name of the country.", example = "England",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String country,

        @Email
        @NotNull
        @Schema(description = "Email address of an employee.", example = "billys@mail.com",
                requiredMode = Schema.RequiredMode.REQUIRED)
        String email,

        Gender gender,
        Set<AddressRequestDto> addresses) {
}
