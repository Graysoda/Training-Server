package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import training.database.dao.impl.AddressDaoImpl;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;
import training.service.AddressService;

import java.util.List;

public class AddressServiceImpl implements AddressService {
	@Autowired @Lazy private AddressDaoImpl addressDaoImpl;

	@Override
	public ResponseEntity<?> insertAddress(CreateAddressRequest request) {
		return addressDaoImpl.insert(request);
	}

	@Override
	public List<Address> getAllAddresses() {
		return addressDaoImpl.getAll();
	}

	@Override
	public ResponseEntity<?> deleteAddress(long addressId) {
		return addressDaoImpl.delete(addressId);
	}

	@Override
	public ResponseEntity<?> updateAddress(UpdateAddressRequest request) {
		return addressDaoImpl.update(request);
	}

	@Override
	public Address getAddressById(long addressId) {
		return addressDaoImpl.getById(addressId);
	}

	@Override
	public List<Address> getAddressByCity(String city) {
		return addressDaoImpl.getByCity(city);
	}

	@Override
	public List<Address> getAddressByPostalCode(String postalCode) {
		return addressDaoImpl.getByPostalCode(postalCode);
	}
}
