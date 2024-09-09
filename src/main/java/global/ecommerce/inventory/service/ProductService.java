package global.ecommerce.inventory.service;


import global.ecommerce.inventory.modal.Product;
import global.ecommerce.inventory.repo.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private ProductRepository repository;

    public Product getProductByName(String name) {
        log.info("Fetching product with name: {}", name);
        return repository.findByName(name);
    }

    public Product saveProduct(Product product) {
        log.info("Saving product: {}", product);
        return repository.save(product);
    }

    public void updateProductQuantity(String name, int quantity) {
        Product product = repository.findByName(name);
        if (product != null) {
            log.info("Updating quantity of product: {} to {}", name, quantity);
            product.setQuantity(quantity);
            repository.save(product);
        } else {
            log.warn("Product not found: {}", name);
        }
    }
}