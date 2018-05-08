package soap.database.entity;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Blob;

@Entity(name = "sakila.staff")
@Table(name = "staff")
public class StaffEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long staff_id;
	@NotNull
	private String first_name;
	@NotNull
	private String last_name;
	@NotNull
	private long address_id;
	private Blob picture;
	@NotNull
	private String email;
	@NotNull
	private long store_id;
	@NotNull
	private boolean active;
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String last_update;

	public StaffEntity() {
	}

	public StaffEntity(long staff_id, @NotNull String first_name, @NotNull String last_name, @NotNull long address_id, Blob picture, @NotNull String email, @NotNull long store_id, @NotNull boolean active, @NotNull String username, @NotNull String password, @NotNull String last_update) {
		this.staff_id = staff_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address_id = address_id;
		this.picture = picture;
		this.email = email;
		this.store_id = store_id;
		this.active = active;
		this.username = username;
		this.password = password;
		this.last_update = last_update;
	}

	public long getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(long staff_id) {
		this.staff_id = staff_id;
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

	public long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(long address_id) {
		this.address_id = address_id;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
}
