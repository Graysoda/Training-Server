package training.database.dao;

import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;

import java.util.List;

public interface CustomerDao {
    List<Customer> getActive();
    Customer getById(long id);
    List<Customer> getByStore(long id);
    List<Customer> getAll();
    void insert(CreateCustomerRequest request);
    void update(UpdateCustomerRequest request);
    void delete(long customerId);
}
