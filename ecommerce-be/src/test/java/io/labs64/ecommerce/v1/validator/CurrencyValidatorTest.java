package io.labs64.ecommerce.v1.validator;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import io.labs64.ecommerce.config.CartProperties;
import io.labs64.ecommerce.exception.ValidationException;
import io.labs64.ecommerce.v1.model.Cart;

@ExtendWith(MockitoExtension.class)
class CurrencyValidatorTest {

    @Mock
    private MessageSource messageSource;

    private CurrencyValidator validator;

    @BeforeEach
    void setUp() {
        messageSource = Mockito.mock(MessageSource.class);
        CartProperties properties = new CartProperties();
        properties.setCurrency(List.of("USD", "EUR"));

        validator = new CurrencyValidator(messageSource, properties);
    }

    @Test
    void shouldPassForValidCurrency() {
        Cart cart = new Cart();
        cart.setCurrency("USD");

        validator.validate(cart);
    }

    @Test
    void shouldThrowForInvalidCurrency() {
        when(messageSource.getMessage("validation.currency.invalid", new Object[] { "USD, EUR" },
                LocaleContextHolder.getLocale())).thenReturn("Currency is invalid");

        Cart cart = new Cart();
        cart.setCurrency("JPY");

        assertThatThrownBy(() -> validator.validate(cart)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Currency is invalid");
    }
}
