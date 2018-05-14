package training.database.dao;

import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.util.List;

public interface InventoryDao {
    List<Inventory> getAll();
    Inventory getById(long id);
    List<Inventory> getStoreInventory(long storeId);
    void insert(CreateInventoryRequest request);
    void update(UpdateInventoryRequest request);
    void delete(long inventoryId);
}
