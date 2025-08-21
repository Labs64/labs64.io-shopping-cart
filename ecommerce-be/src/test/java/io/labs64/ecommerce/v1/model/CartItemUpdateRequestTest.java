package io.labs64.ecommerce.v1.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import io.labs64.ecommerce.v1.model.UpdateCartItemRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@DisplayName("CartItemUpdateRequest validation")
class UpdateCartItemRequestTest {
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
        UpdateCartItemRequest item = makeValidItem();
        item.setTitle("");

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("title")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class));
    }

    @Test
    @DisplayName("passes when title is null (optional field)")
    void shouldPassWhenTitleIsNull() {
        UpdateCartItemRequest item = makeValidItem();
        item.setTitle(null);

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("fails when quantity < 1")
    void shouldFailWhenQuantityIsLessThanOne() {
        UpdateCartItemRequest item = makeValidItem();
        item.setQuantity(0);

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("quantity")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(Min.class));
    }

    @Test
    @DisplayName("passes when quantity is null (optional field)")
    void shouldPassWhenQuantityIsNull() {
        UpdateCartItemRequest item = makeValidItem();
        item.setQuantity(null);

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("fails when price < 0.0")
    void shouldFailWhenPriceIsNegative() {
        UpdateCartItemRequest item = makeValidItem();
        item.setPrice(BigDecimal.valueOf(-1.0));

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("price")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(DecimalMin.class));
    }

    @Test
    @DisplayName("passes when price is null (optional field)")
    void shouldPassWhenPriceIsNull() {
        UpdateCartItemRequest item = makeValidItem();
        item.setPrice(null);

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("passes when all fields are valid")
    void shouldPassWhenAllFieldsValid() {
        UpdateCartItemRequest item = makeValidItem();

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("validates meta field successfully")
    void shouldValidateMetaField() {
        UpdateCartItemRequest item = makeValidItem();
        item.setExtra(Map.of("extra", "value"));

        Set<ConstraintViolation<UpdateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    private UpdateCartItemRequest makeValidItem() {
        UpdateCartItemRequest item = new UpdateCartItemRequest();
        item.setItemId("item-123");
        item.setTitle("Sample title");
        item.setDescription("Description text");
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(19.99));
        return item;
    }
}
