package io.labs64.ecommerce.v1.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.springframework.lang.Nullable;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Represents a single item in the shopping cart.
 */

@Schema(name = "CartItem", description = "Represents a single item in the shopping cart.")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-07-23T07:44:20.832994+02:00[Europe/Berlin]", comments = "Generator version: 7.14.0")
public class CartItem {

  private String productId;

  private String productName;

  private Integer quantity;

  private Float price;

  public CartItem() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CartItem(String productId, String productName, Integer quantity, Float price) {
    this.productId = productId;
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
  }

  public CartItem productId(String productId) {
    this.productId = productId;
    return this;
  }

  /**
   * Unique identifier of the product.
   * @return productId
   */
  @NotNull 
  @Schema(name = "productId", example = "E12345678", description = "Unique identifier of the product.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("productId")
  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public CartItem productName(String productName) {
    this.productName = productName;
    return this;
  }

  /**
   * Name of the product.
   * @return productName
   */
  @NotNull 
  @Schema(name = "productName", example = "Basic License", description = "Name of the product.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("productName")
  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public CartItem quantity(Integer quantity) {
    this.quantity = quantity;
    return this;
  }

  /**
   * Quantity of the product in the cart.
   * minimum: 1
   * @return quantity
   */
  @NotNull @Min(1) 
  @Schema(name = "quantity", example = "1", description = "Quantity of the product in the cart.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("quantity")
  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public CartItem price(Float price) {
    this.price = price;
    return this;
  }

  /**
   * Unit price of the product at the time it was added to the cart.
   * minimum: 0
   * @return price
   */
  @NotNull @DecimalMin("0") 
  @Schema(name = "price", example = "1200.0", description = "Unit price of the product at the time it was added to the cart.", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("price")
  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CartItem cartItem = (CartItem) o;
    return Objects.equals(this.productId, cartItem.productId) &&
        Objects.equals(this.productName, cartItem.productName) &&
        Objects.equals(this.quantity, cartItem.quantity) &&
        Objects.equals(this.price, cartItem.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId, productName, quantity, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CartItem {\n");
    sb.append("    productId: ").append(toIndentedString(productId)).append("\n");
    sb.append("    productName: ").append(toIndentedString(productName)).append("\n");
    sb.append("    quantity: ").append(toIndentedString(quantity)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
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

