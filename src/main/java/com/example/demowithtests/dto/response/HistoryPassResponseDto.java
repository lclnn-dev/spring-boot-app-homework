package com.example.demowithtests.dto.response;

public record HistoryPassResponseDto(
        Integer employeeId,
        Long passId,
        String action
) {
}
