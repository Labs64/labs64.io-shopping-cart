package io.labs64.ecommerce.v1.service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.labs64.ecommerce.v1.model.Cart;

@Service
public class CartService {
    private final RedisTemplate<String, Cart> redisTemplate;
    private static final String CART_PREFIX = "ecommerce:cart:";

    @Value("${cart.ttl-hours:6}")
    private long cartTtlHours;

    public CartService(RedisTemplate<String, Cart> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Optional<Cart> getCart(final UUID cartId) {
        Cart cart = redisTemplate.opsForValue().get(getCartKey(cartId));
        return Optional.ofNullable(cart);
    }

    public void saveCart(final UUID cartId, final Cart cart) {
        redisTemplate.opsForValue().set(getCartKey(cartId), cart, Duration.ofHours(cartTtlHours));
    }

    public void deleteCart(final UUID cartId) {
        redisTemplate.delete(CART_PREFIX + cartId);
    }

    public Optional<Duration> getCartTTL(final UUID cartId) {
        final Long expire = redisTemplate.getExpire(getCartKey(cartId));
        return Optional.ofNullable(Duration.ofSeconds(expire));
    }

    private String getCartKey(final UUID cartId) {
        return CART_PREFIX + cartId.toString();
    }
}