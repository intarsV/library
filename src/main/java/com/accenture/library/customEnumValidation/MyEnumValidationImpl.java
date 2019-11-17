package com.accenture.library.customEnumValidation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MyEnumValidationImpl implements ConstraintValidator<MyEnumValidation, String> {

    private List<String> valueList;

    @Override
    public void initialize(MyEnumValidation constraintAnnotation) {
        valueList = new ArrayList<>();
        valueList = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(e -> e.toString()).collect(toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return valueList.contains(value.toUpperCase());
    }
}
