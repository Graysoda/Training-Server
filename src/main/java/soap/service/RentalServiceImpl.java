package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.RentalDao;
import soap.generated.CreateRentalRequest;
import soap.generated.Rental;
import soap.generated.UpdateRentalRequest;

import java.sql.SQLException;
import java.util.List;

@Service
public class RentalServiceImpl implements RentalService{
	@Autowired private RentalDao rentalDao;

//	@Autowired
//	public void setRentalDao(@Lazy RentalDao rentalDao) {
//		this.rentalDao = rentalDao;
//	}

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
