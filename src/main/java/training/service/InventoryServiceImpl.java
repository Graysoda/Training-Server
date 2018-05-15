package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.InventoryDaoImpl;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{
	@Autowired @Lazy private InventoryDaoImpl inventoryDaoImpl;

	@Override
	@Transactional
	public List<Inventory> getAllInventory() {
		return inventoryDaoImpl.getAll();
	}

	@Override
	@Transactional
	public Inventory getInventoryById(long inventoryId) {
		return inventoryDaoImpl.getById(inventoryId);
	}

	@Override
	@Transactional
	public List<Inventory> getStoreInventory(long storeId) {
		return inventoryDaoImpl.getStoreInventory(storeId);
	}

	@Override
	@Transactional
	public ResponseEntity<?> insert(CreateInventoryRequest request) {
		return inventoryDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteInventory(long inventoryId) {
		return inventoryDaoImpl.delete(inventoryId);
	}

	@Override
	@Transactional
	public ResponseEntity<?> updateInventory(UpdateInventoryRequest request) {
		return inventoryDaoImpl.update(request);
	}
}
