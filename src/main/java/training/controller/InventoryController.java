package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Inventory;
import training.service.InventoryServiceImpl;

import java.util.List;

@RestController
public class InventoryController {
    @Autowired @Lazy private InventoryServiceImpl inventoryService;

    @RequestMapping(value = "/inventory/", method = RequestMethod.GET)
    public List<Inventory> getAllInventory(){
        return inventoryService.getAllInventory();
    }

    @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.GET)
    public Inventory getInventoryById(@RequestParam(name = "inventoryId")long inventoryId){
        return inventoryService.getInventoryById(inventoryId);
    }

    @RequestMapping(value = "/inventory/{storeId}", method = RequestMethod.GET)
    public List<Inventory> getStoreInventory(@RequestParam(name = "storeId")long storeId){
        return inventoryService.getStoreInventory(storeId);
    }
}
