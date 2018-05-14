package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.AddressDaoImpl;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
	@Autowired @Lazy private AddressDaoImpl addressDaoImpl;

	@Override
	@Transactional
	public void insertAddress(CreateAddressRequest request) {
		addressDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public List<Address> getAllAddresses() {
		return addressDaoImpl.getAll();
	}

	@Override
	@Transactional
	public void deleteAddress(long addressId) {
		addressDaoImpl.delete(addressId);
	}

	@Override
	@Transactional
	public void updateAddress(UpdateAddressRequest request) {
		addressDaoImpl.update(request);
	}
}
