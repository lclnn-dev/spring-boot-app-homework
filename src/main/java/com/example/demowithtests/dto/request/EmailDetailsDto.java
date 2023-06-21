package com.example.demowithtests.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record EmailDetailsDto(
        @Email
        String recipient,

        String msgBody,

        @NotNull
        String subject) {
}