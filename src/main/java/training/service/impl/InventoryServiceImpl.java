package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import training.database.dao.impl.InventoryDaoImpl;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;
import training.service.InventoryService;

import java.util.List;

public class InventoryServiceImpl implements InventoryService {
	@Autowired @Lazy private InventoryDaoImpl inventoryDaoImpl;

	@Override
	public List<Inventory> getAllInventory() {
		return inventoryDaoImpl.getAll();
	}

	@Override
	public Inventory getInventoryById(long inventoryId) {
		return inventoryDaoImpl.getById(inventoryId);
	}

	@Override
	public List<Inventory> getStoreInventory(long storeId) {
		return inventoryDaoImpl.getStoreInventory(storeId);
	}

	@Override
	public ResponseEntity<?> insert(CreateInventoryRequest request) {
		return inventoryDaoImpl.insert(request);
	}

	@Override
	public ResponseEntity<?> deleteInventory(long inventoryId) {
		return inventoryDaoImpl.delete(inventoryId);
	}

	@Override
	public ResponseEntity<?> updateInventory(UpdateInventoryRequest request) {
		return inventoryDaoImpl.update(request);
	}
}
