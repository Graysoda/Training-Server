package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;
import training.persistence.dto.RentalDto;
import training.service.CustomerService;
import training.service.RentalService;
import training.service.StaffService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class RentalRestController {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getAllRentals(){
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    @GetMapping("/rentals/{rentalId}")
    public ResponseEntity<?> getRentalById(@PathVariable int rentalId){
        if (!rentalService.exists(rentalId)){
            return ResponseEntity.badRequest().body("rental " + Constants.DNE);
        }
        return ResponseEntity.ok(rentalService.getRentalById(rentalId));
    }

    @GetMapping("/customer/{customerId}/rentals")
    public ResponseEntity<?> getCustomerRentals(@PathVariable int customerId){
        if (!customerService.exists(customerId)){
            return ResponseEntity.badRequest().body("customer " + Constants.DNE);
        }
        return ResponseEntity.ok(rentalService.getCustomerRentals(customerId));
    }

    @GetMapping("/rentals/start/{date}")
    public ResponseEntity<?> getRentalsByStartDate(@PathVariable String date){
        if (Objects.isNull(Constants.formatString(date))){
            return ResponseEntity.badRequest().body("date is invalid format should be [" + Constants.DATE_FORMAT + "]");
        }
        return ResponseEntity.ok(rentalService.getRentalsByStartDate(Constants.formatString(date)));
    }

    @GetMapping("/rentals/return/{date}")
    public ResponseEntity<?> getRentalsByReturnDate(@PathVariable String date){
        if (Objects.isNull(Constants.formatString(date))){
            return ResponseEntity.badRequest().body("date is invalid format should be [" + Constants.DATE_FORMAT + "]");
        }
        return ResponseEntity.ok(rentalService.getRentalsByReturnDate(Constants.formatString(date)));
    }

    @GetMapping("/staff/{staffId}/rentals")
    public ResponseEntity<?> getStaffRentals(@PathVariable int staffId){
        if (!staffService.exists(staffId)){
            return ResponseEntity.badRequest().body("staff does not exist.");
        }
        return ResponseEntity.ok(rentalService.getStaffRentals(staffId));
    }

    @PostMapping(value = "/rentals", consumes = "application/json")
    public ResponseEntity<?> createRental(@RequestBody CreateRentalRequest request){
        RentalDto rental = new RentalDto(request);
        String error = validationHelper.validateRentalCreation(rental);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(rentalService.save(rental)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/rentals/{rentalId}", consumes = "application/json")
    public ResponseEntity<?> updateRental(@PathVariable int rentalId, @RequestBody UpdateRentalRequest request){
        RentalDto rental = new RentalDto(request);
        String error = validationHelper.validateRentalUpdate(rentalId, rental);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(rentalService.save(rental)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/rentals", consumes = "application/json")
    public ResponseEntity<?> updateRental(@RequestBody UpdateRentalRequest request){
        RentalDto rental = new RentalDto(request);
        String error = validationHelper.validateRentalUpdate(rental);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(rentalService.save(rental)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping("/rentals/{rentalId}")
    public ResponseEntity<?> deleteRental(@PathVariable int rentalId){
        if (rentalId <= 0 || !rentalService.exists(rentalId)){
            return ResponseEntity.badRequest().body("rental id is invalid.");
        }
        rentalService.delete(rentalId);
        return ResponseEntity.ok("rental with id [" + rentalId + "] was deleted.");
    }
}
