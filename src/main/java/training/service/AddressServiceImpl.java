package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.AddressDao;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
	@Autowired @Lazy private AddressDao addressDao;

	@Override
	@Transactional
	public void insertAddress(CreateAddressRequest request) {
		addressDao.insert(request);
	}

	@Override
	@Transactional
	public List<Address> getAllAddresses() {
		return addressDao.getAll();
	}

	@Override
	@Transactional
	public void deleteAddress(long addressId) {
		addressDao.delete(addressId);
	}

	@Override
	@Transactional
	public void updateAddress(UpdateAddressRequest request) {
		addressDao.update(request);
	}
}