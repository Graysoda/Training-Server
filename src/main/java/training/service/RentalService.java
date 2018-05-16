package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;

import java.util.List;

public interface RentalService {
	List<Rental> getRentalsByCustomerId(long id);
	Rental getRentalById(long id);
	List<Rental> getRentalsByReturnDate(String date);
	List<Rental> getRentalByStaffId(long id);
	List<Rental> getRentalByStartDate(String date);
	ResponseEntity<?> insertRental(CreateRentalRequest request);
	ResponseEntity<?> deleteRental(long rentalId);
	ResponseEntity<?> updateRental(UpdateRentalRequest request);
    List<Rental> getAllRentals();
}
