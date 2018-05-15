package training.database.dao;

import org.springframework.http.ResponseEntity;
import training.generated.*;

import java.util.List;

public interface FilmDao {
    List<Film> getAll();
    Film getById(long id);
    List<Film> getByRating(String rating);
    List<Film> getByReleaseYear(int year);
    List<Film> getByTitle(String title);
    List<Summary> getFilmsById(List<Long> filmIds);
    List<Actor> getFilmsActors(long filmId);
    Summary getSummary(long filmId);
    ResponseEntity<?> insert(CreateFilmRequest request);
    ResponseEntity<?> update(UpdateFilmRequest request);
    ResponseEntity<?> delete(long filmId);
}
