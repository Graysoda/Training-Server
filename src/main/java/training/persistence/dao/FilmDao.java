package training.persistence.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Actor;
import training.persistence.entity.Film;

@Repository
public interface FilmDao extends CrudRepository<Film, Integer> {
    @Query("SELECT f FROM Film f WHERE cast(rating as text)=?1")
    Iterable<Film> findByRating(String rating);
    @Query("SELECT f FROM Film f WHERE f.releaseYear=?1")
    Iterable<Film> findByReleaseYear(int releaseYear);

    Iterable<Film> findByTitle(String title);

    @Query("SELECT a FROM Actor a WHERE a.id IN" +
            "(SELECT fa.actorId FROM FilmActor fa WHERE fa.filmId=?1)")
    Iterable<Actor> findActorsInFilm(int filmId);

    Iterable<Film> findAllByCategory(String category);
}
