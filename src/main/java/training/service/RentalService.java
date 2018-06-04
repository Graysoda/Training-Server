package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;

import java.util.List;

@Service
public interface RentalService {
	@Transactional
	List<Rental> getRentalsByCustomerId(long id);
	@Transactional
	Rental getRentalById(long id);
	@Transactional
	List<Rental> getRentalsByReturnDate(String date);
	@Transactional
	List<Rental> getRentalByStaffId(long id);
	@Transactional
	List<Rental> getRentalByStartDate(String date);
	@Transactional
	ResponseEntity<?> insertRental(CreateRentalRequest request);
	@Transactional
	ResponseEntity<?> deleteRental(long rentalId);
	@Transactional
	ResponseEntity<?> updateRental(UpdateRentalRequest request);
	@Transactional
	List<Rental> getAllRentals();
}
