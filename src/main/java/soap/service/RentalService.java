package soap.service;

import soap.generated.CreateRentalRequest;
import soap.generated.Rental;
import soap.generated.UpdateRentalRequest;

import java.util.List;

public interface RentalService {
	List<Rental> getRentalsByCustomerId(long id);
	Rental getRentalById(long id);
	List<Rental> getRentalsByReturnDate(String date);
	List<Rental> getRentalByStaffId(long id);
	List<Rental> getRentalByStartDate(String date);
	void insertRental(CreateRentalRequest request);
	void deleteRental(long rentalId);
	void updateRental(UpdateRentalRequest request);
}
