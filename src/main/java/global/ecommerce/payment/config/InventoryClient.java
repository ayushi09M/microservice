package global.ecommerce.payment.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "${inventory-service.url}")
public interface InventoryClient {
    @PutMapping("/update")
    void updateInventory(@RequestParam String name, @RequestParam int quantity);
}
