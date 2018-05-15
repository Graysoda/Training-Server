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

	public InventoryEntity() {
	}

	public InventoryEntity(long inventory_id, @NotNull long film_id, @NotNull long store_id) {
		this.inventory_id = inventory_id;
		this.film_id = film_id;
		this.store_id = store_id;
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
}
