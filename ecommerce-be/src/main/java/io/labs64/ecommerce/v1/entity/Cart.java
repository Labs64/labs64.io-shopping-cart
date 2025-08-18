package io.labs64.ecommerce.v1.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Cart {
    private UUID cartId;
    private String userId;
    private Currency currency;
    private OffsetDateTime expiresAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private List<CartItem> items = new ArrayList<>();
    private Map<String, Object> meta = new HashMap<>();
}