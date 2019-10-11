package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Payment;

@Repository
public interface PaymentDao extends CrudRepository<Payment, Integer> {

    Iterable<Payment> findByRentalId(int rentalId);

    Iterable<Payment> findByCustomerId(int customerId);
}
