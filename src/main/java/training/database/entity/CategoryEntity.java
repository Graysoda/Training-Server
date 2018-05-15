package training.database.entity;

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

	public CategoryEntity() {
	}

	public CategoryEntity(long category_id, @NotNull String name) {
		this.category_id = category_id;
		this.name = name;
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
}
