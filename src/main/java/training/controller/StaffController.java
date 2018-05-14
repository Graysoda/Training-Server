package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Staff;
import training.service.StaffServiceImpl;

import java.util.List;

@RestController
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
}
