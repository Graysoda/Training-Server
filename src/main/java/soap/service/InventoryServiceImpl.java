package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.InventoryDao;
import soap.generated.CreateInventoryRequest;
import soap.generated.Inventory;
import soap.generated.UpdateInventoryRequest;

import java.sql.SQLException;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{
	private InventoryDao inventoryDao;

	@Autowired
	public void setInventoryDao(@Lazy InventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
	}

	@Override
	@Transactional
	public List<Inventory> getAllInventory() throws SQLException {
		return inventoryDao.getAll();
	}

	@Override
	@Transactional
	public Inventory getInventoryById(long inventoryId) throws SQLException {
		return inventoryDao.getById(inventoryId);
	}

	@Override
	@Transactional
	public List<Inventory> getStoreInventory(long storeId) throws SQLException {
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
