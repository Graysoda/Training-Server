package training.database.dao;

import org.springframework.http.ResponseEntity;
import training.generated.CreatePaymentRequest;
import training.generated.Payment;
import training.generated.UpdatePaymentRequest;

import java.util.List;

public interface PaymentDao {
    List<Payment> getAll();
    ResponseEntity<?> insert(CreatePaymentRequest request);
    ResponseEntity<?> update(UpdatePaymentRequest request);
    ResponseEntity<?> delete(long paymentId);
    Payment getById(long paymentId);
    List<Payment> getForRental(long rentalId);
    List<Payment> getFromCustomer(long customerId);
}
