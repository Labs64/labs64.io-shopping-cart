package io.labs64.cart.v1.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import io.labs64.cart.v1.model.Cart;

@Component
public class CartValidator {
    private final List<Validator<Cart>> validators;

    public CartValidator(List<Validator<Cart>> validators) {
        this.validators = validators;
    }

    public void validate(Cart cart) {
        validators.forEach(v -> v.validate(cart));
    }
}
