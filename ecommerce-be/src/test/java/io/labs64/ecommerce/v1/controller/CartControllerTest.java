package io.labs64.ecommerce.v1.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import io.labs64.ecommerce.v1.dto.CartItemCreateRequest;
import io.labs64.ecommerce.v1.dto.CartItemUpdateRequest;
import io.labs64.ecommerce.v1.dto.CartResponse;
import io.labs64.ecommerce.v1.dto.CartUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.labs64.ecommerce.v1.dto.CartCreateRequest;
import io.labs64.ecommerce.v1.entity.Cart;
import io.labs64.ecommerce.v1.entity.CartItem;
import io.labs64.ecommerce.v1.mapper.CartMapper;
import io.labs64.ecommerce.v1.service.CartService;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private CartMapper cartMapper;

    private Cart cart;
    private UUID cartId;

    @BeforeEach
    void setUp() {
        cartId = UUID.randomUUID();
        cart = new Cart();
        cart.setCartId(cartId);
    }

    @Test
    void createCartShouldReturnCreated() throws Exception {
        CartCreateRequest request = new CartCreateRequest();
        request.setCurrency("USD");

        CartResponse response = new CartResponse();
        response.setCartId(cart.getCartId());

        when(cartMapper.toCart(any(CartCreateRequest.class))).thenReturn(cart);
        when(cartService.createCart(any(Cart.class))).thenReturn(cart);
        when(cartMapper.toResponse(any(Cart.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/cart").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))).andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cartId").value(cart.getCartId().toString()));

        verify(cartService).createCart(any(Cart.class));
    }

    @Test
    void getCartShouldReturnCart() throws Exception {
        when(cartService.getCart(cartId)).thenReturn(Optional.of(cart));
        when(cartMapper.toResponse(cart)).thenReturn(new CartResponse());

        mockMvc.perform(get("/api/v1/cart/{cartId}", cartId)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getCartShouldReturn404WhenCartNotFound() throws Exception {
        when(cartService.getCart(cartId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/cart/{cartId}", cartId)).andExpect(status().isNotFound());
    }

    @Test
    void updateCartShouldReturnUpdatedCart() throws Exception {
        CartUpdateRequest updateRequest = new CartUpdateRequest();
        updateRequest.setCurrency("EUR");

        when(cartService.updateCart(eq(cartId), any())).thenReturn(Optional.of(cart));
        when(cartMapper.toResponse(cart)).thenReturn(new CartResponse());

        mockMvc.perform(patch("/api/v1/cart/{cartId}", cartId).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateCartShouldReturn404WhenCartNotFound() throws Exception {
        CartUpdateRequest updateRequest = new CartUpdateRequest();

        when(cartService.updateCart(eq(cartId), any())).thenReturn(Optional.empty());

        mockMvc.perform(patch("/api/v1/cart/{cartId}", cartId).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest))).andExpect(status().isNotFound());
    }

    @Test
    void deleteCartShouldReturnNoContent() throws Exception {
        doNothing().when(cartService).deleteCart(cartId);

        mockMvc.perform(delete("/api/v1/cart/{cartId}", cartId)).andExpect(status().isNoContent());
    }

    @Test
    void addCartItemShouldReturnItem() throws Exception {
        CartItemCreateRequest itemRequest = new CartItemCreateRequest();
        itemRequest.setTitle("Item1");
        itemRequest.setQuantity(1);
        itemRequest.setPrice(BigDecimal.TEN);

        CartItem cartItem = new CartItem();
        cartItem.setItemId(UUID.randomUUID().toString());

        when(cartMapper.toCartItem(itemRequest)).thenReturn(cartItem);
        when(cartService.updateCart(eq(cartId), any())).thenReturn(Optional.of(cart));

        mockMvc.perform(post("/api/v1/cart/{cartId}/item", cartId).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(itemRequest))).andExpect(status().isOk());
    }

    @Test
    void updateCartItemShouldReturnUpdatedItem() throws Exception {
        String itemId = "item-1";

        CartItem item = new CartItem();
        item.setItemId("item-1");
        cart.setItems(List.of(item));

        CartItemUpdateRequest updateRequest = new CartItemUpdateRequest();

        CartItem updatedItem = new CartItem();
        updatedItem.setItemId(itemId);

        when(cartService.updateCart(eq(cartId), any())).thenReturn(Optional.of(cart));

        mockMvc.perform(patch("/api/v1/cart/{cartId}/item/{itemId}", cartId, itemId)
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteCartItemShouldReturnNoContent() throws Exception {
        String itemId = "item-1";

        when(cartService.updateCart(eq(cartId), any())).thenReturn(Optional.of(cart));

        mockMvc.perform(delete("/api/v1/cart/{cartId}/item/{itemId}", cartId, itemId))
                .andExpect(status().isNoContent());
    }
}
