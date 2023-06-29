package com.example.demowithtests.web;

import com.example.demowithtests.dto.request.EmailDetailsDto;
import com.example.demowithtests.service.EmailService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
@Hidden
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send/simple")
    @ResponseStatus(HttpStatus.OK)
    public String sendMail(@Valid @RequestBody EmailDetailsDto details) {
        return emailService.sendSimpleMail(details.recipient(), details.msgBody(), details.subject());
    }
}