package io.labs64.ecommerce.v1.validator;

public interface Validator<T> {
    void validate(T entity);
}