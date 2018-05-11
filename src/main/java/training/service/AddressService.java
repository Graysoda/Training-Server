package training.service;

import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import java.util.List;

public interface AddressService {
	void insertAddress(CreateAddressRequest request);
	List<Address> getAllAddresses();
	void deleteAddress(long addressId);
	void updateAddress(UpdateAddressRequest request);
}
