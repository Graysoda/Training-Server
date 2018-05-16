package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.util.List;

public interface InventoryService {
	List<Inventory> getAllInventory();
	Inventory getInventoryById(long id);
	List<Inventory> getStoreInventory(long storeId);
	ResponseEntity<?> insert(CreateInventoryRequest request);
	ResponseEntity<?> deleteInventory(long inventoryId);
	ResponseEntity<?> updateInventory(UpdateInventoryRequest request);
}
