package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.sql.SQLException;
import java.util.List;

public interface InventoryService {
	List<Inventory> getAllInventory() throws SQLException;
	Inventory getInventoryById(long id) throws SQLException;
	List<Inventory> getStoreInventory(long storeId) throws SQLException;
	ResponseEntity<?> insert(CreateInventoryRequest request);
	ResponseEntity<?> deleteInventory(long inventoryId);
	ResponseEntity<?> updateInventory(UpdateInventoryRequest request);
}
