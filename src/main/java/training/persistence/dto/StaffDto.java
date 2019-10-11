package training.persistence.dto;

import lombok.Data;
import training.generated.CreateStaffRequest;
import training.generated.UpdateStaffRequest;
import training.persistence.entity.Address;
import training.persistence.entity.Staff;
import training.persistence.entity.Store;

import java.util.Objects;

@Data
public class StaffDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer address;
    private String email;
    private Integer store;
    private Boolean active;
    private String username;
    private String password;

    public StaffDto(){}

    public StaffDto(CreateStaffRequest request) {
        firstName = request.getFirstName();
        lastName = request.getLastName();
        address = request.getAddressId();
        email = request.getEmail();
        store = request.getStoreId();
        active = request.isActive();
        username = request.getUsername();
        password = request.getPassword();
    }

    public StaffDto(UpdateStaffRequest request) {
        id = request.getStaffId();
        firstName = request.getFirstName();
        lastName = request.getLastName();
        address = request.getAddressId();
        email = request.getEmail();
        store = request.getStoreId();
        active = request.isActive();
        username = request.getUsername();
        password = request.getPassword();
    }

    public Staff makeEntity(Address address, Store store){
        Staff staff = new Staff();

        staff.setId(id);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setAddress(address);
        staff.setEmail(email);
        staff.setStore(store);
        staff.setActive(active);
        staff.setUsername(username);
        staff.setPassword(password);

        return staff;
    }

    public Staff makeEntity(Staff s) {
        if (Objects.isNull(firstName)){
            firstName = s.getFirstName();
        }
        if (Objects.isNull(lastName)){
            lastName = s.getLastName();
        }
        if (Objects.isNull(email)){
            email = s.getEmail();
        }
        if (Objects.isNull(active)){
            active = s.isActive();
        }
        if (Objects.isNull(username)){
            username = s.getUsername();
        }
        if (Objects.isNull(password)){
            password = s.getPassword();
        }
        return makeEntity(s.getAddress(), s.getStore());
    }
}
