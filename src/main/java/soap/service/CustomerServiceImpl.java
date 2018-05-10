package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.CustomerDao;
import soap.generated.CreateCustomerRequest;
import soap.generated.Customer;
import soap.generated.UpdateCustomerRequest;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired @Lazy
	private CustomerDao customerDao;

//	@Autowired
//	public void setCustomerDao(@Lazy CustomerDao customerDao) {
//		this.customerDao = customerDao;
//	}

	@Override
	@Transactional
	public void insertCustomer(CreateCustomerRequest request) {
		customerDao.insert(request);
	}

	@Override
	@Transactional
	public List<Customer> getActiveCustomers() {
		return customerDao.getActive();
	}

	@Override
	@Transactional
	public Customer getCustomerById(long id) {
		return customerDao.getById(id);
	}

	@Override
	@Transactional
	public List<Customer> getCustomersByStore(long id) {
		return customerDao.getByStore(id);
	}

	@Override
	@Transactional
	public List<Customer> getAllCustomers() {
		return customerDao.getAll();
	}

	@Override
	@Transactional
	public void deleteCustomer(long customerId) {
		customerDao.delete(customerId);
	}

	@Override
	@Transactional
	public void updateCustomer(UpdateCustomerRequest request) {
		customerDao.update(request);
	}
}
