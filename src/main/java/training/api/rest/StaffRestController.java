package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.rest.jsonObjects.StaffJson;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;
import training.service.impl.StaffServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class StaffRestController {
    @Autowired @Lazy private StaffServiceImpl staffService;

    @RequestMapping(value = "/staff", method = RequestMethod.GET)
    public ResponseEntity<List<Staff>> getAllStaff(){
        return new ResponseEntity<>(staffService.getAllStaff(),HttpStatus.OK);
    }

    @RequestMapping(value = "/staff/{staffId}", method = RequestMethod.GET)
    public ResponseEntity<Staff> getStaffById(@PathVariable long staffId){
        return new ResponseEntity<>(staffService.getStaffById((int) staffId), HttpStatus.OK);
    }

    @RequestMapping(value = "/staff", method = RequestMethod.POST)
    public ResponseEntity<?> createStaff(@RequestBody StaffJson staffJson){
        CreateStaffRequest request = new CreateStaffRequest();

        if (staffJson.getActive() != null)
            request.setActive(staffJson.getActive());
        else
            return ResponseEntity.badRequest().body("Staff active cannot be null.");

        if (staffJson.getAddressId() != null)
            request.setAddressId(staffJson.getAddressId());
        else
            return ResponseEntity.badRequest().body("Staff addressId cannot be null.");

        if (staffJson.getEmail() != null)
            request.setEmail(staffJson.getEmail());
        else
            return ResponseEntity.badRequest().body("Staff email cannot be null.");

        if (staffJson.getFirstName() != null)
            request.setFirstName(staffJson.getFirstName());
        else
            return ResponseEntity.badRequest().body("Staff firstName cannot be null.");

        if (staffJson.getLastName() != null)
            request.setLastName(staffJson.getLastName());
        else
            return ResponseEntity.badRequest().body("Staff lastName cannot be null.");

        if (staffJson.getUsername() != null)
            request.setUsername(staffJson.getUsername());
        else
            return ResponseEntity.badRequest().body("Staff username cannot be null.");

        if (staffJson.getPassword() != null)
            request.setPassword(staffJson.getPassword());
        else
            return ResponseEntity.badRequest().body("Staff password cannot be null.");

        if (staffJson.getStoreId() != null)
            request.setStoreId(staffJson.getStoreId());
        else
            return ResponseEntity.badRequest().body("Staff storeId cannot be null.");

        return staffService.insertStaff(request);
    }

    @RequestMapping(value = "/staff/{staffId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateStaff(@PathVariable long staffId, @RequestBody StaffJson staffJson){
        UpdateStaffRequest request = new UpdateStaffRequest();

        request.setStaffId(staffId);

        if (staffJson.getActive() != null)
            request.setActive(staffJson.getActive());
        if (staffJson.getAddressId() != null)
            request.setAddressId(staffJson.getAddressId());
        if (staffJson.getEmail() != null)
            request.setEmail(staffJson.getEmail());
        if (staffJson.getFirstName() != null)
            request.setFirstName(staffJson.getFirstName());
        if (staffJson.getLastName() != null)
            request.setLastName(staffJson.getLastName());
        if (staffJson.getUsername() != null)
            request.setUsername(staffJson.getUsername());
        if (staffJson.getPassword() != null)
            request.setPassword(staffJson.getPassword());
        if (staffJson.getStoreId() != null)
            request.setStoreId(staffJson.getStoreId());

        return staffService.updateStaff(request);
    }

    @RequestMapping(value = "/staff/{staffId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStaff(@PathVariable long staffId){
        return staffService.deleteStaff(staffId);
    }
}
