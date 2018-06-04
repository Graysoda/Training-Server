package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import training.database.dao.impl.FilmDaoImpl;
import training.generated.*;
import training.service.FilmService;

import java.util.List;

public class FilmServiceImpl implements FilmService {
	@Autowired @Lazy private FilmDaoImpl filmDaoImpl;

	@Override
	public ResponseEntity<?> createFilm(CreateFilmRequest film) {
		return this.filmDaoImpl.insert(film);
	}

	@Override
	public ResponseEntity<?> updateFilm(UpdateFilmRequest film) {
		return this.filmDaoImpl.update(film);
	}

	@Override
	public Film getFilmById(long id) {
		return this.filmDaoImpl.getById(id);
	}

	@Override
	public List<Film> getAllFilms() {
		return this.filmDaoImpl.getAll();
	}

	@Override
	public List<Film> getFilmByRating(String rating) {
		return this.filmDaoImpl.getByRating(rating);
	}

	@Override
	public List<Film> getFilmByReleaseYear(int year) {
		return this.filmDaoImpl.getByReleaseYear(year);
	}

	@Override
	public List<Film> getFilmByTitle(String title) {
		return this.filmDaoImpl.getByTitle(title);
	}

	@Override
	public ResponseEntity<?> deleteFilm(long filmId) {
		return filmDaoImpl.delete(filmId);
	}

	@Override
	public Summary getFilmSummary(long filmId) {
		return filmDaoImpl.getSummary(filmId);
	}

	@Override
	public List<Actor> getFilmsActors(long filmId) {
		return filmDaoImpl.getFilmsActors(filmId);
	}
}
