package training.service;

import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.sql.SQLException;
import java.util.List;

public interface InventoryService {
	List<Inventory> getAllInventory() throws SQLException;
	Inventory getInventoryById(long id) throws SQLException;
	List<Inventory> getStoreInventory(long storeId) throws SQLException;
	void insert(CreateInventoryRequest request);
	void deleteInventory(long inventoryId);
	void updateInventory(UpdateInventoryRequest request);
}
