package training.database.dao;

import org.springframework.http.ResponseEntity;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;

import java.util.List;

public interface AddressDao {
    Address getById(long id);
    List<Address> getAll();
    ResponseEntity<?> delete(long addressId);
    ResponseEntity<?> update(UpdateAddressRequest request);
    ResponseEntity<?> insert(CreateAddressRequest request);
    List<Address> getByCity(String city);
    List<Address> getByPostalCode(String postalCode);
}
