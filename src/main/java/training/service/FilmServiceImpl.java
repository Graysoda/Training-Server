package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.FilmDao;
import training.generated.*;

import java.util.List;
@Service
public class FilmServiceImpl implements FilmService {
	@Autowired @Lazy private FilmDao filmDao;

	@Override
	@Transactional
	public void createFilm(CreateFilmRequest film) {
		this.filmDao.create(film);
	}

	@Override
	@Transactional
	public void updateFilm(UpdateFilmRequest film) {
		this.filmDao.update(film);
	}

	@Override
	@Transactional
	public Film getFilmById(long id) {
		return this.filmDao.getById(id);
	}

	@Override
	@Transactional
	public List<Film> listFilms() {
		return this.filmDao.getAll();
	}


	@Override
	@Transactional
	public List<Film> getFilmByRating(String rating) {
		return this.filmDao.getByRating(rating);
	}

	@Override
	@Transactional
	public List<Film> getFilmByReleaseYear(int year) {
		return this.filmDao.getByReleaseYear(year);
	}

	@Override
	@Transactional
	public List<Film> getFilmByTitle(String title) {
		return this.filmDao.getByTitle(title);
	}

	@Override
	@Transactional
	public void deleteFilm(long filmId) {
		filmDao.delete(filmId);
	}

	@Override
	@Transactional
	public Summary getFilmSummary(long filmId) {
		return filmDao.getSummary(filmId);
	}

	@Override
	@Transactional
	public List<Actor> getFilmsActors(long filmId) {
		return filmDao.getFilmsActors(filmId);
	}
}
