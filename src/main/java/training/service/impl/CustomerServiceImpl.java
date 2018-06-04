package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import training.database.dao.impl.CustomerDaoImpl;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;
import training.service.CustomerService;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired @Lazy private CustomerDaoImpl customerDaoImpl;

	@Override
	public ResponseEntity<?> insertCustomer(CreateCustomerRequest request) {
		return customerDaoImpl.insert(request);
	}

	@Override
	public List<Customer> getActiveCustomers() {
		return customerDaoImpl.getActive();
	}

	@Override
	public Customer getCustomerById(long id) {
		return customerDaoImpl.getById(id);
	}

	@Override
	public List<Customer> getCustomersByStore(long id) {
		return customerDaoImpl.getByStore(id);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerDaoImpl.getAll();
	}

	@Override
	public ResponseEntity<?> deleteCustomer(long customerId) {
		return customerDaoImpl.delete(customerId);
	}

	@Override
	public ResponseEntity<?> updateCustomer(UpdateCustomerRequest request) {
		return customerDaoImpl.update(request);
	}

	@Override
	public List<Customer> getInactiveCustomers() {
		return customerDaoImpl.getInactive();
	}
}
