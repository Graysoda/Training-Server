package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.CreateRentalRequest;
import training.generated.UpdateRentalRequest;
import training.service.RentalServiceImpl;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class RentalController {
    @Autowired @Lazy private RentalServiceImpl rentalService;

    @RequestMapping(value = "/rentals/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRentals(){
        return new ResponseEntity<>(rentalService.getAllRentals(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{customerId}/rentals/", method = RequestMethod.GET)
    public ResponseEntity<?> getRentalsByCustomerId(@PathVariable long customerId){
        return new ResponseEntity<>(rentalService.getRentalsByCustomerId(customerId),HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/return/{date}", method = RequestMethod.GET)
    public ResponseEntity<?> getRentalsByReturnDate(@PathVariable String date){
        return new ResponseEntity<>(rentalService.getRentalsByReturnDate(date), HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/start/{date}",method = RequestMethod.GET)
    public ResponseEntity<?> getRentalsByStartDate(@PathVariable String date){
        return new ResponseEntity<>(rentalService.getRentalByStartDate(date), HttpStatus.OK);
    }

    @RequestMapping(value = "/staff/{staffId}/rentals", method = RequestMethod.GET)
    public ResponseEntity<?> getRentalsByStaffId(@PathVariable long staffId){
        return new ResponseEntity<>(rentalService.getRentalByStaffId(staffId), HttpStatus.OK);
    }

    @RequestMapping(value = "/rentals/create", method = RequestMethod.PUT)
    public ResponseEntity<?> createRental(@RequestBody CreateRentalRequest request){
        return rentalService.insertRental(request);
    }

    @RequestMapping(value = "/rentals/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateRental(@RequestBody UpdateRentalRequest request){
        return rentalService.updateRental(request);
    }

    @RequestMapping(value = "/rentals/{rentalId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRental(@PathVariable long rentalId){
        return rentalService.deleteRental(rentalId);
    }
}
