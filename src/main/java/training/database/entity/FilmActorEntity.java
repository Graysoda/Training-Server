package training.database.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.film_actor")
@Table(name = "film_actor")
@IdClass(FilmActorKey.class)
public class FilmActorEntity {

	@Id
	private long actor_id;
	@Id
	private long film_id;

	public FilmActorEntity() {
	}

	public FilmActorEntity(@NotNull long actor_id, @NotNull long film_id) {
		this.actor_id = actor_id;
		this.film_id = film_id;
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
}
