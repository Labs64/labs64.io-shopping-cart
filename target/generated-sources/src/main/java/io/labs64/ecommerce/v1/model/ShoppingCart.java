package io.labs64.ecommerce.v1.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.labs64.ecommerce.v1.model.CartItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Represents a shopping cart.
 */

@Schema(name = "ShoppingCart", description = "Represents a shopping cart.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-23T07:44:20.832994+02:00[Europe/Berlin]", comments = "Generator version: 7.14.0")
public class ShoppingCart {

  private UUID cartId;

  private @Nullable String userId;

  @Valid
  private List<@Valid CartItem> items = new ArrayList<>();

  private Integer totalItems;

  private Float totalAmount;

  public ShoppingCart() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ShoppingCart(UUID cartId, List<@Valid CartItem> items, Integer totalItems, Float totalAmount) {
    this.cartId = cartId;
    this.items = items;
    this.totalItems = totalItems;
    this.totalAmount = totalAmount;
  }

  public ShoppingCart cartId(UUID cartId) {
    this.cartId = cartId;
    return this;
  }

  /**
   * Unique identifier for this shopping cart. This will often serve as the sessionId for shopping cart correlation.
   * @return cartId
   */
  @NotNull @Valid 
  @Schema(name = "cartId", description = "Unique identifier for this shopping cart. This will often serve as the sessionId for shopping cart correlation.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("cartId")
  public UUID getCartId() {
    return cartId;
  }

  public void setCartId(UUID cartId) {
    this.cartId = cartId;
  }

  public ShoppingCart userId(@Nullable String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * (Optional) ID of the user associated with the cart.
   * @return userId
   */
  
  @Schema(name = "userId", example = "V12345678", description = "(Optional) ID of the user associated with the cart.", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("userId")
  public @Nullable String getUserId() {
    return userId;
  }

  public void setUserId(@Nullable String userId) {
    this.userId = userId;
  }

  public ShoppingCart items(List<@Valid CartItem> items) {
    this.items = items;
    return this;
  }

  public ShoppingCart addItemsItem(CartItem itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * List of items currently in the cart.
   * @return items
   */
  @NotNull @Valid @Size(min = 1) 
  @Schema(name = "items", description = "List of items currently in the cart.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("items")
  public List<@Valid CartItem> getItems() {
    return items;
  }

  public void setItems(List<@Valid CartItem> items) {
    this.items = items;
  }

  public ShoppingCart totalItems(Integer totalItems) {
    this.totalItems = totalItems;
    return this;
  }

  /**
   * Total number of distinct items in the cart.
   * minimum: 0
   * @return totalItems
   */
  @NotNull @Min(0) 
  @Schema(name = "totalItems", description = "Total number of distinct items in the cart.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalItems")
  public Integer getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(Integer totalItems) {
    this.totalItems = totalItems;
  }

  public ShoppingCart totalAmount(Float totalAmount) {
    this.totalAmount = totalAmount;
    return this;
  }

  /**
   * Total monetary value of all items in the cart.
   * minimum: 0
   * @return totalAmount
   */
  @NotNull @DecimalMin("0") 
  @Schema(name = "totalAmount", description = "Total monetary value of all items in the cart.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalAmount")
  public Float getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Float totalAmount) {
    this.totalAmount = totalAmount;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShoppingCart shoppingCart = (ShoppingCart) o;
    return Objects.equals(this.cartId, shoppingCart.cartId) &&
        Objects.equals(this.userId, shoppingCart.userId) &&
        Objects.equals(this.items, shoppingCart.items) &&
        Objects.equals(this.totalItems, shoppingCart.totalItems) &&
        Objects.equals(this.totalAmount, shoppingCart.totalAmount);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cartId, userId, items, totalItems, totalAmount);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShoppingCart {\n");
    sb.append("    cartId: ").append(toIndentedString(cartId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("    totalItems: ").append(toIndentedString(totalItems)).append("\n");
    sb.append("    totalAmount: ").append(toIndentedString(totalAmount)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

