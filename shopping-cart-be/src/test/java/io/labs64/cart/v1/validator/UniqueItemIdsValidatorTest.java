package io.labs64.cart.v1.validator;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.labs64.cart.exception.ValidationException;
import io.labs64.cart.v1.model.Cart;
import io.labs64.cart.v1.model.CartItem;

@ExtendWith(MockitoExtension.class)
class UniqueItemIdsValidatorTest {

    @Mock
    private MessageSource messageSource;

    private UniqueItemIdsValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UniqueItemIdsValidator(messageSource);
    }

    @Test
    void shouldPassForUniqueItemIds() {
        Cart cart = new Cart();
        CartItem item1 = new CartItem();
        item1.setItemId("1");
        CartItem item2 = new CartItem();
        item2.setItemId("2");
        cart.setItems(List.of(item1, item2));

        validator.validate(cart);
    }

    @Test
    void shouldThrowForDuplicateItemIds() {
        when(messageSource.getMessage(anyString(), ArgumentMatchers.<Object[]> any(), any()))
                .thenReturn("Duplicate itemId '1'");

        Cart cart = new Cart();
        CartItem item1 = new CartItem();
        item1.setItemId("1");
        CartItem item2 = new CartItem();
        item2.setItemId("1");
        cart.setItems(List.of(item1, item2));

        assertThatThrownBy(() -> validator.validate(cart)).isInstanceOf(ValidationException.class)
                .hasMessageContaining("Duplicate itemId '1'");
    }
}
