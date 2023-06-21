package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSimpleSenderException extends RuntimeException {

    public EmailSimpleSenderException(String message) {
        super(message);
    }

    public EmailSimpleSenderException(String message, Throwable cause) {
        super(message, cause);
    }
}
