package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Inventory;
import training.service.InventoryServiceImpl;

import java.util.List;

@RestController
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

    @RequestMapping(value = "/inventory/{storeId}", method = RequestMethod.GET)
    public ResponseEntity<List<Inventory>> getStoreInventory(@PathVariable long storeId){
        return new ResponseEntity<>(inventoryService.getStoreInventory(storeId), HttpStatus.OK);
    }
}
