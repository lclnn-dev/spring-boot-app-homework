package com.example.demowithtests.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    private ResponseEntity<Object> buildErrorResponse(ErrorDetails errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND);
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setPath(request.getDescription(false));

        return buildErrorResponse(errorDetails);
    }

    @ExceptionHandler(ResourceDeleteStatusException.class)
    protected ResponseEntity<Object> ResourceDeleteStatusException(ResourceDeleteStatusException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST);
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setPath(request.getDescription(false));

        return buildErrorResponse(errorDetails);
    }

    @ExceptionHandler(NoResultsFoundException.class)
    protected ResponseEntity<Object> handleNoResultsFoundException(NoResultsFoundException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.NOT_FOUND);
        errorDetails.setMessage(ex.getMessage());
        errorDetails.setPath(request.getDescription(false));

        return buildErrorResponse(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        errorDetails.setMessage((String.format("%s : %s", ex.getClass().getSimpleName(), errorDetails.getMessage())));

        return buildErrorResponse(errorDetails);
    }
}
