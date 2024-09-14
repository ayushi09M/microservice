package global.ecommerce.payment.config;

import global.ecommerce.payment.modal.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "inventory-service", url = "${inventory-service.url}")
public interface FeignInventoryClient {

    @GetMapping("/productByID/{id}")
    Optional<Product> findProductById(@PathVariable("id") Long id);
}
