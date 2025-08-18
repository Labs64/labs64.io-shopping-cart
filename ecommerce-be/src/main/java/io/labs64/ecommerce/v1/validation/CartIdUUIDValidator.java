package io.labs64.ecommerce.v1.validation;

import java.util.UUID;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CartIdUUIDValidator implements ConstraintValidator<CartIdUUID, String> {

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        try {
            UUID.fromString(value);
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}