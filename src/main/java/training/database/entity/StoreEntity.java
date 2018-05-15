package training.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.store")
@Table(name = "store")
public class StoreEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long store_id;
	@NotNull
	private long manager_staff_id;
	@NotNull
	private long address_id;

	public StoreEntity() {
	}

	public StoreEntity(long store_id, @NotNull long manager_staff_id, @NotNull long address_id) {
		this.store_id = store_id;
		this.manager_staff_id = manager_staff_id;
		this.address_id = address_id;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

	public long getManager_staff_id() {
		return manager_staff_id;
	}

	public void setManager_staff_id(long manager_staff_id) {
		this.manager_staff_id = manager_staff_id;
	}

	public long getAddress_id() {
		return address_id;
	}

	public void setAddress_id(long address_id) {
		this.address_id = address_id;
	}
}
