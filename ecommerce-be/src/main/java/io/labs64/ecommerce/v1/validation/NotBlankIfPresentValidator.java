package io.labs64.ecommerce.v1.validation;

import org.apache.commons.lang3.StringUtils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotBlankIfPresentValidator implements ConstraintValidator<NotBlankIfPresent, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || StringUtils.isNotBlank(value);
    }
}
