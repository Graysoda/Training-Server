package soap.service;

import soap.generated.CreatePaymentRequest;
import soap.generated.Payment;
import soap.generated.UpdatePaymentRequest;

import java.util.List;

public interface PaymentService {
	public List<Payment> getAllPayments();
	void insertPayment(CreatePaymentRequest request);
	void deletePayment(long paymentId);
	void updatePayment(UpdatePaymentRequest request);
}
