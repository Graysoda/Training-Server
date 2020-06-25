package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(referencedColumnName = "staff_id", name = "manager_staff_id")
    private Staff manager;

    @ManyToOne
    @JoinColumn(referencedColumnName = "address_id", name = "address_id")
    private Address address;

    public Store() {
    }

    public Store(training.generated.Store store){
        id = store.getStoreId();
        manager = new Staff(store.getManager());
        address = new Address(store.getAddress());
    }


    public training.generated.Store makeGenerated() {
        training.generated.Store store = new training.generated.Store();
        store.setStoreId(id);
        store.setAddress(address.makeGenerated());
        store.setManager(manager.makeGenerated());
        return store;
    }
}
