package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Inventory;

@Repository
public interface InventoryDao extends CrudRepository<Inventory, Integer> {

    Iterable<Inventory> findByStoreId(int storeId);
}
