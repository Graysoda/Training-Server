package training.database.entity;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FilmActorKey implements Serializable {
    @NotNull
    private long film_id;
    @NotNull
    private long actor_id;

    public FilmActorKey() {
    }

    public FilmActorKey(@NotNull long film_id, @NotNull long actor_id) {
        this.film_id = film_id;
        this.actor_id = actor_id;
    }

    public long getFilm_id() {
        return film_id;
    }

    public void setFilm_id(long film_id) {
        this.film_id = film_id;
    }

    public long getActor_id() {
        return actor_id;
    }

    public void setActor_id(long actor_id) {
        this.actor_id = actor_id;
    }
}
