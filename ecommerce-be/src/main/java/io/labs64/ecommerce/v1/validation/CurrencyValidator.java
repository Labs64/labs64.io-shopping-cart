package io.labs64.ecommerce.v1.validation;

import java.util.Arrays;

import io.labs64.ecommerce.v1.model.Currency;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<ValidCurrency, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return Arrays.stream(Currency.values()).anyMatch(c -> c.name().equalsIgnoreCase(value));
    }
}
