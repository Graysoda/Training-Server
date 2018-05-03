package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.AddressDao;
import soap.generated.Address;
import soap.generated.CreateAddressRequest;
import soap.generated.UpdateAddressRequest;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
	private AddressDao addressDao;

	@Autowired
	public void setAddressDao(AddressDao addressDao){
		this.addressDao = addressDao;
	}

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
	public void deleteAddress(long addressId) {
		addressDao.delete(addressId);
	}

	@Override
	public void updateAddress(UpdateAddressRequest request) {
		addressDao.update(request);
	}
}
