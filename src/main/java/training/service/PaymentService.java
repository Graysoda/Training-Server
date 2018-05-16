package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;

import java.util.List;

public interface PaymentService {
	List<Payment> getAllPayments();
	ResponseEntity<?> insertPayment(CreatePaymentRequest request);
	ResponseEntity<?> deletePayment(long paymentId);
	ResponseEntity<?> updatePayment(UpdatePaymentRequest request);
    Payment getPaymentById(long paymentId);
	List<Payment> getPaymentsForRental(long rentalId);
	List<Payment> getPaymentFromCustomer(long customerId);
}
