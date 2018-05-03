package soap.service;

import soap.generated.CreateCustomerRequest;
import soap.generated.Customer;
import soap.generated.UpdateCustomerRequest;

import java.util.List;

public interface CustomerService {
	void insertCustomer(CreateCustomerRequest request);
	List<Customer> getActiveCustomers();
	Customer getCustomerById(long id);
	List<Customer> getCustomersByStore(long id);
	List<Customer> getAllCustomers();
	void deleteCustomer(long customerId);
	void updateCustomer(UpdateCustomerRequest request);
}
