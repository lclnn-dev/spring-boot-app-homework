package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class WorkPlaceException extends RuntimeException {

    public WorkPlaceException(String message) {
        super(message);
    }

    public WorkPlaceException(String message, Throwable cause) {
        super(message, cause);
    }
}
