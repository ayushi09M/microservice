package global.ecommerce.payment.repo;

import global.ecommerce.payment.modal.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(int orderId);
}
