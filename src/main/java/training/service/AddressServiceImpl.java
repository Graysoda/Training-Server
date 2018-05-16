package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> insertAddress(CreateAddressRequest request) {
		return addressDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public List<Address> getAllAddresses() {
		return addressDaoImpl.getAll();
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteAddress(long addressId) {
		return addressDaoImpl.delete(addressId);
	}

	@Override
	@Transactional
	public ResponseEntity<?> updateAddress(UpdateAddressRequest request) {
		return addressDaoImpl.update(request);
	}

	@Override
	@Transactional
	public Address getAddressById(long addressId) {
		return addressDaoImpl.getById(addressId);
	}

	@Override
	@Transactional
	public List<Address> getAddressByCity(String city) {
		return addressDaoImpl.getByCity(city);
	}

	@Override
	@Transactional
	public List<Address> getAddressByPostalCode(String postalCode) {
		return addressDaoImpl.getByPostalCode(postalCode);
	}
}
