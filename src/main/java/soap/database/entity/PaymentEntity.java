package soap.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.payment")
@Table(name = "payment")
public class PaymentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long payment_id;
	@NotNull
	private float amount;
	@NotNull
	private long customer_id;
	@NotNull
	private String payment_date;
	@NotNull
	private long rental_id;
	@NotNull
	private long staff_id;

	public long getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(long payment_id) {
		this.payment_id = payment_id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public String getPayment_date() {
		return payment_date;
	}

	public void setPayment_date(String payment_date) {
		this.payment_date = payment_date;
	}

	public long getRental_id() {
		return rental_id;
	}

	public void setRental_id(long rental_id) {
		this.rental_id = rental_id;
	}

	public long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}
}
