package io.labs64.cart.v1.validator;

public interface Validator<T> {
    void validate(T entity);
}