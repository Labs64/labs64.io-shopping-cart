package io.labs64.ecommerce.v1.validation;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueItemIdsValidator implements ConstraintValidator<UniqueItemIds, List<?>> {

    @Override
    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        Set<String> uniqueIds = value.stream().map(item -> {
            try {
                Method getId = item.getClass().getMethod("getItemId");
                Object id = getId.invoke(item);
                return id != null ? id.toString() : null;
            } catch (Exception e) {
                throw new RuntimeException("Cannot read itemId from " + item.getClass(), e);
            }
        }).collect(Collectors.toSet());

        return uniqueIds.size() == value.size();
    }
}