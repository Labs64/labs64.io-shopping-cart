package io.labs64.ecommerce.v1.service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import io.labs64.ecommerce.v1.entity.Cart;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CartService {

    private final RedisTemplate<String, Cart> redisTemplate;

    private final Duration cartTtl;
    private final String cartPrefix;

    public CartService(RedisTemplate<String, Cart> redisTemplate,
            @Value("${cart.prefix:ecommerce:cart:}") String cartPrefix, @Value("${cart.ttl:PT6H}") Duration cartTtl) {
        this.redisTemplate = redisTemplate;
        this.cartPrefix = cartPrefix;
        this.cartTtl = cartTtl;
    }

    public Optional<Cart> getCart(final UUID cartId) {
        Cart cart = redisTemplate.opsForValue().get(getCartKey(cartId));
        return Optional.ofNullable(cart);
    }

    public Cart createCart(final Cart cart) {
        OffsetDateTime now = OffsetDateTime.now();
        cart.setCartId(UUID.randomUUID());
        cart.setCreatedAt(now);

        saveCart(cart);

        log.debug("Created cart with id {}", cart.getCartId());
        return cart;
    }

    public Optional<Cart> updateCart(UUID cartId, Consumer<Cart> updater) {
        return getCart(cartId).map(cart -> {
            updater.accept(cart);
            saveCart(cart);
            log.debug("Updated cart with id {}", cartId);
            return cart;
        });
    }

    public void deleteCart(final UUID cartId) {
        redisTemplate.delete(getCartKey(cartId));
        log.debug("Deleted cart with id {}", cartId);
    }

    public Optional<Duration> getCartTTL(final UUID cartId) {
        long expire = redisTemplate.getExpire(getCartKey(cartId));
        return (expire > 0) ? Optional.ofNullable(Duration.ofSeconds(expire)) : Optional.empty();
    }

    private void saveCart(final Cart cart) {
        Objects.requireNonNull(cart, "Cart must not be null");

        if (cart.getItems() != null) {
            cart.getItems().forEach(i -> {
                if (StringUtils.isBlank(i.getItemId())) {
                    i.setItemId(UUID.randomUUID().toString());
                }
            });
        }

        OffsetDateTime now = OffsetDateTime.now();
        cart.setUpdatedAt(now);
        cart.setExpiresAt(now.plusSeconds(cartTtl.getSeconds()));

        redisTemplate.opsForValue().set(getCartKey(cart.getCartId()), cart, cartTtl);
    }

    private String getCartKey(final UUID cartId) {
        return cartPrefix + cartId;
    }
}