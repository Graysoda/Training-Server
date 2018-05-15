package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
	ResponseEntity<?> insertCustomer(CreateCustomerRequest request);
	List<Customer> getActiveCustomers();
	Customer getCustomerById(long id);
	List<Customer> getCustomersByStore(long id);
	List<Customer> getAllCustomers();
	ResponseEntity<?> deleteCustomer(long customerId);
	ResponseEntity<?> updateCustomer(UpdateCustomerRequest request);
}
