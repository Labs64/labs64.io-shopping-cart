package io.labs64.cart.v1.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import io.labs64.cart.exception.ValidationException;
import io.labs64.cart.v1.model.Cart;

@Component
public class ExtraValidator implements Validator<Cart> {
    private final MessageSource messageSource;

    public ExtraValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void validate(Cart cart) {
        if (cart.getExtra() == null)
            return;

        cart.getExtra().forEach((key, value) -> {
            if (StringUtils.isBlank(key)) {
                throw new ValidationException("extra", getMessage("validation.extra.blankKey", null));
            }

            if (!(value instanceof String || value instanceof Number || value instanceof Boolean)) {
                throw new ValidationException("extra." + key,
                        getMessage("validation.extra.invalidValue", new Object[] { key }));
            }
        });
    }

    private String getMessage(String key, Object[] args) {
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }
}
