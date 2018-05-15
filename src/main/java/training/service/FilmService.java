package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.*;

import java.sql.SQLException;
import java.util.List;

public interface FilmService {
	ResponseEntity<?> createFilm(CreateFilmRequest film) throws SQLException;
	ResponseEntity<?> updateFilm(UpdateFilmRequest film) throws SQLException;
	Film getFilmById(long id) throws SQLException;
	List<Film> getAllFilms() throws SQLException;
	List<Film> getFilmByRating(String rating) throws SQLException;
	List<Film> getFilmByReleaseYear(int year) throws SQLException;
	List<Film> getFilmByTitle(String title) throws SQLException;
	ResponseEntity<?> deleteFilm(long filmId) throws SQLException;
	Summary getFilmSummary(long filmId);
	List<Actor> getFilmsActors(long filmId) throws SQLException;
}
