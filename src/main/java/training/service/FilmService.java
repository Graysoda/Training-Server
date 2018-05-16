package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.*;

import java.util.List;

public interface FilmService {
	ResponseEntity<?> createFilm(CreateFilmRequest film);
	ResponseEntity<?> updateFilm(UpdateFilmRequest film);
	Film getFilmById(long id);
	List<Film> getAllFilms();
	List<Film> getFilmByRating(String rating);
	List<Film> getFilmByReleaseYear(int year);
	List<Film> getFilmByTitle(String title);
	ResponseEntity<?> deleteFilm(long filmId);
	Summary getFilmSummary(long filmId);
	List<Actor> getFilmsActors(long filmId);
}
