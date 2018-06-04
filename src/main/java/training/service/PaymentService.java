package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;

import java.util.List;
@Service
public interface PaymentService {
	@Transactional
	List<Payment> getAllPayments();
	@Transactional
	ResponseEntity<?> insertPayment(CreatePaymentRequest request);
	@Transactional
	ResponseEntity<?> deletePayment(long paymentId);
	@Transactional
	ResponseEntity<?> updatePayment(UpdatePaymentRequest request);
	@Transactional
	Payment getPaymentById(long paymentId);
	@Transactional
	List<Payment> getPaymentsForRental(long rentalId);
	@Transactional
	List<Payment> getPaymentFromCustomer(long customerId);
}
