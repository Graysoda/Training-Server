package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Address;
import training.persistence.dao.AddressDao;
import training.persistence.dao.CityDao;
import training.persistence.dto.AddressDto;
import training.persistence.entity.City;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AddressService {
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private CityDao cityDao;


    public List<Address> getAllAddresses() {
        return convert(addressDao.findAll());
    }

    private List<Address> convert(Iterable<training.persistence.entity.Address> addressIterable) {
        List<Address> addresses = new ArrayList<>();
        addressIterable.forEach(address -> addresses.add(address.makeGenerated()));
        return addresses;
    }

    public Address getAddressById(int addressId) {
        return addressDao.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(addressId))).makeGenerated();
    }

    public void deleteById(long addressId) {
        addressDao.deleteById(Math.toIntExact(addressId));
    }

    public List<Address> getAddressByPostalCode(String postalCode) {
        return convert(addressDao.findByPostalCode(postalCode));
    }

    public List<Address> getAddressByCity(String cityName) {
        return convert(addressDao.findByCity(cityName));
    }

    public Address save(AddressDto address) {
        training.persistence.entity.Address entity;

        if (Objects.nonNull(address.getId())){
            training.persistence.entity.Address a = addressDao.findById(address.getId())
                    .orElseThrow(() -> new EntityNotFoundException(address.getId().toString()));

            if (Objects.nonNull(address.getCity())){
                a.setCity(cityDao.findById(address.getCity())
                        .orElseThrow(() -> new EntityNotFoundException(address.getCity().toString())));
            }

            entity = address.makeEntity(a);
        } else {
            City city = cityDao.findById(address.getCity())
                    .orElseThrow(() -> new EntityNotFoundException(address.getCity().toString()));
            entity = address.makeEntity(city);
        }
        return addressDao.save(entity).makeGenerated();
    }

    public boolean exists(int addressId) {
        return addressDao.existsById(addressId);
    }

    public List<Address> getAddressByCityId(int cityId) {
        return convert(addressDao.findByCityId(cityId));
    }

    public boolean existsByAddress(String address) {
        return addressDao.existsByAddress(address);
    }
}
