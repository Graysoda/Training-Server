package soap.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.film_text")
@Table(name = "film_text")
public class SummaryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private long film_id;
	@NotNull
	private String title;
	@NotNull
	private String description;

	public SummaryEntity() {
	}

	public SummaryEntity(long id, @NotNull long film_id, @NotNull String title, @NotNull String description) {
		this.id = id;
		this.film_id = film_id;
		this.title = title;
		this.description = description;
	}

	public long getFilm_id() {
		return film_id;
	}

	public void setFilm_id(long film_id) {
		this.film_id = film_id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
