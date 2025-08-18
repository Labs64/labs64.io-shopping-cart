package io.labs64.ecommerce.v1.dto;

import java.util.List;
import java.util.Map;

import io.labs64.ecommerce.v1.validation.UniqueItemIds;
import io.labs64.ecommerce.v1.validation.ValidCurrency;
import io.labs64.ecommerce.v1.validation.ValidMeta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartCreateRequest {
    private String userId;

    @NotNull(message = "{validation.currency.notnull}")
    @ValidCurrency(ignoreCase = true)
    private String currency;

    @Valid
    @Size(message = "{validation.items.notnull}")
    @UniqueItemIds
    private List<CartItemCreateRequest> items;

    @ValidMeta
    private Map<String, Object> meta;
}