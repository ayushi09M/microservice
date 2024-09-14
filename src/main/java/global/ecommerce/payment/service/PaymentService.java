package global.ecommerce.payment.service;

import global.ecommerce.payment.config.FeignInventoryClient;
import global.ecommerce.payment.config.ResourceNotFoundException;
import global.ecommerce.payment.modal.Payment;
import global.ecommerce.payment.modal.Product;
import global.ecommerce.payment.modal.ProductPaymentDetails;
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
    private FeignInventoryClient feignInventoryClient;

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    public Payment processPayment(Payment payment) {
        log.info("Processing payment for order: {}", payment.getOrderId());
        payment.setPaymentStatus("Processed");
        Payment savedPayment = repository.save(payment);

        log.info("Payment processed, updating inventory for order: {}", payment.getOrderId());
        return savedPayment;
    }

    public Payment getPaymentByOrderId(int orderId) {
        log.info("Fetching payment with orderId: {}", orderId);
        return repository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment", "orderId", (long) orderId));
    }


    public ProductPaymentDetails getProductPaymentDetails(int orderId) {
        Payment payment = this.getPaymentByOrderId(orderId);

        Product product = feignInventoryClient.findProductById(payment.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", payment.getProductId()));

        ProductPaymentDetails details = new ProductPaymentDetails();
        details.setOrderId(payment.getOrderId());
        details.setProduct(product);
        return details;
    }

}
