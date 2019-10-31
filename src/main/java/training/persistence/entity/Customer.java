package training.persistence.entity;

import lombok.Data;
import org.hibernate.annotations.Type;
import training.Constants;
import training.generated.CreateCustomerRequest;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "store_id", name = "store_id")
    private Store store;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;

    @ManyToOne
    @JoinColumn(referencedColumnName = "address_id", name = "address_id")
    private Address address;
    @Type(type = "numeric_boolean")
    private boolean active;
    @Temporal(TemporalType.DATE)
    @Column(name = "create_date")
    private Date createDate;

    public Customer() {
    }

    public Customer(training.generated.Customer customer) {
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        email = customer.getEmail();
        active = customer.isIsActive();
        address = new Address(customer.getAddress());
        store = new Store();
        store.setId((customer.getStore().getStoreId()));
        createDate = Constants.formatString(customer.getCreateDate());
    }

    public Customer(CreateCustomerRequest customer, Address address, Store store) {
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        email = customer.getEmail();
        active = customer.isActive();
        this.address = address;
        this.store = store;
        createDate = new Date(Calendar.getInstance().getTimeInMillis());
    }

    public training.generated.Customer makeGenerated() {
        training.generated.Customer customer = new training.generated.Customer();

        customer.setCustomerId(id);
        customer.setAddress(address.makeGenerated());
        customer.setCreateDate(Constants.formatDate(createDate));
        customer.setEmail(email);
        customer.setFirstName(firstName);
        customer.setIsActive(active);
        customer.setLastName(lastName);
        customer.setStore(store.makeGenerated());

        return customer;
    }
}
