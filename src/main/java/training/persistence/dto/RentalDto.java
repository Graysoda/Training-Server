package training.persistence.dto;

import lombok.Data;
import training.generated.CreateRentalRequest;
import training.generated.UpdateRentalRequest;
import training.persistence.entity.Customer;
import training.persistence.entity.Inventory;
import training.persistence.entity.Rental;
import training.persistence.entity.Staff;

import java.util.Objects;

@Data
public class RentalDto {
    private Integer id;
    private Integer customer;
    private Integer staff;
    private Integer inventory;
    private String rentalDate;
    private String returnDate;

    public RentalDto(){}

    public RentalDto(CreateRentalRequest request) {
        customer = request.getCustomerId();
        staff = request.getStaffId();
        inventory = request.getInventoryId();
        rentalDate = request.getRentalDate();
        returnDate = request.getReturnDate();
    }

    public RentalDto(UpdateRentalRequest request) {
        id = request.getRentalId();
        customer = request.getCustomerId();
        staff = request.getStaffId();
        inventory = request.getInventoryId();
        rentalDate = request.getRentalDate();
        returnDate = request.getReturnDate();
    }

    public Rental makeEntity(Customer customer, Staff staff, Inventory inventory){
        Rental rental = new Rental();

        rental.setId(id);
        rental.setCustomer(customer);
        rental.setInventory(inventory);
        rental.setStaff(staff);
        rental.setRentalDate(rentalDate);
        rental.setReturnDate(returnDate);

        return rental;
    }

    public Rental makeEntity(Rental r) {
        if (Objects.isNull(rentalDate)){
            rentalDate = r.getRentalDate();
        }
        if (Objects.isNull(returnDate)){
            returnDate = r.getReturnDate();
        }
        return makeEntity(r.getCustomer(), r.getStaff(), r.getInventory());
    }
}
