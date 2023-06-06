package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoResultsFoundException extends RuntimeException {

    public NoResultsFoundException(String message) {
        super(message);
    }

    public NoResultsFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoResultsFoundException(String pluralName, String param, String value) {
        super("No " + pluralName + " found for " + param + " = " + value);
    }
}
