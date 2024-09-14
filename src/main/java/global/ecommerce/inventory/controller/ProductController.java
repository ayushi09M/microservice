package global.ecommerce.inventory.controller;


import global.ecommerce.inventory.modal.Product;
import global.ecommerce.inventory.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/inventory")
public class ProductController {

    @Autowired
    private ProductService service;

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    @GetMapping("productByID/{id}")
    public Product findProductById(@PathVariable long id){
        return service.findProductById(id);

    }

    @GetMapping("/productName/{name}")
    public Optional<Product> getProduct(@PathVariable String name) {
        log.info("Received request to get product with name: {}", name);
        return service.getProductByName(name);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        log.info("Received request to add product: {}", product);
        return service.saveProduct(product);
    }

    @PutMapping("/update")
    public String updateProduct(@RequestParam String name, @RequestParam int quantity) {
        log.info("Received request to update product: {} with quantity: {}", name, quantity);
        service.updateProductQuantity(name, quantity);
        return "Product Updated";
    }
}
