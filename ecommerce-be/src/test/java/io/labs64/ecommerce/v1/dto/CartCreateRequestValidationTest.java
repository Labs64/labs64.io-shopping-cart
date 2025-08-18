package io.labs64.ecommerce.v1.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import io.labs64.ecommerce.v1.validation.UniqueItemIds;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("CartCreateRequest validation")
class CartCreateRequestValidationTest {

    private static Validator validator;
    private static ValidatorFactory factory;

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void closeFactory() {
        factory.close();
    }

    @Test
    @DisplayName("fails when currency is null")
    void shouldFailWhenCurrencyIsNull() {
        CartCreateRequest request = new CartCreateRequest();
        request.setCurrency(null);

        Set<ConstraintViolation<CartCreateRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("currency")
                && v.getMessage().equals("{validation.currency.notnull}"));
    }

    @Test
    @DisplayName("fails when currency is invalid")
    void shouldFailWhenCurrencyIsInvalid() {
        CartCreateRequest request = new CartCreateRequest();
        request.setCurrency("some-currency");

        Set<ConstraintViolation<CartCreateRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("currency")
                && v.getMessage().equals("{validation.currency.invalid}"));
    }

    @Test
    @DisplayName("passes when currency is valid")
    void shouldPassWhenCurrencyIsValid() {
        CartCreateRequest request = new CartCreateRequest();
        request.setCurrency("USD");

        Set<ConstraintViolation<CartCreateRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("fails when items contain duplicate IDs")
    void shouldFailWhenItemsContainDuplicateIds() {
        CartCreateRequest request = new CartCreateRequest();
        request.setCurrency("USD");
        request.setItems(List.of(createItem("item-1", "title-1", 1, BigDecimal.valueOf(10)),
                createItem("item-1", "title-2", 2, BigDecimal.valueOf(20))));

        Set<ConstraintViolation<CartCreateRequest>> violations = validator.validate(request);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("items")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(UniqueItemIds.class));
    }

    @Test
    @DisplayName("passes when items are unique")
    void shouldPassWhenItemsAreUnique() {
        CartCreateRequest request = new CartCreateRequest();
        request.setCurrency("USD");
        request.setItems(List.of(createItem("item-1", "title-1", 1, BigDecimal.valueOf(10)),
                createItem("item-2", "title-2", 2, BigDecimal.valueOf(20))));

        Set<ConstraintViolation<CartCreateRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("validates meta field successfully")
    void shouldValidateMetaField() {
        CartCreateRequest request = new CartCreateRequest();
        request.setCurrency("USD");
        request.setMeta(Map.of("someKey", "someValue"));

        Set<ConstraintViolation<CartCreateRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    private CartItemCreateRequest createItem(String id, String title, int qty, BigDecimal price) {
        CartItemCreateRequest item = new CartItemCreateRequest();
        item.setItemId(id);
        item.setTitle(title);
        item.setQuantity(qty);
        item.setPrice(price);
        return item;
    }
}
