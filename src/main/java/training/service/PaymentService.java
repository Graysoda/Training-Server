package training.service;

import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;

import java.util.List;

public interface PaymentService {
	List<Payment> getAllPayments();
	void insertPayment(CreatePaymentRequest request);
	void deletePayment(long paymentId);
	void updatePayment(UpdatePaymentRequest request);
}
