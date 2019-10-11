package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Store;

@Repository
public interface StoreDao extends CrudRepository<Store, Integer> {

}
