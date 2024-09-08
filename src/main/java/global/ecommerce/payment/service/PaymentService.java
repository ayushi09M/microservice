package global.ecommerce.payment.service;

import global.ecommerce.payment.config.InventoryClient;
import global.ecommerce.payment.modal.Payment;
import global.ecommerce.payment.repo.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private InventoryClient inventoryClient;

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    public Payment processPayment(Payment payment) {
        log.info("Processing payment for order: {}", payment.getOrderId());
        payment.setPaymentStatus("Processed");
        Payment savedPayment = repository.save(payment);

        log.info("Payment processed, updating inventory for order: {}", payment.getOrderId());
        inventoryClient.updateInventory(payment.getOrderId(), -1);  // Assuming 1 item per order

        return savedPayment;
    }

    public Payment getPaymentByOrderId(String orderId) {
        log.info("Fetching payment with orderId: {}", orderId);
        return repository.findByOrderId(orderId);
    }
}
