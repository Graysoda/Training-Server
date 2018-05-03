package soap.service;

import soap.generated.CreateInventoryRequest;
import soap.generated.Inventory;
import soap.generated.UpdateInventoryRequest;

import java.util.List;

public interface InventoryService {
	List<Inventory> getAllInventory();
	Inventory getInventoryById(long id);
	List<Inventory> getStoreInventory(long storeId);
	void insert(CreateInventoryRequest request);
	void deleteInventory(long inventoryId);
	void updateInventory(UpdateInventoryRequest request);
}
