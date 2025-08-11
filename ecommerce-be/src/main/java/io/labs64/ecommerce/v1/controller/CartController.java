package io.labs64.ecommerce.v1.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.labs64.ecommerce.exception.ErrorCode;
import io.labs64.ecommerce.exception.NotFoundException;
import io.labs64.ecommerce.messaging.CartPublisherService;
import io.labs64.ecommerce.v1.api.CartApi;
import io.labs64.ecommerce.v1.model.Cart;
import io.labs64.ecommerce.v1.model.ErrorResponse;
import io.labs64.ecommerce.v1.service.CartService;

@RestController
@RequestMapping("/api/v1")
public class CartController implements CartApi {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public ResponseEntity<Cart> getCartById(final UUID cartId) {
        final Cart cart = cartService.getCart(cartId).orElseThrow(() -> new NotFoundException(ErrorCode.CART_NOT_FOUND,
                "Shopping cart with ID '" + cartId + "' not found."));
        return ResponseEntity.ok(cart);
    }

    @Override
    public ResponseEntity<Cart> saveCart(Cart cart) {
        cartService.saveCart(cart.getCartId(), cart);
        return ResponseEntity.status(HttpStatus.CREATED).body(cart);
    }
}
