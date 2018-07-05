package training.api.rest.jsonObjects;

public class CustomerJson {
    private Boolean active;
    private Long addressId;
    private String email;
    private String firstName;
    private String lastName;
    private Long storeId;

    public CustomerJson(Boolean active, Long addressId, String email, String firstName, String lastName, Long storeId) {
        this.active = active;
        this.addressId = addressId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.storeId = storeId;
    }

    public CustomerJson() {
    }

    public Boolean isActive() {
        return active;
    }

    public Long getAddressId() {
        return addressId;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Long getStoreId() {
        return storeId;
    }
}


