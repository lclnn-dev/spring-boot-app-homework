package com.example.demowithtests.util.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<Object> buildErrorResponse(ErrorDetails errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex);

        errorDetails.setMessage("Malformed JSON request");
        errorDetails.setPath(request.getDescription(false));
        errorDetails.setDetails(String.format("{%s} to {%s}. %s",
                servletWebRequest.getHttpMethod(),
                servletWebRequest.getRequest().getServletPath(),
                ex.getMessage()));

        return buildErrorResponse(errorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST, ex);
        errorDetails.setPath(request.getDescription(false));
        errorDetails.setMessage(ex.getParameterName() + " parameter is missing");

        return buildErrorResponse(errorDetails);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST);
        errorDetails.setPath(request.getDescription(false));
        errorDetails.setMessage("Validation error. Check 'details' field.");

        List<String> infoValidErrors = extractInfoValidErrors(ex.getBindingResult().getFieldErrors());
        errorDetails.setDetails(infoValidErrors.toString());

        return buildErrorResponse(errorDetails);
    }

    private List<String> extractInfoValidErrors(List<FieldError> fieldErrors) {
        List<String> infoValidErrors = new ArrayList<>();

        for (FieldError fieldError : fieldErrors) {
            infoValidErrors.add(String.format("Object name = {%s}, field = {%S}, reject value = {%s}, message = {%s}",
                    fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage()));
        }

        return infoValidErrors;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(HttpStatus.BAD_REQUEST);
        errorDetails.setPath(request.getDescription(false));
        errorDetails.setMessage("Invalid arguments: " + ex.getMessage());

        return buildErrorResponse(errorDetails);
    }
}
