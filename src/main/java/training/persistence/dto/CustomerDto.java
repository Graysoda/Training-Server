package training.persistence.dto;

import lombok.Data;
import training.generated.CreateCustomerRequest;
import training.generated.UpdateCustomerRequest;
import training.persistence.entity.Address;
import training.persistence.entity.Customer;
import training.persistence.entity.Store;

import java.util.Objects;

@Data
public class CustomerDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer addressId;
    private Integer storeId;
    private Boolean active;

    public CustomerDto(){}

    public CustomerDto(UpdateCustomerRequest customer) {
        this.id = customer.getCustomerId();
        this.active = customer.isActive();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.addressId = customer.getAddressId();
        this.storeId = customer.getStoreId();
        this.email = customer.getEmail();
    }

    public CustomerDto(CreateCustomerRequest request) {
        firstName = request.getFirstName();
        lastName = request.getLastName();
        email = request.getEmail();
        addressId = request.getAddressId();
        storeId = request.getStoreId();
        active = request.isActive();
    }

    public Customer makeEntity(Address address, Store store) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setActive(active);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setStore(store);
        customer.setAddress(address);
        return customer;
    }

    public Customer makeEntity(Customer c) {
        if (Objects.isNull(firstName)){
            firstName = c.getFirstName();
        }
        if (Objects.isNull(lastName)){
            lastName = c.getFirstName();
        }
        if (Objects.isNull(email)){
            email = c.getEmail();
        }
        if (Objects.isNull(active)){
            active = c.isActive();
        }
        return makeEntity(c.getAddress(), c.getStore());
    }
}
