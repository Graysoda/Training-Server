package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.PaymentDaoImpl;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{
	@Autowired @Lazy private PaymentDaoImpl paymentDaoImpl;

	@Override
	@Transactional
	public List<Payment> getAllPayments() {
		return paymentDaoImpl.getAll();
	}

	@Override
	@Transactional
	public ResponseEntity<?> insertPayment(CreatePaymentRequest request) {
		return paymentDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public ResponseEntity<?> deletePayment(long paymentId) {
		return paymentDaoImpl.delete(paymentId);
	}

	@Override
	@Transactional
	public ResponseEntity<?> updatePayment(UpdatePaymentRequest request) {
		return paymentDaoImpl.update(request);
	}
}
