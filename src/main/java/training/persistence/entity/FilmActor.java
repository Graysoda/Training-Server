package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "film_actor")
@IdClass(FilmActor.FAKey.class)
public class FilmActor {
    @Id
    @Column(name = "film_id")
    private int filmId;
    @Id
    @Column(name = "actor_id")
    private int actorId;

    @Data
    class FAKey implements Serializable {
        @Column(name = "film_id")
        private int filmId;
        @Column(name = "actor_id")
        private int actorId;
    }

    public FilmActor() {
    }
}
