package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.rest.jsonObjects.InventoryJson;
import training.generated.CreateInventoryRequest;
import training.generated.Inventory;
import training.generated.UpdateInventoryRequest;
import training.service.impl.InventoryServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class InventoryRestController {
    @Autowired @Lazy private InventoryServiceImpl inventoryService;

    @RequestMapping(value = "/inventory", method = RequestMethod.GET)
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

    @RequestMapping(value = "/inventory", method = RequestMethod.POST)
    public ResponseEntity<?> createInventory(@RequestBody InventoryJson inventoryJson){
        CreateInventoryRequest request = new CreateInventoryRequest();

        if (inventoryJson.getFilmId() != null)
            request.setFilmId(inventoryJson.getFilmId());
        else
            return ResponseEntity.badRequest().body("Inventory filmId cannot be null.");

        if (inventoryJson.getStoreId() != null)
            request.setStoreId(inventoryJson.getStoreId());
        else
            return ResponseEntity.badRequest().body("Inventory storeId canoot be null.");

        return inventoryService.insert(request);
    }

    @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateInventory(@PathVariable long inventoryId, @RequestBody InventoryJson inventoryJson){
        UpdateInventoryRequest request = new UpdateInventoryRequest();

        request.setInventoryId(inventoryId);

        if (inventoryJson.getFilmId() != null)
            request.setFilmId(inventoryJson.getFilmId());
        if (inventoryJson.getStoreId() != null)
            request.setStoreId(inventoryJson.getStoreId());

        return inventoryService.updateInventory(request);
    }

    @RequestMapping(value = "/inventory/{inventoryId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteInventory(@PathVariable long inventoryId){
        return inventoryService.deleteInventory(inventoryId);
    }
}
