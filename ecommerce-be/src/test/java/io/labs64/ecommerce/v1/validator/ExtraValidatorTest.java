package io.labs64.ecommerce.v1.validator;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import io.labs64.ecommerce.exception.ValidationException;
import io.labs64.ecommerce.v1.model.Cart;

@ExtendWith(MockitoExtension.class)
class ExtraValidatorTest {

    @Mock
    private MessageSource messageSource;

    private ExtraValidator validator;

    @BeforeEach
    void setUp() {
        validator = new ExtraValidator(messageSource);
    }

    @Test
    void shouldPassWhenExtraIsNull() {
        Cart cart = new Cart();
        cart.setExtra(null);

        validator.validate(cart); // не кидає помилку
    }

    @Test
    void shouldThrowWhenKeyIsBlank() {
        Cart cart = new Cart();
        Map<String, Object> extra = new HashMap<>();
        extra.put(" ", "value");
        cart.setExtra(extra);

        when(messageSource.getMessage("validation.extra.blankKey", null, LocaleContextHolder.getLocale()))
                .thenReturn("Extra key cannot be blank");

        assertThatThrownBy(() -> validator.validate(cart)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Extra key cannot be blank");
    }

    @Test
    void shouldThrowWhenValueIsInvalid() {
        Cart cart = new Cart();
        Map<String, Object> extra = new HashMap<>();
        extra.put("someKey", new Object()); // недопустиме значення
        cart.setExtra(extra);

        when(messageSource.getMessage("validation.extra.invalidValue", new Object[] { "someKey" },
                LocaleContextHolder.getLocale())).thenReturn("Invalid extra value for key someKey");

        assertThatThrownBy(() -> validator.validate(cart)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Invalid extra value for key someKey");
    }

    @Test
    void shouldPassForValidExtra() {
        Cart cart = new Cart();
        Map<String, Object> extra = new HashMap<>();
        extra.put("key1", "string");
        extra.put("key2", 123);
        extra.put("key3", true);
        cart.setExtra(extra);

        validator.validate(cart);
    }
}
