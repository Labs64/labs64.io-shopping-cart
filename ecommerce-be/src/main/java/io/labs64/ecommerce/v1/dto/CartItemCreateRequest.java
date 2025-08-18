package io.labs64.ecommerce.v1.dto;

import java.math.BigDecimal;
import java.util.Map;

import io.labs64.ecommerce.v1.validation.ValidMeta;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemCreateRequest {
    private String itemId;

    @NotBlank(message = "{validation.title.notblank}")
    private String title;

    private String description;

    @NotNull(message = "{validation.quantity.notnull}")
    @Min(value = 1, message = "{validation.quantity.min}")
    private Integer quantity;

    @NotNull(message = "{validation.price.notnull}")
    @DecimalMin(value = "0.0", message = "{validation.price.min}")
    private BigDecimal price;

    @ValidMeta
    private Map<String, Object> meta;
}
