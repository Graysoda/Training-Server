package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Blob;

@Data
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne
    @JoinColumn(referencedColumnName = "address_id", name = "address_id")
    private Address address;
    private Blob picture;
    private String email;
    @ManyToOne
    @JoinColumn(referencedColumnName = "store_id", name = "store_id")
    private Store store;
    private boolean active;
    private String username;
    private String password;

    public Staff() {
    }

    public Staff(training.generated.Staff staff) {
        id = staff.getStaffId();
        firstName = staff.getFirstName();
        lastName = staff.getLastName();
        address = new Address(staff.getAddress());
        email = staff.getEmail();
        store = new Store();
        store.setId(staff.getStoreId());
        active = staff.isIsActive();
        username = staff.getUsername();
        password = staff.getPassword();
    }

    public training.generated.Staff makeGenerated() {
        training.generated.Staff staff = new training.generated.Staff();
        staff.setStaffId(id);
        staff.setFirstName(firstName);
        staff.setLastName(lastName);
        staff.setAddress(address.makeGenerated());
        staff.setEmail(email);
        staff.setStoreId(store.getId());
        staff.setIsActive(active);
        staff.setUsername(username);
        staff.setPassword(password);
        return staff;
    }
}
