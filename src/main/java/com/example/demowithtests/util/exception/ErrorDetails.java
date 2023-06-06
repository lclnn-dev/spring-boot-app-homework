package com.example.demowithtests.util.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ErrorDetails {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private HttpStatus status;
    private String path;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String details;


    private ErrorDetails() {
        timestamp = LocalDateTime.now();
    }

    public ErrorDetails(HttpStatus status) {
        this();
        this.status = status;
    }

    public ErrorDetails(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.details = ex.getLocalizedMessage();
    }
}
