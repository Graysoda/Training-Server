package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.CreateStoreRequest;
import training.generated.UpdateStoreRequest;
import training.persistence.dto.StoreDto;
import training.service.StoreService;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class StoreRestController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping("/stores")
    public ResponseEntity<?> getAllStores(){
        return ResponseEntity.ok(storeService.getAll());
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<?> getStoreById(@PathVariable int storeId){
        return storeService.exists(storeId) ? ResponseEntity.ok(storeService.getStoreById(storeId)) :
                ResponseEntity.badRequest().body("Store " + Constants.DNE);
    }

    @GetMapping("/store/{storeId}/address")
    public ResponseEntity<?> getStoreAddress(@PathVariable int storeId){
        return storeService.exists(storeId) ? ResponseEntity.ok(storeService.getStoreById(storeId).getAddress()) :
                ResponseEntity.badRequest().body("Store " + Constants.DNE);
    }

    @GetMapping("/store/{storeId}/manager")
    public ResponseEntity<?> getStoreManager(@PathVariable int storeId){
        return storeService.exists(storeId) ? ResponseEntity.ok(storeService.getStoreById(storeId).getManager()) :
                ResponseEntity.badRequest().body("Store " + Constants.DNE);
    }

    @PostMapping(value = "/store", consumes = "application/json")
    public ResponseEntity<?> createStore(@RequestBody CreateStoreRequest request){
        StoreDto store = new StoreDto(request);
        String error = validationHelper.validateStoreCreation(store);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(storeService.save(store)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/store/{storeId}", consumes = "application/json")
    public ResponseEntity<?> updateStore(@PathVariable int storeId, @RequestBody UpdateStoreRequest request){
        StoreDto store = new StoreDto(request);
        String error = validationHelper.validateStoreUpdate(storeId, store);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(storeService.save(store)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/store", consumes = "application/json")
    public ResponseEntity<?> updateStore(@RequestBody UpdateStoreRequest request){
        StoreDto store = new StoreDto(request);
        String error = validationHelper.validateStoreUpdate(store);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(storeService.save(store)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping(value = "/store/{storeId}")
    public ResponseEntity<?> deleteStore(@PathVariable int storeId){
        if (storeId <= 0 || !storeService.exists(storeId)){
            return ResponseEntity.badRequest().body("store id is invalid.");
        }
        storeService.delete(storeId);
        return ResponseEntity.ok("Store with id [" + storeId + "] was deleted.");
    }
}
