package com.example.demowithtests.service;

public interface EmailService {

    String sendSimpleMail(String recipient, String msgBody, String subject);
}