package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import java.util.List;

public interface AddressService {
	ResponseEntity<?> insertAddress(CreateAddressRequest request);
	List<Address> getAllAddresses();
	ResponseEntity<?> deleteAddress(long addressId);
	ResponseEntity<?> updateAddress(UpdateAddressRequest request);
}
