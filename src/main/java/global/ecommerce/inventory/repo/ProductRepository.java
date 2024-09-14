package global.ecommerce.inventory.repo;

import global.ecommerce.inventory.modal.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductById(long id);

    Optional<Product> findByName(String name);
}