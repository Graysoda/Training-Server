package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import training.database.dao.impl.RentalDaoImpl;
import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;
import training.service.RentalService;

import java.util.List;

public class RentalServiceImpl implements RentalService {
	@Autowired @Lazy private RentalDaoImpl rentalDaoImpl;

	@Override
	public List<Rental> getRentalsByCustomerId(long id) {
		return rentalDaoImpl.getByCustomerId(id);
	}

	@Override
	public Rental getRentalById(long id) {
		return rentalDaoImpl.getById(id);
	}

	@Override
	public List<Rental> getRentalsByReturnDate(String date) {
		return rentalDaoImpl.getByReturnDate(date);
	}

	@Override
	public List<Rental> getRentalByStaffId(long id) {
		return rentalDaoImpl.getByStaffId(id);
	}

	@Override
	public List<Rental> getRentalByStartDate(String date) {
		return rentalDaoImpl.getByStartDate(date);
	}

	@Override
	public ResponseEntity<?> insertRental(CreateRentalRequest request) {
		return rentalDaoImpl.insert(request);
	}

	@Override
	public ResponseEntity<?> deleteRental(long rentalId) {
		return rentalDaoImpl.delete(rentalId);
	}

	@Override
	public ResponseEntity<?> updateRental(UpdateRentalRequest request) {
		return rentalDaoImpl.update(request);
	}

	@Override
	public List<Rental> getAllRentals() {
		return rentalDaoImpl.getAll();
	}
}
