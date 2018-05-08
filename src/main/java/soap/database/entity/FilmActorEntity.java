package soap.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.film_actor")
@Table(name = "film_actor")
public class FilmActorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private long actor_id;
	@NotNull
	private long film_id;
	@NotNull
	private String last_update;

	public FilmActorEntity() {
	}

	public FilmActorEntity(long id, @NotNull long actor_id, @NotNull long film_id, @NotNull String last_update) {
		this.id = id;
		this.actor_id = actor_id;
		this.film_id = film_id;
		this.last_update = last_update;
	}

	public long getActor_id() {
		return actor_id;
	}

	public void setActor_id(long actor_id) {
		this.actor_id = actor_id;
	}

	public long getFilm_id() {
		return film_id;
	}

	public void setFilm_id(long film_id) {
		this.film_id = film_id;
	}

	public String getLast_update() {
		return last_update;
	}

	public void setLast_update(String last_update) {
		this.last_update = last_update;
	}
}
