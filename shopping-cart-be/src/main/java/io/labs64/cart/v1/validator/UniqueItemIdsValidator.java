package io.labs64.cart.v1.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import io.labs64.cart.exception.ValidationException;
import io.labs64.cart.v1.model.Cart;
import io.labs64.cart.v1.model.CartItem;

@Component
public class UniqueItemIdsValidator implements Validator<Cart> {
    private final MessageSource messageSource;

    public UniqueItemIdsValidator(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void validate(Cart cart) {
        if (cart.getItems() == null)
            return;

        Map<String, Integer> seen = new HashMap<>();
        for (int i = 0; i < cart.getItems().size(); i++) {
            CartItem item = cart.getItems().get(i);
            String itemId = item.getItemId();
            if (itemId == null)
                continue;

            if (seen.containsKey(itemId)) {
                int firstIndex = seen.get(itemId);
                throw new ValidationException(String.format("items[%d].itemId", i), getMessage(itemId, firstIndex));
            }
            seen.put(itemId, i);
        }
    }

    private String getMessage(String itemId, int firstIndex) {
        return messageSource.getMessage("validation.items.duplicate", new Object[] { itemId, firstIndex },
                LocaleContextHolder.getLocale());
    }
}
