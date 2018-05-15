package training.database.dao;

import org.springframework.http.ResponseEntity;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.util.List;

public interface InventoryDao {
    List<Inventory> getAll();
    Inventory getById(long id);
    List<Inventory> getStoreInventory(long storeId);
    ResponseEntity<?> insert(CreateInventoryRequest request);
    ResponseEntity<?> update(UpdateInventoryRequest request);
    ResponseEntity<?> delete(long inventoryId);
}
