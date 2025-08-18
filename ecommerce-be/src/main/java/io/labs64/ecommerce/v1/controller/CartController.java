package io.labs64.ecommerce.v1.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import io.labs64.ecommerce.exception.NotFoundException;
import io.labs64.ecommerce.v1.dto.CartCreateRequest;
import io.labs64.ecommerce.v1.dto.CartItemCreateRequest;
import io.labs64.ecommerce.v1.dto.CartItemUpdateRequest;
import io.labs64.ecommerce.v1.dto.CartResponse;
import io.labs64.ecommerce.v1.dto.CartUpdateRequest;
import io.labs64.ecommerce.v1.entity.Cart;
import io.labs64.ecommerce.v1.entity.CartItem;
import io.labs64.ecommerce.v1.mapper.CartMapper;
import io.labs64.ecommerce.v1.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;
    private final CartMapper cartMapper;

    public CartController(final CartService cartService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.cartMapper = cartMapper;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponse> createCart(@Valid @RequestBody final CartCreateRequest request) {
        final Cart cart = cartMapper.toCart(request);
        final CartResponse cartResponse = cartMapper.toResponse(cartService.createCart(cart));

        return ResponseEntity.status(HttpStatus.CREATED).body(cartResponse);
    }

    @GetMapping(path = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponse> getCart(@PathVariable("cartId") final UUID cartId) {
        final Cart cart = cartService.getCart(cartId)
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        final CartResponse cartResponse = cartMapper.toResponse(cart);

        return ResponseEntity.ok(cartResponse);
    }

    @PatchMapping(path = "/{cartId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartResponse> updateCart(@PathVariable("cartId") final UUID cartId,
            @RequestBody final CartUpdateRequest request) {
        final Optional<Cart> updated = cartService.updateCart(cartId, cart -> cartMapper.updateCart(request, cart));

        final CartResponse cartResponse = updated.map(cartMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.ok(cartResponse);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable("cartId") final UUID cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{cartId}/item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItem> addCartItem(@PathVariable("cartId") final UUID cartId,
            @Valid @RequestBody final CartItemCreateRequest request) {
        CartItem cartItem = cartMapper.toCartItem(request);

        cartService.updateCart(cartId, cart -> cart.getItems().add(cartItem))
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.ok(cartItem);
    }

    @PatchMapping(path = "/{cartId}/item/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartItem> updateCartItem(@PathVariable("cartId") final UUID cartId,
            @PathVariable("itemId") final String itemId, @Valid @RequestBody final CartItemUpdateRequest request) {

        CartItem updatedItem = cartService.updateCart(cartId, cart -> {
            CartItem item = cart.getItems().stream().filter(i -> itemId.equals(i.getItemId())).findFirst().orElseThrow(
                    () -> new NotFoundException("Item with ID '" + itemId + "' not found in cart '" + cartId + "'."));

            cartMapper.updateCartItem(request, item);
        }).flatMap(cart -> cart.getItems().stream().filter(i -> itemId.equals(i.getItemId())).findFirst())
                .orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{cartId}/item/{itemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable("cartId") final UUID cartId,
            @PathVariable("itemId") final String itemId) {
        cartService.updateCart(cartId, cart -> {
            CartItem item = cart.getItems().stream().filter(i -> itemId.equals(i.getItemId())).findFirst().orElseThrow(
                    () -> new NotFoundException("Item with ID '" + itemId + "' not found in cart '" + cartId + "'."));
            cart.getItems().remove(item);
        }).orElseThrow(() -> new NotFoundException("Shopping cart with ID '" + cartId + "' not found."));

        return ResponseEntity.noContent().build();
    }
}
