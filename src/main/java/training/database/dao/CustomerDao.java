package training.database.dao;

import org.springframework.http.ResponseEntity;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;

import java.util.List;

public interface CustomerDao {
    List<Customer> getActive();
    Customer getById(long id);
    List<Customer> getByStore(long id);
    List<Customer> getAll();
    ResponseEntity<?> insert(CreateCustomerRequest request);
    ResponseEntity<?> update(UpdateCustomerRequest request);
    ResponseEntity<?> delete(long customerId);
}
