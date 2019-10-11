package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Store;
import training.persistence.dao.AddressDao;
import training.persistence.dao.StaffDao;
import training.persistence.dao.StoreDao;
import training.persistence.dto.StoreDto;
import training.persistence.entity.Address;
import training.persistence.entity.Staff;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StoreService {
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private AddressDao addressDao;

    public boolean exists(int storeId){
        return storeDao.existsById(storeId);
    }

    public Store getStoreById(int storeId) {
        return storeDao.findById(storeId).orElseThrow(RuntimeException::new).makeGenerated();
    }

    public List<Store> getAll() {
        return convert(storeDao.findAll());
    }

    private List<Store> convert(Iterable<training.persistence.entity.Store> all) {
        List<Store> stores = new ArrayList<>();
        all.forEach(s -> stores.add(s.makeGenerated()));
        return stores;
    }

    public Store save(StoreDto store){
        training.persistence.entity.Store entity;

        if (Objects.nonNull(store.getId())){
            training.persistence.entity.Store s = storeDao.findById(store.getId())
                    .orElseThrow(() -> new EntityNotFoundException(store.getId().toString()));

            if (Objects.nonNull(store.getAddress())){
                s.setAddress(addressDao.findById(store.getAddress())
                        .orElseThrow(() -> new EntityNotFoundException(store.getAddress().toString())));
            }
            if (Objects.nonNull(store.getStaff())){
                s.setManager(staffDao.findById(store.getStaff())
                        .orElseThrow(() -> new EntityNotFoundException(store.getStaff().toString())));
            }

            entity = store.makeEntity(s);
        } else {
            Address address = addressDao.findById(store.getAddress())
                    .orElseThrow(() -> new EntityNotFoundException(store.getAddress().toString()));
            Staff staff = staffDao.findById(store.getStaff())
                    .orElseThrow(() -> new EntityNotFoundException(store.getStaff().toString()));

            entity = store.makeEntity(address, staff);
        }

        return storeDao.save(entity).makeGenerated();
    }

    public void delete(int storeId) {
        storeDao.deleteById(storeId);
    }
}
