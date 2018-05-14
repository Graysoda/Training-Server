package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import training.service.RentalServiceImpl;

@RestController
public class RentalController {
    @Autowired @Lazy private RentalServiceImpl rentalService;

    @RequestMapping(value = "/rentals/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRentals(){
        return new ResponseEntity<>(rentalService.getAllRentals(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{customerId}/rentals/", method = RequestMethod.GET)
    public ResponseEntity<?> getRentalsByCustomerId(@PathVariable("customerId") long customerId){
        return new ResponseEntity<>(rentalService.getRentalsByCustomerId(customerId),HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/return/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getRentalsByReturnDate(@PathVariable("date") String date){
        return new ResponseEntity<>(rentalService.getRentalsByReturnDate(date), HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/start/{date}",method = RequestMethod.GET)
    public ResponseEntity<?> getRentalsByStartDate(@PathVariable("date")String date){
        return new ResponseEntity<>(rentalService.getRentalByStartDate(date), HttpStatus.OK);
    }

    @RequestMapping(value = "/staff/{staffId}/rentals")
    public ResponseEntity<?> getRentalsByStaffId(@PathVariable("staffId")long staffId){
        return new ResponseEntity<>(rentalService.getRentalByStaffId(staffId), HttpStatus.OK);
    }
}