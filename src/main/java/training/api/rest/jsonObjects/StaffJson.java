package training.api.rest.jsonObjects;

public class StaffJson {
    private Boolean active;
    private Long addressId;
    private Long storeId;
    private String email;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    public StaffJson() {
    }

    public StaffJson(Boolean active, Long addressId, Long storeId, String email, String firstName, String lastName, String username, String password) {
        this.active = active;
        this.addressId = addressId;
        this.storeId = storeId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public Long getAddressId() {
        return addressId;
    }

    public Long getStoreId() {
        return storeId;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
