package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.PaymentDao;
import soap.generated.CreatePaymentRequest;
import soap.generated.Payment;
import soap.generated.UpdatePaymentRequest;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService{
	private PaymentDao paymentDao;

	@Autowired
	public void setPaymentDao(PaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	@Override
	@Transactional
	public List<Payment> getAllPayments() {
		return paymentDao.getAll();
	}

	@Override
	@Transactional
	public void insertPayment(CreatePaymentRequest request) {
		paymentDao.insert(request);
	}

	@Override
	public void deletePayment(long paymentId) {
		paymentDao.delete(paymentId);
	}

	@Override
	public void updatePayment(UpdatePaymentRequest request) {
		paymentDao.update(request);
	}
}
