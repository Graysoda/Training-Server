package training.database.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.customer")
@Table(name = "customer")
public class CustomerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long customer_id;
	@NotNull
	private Long store_id;
	@NotNull
	private String first_name;
	@NotNull
	private String last_name;
	@NotNull
	private String email;
	@NotNull
	private Long address_id;
	@NotNull
	private Boolean active;
	@NotNull
	private String create_date;

	public CustomerEntity() {
	}

	public CustomerEntity(long customer_id, @NotNull long store_id, @NotNull String first_name, @NotNull String last_name, @NotNull String email, @NotNull long address_id, @NotNull boolean active, @NotNull String create_date) {
		this.customer_id = customer_id;
		this.store_id = store_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.address_id = address_id;
		this.active = active;
		this.create_date = create_date;
	}

	public long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(long customer_id) {
		this.customer_id = customer_id;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(long address_id) {
		this.address_id = address_id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
}
