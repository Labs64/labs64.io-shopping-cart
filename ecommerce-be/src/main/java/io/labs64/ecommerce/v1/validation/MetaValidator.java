package io.labs64.ecommerce.v1.validation;

import java.util.Map;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MetaValidator implements ConstraintValidator<ValidMeta, Map<String, Object>> {
    @Override
    public boolean isValid(Map<String, Object> value, ConstraintValidatorContext context) {
        if (value != null) {
            for (Map.Entry<String, Object> entry : value.entrySet()) {
                Object v = entry.getValue();
                if (!(v instanceof String || v instanceof Number || v instanceof Boolean)) {
                    return false;
                }
            }
        }

        return true;
    }
}
