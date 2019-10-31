package training.persistence.entity;

import lombok.Data;
import training.Constants;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;
    private Float amount;
    @ManyToOne
    @JoinColumn(referencedColumnName = "customer_id", name = "customer_id")
    private Customer customer;
    private Date paymentDate;
    @ManyToOne
    @JoinColumn(referencedColumnName = "rental_id", name = "rental_id")
    private Rental rental;
    @ManyToOne
    @JoinColumn(referencedColumnName = "staff_id", name = "staff_id")
    private Staff staff;

    public Payment() {
    }

    public training.generated.Payment makeGenerated() {
        training.generated.Payment payment = new training.generated.Payment();
        payment.setPaymentId(id);
        payment.setAmount(amount);
        payment.setCustomer(customer.makeGenerated());
        payment.setPaymentDate(Constants.formatDate(paymentDate));
        payment.setRental(rental.makeGenerated());
        payment.setStaff(staff.makeGenerated());
        return payment;
    }
}
