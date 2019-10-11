package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Rental;
import training.persistence.dao.CustomerDao;
import training.persistence.dao.InventoryDao;
import training.persistence.dao.RentalDao;
import training.persistence.dao.StaffDao;
import training.persistence.dto.RentalDto;
import training.persistence.entity.Customer;
import training.persistence.entity.Inventory;
import training.persistence.entity.Staff;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class RentalService {
    @Autowired
    private RentalDao rentalDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private InventoryDao inventoryDao;

    public boolean exists(int rentalId) {
        return rentalDao.existsById(rentalId);
    }

    public List<Rental> getAllRentals() {
        return convert(rentalDao.findAll());
    }

    private List<Rental> convert(Iterable<training.persistence.entity.Rental> all) {
        List<Rental> rentals = new ArrayList<>();
        all.forEach(r -> rentals.add(r.makeGenerated()));
        return rentals;
    }

    public Rental getRentalById(int rentalId) {
        return rentalDao.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(rentalId))).makeGenerated();
    }

    public List<Rental> getCustomerRentals(int customerId) {
        return convert(rentalDao.findAllByCustomerId(customerId));
    }

    public List<Rental> getRentalsByStartDate(Date startDate) {
        return convert(rentalDao.findAllByRentalDate(startDate));
    }

    public List<Rental> getRentalsByReturnDate(Date returnDate) {
        return convert(rentalDao.findAllByReturnDate(returnDate));
    }

    public List<Rental> getStaffRentals(int staffId) {
        return convert(rentalDao.findAllByStaffId(staffId));
    }

    public Rental save(RentalDto rental) {
        training.persistence.entity.Rental entity;

        if (Objects.nonNull(rental.getId())){
            training.persistence.entity.Rental r = rentalDao.findById(rental.getId())
                    .orElseThrow(() -> new EntityNotFoundException(rental.getId().toString()));

            if (Objects.nonNull(rental.getCustomer())){
                r.setCustomer(customerDao.findById(rental.getCustomer())
                        .orElseThrow(() -> new EntityNotFoundException(rental.getCustomer().toString())));
            }
            if (Objects.nonNull(rental.getInventory())){
                r.setInventory(inventoryDao.findById(rental.getInventory())
                        .orElseThrow(() -> new EntityNotFoundException(rental.getInventory().toString())));
            }
            if (Objects.nonNull(rental.getStaff())){
                r.setStaff(staffDao.findById(rental.getStaff())
                        .orElseThrow(() -> new EntityNotFoundException(rental.getStaff().toString())));
            }

            entity = rental.makeEntity(r);
        } else {
            Customer customer = customerDao.findById(rental.getCustomer())
                    .orElseThrow(() -> new EntityNotFoundException(rental.getCustomer().toString()));
            Inventory inventory = inventoryDao.findById(rental.getInventory())
                    .orElseThrow(() -> new EntityNotFoundException(rental.getInventory().toString()));
            Staff staff = staffDao.findById(rental.getStaff())
                    .orElseThrow(() -> new EntityNotFoundException(rental.getStaff().toString()));

            entity = rental.makeEntity(customer, staff, inventory);
        }

        return rentalDao.save(entity).makeGenerated();
    }

    public void delete(int rentalId) {
        rentalDao.deleteById(rentalId);
    }
}
