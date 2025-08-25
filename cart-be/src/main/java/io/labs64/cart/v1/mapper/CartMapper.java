package io.labs64.cart.v1.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import io.labs64.cart.v1.model.Cart;
import io.labs64.cart.v1.model.CartItem;
import io.labs64.cart.v1.model.CreateCartItemRequest;
import io.labs64.cart.v1.model.CreateCartRequest;
import io.labs64.cart.v1.model.UpdateCartItemRequest;
import io.labs64.cart.v1.model.UpdateCartRequest;

@Mapper(componentModel = "spring")
public interface CartMapper {
    /**
     * Converts a CartCreateRequest DTO to a Cart entity. Ignores auto-generated fields like cartId, timestamps, totals.
     *
     * @param request
     *            DTO containing cart creation data
     * @return Cart entity
     */
    @Mapping(target = "cartId", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Cart toCart(CreateCartRequest request);

    /**
     * Updates an existing Cart entity from a CartUpdateRequest DTO. Null values in the request are ignored.
     *
     * @param request
     *            DTO containing cart update data
     * @param cart
     *            the Cart entity to update
     */
    @InheritConfiguration(name = "toCart")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCart(UpdateCartRequest request, @MappingTarget Cart cart);

    /**
     * Converts a CartItemCreateRequest DTO to a CartItem entity.
     *
     * @param request
     *            DTO containing cart item data
     * @return CartItem entity
     */
    CartItem toCartItem(CreateCartItemRequest request);

    /**
     * Updates an existing CartItem entity from a CartItemUpdateRequest DTO. Null values in the request are ignored.
     *
     * @param request
     *            DTO containing cart update data
     * @param cartItem
     *            the CartItem entity to update
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCartItem(UpdateCartItemRequest request, @MappingTarget CartItem cartItem);

    /**
     * Converts a list of CartItemCreateRequest DTOs to a list of CartItem entities.
     *
     * @param request
     *            list of cart item DTOs
     * @return list of CartItem entities
     */
    List<CartItem> toCartItemList(List<CreateCartItemRequest> request);

    @AfterMapping
    default void setDefaults(@MappingTarget Cart cart) {
        if (cart.getItems() == null) {
            cart.setItems(new ArrayList<>());
        }
        if (cart.getExtra() == null) {
            cart.setExtra(new HashMap<>());
        }
    }

    @AfterMapping
    default void setDefaults(@MappingTarget CartItem cartItem) {
        if (cartItem.getExtra() == null) {
            cartItem.setExtra(new HashMap<>());
        }
    }
}