package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.RentalDao;
import soap.generated.CreateRentalRequest;
import soap.generated.Rental;
import soap.generated.UpdateRentalRequest;

import java.util.List;

@Service
public class RentalServiceImpl implements RentalService{
	private RentalDao rentalDao;

	@Autowired
	public void setRentalDao(RentalDao rentalDao) {
		this.rentalDao = rentalDao;
	}

	@Override
	@Transactional
	public List<Rental> getRentalsByCustomerId(long id) {
		return rentalDao.getByCustomerId(id);
	}

	@Override
	@Transactional
	public Rental getRentalById(long id) {
		return rentalDao.getById(id);
	}

	@Override
	@Transactional
	public List<Rental> getRentalsByReturnDate(String date) {
		return rentalDao.getByReturnDate(date);
	}

	@Override
	@Transactional
	public List<Rental> getRentalByStaffId(long id) {
		return rentalDao.getByStaffId(id);
	}

	@Override
	@Transactional
	public List<Rental> getRentalByStartDate(String date) {
		return rentalDao.getByStartDate(date);
	}

	@Override
	public void insertRental(CreateRentalRequest request) {
		rentalDao.insert(request);
	}

	@Override
	public void deleteRental(long rentalId) {
		rentalDao.delete(rentalId);
	}

	@Override
	public void updateRental(UpdateRentalRequest request) {
		rentalDao.update(request);
	}
}
