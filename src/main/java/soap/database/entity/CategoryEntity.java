package soap.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.category")
@Table(name = "category")
public class CategoryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long category_id;
	@NotNull
	private String name;
	@NotNull
	private String last_update;

	public CategoryEntity() {
	}

	public CategoryEntity(long category_id, @NotNull String name, @NotNull String last_update) {
		this.category_id = category_id;
		this.name = name;
		this.last_update = last_update;
	}

	public long getCategory_id() {
		return category_id;
	}

	public void setCategory_id(long category_id) {
		this.category_id = category_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
}
