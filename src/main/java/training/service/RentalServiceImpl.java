package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.RentalDao;
import training.generated.CreateRentalRequest;
import training.generated.Rental;
import training.generated.UpdateRentalRequest;

import java.sql.SQLException;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService{
	@Autowired @Lazy private RentalDao rentalDao;

	@Override
	@Transactional
	public List<Rental> getRentalsByCustomerId(long id) throws SQLException {
		return rentalDao.getByCustomerId(id);
	}

	@Override
	@Transactional
	public Rental getRentalById(long id) throws SQLException {
		return rentalDao.getById(id);
	}

	@Override
	@Transactional
	public List<Rental> getRentalsByReturnDate(String date) throws SQLException {
		return rentalDao.getByReturnDate(date);
	}

	@Override
	@Transactional
	public List<Rental> getRentalByStaffId(long id) throws SQLException {
		return rentalDao.getByStaffId(id);
	}

	@Override
	@Transactional
	public List<Rental> getRentalByStartDate(String date) throws SQLException {
		return rentalDao.getByStartDate(date);
	}

	@Override
	@Transactional
	public void insertRental(CreateRentalRequest request) {
		rentalDao.insert(request);
	}

	@Override
	@Transactional
	public void deleteRental(long rentalId) {
		rentalDao.delete(rentalId);
	}

	@Override
	@Transactional
	public void updateRental(UpdateRentalRequest request) {
		rentalDao.update(request);
	}
}
