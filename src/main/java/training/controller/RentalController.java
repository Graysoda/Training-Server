package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.controller.jsonObjects.RentalJson;
import training.generated.CreateRentalRequest;
import training.generated.UpdateRentalRequest;
import training.service.impl.RentalServiceImpl;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class RentalController {
    @Autowired @Lazy private RentalServiceImpl rentalService;

    @RequestMapping(value = "/rentals", method = RequestMethod.GET)
    public ResponseEntity<?> getAllRentals(){
        return new ResponseEntity<>(rentalService.getAllRentals(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/{customerId}/rentals", method = RequestMethod.GET)
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

    @RequestMapping(value = "/rentals", method = RequestMethod.POST)
    public ResponseEntity<?> createRental(@RequestBody RentalJson rentalJson){
        CreateRentalRequest request = new CreateRentalRequest();

        if (rentalJson.getCustomerId() != null)
            request.setCustomerId(rentalJson.getCustomerId());
        else
            return ResponseEntity.badRequest().body("Rental customerId cannot be null.");

        if (rentalJson.getInventoryId() != null)
            request.setInventoryId(rentalJson.getInventoryId());
        else
            return ResponseEntity.badRequest().body("Rental inventoryId cannot be null.");

        if (rentalJson.getRentalDate() != null)
            request.setRentalDate(rentalJson.getRentalDate());
        else
            return ResponseEntity.badRequest().body("Rental rentalDate cannot be null.");

        if (rentalJson.getReturnDate() != null)
            request.setReturnDate(rentalJson.getReturnDate());
        else
            return ResponseEntity.badRequest().body("Rental returnDate cannot be null.");

        if (rentalJson.getStaffId() != null)
            request.setStaffId(rentalJson.getStaffId());
        else
            return ResponseEntity.badRequest().body("Rental staffId cannot be null.");

        return rentalService.insertRental(request);
    }

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateRental(@PathVariable long rentalId, @RequestBody RentalJson rentalJson){
        UpdateRentalRequest request = new UpdateRentalRequest();

        request.setRentalId(rentalId);

        if (rentalJson.getCustomerId() != null)
            request.setCustomerId(rentalJson.getCustomerId());
        if (rentalJson.getInventoryId() != null)
            request.setInventoryId(rentalJson.getInventoryId());
        if (rentalJson.getRentalDate() != null)
            request.setRentalDate(rentalJson.getRentalDate());
        if (rentalJson.getReturnDate() != null)
            request.setReturnDate(rentalJson.getReturnDate());
        if (rentalJson.getStaffId() != null)
            request.setStaffId(rentalJson.getStaffId());

        return rentalService.updateRental(request);
    }

    @RequestMapping(value = "/rentals/{rentalId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteRental(@PathVariable long rentalId){
        return rentalService.deleteRental(rentalId);
    }
}
