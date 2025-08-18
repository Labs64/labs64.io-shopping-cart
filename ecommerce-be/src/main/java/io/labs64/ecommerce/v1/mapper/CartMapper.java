package io.labs64.ecommerce.v1.mapper;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import io.labs64.ecommerce.v1.dto.CartCreateRequest;
import io.labs64.ecommerce.v1.dto.CartItemCreateRequest;
import io.labs64.ecommerce.v1.dto.CartItemUpdateRequest;
import io.labs64.ecommerce.v1.dto.CartResponse;
import io.labs64.ecommerce.v1.dto.CartUpdateRequest;
import io.labs64.ecommerce.v1.entity.Cart;
import io.labs64.ecommerce.v1.entity.CartItem;
import io.labs64.ecommerce.v1.entity.Currency;

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
    @Mapping(target = "currency", source = "currency", qualifiedByName = "stringToCurrency")
    Cart toCart(CartCreateRequest request);

    /**
     * Converts a {@link Cart} entity into a {@link CartResponse} DTO for API responses.
     * <p>
     * This method copies all relevant fields from the {@code Cart} object to a new {@code CartResponse} instance, and
     * also calculates derived fields such as total number of items and total amount in the cart.
     * </p>
     *
     * @param cart
     *            the cart entity to convert into a DTO. If {@code null}, {@code null} is returned.
     * @return a new {@code CartResponse} object containing data from the given {@code cart}.
     */
    CartResponse toResponse(Cart cart);

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
    @Mapping(target = "currency", source = "currency", qualifiedByName = "stringToCurrency")
    void updateCart(CartUpdateRequest request, @MappingTarget Cart cart);

    /**
     * Converts a CartItemCreateRequest DTO to a CartItem entity.
     *
     * @param request
     *            DTO containing cart item data
     * @return CartItem entity
     */
    CartItem toCartItem(CartItemCreateRequest request);

    /**
     * Updates an existing CartItem entity from a CartItemUpdateRequest DTO. Null values in the request are ignored.
     *
     * @param request
     *            DTO containing cart update data
     * @param cartItem
     *            the CartItem entity to update
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCartItem(CartItemUpdateRequest request, @MappingTarget CartItem cartItem);

    /**
     * Converts a list of CartItemCreateRequest DTOs to a list of CartItem entities.
     *
     * @param request
     *            list of cart item DTOs
     * @return list of CartItem entities
     */
    List<CartItem> toCartItemList(List<CartItemCreateRequest> request);

    /**
     * Converts a String to a Currency enum.
     *
     * @param currency
     *            string representation of the currency
     * @return Currency enum
     */
    @Named("stringToCurrency")
    default Currency stringToCurrency(String currency) {
        return Currency.fromString(currency);
    }
}