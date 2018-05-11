package training.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.inventory")
@Table(name = "inventory")
public class InventoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long inventory_id;
	@NotNull
	private long film_id;
	@NotNull
	private long store_id;
	@NotNull
	private String last_update;

	public InventoryEntity() {
	}

	public InventoryEntity(long inventory_id, @NotNull long film_id, @NotNull long store_id, @NotNull String last_update) {
		this.inventory_id = inventory_id;
		this.film_id = film_id;
		this.store_id = store_id;
		this.last_update = last_update;
	}

	public long getInventory_id() {
		return inventory_id;
	}

	public void setInventory_id(long inventory_id) {
		this.inventory_id = inventory_id;
	}

	public long getFilm_id() {
		return film_id;
	}

	public void setFilm_id(long film_id) {
		this.film_id = film_id;
	}

	public long getStore_id() {
		return store_id;
	}

	public void setStore_id(long store_id) {
		this.store_id = store_id;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
}
