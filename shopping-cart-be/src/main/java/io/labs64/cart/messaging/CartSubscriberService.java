package io.labs64.cart.messaging;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class CartSubscriberService {

    private static final Logger logger = LoggerFactory.getLogger(CartSubscriberService.class);

    @PostConstruct
    public void init() {
        logger.info("ShoppingCartSubscriberService initialized.");
    }

    @Bean
    public Consumer<String> cart() {
        return message -> {
            logger.debug("Received entity: {}", message);
        };
    }

}
