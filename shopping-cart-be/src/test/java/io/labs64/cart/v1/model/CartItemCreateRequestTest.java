package io.labs64.cart.v1.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import io.labs64.cart.v1.model.CreateCartItemRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@DisplayName("CartItemCreateRequest validation")
class CreateCartItemRequestTest {

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
        CreateCartItemRequest item = createItem();
        item.setTitle(null);

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("title")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class));
    }

    @Test
    @DisplayName("fails when title is blank")
    void shouldFailWhenTitleIsBlank() {
        CreateCartItemRequest item = createItem();
        item.setTitle("");

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("title")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(Size.class));
    }

    @Test
    @DisplayName("fails when quantity is null")
    void shouldFailWhenQuantityIsNull() {
        CreateCartItemRequest item = createItem();
        item.setQuantity(null);

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("quantity")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class));
    }

    @Test
    @DisplayName("fails when quantity < 1")
    void shouldFailWhenQuantityIsLessThanOne() {
        CreateCartItemRequest item = createItem();
        item.setQuantity(0);

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("quantity")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(Min.class));
    }

    @Test
    @DisplayName("fails when price is null")
    void shouldFailWhenPriceIsNull() {
        CreateCartItemRequest item = createItem();
        item.setPrice(null);

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("price")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(NotNull.class));
    }

    @Test
    @DisplayName("fails when price < 0.0")
    void shouldFailWhenPriceIsNegative() {
        CreateCartItemRequest item = createItem();
        item.setPrice(BigDecimal.valueOf(-5.0));

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("price")
                && v.getConstraintDescriptor().getAnnotation().annotationType().equals(DecimalMin.class));
    }

    @Test
    @DisplayName("passes when all fields are valid")
    void shouldPassWhenAllFieldsValid() {
        CreateCartItemRequest item = createItem();

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("validates meta field successfully")
    void shouldValidateMetaField() {
        CreateCartItemRequest item = createItem();
        item.setExtra(Map.of("extra", "value"));

        Set<ConstraintViolation<CreateCartItemRequest>> violations = validator.validate(item);

        assertThat(violations).isEmpty();
    }

    private CreateCartItemRequest createItem() {
        CreateCartItemRequest item = new CreateCartItemRequest();
        item.setItemId("item-123");
        item.setTitle("Sample item");
        item.setDescription("Some description");
        item.setQuantity(2);
        item.setPrice(BigDecimal.valueOf(9.99));
        return item;
    }
}
