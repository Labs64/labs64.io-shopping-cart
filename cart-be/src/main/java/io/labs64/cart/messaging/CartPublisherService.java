package io.labs64.cart.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.labs64.cart.v1.model.Cart;

@Service
public class CartPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(CartPublisherService.class);

    public static final String SHOPPING_CART_OUT_0 = "shopping-cart-out-0";

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    @Autowired
    public CartPublisherService(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public boolean publishCart(Cart cart) {
        String json;
        try {
            json = objectMapper.writeValueAsString(cart);
        } catch (JsonProcessingException e) {
            logger.error("Failed to convert Object to JSON! Error: {}", e.getMessage());
            return false;
        }

        logger.debug("Publish shopping cart '{}' to binding '{}'", json, SHOPPING_CART_OUT_0);
        return streamBridge.send(SHOPPING_CART_OUT_0, MessageBuilder.withPayload(json).build());
    }

}
