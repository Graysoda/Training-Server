package soap.service;

import soap.generated.Address;
import soap.generated.CreateAddressRequest;
import soap.generated.UpdateAddressRequest;

import java.util.List;

public interface AddressService {
	void insertAddress(CreateAddressRequest request);
	List<Address> getAllAddresses();
	void deleteAddress(long addressId);
	void updateAddress(UpdateAddressRequest request);
}
