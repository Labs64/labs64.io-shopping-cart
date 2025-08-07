package io.labs64.ecommerce.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ShoppingCartPublisherService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartPublisherService.class);

    public static final String ECOMMERCE_OUT_0 = "ecommerce-out-0";

    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    @Autowired
    public ShoppingCartPublisherService(StreamBridge streamBridge, ObjectMapper objectMapper) {
        this.streamBridge = streamBridge;
        this.objectMapper = objectMapper;
    }

    public boolean publishCart(io.labs64.ecommerce.v1.model.ShoppingCart cart) {
        String json;
        try {
            json = objectMapper.writeValueAsString(cart);
        } catch (JsonProcessingException e) {
            logger.error("Failed to convert Object to JSON! Error: {}", e.getMessage());
            return false;
        }

        logger.debug("Publish shopping cart '{}' to binding '{}'", json, ECOMMERCE_OUT_0);
        return streamBridge.send(ECOMMERCE_OUT_0, MessageBuilder.withPayload(json).build());
    }

}
