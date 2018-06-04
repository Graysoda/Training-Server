package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import java.util.List;

@Service
public interface AddressService {
	@Transactional
	ResponseEntity<?> insertAddress(CreateAddressRequest request);
	@Transactional
	List<Address> getAllAddresses();
	@Transactional
	ResponseEntity<?> deleteAddress(long addressId);
	@Transactional
	ResponseEntity<?> updateAddress(UpdateAddressRequest request);
	@Transactional
    Address getAddressById(long addressId);
	@Transactional
	List<Address> getAddressByCity(String city);
	@Transactional
	List<Address> getAddressByPostalCode(String postalCode);
}
