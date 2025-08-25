package io.labs64.cart.v1.validator;

import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import io.labs64.cart.config.CartProperties;
import io.labs64.cart.exception.ValidationException;
import io.labs64.cart.v1.model.Cart;

@Component
public class CurrencyValidator implements Validator<Cart> {
    private final MessageSource messageSource;
    private final CartProperties properties;

    public CurrencyValidator(MessageSource messageSource, CartProperties properties) {
        this.messageSource = messageSource;
        this.properties = properties;
    }

    @Override
    public void validate(Cart cart) {
        String currency = cart.getCurrency();
        List<String> allowedCurrencies = properties.getCurrency();

        if (currency != null && allowedCurrencies.stream().noneMatch(c -> c.equalsIgnoreCase(currency))) {
            throw new ValidationException("currency", getMessage(allowedCurrencies));
        }
    }

    private String getMessage(List<String> allowedCurrencies) {
        return messageSource.getMessage("validation.currency.invalid",
                new Object[] { String.join(", ", allowedCurrencies) }, LocaleContextHolder.getLocale());
    }
}
