package training.database.dao;

import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import java.util.List;

public interface AddressDao {
    Address getById(long id);
    List<Address> getAll();
    void delete(long addressId);
    void update(UpdateAddressRequest request);
    void insert(CreateAddressRequest request);
}
