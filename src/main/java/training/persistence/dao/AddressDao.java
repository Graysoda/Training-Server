package training.persistence.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Address;

import java.util.List;

@Repository
public interface AddressDao extends CrudRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.city.name=?1")
    Iterable<Address> findByCity(String city);

    List<Address> findByPostalCode(String postalCode);

    @Query("SELECT a FROM Address a WHERE a.city.id=?1")
    Iterable<Address> findByCityId(int cityId);

    boolean existsByAddress(String address);
}
