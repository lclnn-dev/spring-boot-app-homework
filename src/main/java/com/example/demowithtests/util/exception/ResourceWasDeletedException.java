package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceWasDeletedException extends RuntimeException {

    public ResourceWasDeletedException(String message) {
        super(message);
    }

    public ResourceWasDeletedException(Class clazz, String param, String value) {
        super(clazz.getSimpleName() + " was deleted with parameter " + param + " = " + value);
    }
}
