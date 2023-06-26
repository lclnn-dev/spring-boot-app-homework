package com.example.demowithtests.dto.request;

import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.util.annotation.dto.BlockedEmailDomains;
import com.example.demowithtests.util.annotation.dto.CountryRightFormed;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
        @CountryRightFormed
        String country,

        @Email
        @NotEmpty
        @Schema(description = "Email address of an employee.", example = "billys@mail.com",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @BlockedEmailDomains(contains = {"com1", "rambler"}, appendDefault = true)
        String email,

        Gender gender,

        @Valid
        Set<AddressRequestDto> addresses) {
}
