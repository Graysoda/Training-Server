package training.database.dao;

import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;

import java.util.List;

public interface PaymentDao {
    List<Payment> getAll();
    void insert(CreatePaymentRequest request);
    void update(UpdatePaymentRequest request);
    void delete(long paymentId);
}
