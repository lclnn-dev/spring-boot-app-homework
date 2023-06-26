package com.example.demowithtests.util.annotation.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryRightFormedValidator implements ConstraintValidator<CountryRightFormed, String> {

    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        return country != null && country.length() == 2 && country.equals(country.toUpperCase());
    }
}