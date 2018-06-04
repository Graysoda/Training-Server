package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;

import java.util.List;

@Service
public interface InventoryService {
	@Transactional
	List<Inventory> getAllInventory();
	@Transactional
	Inventory getInventoryById(long id);
	@Transactional
	List<Inventory> getStoreInventory(long storeId);
	@Transactional
	ResponseEntity<?> insert(CreateInventoryRequest request);
	@Transactional
	ResponseEntity<?> deleteInventory(long inventoryId);
	@Transactional
	ResponseEntity<?> updateInventory(UpdateInventoryRequest request);
}
