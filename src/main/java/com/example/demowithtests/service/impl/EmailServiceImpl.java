package com.example.demowithtests.service.impl;

import com.example.demowithtests.service.EmailService;
import com.example.demowithtests.util.exception.EmailSimpleSenderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public String sendSimpleMail(String recipient, String msgBody, String subject) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(recipient);
        mailMessage.setText(msgBody);
        mailMessage.setSubject(subject);

        try {
            emailSender.send(mailMessage);
            return "Email sent successfully";

        } catch (MailException ex) {
            log.debug("sendSimpleMail() EmailServiceImpl - Error while sending out email to {}", recipient);
            throw new EmailSimpleSenderException("Unable to send email", ex);
        }
    }
}
