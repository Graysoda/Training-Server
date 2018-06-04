package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import training.database.dao.impl.PaymentDaoImpl;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;
import training.service.PaymentService;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
	@Autowired @Lazy private PaymentDaoImpl paymentDaoImpl;

	@Override
	public List<Payment> getAllPayments() {
		return paymentDaoImpl.getAll();
	}

	@Override
	public ResponseEntity<?> insertPayment(CreatePaymentRequest request) {
		return paymentDaoImpl.insert(request);
	}

	@Override
	public ResponseEntity<?> deletePayment(long paymentId) {
		return paymentDaoImpl.delete(paymentId);
	}

	@Override
	public ResponseEntity<?> updatePayment(UpdatePaymentRequest request) {
		return paymentDaoImpl.update(request);
	}

	@Override
	public Payment getPaymentById(long paymentId) {
		return paymentDaoImpl.getById(paymentId);
	}

	@Override
	public List<Payment> getPaymentsForRental(long rentalId) {
		return paymentDaoImpl.getForRental(rentalId);
	}

	@Override
	public List<Payment> getPaymentFromCustomer(long customerId) {
		return paymentDaoImpl.getFromCustomer(customerId);
	}
}
