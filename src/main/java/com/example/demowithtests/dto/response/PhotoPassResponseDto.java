package com.example.demowithtests.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PhotoPassResponseDto(

        @Schema(description = "Id in DB")
        Long id,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createDate,

        String url,
        String fileName,

        @Schema(description = "Employee Id")
        Long employeeId,

        @Schema(description = "Work pass Id")
        Long workPassId) {
}
