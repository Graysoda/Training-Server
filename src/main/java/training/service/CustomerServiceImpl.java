package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.CustomerDaoImpl;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired @Lazy private CustomerDaoImpl customerDaoImpl;

	@Override
	@Transactional
	public ResponseEntity<?> insertCustomer(CreateCustomerRequest request) {
		return customerDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public List<Customer> getActiveCustomers() {
		return customerDaoImpl.getActive();
	}

	@Override
	@Transactional
	public Customer getCustomerById(long id) {
		return customerDaoImpl.getById(id);
	}

	@Override
	@Transactional
	public List<Customer> getCustomersByStore(long id) {
		return customerDaoImpl.getByStore(id);
	}

	@Override
	@Transactional
	public List<Customer> getAllCustomers() {
		return customerDaoImpl.getAll();
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteCustomer(long customerId) {
		return customerDaoImpl.delete(customerId);
	}

	@Override
	@Transactional
	public ResponseEntity<?> updateCustomer(UpdateCustomerRequest request) {
		return customerDaoImpl.update(request);
	}

	@Override
	@Transactional
	public List<Customer> getInactiveCustomers() {
		return customerDaoImpl.getInactive();
	}
}
