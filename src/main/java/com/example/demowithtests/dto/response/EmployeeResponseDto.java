package com.example.demowithtests.dto.response;

import com.example.demowithtests.domain.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class EmployeeResponseDto {

    private Integer id;
    private String name;
    private String country;
    private String email;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date date = Date.from(Instant.now());

    private Gender gender;
    private Set<AddressResponseDto> addresses = new HashSet<>();
    private boolean isDeleted;
}
