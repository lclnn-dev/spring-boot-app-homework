package com.example.demowithtests.dto.response;

import com.example.demowithtests.domain.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public record EmployeeResponseDto(

        @Schema(description = "Id in DB")
        Integer id,

        String name,
        String country,
        String email,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "Date response")
        Date date,

        Gender gender,
        Set<AddressResponseDto> addresses,

        @Schema(description = "Flag of deletion marker")
        boolean isDeleted) {

    public EmployeeResponseDto(Integer id, String name, String country, String email, Date date, Gender gender,
                               Set<AddressResponseDto> addresses, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.email = email;
        this.date = date != null ? date : Date.from(Instant.now());
        this.gender = gender;
        this.addresses = addresses != null ? addresses : new HashSet<>();
        this.isDeleted = isDeleted;
    }
}
