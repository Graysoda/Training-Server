package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.*;

import java.util.List;

@Service
public interface FilmService {
	@Transactional
	ResponseEntity<?> createFilm(CreateFilmRequest film);
	@Transactional
	ResponseEntity<?> updateFilm(UpdateFilmRequest film);
	@Transactional
	Film getFilmById(long id);
	@Transactional
	List<Film> getAllFilms();
	@Transactional
	List<Film> getFilmByRating(String rating);
	@Transactional
	List<Film> getFilmByReleaseYear(int year);
	@Transactional
	List<Film> getFilmByTitle(String title);
	@Transactional
	ResponseEntity<?> deleteFilm(long filmId);
	@Transactional
	Summary getFilmSummary(long filmId);
	@Transactional
	List<Actor> getFilmsActors(long filmId);
}
