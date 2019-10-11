package training.persistence.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Actor;
import training.persistence.entity.Film;


@Repository
public interface ActorDao extends CrudRepository<Actor, Integer> {
    Iterable<Actor> findByFirstName(String firstName);

    Iterable<Actor> findByLastName(String lastName);

    @Query("SELECT f FROM Film f WHERE f.id IN " +
            "(SELECT fa.filmId FROM FilmActor fa WHERE fa.actorId=?1)")
    Iterable<Film> findFilmsWithActor(int actorId);
}
