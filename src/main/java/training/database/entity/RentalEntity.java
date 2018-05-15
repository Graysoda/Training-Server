package training.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.rental")
@Table(name = "rental")
public class RentalEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long rental_id;
	@NotNull
	private long customer_id;
	@NotNull
	private long staff_id;
	@NotNull
	private long inventory_id;
	@NotNull
	private String rental_date;
	@NotNull
	private String return_date;

	public RentalEntity() {
	}

	public RentalEntity(long rental_id, @NotNull long customer_id, @NotNull long staff_id, @NotNull long inventory_id, @NotNull String rental_date, @NotNull String return_date) {
		this.rental_id = rental_id;
		this.customer_id = customer_id;
		this.staff_id = staff_id;
		this.inventory_id = inventory_id;
		this.rental_date = rental_date;
		this.return_date = return_date;
	}

	public long getRental_id() {
		return rental_id;
	}

	public void setRental_id(long rental_id) {
		this.rental_id = rental_id;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
	}

	public long getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(long inventory_id) {
		this.inventory_id = inventory_id;
	}

	public String getRental_date() {
		return rental_date;
	}

	public void setRental_date(String rental_date) {
		this.rental_date = rental_date;
	}

	public String getReturn_date() {
		return return_date;
	}

	public void setReturn_date(String return_date) {
		this.return_date = return_date;
	}
}
