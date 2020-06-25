package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(referencedColumnName = "customer_id", name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(referencedColumnName = "staff_id", name = "staff_id")
    private Staff staff;
    @ManyToOne
    @JoinColumn(referencedColumnName = "inventory_id", name = "inventory_id")
    private Inventory inventory;
    private String rentalDate;
    private String returnDate;

    public Rental() {
    }

    public training.generated.Rental makeGenerated() {
        training.generated.Rental rental = new training.generated.Rental();
        rental.setRentalId(id);
        rental.setCustomer(customer.makeGenerated());
        rental.setStaff(staff.makeGenerated());
        rental.setItem(inventory.getFilm().makeGenerated());
        rental.setRentalDate(rentalDate);
        rental.setReturnDate(returnDate);
        return rental;
    }
}
