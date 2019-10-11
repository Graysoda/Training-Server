package training.persistence.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Customer;

@Repository
public interface CustomerDao extends CrudRepository<Customer, Integer> {

    Iterable<Customer> findByStoreId(int storeId);

    @Query("SELECT c FROM Customer c WHERE c.active=1")
    Iterable<Customer> findActive();

    @Query("SELECT c FROM Customer c WHERE c.active='0'")
    Iterable<Customer> findInactive();
}
