package io.labs64.ecommerce.v1.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import io.labs64.ecommerce.exception.NotFoundException;
import io.labs64.ecommerce.v1.api.CartApi;
import io.labs64.ecommerce.v1.api.CartItemsApi;
import io.labs64.ecommerce.v1.mapper.CartMapper;
import io.labs64.ecommerce.v1.model.Cart;
import io.labs64.ecommerce.v1.model.CartItem;
import io.labs64.ecommerce.v1.model.CreateCartItemRequest;
import io.labs64.ecommerce.v1.model.CreateCartRequest;
import io.labs64.ecommerce.v1.model.UpdateCartItemRequest;
import io.labs64.ecommerce.v1.model.UpdateCartRequest;
import io.labs64.ecommerce.v1.service.CartService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CartController implements CartApi, CartItemsApi {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(final CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return CartApi.super.getRequest();
    }

    @Override
    public ResponseEntity<Cart> createCart(@Valid @RequestBody final CreateCartRequest request) {
        final Cart cart = cartService.createCart(cartMapper.toCart(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }

    @Override
    public ResponseEntity<Cart> getCart(@PathVariable("cartId") UUID cartId) {
        final Cart cart = cartService.getCart(cartId)
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));
        return ResponseEntity.ok(cart);
    }

    @Override
    public ResponseEntity<Cart> updateCart(@PathVariable("cartId") UUID cartId,
            @Valid @RequestBody UpdateCartRequest request) {
        final Cart updated = cartService.updateCart(cartId, cart -> cartMapper.updateCart(request, cart))
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.ok(updated);
    }

    @Override
    public ResponseEntity<Void> deleteCart(@PathVariable("cartId") UUID cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CartItem> createCartItem(@PathVariable("cartId") UUID cartId,
            @Valid @RequestBody CreateCartItemRequest request) {
        CartItem cartItem = cartMapper.toCartItem(request);

        cartService.updateCart(cartId, cart -> cart.getItems().add(cartItem))
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.ok(cartItem);
    }

    @Override
    public ResponseEntity<CartItem> updateCartItem(@PathVariable("cartId") UUID cartId,
            @PathVariable("itemId") String itemId, @Valid @RequestBody UpdateCartItemRequest request) {
        CartItem updatedItem = cartService.updateCart(cartId, cart -> {

            CartItem item = safeItems(cart).stream().filter(i -> itemId.equals(i.getItemId())).findFirst().orElseThrow(
                    () -> new NotFoundException("Item with ID '" + itemId + "' not found in cart '" + cartId + "'."));

            cartMapper.updateCartItem(request, item);
        }).flatMap(cart -> safeItems(cart).stream().filter(i -> itemId.equals(i.getItemId())).findFirst())
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.ok(updatedItem);
    }

    @Override
    public ResponseEntity<Void> deleteCartItem(@PathVariable("cartId") UUID cartId,
            @PathVariable("itemId") String itemId) {
        cartService.updateCart(cartId, cart -> {
            List<CartItem> cartItems = safeItems(cart);

            CartItem item = cartItems.stream().filter(i -> itemId.equals(i.getItemId())).findFirst().orElseThrow(
                    () -> new NotFoundException("Item with ID '" + itemId + "' not found in cart '" + cartId + "'."));

            cartItems.remove(item);
        }).orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.noContent().build();
    }

    private List<CartItem> safeItems(Cart cart) {
        return Optional.ofNullable(cart.getItems()).orElseGet(Collections::emptyList);
    }
}
