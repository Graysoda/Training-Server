package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.InventoryDao;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{
	@Autowired @Lazy private InventoryDao inventoryDao;

	@Override
	@Transactional
	public List<Inventory> getAllInventory() {
		return inventoryDao.getAll();
	}

	@Override
	@Transactional
	public Inventory getInventoryById(long inventoryId) {
		return inventoryDao.getById(inventoryId);
	}

	@Override
	@Transactional
	public List<Inventory> getStoreInventory(long storeId) {
		return inventoryDao.getStoreInventory(storeId);
	}

	@Override
	@Transactional
	public void insert(CreateInventoryRequest request) {
		inventoryDao.insert(request);
	}

	@Override
	@Transactional
	public void deleteInventory(long inventoryId) {
		inventoryDao.delete(inventoryId);
	}

	@Override
	@Transactional
	public void updateInventory(UpdateInventoryRequest request) {
		inventoryDao.update(request);
	}
}
