package io.labs64.ecommerce.v1.dto;

import java.math.BigDecimal;
import java.util.Map;

import io.labs64.ecommerce.v1.validation.NotBlankIfPresent;
import io.labs64.ecommerce.v1.validation.ValidMeta;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemUpdateRequest {
    private String itemId;

    @NotBlankIfPresent(message = "{validation.title.notblank}")
    private String title;

    private String description;

    @Min(value = 1, message = "{validation.quantity.min}")
    private Integer quantity;

    @DecimalMin(value = "0.0", message = "{validation.price.min}")
    private BigDecimal price;

    @ValidMeta
    private Map<String, Object> meta;
}
