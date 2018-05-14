package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
	public void insert(CreateInventoryRequest request) {
		inventoryDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public void deleteInventory(long inventoryId) {
		inventoryDaoImpl.delete(inventoryId);
	}

	@Override
	@Transactional
	public void updateInventory(UpdateInventoryRequest request) {
		inventoryDaoImpl.update(request);
	}
}
