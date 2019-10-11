package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.ValidationHelper;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;
import training.persistence.dto.StaffDto;
import training.service.StaffService;
import training.service.StoreService;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class StaffRestController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping("/staff")
    public ResponseEntity<List<Staff>> getAllStaff(){
        return ResponseEntity.ok(staffService.getAllStaff());
    }

    @GetMapping("/staff/{staffId}")
    public ResponseEntity<?> getStaffById(@PathVariable int staffId){
        if (!staffService.exists(staffId)){
            return ResponseEntity.badRequest().body("staff id invalid");
        }
        return ResponseEntity.ok(staffService.getStaffById(staffId));
    }

    @GetMapping("/store/{storeId}/staff")
    public ResponseEntity<?> getStoreStaff(@PathVariable int storeId){
        if (!storeService.exists(storeId)){
            return ResponseEntity.badRequest().body("store id is invalid");
        }
        return ResponseEntity.ok(staffService.getStaffByStoreId(storeId));
    }

    @GetMapping("/staff/active")
    public ResponseEntity<List<Staff>> getActiveStaff(){
        return ResponseEntity.ok(staffService.getActiveStaff());
    }

    @GetMapping("/staff/inactive")
    public ResponseEntity<List<Staff>> getInactiveStaff(){
        return ResponseEntity.ok(staffService.getInactiveStaff());
    }

    @PostMapping(value = "/staff", consumes = "application/json")
    public ResponseEntity<?> createStaff(@RequestBody CreateStaffRequest request){
        StaffDto staff = new StaffDto(request);
        String error = validationHelper.validateStaffCreation(staff);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(staffService.save(staff)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/staff/{staffId}", consumes = "application/json")
    public ResponseEntity<?> updateStaff(@PathVariable int staffId, @RequestBody UpdateStaffRequest request){
        StaffDto staff = new StaffDto(request);
        String error = validationHelper.validateStaffUpdate(staffId, staff);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(staffService.save(staff)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/staff", consumes = "application/json")
    public ResponseEntity<?> updateStaff(@RequestBody UpdateStaffRequest request){
        StaffDto staff = new StaffDto(request);
        String error = validationHelper.validateStaffUpdate(staff);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(staffService.save(staff)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping("/staff/{staffId}")
    public ResponseEntity<?> deleteStaff(@PathVariable int staffId){
        if (staffId <= 0 || !staffService.exists(staffId)){
            return ResponseEntity.badRequest().body("staff id is invalid.");
        }
        staffService.delete(staffId);
        return ResponseEntity.ok("staff with id [" + staffId + "] was deleted.");
    }
}
