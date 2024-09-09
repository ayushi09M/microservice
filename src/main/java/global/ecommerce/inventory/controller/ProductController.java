package global.ecommerce.inventory.controller;


import global.ecommerce.inventory.modal.Product;
import global.ecommerce.inventory.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private ProductService service;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);


    @GetMapping("/{name}")
    public Product getProduct(@PathVariable String name) {
        log.info("Received request to get product with name: {}", name);
        return service.getProductByName(name);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        log.info("Received request to add product: {}", product);
        return service.saveProduct(product);
    }

    @PutMapping("/update")
    public void updateProduct(@RequestParam String name, @RequestParam int quantity) {
        log.info("Received request to update product: {} with quantity: {}", name, quantity);
        service.updateProductQuantity(name, quantity);
    }
}
