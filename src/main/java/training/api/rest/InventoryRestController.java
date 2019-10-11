package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;
import training.persistence.dto.InventoryDto;
import training.service.InventoryService;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class InventoryRestController {
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping("/inventory")
    public ResponseEntity<List<Inventory>> getAllInventory(){
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/inventory/{inventoryId}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable int inventoryId){
        return ResponseEntity.ok(inventoryService.getInevntoryById(inventoryId));
    }

    @GetMapping("/store/{storeId}/inventory")
    public ResponseEntity<List<Inventory>> getStoreInventory(@PathVariable int storeId){
        return ResponseEntity.ok(inventoryService.getStoreInventory(storeId));
    }

    @PostMapping(value = "/inventory", consumes = "application/json")
    public ResponseEntity<?> createInventory(@RequestBody CreateInventoryRequest request){
        InventoryDto inventory = new InventoryDto(request);
        String error = validationHelper.validateInventoryCreation(inventory);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(inventoryService.save(inventory)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/inventory/{inventoryId}", consumes = "application/json")
    public ResponseEntity<?> updateInventory(@PathVariable int inventoryId, @RequestBody UpdateInventoryRequest request){
        InventoryDto inventory = new InventoryDto(request);
        String error = validationHelper.validateInventoryUpdate(inventoryId, inventory);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(inventoryService.save(inventory)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/inventory", consumes = "application/json")
    public ResponseEntity<?> updateInventory(@RequestBody UpdateInventoryRequest request){
        InventoryDto inventory = new InventoryDto(request);
        String error = validationHelper.validateInventoryUpdate(inventory);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(inventoryService.save(inventory)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping("/inventory/{inventoryId}")
    public ResponseEntity<?> deleteInventory(@PathVariable int inventoryId){
        if (inventoryId <= 0 || !inventoryService.exists(inventoryId)){
            return ResponseEntity.badRequest().body("inventory id " + Constants.ID_ERROR_MSG);
        }
        inventoryService.delete(inventoryId);
        return ResponseEntity.ok("inventory with id [" + inventoryId + "] was deleted");
    }
}
