package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.FilmDaoImpl;
import training.generated.*;

import java.util.List;
@Service
public class FilmServiceImpl implements FilmService {
	@Autowired @Lazy private FilmDaoImpl filmDaoImpl;

	@Override
	@Transactional
	public void createFilm(CreateFilmRequest film) {
		this.filmDaoImpl.insert(film);
	}

	@Override
	@Transactional
	public void updateFilm(UpdateFilmRequest film) {
		this.filmDaoImpl.update(film);
	}

	@Override
	@Transactional
	public Film getFilmById(long id) {
		return this.filmDaoImpl.getById(id);
	}

	@Override
	@Transactional
	public List<Film> getAllFilms() {
		return this.filmDaoImpl.getAll();
	}


	@Override
	@Transactional
	public List<Film> getFilmByRating(String rating) {
		return this.filmDaoImpl.getByRating(rating);
	}

	@Override
	@Transactional
	public List<Film> getFilmByReleaseYear(int year) {
		return this.filmDaoImpl.getByReleaseYear(year);
	}

	@Override
	@Transactional
	public List<Film> getFilmByTitle(String title) {
		return this.filmDaoImpl.getByTitle(title);
	}

	@Override
	@Transactional
	public void deleteFilm(long filmId) {
		filmDaoImpl.delete(filmId);
	}

	@Override
	@Transactional
	public Summary getFilmSummary(long filmId) {
		return filmDaoImpl.getSummary(filmId);
	}

	@Override
	@Transactional
	public List<Actor> getFilmsActors(long filmId) {
		return filmDaoImpl.getFilmsActors(filmId);
	}
}
