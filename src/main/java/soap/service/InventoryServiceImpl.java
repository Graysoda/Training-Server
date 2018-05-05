package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	public void setInventoryDao(InventoryDao inventoryDao){
		this.inventoryDao = inventoryDao;
	}

	@Transactional
	public List<Inventory> getAllInventory() throws SQLException {
		return inventoryDao.getAll();
	}

	@Transactional
	public Inventory getInventoryById(long inventoryId) throws SQLException {
		return inventoryDao.getById(inventoryId);
	}

	@Override
	public List<Inventory> getStoreInventory(long storeId) throws SQLException {
		return inventoryDao.getStoreInventory(storeId);
	}

	@Override
	public void insert(CreateInventoryRequest request) {
		inventoryDao.insert(request);
	}

	@Override
	public void deleteInventory(long inventoryId) {
		inventoryDao.delete(inventoryId);
	}

	@Override
	public void updateInventory(UpdateInventoryRequest request) {
		inventoryDao.update(request);
	}
}
