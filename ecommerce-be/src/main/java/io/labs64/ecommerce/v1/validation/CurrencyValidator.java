package io.labs64.ecommerce.v1.validation;

import java.util.Arrays;

import io.labs64.ecommerce.v1.entity.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {
    private boolean ignoreCase;

    @Override
    public void initialize(ValidCurrency constraintAnnotation) {
        this.ignoreCase = constraintAnnotation.ignoreCase();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.stream(Currency.values())
                .anyMatch(c -> ignoreCase ? c.name().equalsIgnoreCase(value) : c.name().equals(value));
    }
}
