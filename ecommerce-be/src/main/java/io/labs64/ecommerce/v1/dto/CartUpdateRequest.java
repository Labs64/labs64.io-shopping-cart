package io.labs64.ecommerce.v1.dto;

import java.util.List;
import java.util.Map;

import io.labs64.ecommerce.v1.validation.UniqueItemIds;
import io.labs64.ecommerce.v1.validation.ValidCurrency;
import io.labs64.ecommerce.v1.validation.ValidMeta;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartUpdateRequest {
    private String userId;

    @ValidCurrency(ignoreCase = true)
    private String currency;

    @Valid
    @UniqueItemIds
    private List<CartItemCreateRequest> items;

    @ValidMeta
    private Map<String, Object> meta;
}
