package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;

import java.util.List;

@Service
public interface CustomerService {
	@Transactional
	ResponseEntity<?> insertCustomer(CreateCustomerRequest request);
	@Transactional
	List<Customer> getActiveCustomers();
	@Transactional
	Customer getCustomerById(long id);
	@Transactional
	List<Customer> getCustomersByStore(long id);
	@Transactional
	List<Customer> getAllCustomers();
	@Transactional
	ResponseEntity<?> deleteCustomer(long customerId);
	@Transactional
	ResponseEntity<?> updateCustomer(UpdateCustomerRequest request);
    @Transactional
	List<Customer> getInactiveCustomers();
}
