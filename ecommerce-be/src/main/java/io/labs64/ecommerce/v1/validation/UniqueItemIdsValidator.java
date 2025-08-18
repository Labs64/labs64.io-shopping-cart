package io.labs64.ecommerce.v1.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.labs64.ecommerce.v1.dto.CartItemCreateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueItemIdsValidator implements ConstraintValidator<UniqueItemIds, List<CartItemCreateRequest>> {

    @Override
    public boolean isValid(List<CartItemCreateRequest> value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true;
        }

        Set<String> uniqueIds = value.stream().map(CartItemCreateRequest::getItemId).collect(Collectors.toSet());

        return uniqueIds.size() == value.size();
    }
}