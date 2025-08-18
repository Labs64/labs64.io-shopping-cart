package io.labs64.ecommerce.v1.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private String itemId;
    private String title;
    private String description;
    private int quantity;
    private BigDecimal price;
    private Map<String, Object> meta = new HashMap<>();
}