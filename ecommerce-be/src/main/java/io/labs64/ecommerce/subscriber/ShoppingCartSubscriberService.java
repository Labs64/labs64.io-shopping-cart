package io.labs64.ecommerce.subscriber;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ShoppingCartSubscriberService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartSubscriberService.class);

    @PostConstruct
    public void init() {
        logger.info("ShoppingCartSubscriberService initialized.");
    }

    @Bean
    public Consumer<String> ecommerce() {
        return message -> {
            logger.debug("Received entity: {}", message);
        };
    }

}
