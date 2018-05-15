package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.CreateStaffRequest;
import training.generated.Staff;
import training.generated.UpdateStaffRequest;
import training.service.StaffServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class StaffController {
    @Autowired @Lazy private StaffServiceImpl staffService;

    @RequestMapping(value = "/staff/", method = RequestMethod.GET)
    public ResponseEntity<List<Staff>> getAllStaff(){
        return new ResponseEntity<>(staffService.getAllStaff(),HttpStatus.OK);
    }

    @RequestMapping(value = "/staff/{staffId}/", method = RequestMethod.GET)
    public ResponseEntity<Staff> getStaffById(@PathVariable long staffId){
        return new ResponseEntity<>(staffService.getStaffById((int) staffId), HttpStatus.OK);
    }

    @RequestMapping(value = "/staff/create", method = RequestMethod.PUT)
    public ResponseEntity<?> createStaff(@RequestBody CreateStaffRequest request){
        return staffService.insertStaff(request);
    }

    @RequestMapping(value = "/staff/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateStaff(@RequestBody UpdateStaffRequest request){
        return staffService.updateStaff(request);
    }

    @RequestMapping(value = "/staff/{staffId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteStaff(@PathVariable long staffId){
        return staffService.deleteStaff(staffId);
    }
}
