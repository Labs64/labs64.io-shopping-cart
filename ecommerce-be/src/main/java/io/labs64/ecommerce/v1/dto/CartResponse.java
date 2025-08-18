package io.labs64.ecommerce.v1.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.labs64.ecommerce.v1.entity.CartItem;
import io.labs64.ecommerce.v1.entity.Currency;
import lombok.Data;

@Data
public class CartResponse {
    private UUID cartId;
    private String userId;
    private Currency currency;
    private OffsetDateTime expiresAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<CartItem> items;
    private Map<String, Object> meta;
    private int totalItems;
    private BigDecimal totalAmount;

    public List<CartItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }

    public Map<String, Object> getMeta() {
        if (meta == null) {
            meta = new HashMap<>();
        }

        return meta;
    }

    public int getTotalItems() {
        return getItems().stream().map(CartItem::getQuantity).reduce(0, Integer::sum);
    }

    public BigDecimal getTotalAmount() {
        return getItems().stream().map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}