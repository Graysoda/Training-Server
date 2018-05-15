package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;
import training.service.InventoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class InventoryController {
    @Autowired @Lazy private InventoryServiceImpl inventoryService;

    @RequestMapping(value = "/inventory/", method = RequestMethod.GET)
    public ResponseEntity<List<Inventory>> getAllInventory(){
        return new ResponseEntity<>(inventoryService.getAllInventory(), HttpStatus.OK);
    }

    @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.GET)
    public ResponseEntity<Inventory> getInventoryById(@PathVariable long inventoryId){
        return new ResponseEntity<>(inventoryService.getInventoryById(inventoryId), HttpStatus.OK);
    }

    @RequestMapping(value = "/store/{storeId}/inventory", method = RequestMethod.GET)
    public ResponseEntity<List<Inventory>> getStoreInventory(@PathVariable long storeId){
        return new ResponseEntity<>(inventoryService.getStoreInventory(storeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/inventory/create", method = RequestMethod.PUT)
    public ResponseEntity<?> createInventory(@RequestBody CreateInventoryRequest request){
        return inventoryService.insert(request);
    }

    @RequestMapping(value = "/inventory/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateInventory(@RequestBody UpdateInventoryRequest request){
        return inventoryService.updateInventory(request);
    }

    @RequestMapping(value = "/inventory/{inventoryId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteInventory(@PathVariable long inventoryId){
        return inventoryService.deleteInventory(inventoryId);
    }
}
