package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Staff;
import training.persistence.dao.AddressDao;
import training.persistence.dao.StaffDao;
import training.persistence.dao.StoreDao;
import training.persistence.dto.StaffDto;
import training.persistence.entity.Address;
import training.persistence.entity.Store;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StaffService {
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private AddressDao addressDao;
    @Autowired
    private StoreDao storeDao;

    public boolean exists(int staffId) {
        return staffDao.existsById(staffId);
    }

    public List<Staff> getAllStaff() {
        return convert(staffDao.findAll());
    }

    private List<Staff> convert(Iterable<training.persistence.entity.Staff> all) {
        List<Staff> staff = new ArrayList<>();
        all.forEach(s -> staff.add(s.makeGenerated()));
        return staff;
    }

    public Staff getStaffById(int staffId) {
        return staffDao.findById(staffId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(staffId))).makeGenerated();
    }

    public List<Staff> getStaffByStoreId(int storeId) {
        return staffDao.findAllByStoreId(storeId);
    }

    public Staff save(StaffDto staff) {
        training.persistence.entity.Staff entity;

        if (Objects.nonNull(staff.getId())){
            training.persistence.entity.Staff s = staffDao.findById(staff.getId())
                    .orElseThrow(() -> new EntityNotFoundException(staff.getId().toString()));

            if (Objects.nonNull(staff.getAddress())){
                s.setAddress(addressDao.findById(staff.getAddress())
                        .orElseThrow(() -> new EntityNotFoundException(staff.getAddress().toString())));
            }
            if (Objects.nonNull(staff.getStore())){
                s.setStore(storeDao.findById(staff.getStore())
                        .orElseThrow(() -> new EntityNotFoundException(staff.getStore().toString())));
            }

            entity = staff.makeEntity(s);
        } else {
            Address address = addressDao.findById(staff.getId())
                    .orElseThrow(() -> new EntityNotFoundException(staff.getAddress().toString()));
            Store store = storeDao.findById(staff.getStore())
                    .orElseThrow(() -> new EntityNotFoundException(staff.getStore().toString()));

            entity = staff.makeEntity(address, store);
        }

        return staffDao.save(entity).makeGenerated();
    }

    public void delete(int staffId) {
        staffDao.deleteById(staffId);
    }

    public List<Staff> getActiveStaff() {
        return convert(staffDao.findActive());
    }

    public List<Staff> getInactiveStaff() {
        return convert(staffDao.findInactive());
    }
}
