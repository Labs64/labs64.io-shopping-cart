package io.labs64.ecommerce.controller.ecommerce.v1;

import io.labs64.ecommerce.v1.api.ShoppingCartApi;
import io.labs64.ecommerce.v1.model.ShoppingCart;
import io.labs64.ecommerce.publisher.ShoppingCartPublisherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/v1")
public class ShoppingCartController implements ShoppingCartApi {

    private final ShoppingCartPublisherService publisherService;

    public ShoppingCartController(ShoppingCartPublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Override
    public ResponseEntity<String> saveCart(ShoppingCart cart) {
        boolean res = publisherService.publishCart(cart);
        if (res) {
            return ResponseEntity.ok("Message sent successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send message");
        }
    }

}
