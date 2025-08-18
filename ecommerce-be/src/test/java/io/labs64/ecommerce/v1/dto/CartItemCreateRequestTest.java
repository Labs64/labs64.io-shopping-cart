package io.labs64.ecommerce.v1.dto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@DisplayName("CartItemCreateRequest validation")
class CartItemCreateRequestValidationTest {

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
    @DisplayName("fails when title is null")
    void shouldFailWhenTitleIsNull() {
        CartItemCreateRequest item = new CartItemCreateRequest();
        item.setTitle(null);

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("title")
                && v.getMessage().equals("{validation.title.notblank}"));
    }

    @Test
    @DisplayName("fails when title is blank")
    void shouldFailWhenTitleIsBlank() {
        CartItemCreateRequest item = new CartItemCreateRequest();
        item.setTitle("   ");

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("title")
                && v.getMessage().equals("{validation.title.notblank}"));
    }

    @Test
    @DisplayName("fails when quantity is null")
    void shouldFailWhenQuantityIsNull() {
        CartItemCreateRequest item = makeValidItem();
        item.setQuantity(null);

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("quantity")
                && v.getMessage().equals("{validation.quantity.notnull}"));
    }

    @Test
    @DisplayName("fails when quantity < 1")
    void shouldFailWhenQuantityIsLessThanOne() {
        CartItemCreateRequest item = makeValidItem();
        item.setQuantity(0);

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("quantity")
                && v.getMessage().equals("{validation.quantity.min}"));
    }

    @Test
    @DisplayName("fails when price is null")
    void shouldFailWhenPriceIsNull() {
        CartItemCreateRequest item = makeValidItem();
        item.setPrice(null);

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("price")
                && v.getMessage().equals("{validation.price.notnull}"));
    }

    @Test
    @DisplayName("fails when price < 0.0")
    void shouldFailWhenPriceIsNegative() {
        CartItemCreateRequest item = makeValidItem();
        item.setPrice(BigDecimal.valueOf(-5.0));

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(
                v -> v.getPropertyPath().toString().equals("price") && v.getMessage().equals("{validation.price.min}"));
    }

    @Test
    @DisplayName("passes when all fields are valid")
    void shouldPassWhenAllFieldsValid() {
        CartItemCreateRequest item = makeValidItem();

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("validates meta field successfully")
    void shouldValidateMetaField() {
        CartItemCreateRequest item = makeValidItem();
        item.setMeta(Map.of("extra", "value"));

        Set<ConstraintViolation<CartItemCreateRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    private CartItemCreateRequest makeValidItem() {
        CartItemCreateRequest item = new CartItemCreateRequest();
        item.setItemId("item-123");
        item.setTitle("Sample item");
        item.setDescription("Some description");
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(9.99));
        return item;
    }
}
