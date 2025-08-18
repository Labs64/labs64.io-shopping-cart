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

@DisplayName("CartItemUpdateRequest validation")
class CartItemUpdateRequestValidationTest {

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
    @DisplayName("fails when title is blank but present")
    void shouldFailWhenTitleIsBlank() {
        CartItemUpdateRequest item = makeValidItem();
        item.setTitle("   "); // blank but not null

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("title")
                && v.getMessage().equals("{validation.title.notblank}"));
    }

    @Test
    @DisplayName("passes when title is null (optional field)")
    void shouldPassWhenTitleIsNull() {
        CartItemUpdateRequest item = makeValidItem();
        item.setTitle(null);

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("fails when quantity < 1")
    void shouldFailWhenQuantityIsLessThanOne() {
        CartItemUpdateRequest item = makeValidItem();
        item.setQuantity(0);

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("quantity")
                && v.getMessage().equals("{validation.quantity.min}"));
    }

    @Test
    @DisplayName("passes when quantity is null (optional field)")
    void shouldPassWhenQuantityIsNull() {
        CartItemUpdateRequest item = makeValidItem();
        item.setQuantity(null);

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("fails when price < 0.0")
    void shouldFailWhenPriceIsNegative() {
        CartItemUpdateRequest item = makeValidItem();
        item.setPrice(BigDecimal.valueOf(-1.0));

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(
                v -> v.getPropertyPath().toString().equals("price") && v.getMessage().equals("{validation.price.min}"));
    }

    @Test
    @DisplayName("passes when price is null (optional field)")
    void shouldPassWhenPriceIsNull() {
        CartItemUpdateRequest item = makeValidItem();
        item.setPrice(null);

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("passes when all fields are valid")
    void shouldPassWhenAllFieldsValid() {
        CartItemUpdateRequest item = makeValidItem();

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("validates meta field successfully")
    void shouldValidateMetaField() {
        CartItemUpdateRequest item = makeValidItem();
        item.setMeta(Map.of("extra", "value"));

        Set<ConstraintViolation<CartItemUpdateRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    private CartItemUpdateRequest makeValidItem() {
        CartItemUpdateRequest item = new CartItemUpdateRequest();
        item.setItemId("item-123");
        item.setTitle("Sample title");
        item.setDescription("Description text");
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(19.99));
        return item;
    }
}
