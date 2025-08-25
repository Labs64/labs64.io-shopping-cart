package io.labs64.cart.v1.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import io.labs64.cart.config.CartProperties;
import io.labs64.cart.v1.model.Cart;
import io.labs64.cart.v1.validator.CartValidator;

@ExtendWith(MockitoExtension.class)
@DisplayName("CartService unit tests with MockitoExtension")
class CartServiceTest {

    @Mock
    private RedisTemplate<String, Cart> redisTemplate;

    @Mock
    private ValueOperations<String, Cart> valueOperations;

    @Mock
    private CartValidator cartValidator; // замоканий валідатор

    private CartService cartService;
    private CartProperties properties;

    @BeforeEach
    void setUp() {
        properties = new CartProperties();
        properties.setPrefix("test:cart:");
        properties.setTtl(Duration.ofHours(1));
        properties.setCurrency(List.of("USD", "EUR"));

        cartService = new CartService(redisTemplate, properties, cartValidator);
    }

    @Test
    @DisplayName("createCart assigns id, createdAt and stores in redis")
    void shouldCreateCart() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        Cart cart = new Cart();
        Cart created = cartService.createCart(cart);

        assertThat(created.getCartId()).isNotNull();
        assertThat(created.getCreatedAt()).isNotNull();
        assertThat(created.getUpdatedAt()).isNotNull();
        assertThat(created.getExpiresAt()).isAfter(created.getUpdatedAt());

        verify(cartValidator).validate(cart);
        verify(valueOperations).set(eq("test:cart:" + created.getCartId()), eq(created), eq(properties.getTtl()));
    }

    @Test
    @DisplayName("getCart returns empty if redis has no entry")
    void shouldReturnEmptyWhenCartNotFound() {
        UUID cartId = UUID.randomUUID();

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("test:cart:" + cartId)).thenReturn(null);

        Optional<Cart> result = cartService.getCart(cartId);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("getCart returns cart if exists in redis")
    void shouldReturnCartWhenFound() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setCartId(cartId);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("test:cart:" + cartId)).thenReturn(cart);

        Optional<Cart> result = cartService.getCart(cartId);
        assertThat(result).contains(cart);
    }

    @Test
    @DisplayName("updateCart applies updater and saves cart")
    void shouldUpdateCart() {
        UUID cartId = UUID.randomUUID();
        Cart cart = new Cart();
        cart.setCartId(cartId);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get("test:cart:" + cartId)).thenReturn(cart);

        Optional<Cart> updated = cartService.updateCart(cartId, c -> c.setUserId("user-123"));

        assertThat(updated).isPresent();
        assertThat(updated.get().getUserId()).isEqualTo("user-123");

        verify(cartValidator).validate(cart);
        verify(valueOperations).set(eq("test:cart:" + cartId), eq(cart), eq(properties.getTtl()));
    }

    @Test
    @DisplayName("deleteCart removes cart from redis")
    void shouldDeleteCart() {
        UUID cartId = UUID.randomUUID();

        cartService.deleteCart(cartId);

        verify(redisTemplate).delete("test:cart:" + cartId);
    }

    @Test
    @DisplayName("getCartTTL returns duration if expire set in redis")
    void shouldReturnCartTTL() {
        UUID cartId = UUID.randomUUID();
        when(redisTemplate.getExpire("test:cart:" + cartId)).thenReturn(3600L);

        Optional<Duration> ttl = cartService.getCartTTL(cartId);
        assertThat(ttl).contains(Duration.ofHours(1));
    }

    @Test
    @DisplayName("getCartTTL returns empty if expire is null")
    void shouldReturnEmptyWhenExpireNull() {
        UUID cartId = UUID.randomUUID();
        when(redisTemplate.getExpire("test:cart:" + cartId)).thenReturn(-2L);

        Optional<Duration> ttl = cartService.getCartTTL(cartId);
        assertThat(ttl).isEmpty();
    }
}
