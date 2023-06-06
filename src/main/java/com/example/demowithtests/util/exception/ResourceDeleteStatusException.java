package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceDeleteStatusException extends RuntimeException {

    public ResourceDeleteStatusException(String message) {
        super(message);
    }

    public ResourceDeleteStatusException(Class clazz, String param, String value, String status) {
        super(clazz.getSimpleName() + " was " + status + " with parameter " + param + " = " + value);
    }
}
