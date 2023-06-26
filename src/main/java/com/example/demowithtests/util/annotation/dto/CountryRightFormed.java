package com.example.demowithtests.util.annotation.dto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CountryRightFormedValidator.class)
public @interface CountryRightFormed {
    String message() default "Country must be a 2 characters length and uppercase. E.g. UK, CZ, UA";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}