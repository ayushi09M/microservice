package global.ecommerce.payment.controller;
import global.ecommerce.payment.modal.Payment;
import global.ecommerce.payment.modal.ProductPaymentDetails;
import global.ecommerce.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    @Autowired
    private PaymentService productService;

    @PostMapping
    public Payment processPayment(@RequestBody Payment payment) {
        log.info("Received request to process payment: {}", payment);
        return productService.processPayment(payment);
    }

    @GetMapping("/{orderId}")
    public Payment getPayment(@PathVariable int orderId) {
        log.info("Received request to get payment with orderId: {}", orderId);
        return productService.getPaymentByOrderId(orderId);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<ProductPaymentDetails> getProductPaymentDetails(@PathVariable int orderId) {
        log.info("Received request to get product payment details for orderId: {}", orderId);
        ProductPaymentDetails details = productService.getProductPaymentDetails(orderId);
        return ResponseEntity.ok(details);
    }
}
