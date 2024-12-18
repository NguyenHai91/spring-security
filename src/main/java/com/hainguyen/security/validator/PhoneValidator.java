package com.hainguyen.security.validator;

import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private int min;
    String regex = "^[0-9]{10}$";

    @Override
    public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
        if (Objects.isNull(arg0)) return true;
        if (arg0.length() < min) return false;
        return arg0.matches(regex);
    }

    @Override
    public void initialize(Phone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min;
    }
    
}
