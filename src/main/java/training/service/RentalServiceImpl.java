package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.RentalDaoImpl;
import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService{
	@Autowired @Lazy private RentalDaoImpl rentalDaoImpl;

	@Override
	@Transactional
	public List<Rental> getRentalsByCustomerId(long id) {
		return rentalDaoImpl.getByCustomerId(id);
	}

	@Override
	@Transactional
	public Rental getRentalById(long id) {
		return rentalDaoImpl.getById(id);
	}

	@Override
	@Transactional
	public List<Rental> getRentalsByReturnDate(String date) {
		return rentalDaoImpl.getByReturnDate(date);
	}

	@Override
	@Transactional
	public List<Rental> getRentalByStaffId(long id) {
		return rentalDaoImpl.getByStaffId(id);
	}

	@Override
	@Transactional
	public List<Rental> getRentalByStartDate(String date) {
		return rentalDaoImpl.getByStartDate(date);
	}

	@Override
	@Transactional
	public void insertRental(CreateRentalRequest request) {
		rentalDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public void deleteRental(long rentalId) {
		rentalDaoImpl.delete(rentalId);
	}

	@Override
	@Transactional
	public void updateRental(UpdateRentalRequest request) {
		rentalDaoImpl.update(request);
	}

	@Override
	@Transactional
	public List<Rental> getAllRentals() {
		return rentalDaoImpl.getAll();
	}
}
