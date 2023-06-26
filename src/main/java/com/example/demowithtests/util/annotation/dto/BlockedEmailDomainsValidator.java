package com.example.demowithtests.util.annotation.dto;

import com.example.demowithtests.util.config.annotation.DefaultBlockedEmailDomainsConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockedEmailDomainsValidator implements ConstraintValidator<BlockedEmailDomains, String> {

    private List<String> domains;

    @Override
    public void initialize(BlockedEmailDomains constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        domains = new ArrayList<>(Arrays.asList(constraintAnnotation.contains()));
        boolean appendDefault = constraintAnnotation.appendDefault();

        if (appendDefault) {
            List<String> defaultDomains = DefaultBlockedEmailDomainsConfig.getDefaultDomains();
            domains.addAll(defaultDomains);
        }
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return true;
        }

        String domain = email.substring(email.lastIndexOf('.') + 1);
        String[] parts = email.split("@");
        String provider = parts[1].substring(0, parts[1].lastIndexOf('.'));

        return !domains.contains(domain) && !domains.contains(provider);
    }
}
