package com.utp.api.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotDefaultStringValidator implements ConstraintValidator<NotDefaultString, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.equalsIgnoreCase("string");
    }
}
