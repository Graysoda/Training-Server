package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Customer;
import training.persistence.dao.AddressDao;
import training.persistence.dao.CustomerDao;
import training.persistence.dao.StoreDao;
import training.persistence.dto.CustomerDto;
import training.persistence.entity.Address;
import training.persistence.entity.Store;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private StoreDao storeDao;

    public List<Customer> getAllCustomers() {
        return convert(customerDao.findAll());
    }

    public Customer getCustomerById(int customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(customerId))).makeGenerated();
    }

    public List<Customer> getCustomersByStore(int storeId) {
        return convert(customerDao.findByStoreId(storeId));
    }

    public List<Customer> getActiveCustomers() {
        return convert(customerDao.findActive());
    }

    public List<Customer> getInactiveCustomers() {
        return convert(customerDao.findInactive());
    }

    private List<Customer> convert(Iterable<training.persistence.entity.Customer> all){
        List<Customer> customers = new ArrayList<>();
        all.forEach(customer -> customers.add(customer.makeGenerated()));
        return customers;
    }

    public Customer save(CustomerDto customer) {
        training.persistence.entity.Customer entity;

        if (Objects.nonNull(customer.getId())){
            training.persistence.entity.Customer c = customerDao.findById(customer.getId())
                    .orElseThrow(() -> new EntityNotFoundException(customer.getId().toString()));

            if (Objects.nonNull(customer.getAddressId())){
                c.setAddress(addressDao.findById(customer.getAddressId())
                        .orElseThrow(() -> new EntityNotFoundException(customer.getAddressId().toString())));
            }
            if (Objects.nonNull(customer.getStoreId())){
                c.setStore(storeDao.findById(customer.getStoreId())
                        .orElseThrow(() -> new EntityNotFoundException(customer.getStoreId().toString())));
            }

            entity = customer.makeEntity(c);
        } else {
            Address address = addressDao.findById(customer.getAddressId())
                    .orElseThrow(() -> new EntityNotFoundException(customer.getAddressId().toString()));
            Store store = storeDao.findById(customer.getStoreId())
                    .orElseThrow(() -> new EntityNotFoundException(customer.getStoreId().toString()));
            entity = customer.makeEntity(address, store);
        }

        return customerDao.save(entity).makeGenerated();
    }

    public void delete(int customerId) {
        customerDao.deleteById(customerId);
    }

    public boolean exists(int customerId) {
        return customerDao.existsById(customerId);
    }
}
