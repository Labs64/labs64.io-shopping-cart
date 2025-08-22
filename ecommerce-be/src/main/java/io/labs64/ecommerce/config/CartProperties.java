package io.labs64.ecommerce.config;

import java.time.Duration;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "cart")
public class CartProperties {
    private String prefix;
    private Duration ttl;
    private List<String> currency;
}