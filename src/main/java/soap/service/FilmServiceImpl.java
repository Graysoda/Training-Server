package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.FilmDao;
import soap.generated.*;

import java.sql.SQLException;
import java.util.List;
@Service
public class FilmServiceImpl implements FilmService {
	private FilmDao filmDao;

	@Autowired
	public void setFilmDao(FilmDao filmDao) {
		this.filmDao = filmDao;
	}

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
	public Film getFilmById(long id) throws SQLException {
		return this.filmDao.getById(id);
	}

	@Override
	@Transactional
	public List<Film> listFilms() throws SQLException {
		return this.filmDao.getAll();
	}


	@Override
	@Transactional
	public List<Film> getFilmByRating(String rating) throws SQLException {
		return this.filmDao.getByRating(rating);
	}

	@Override
	@Transactional
	public List<Film> getFilmByReleaseYear(int year) throws SQLException {
		return this.filmDao.getByReleaseYear(year);
	}

	@Override
	@Transactional
	public List<Film> getFilmByTitle(String title) throws SQLException {
		return this.filmDao.getByTitle(title);
	}

	@Override
	public void deleteFilm(long filmId) {
		filmDao.delete(filmId);
	}

	@Override
	public Summary getFilmSummary(long filmId) {
		return filmDao.getSummary(filmId);
	}

	@Override
	public List<Actor> getFilmsActors(long filmId) {
		return filmDao.getActors(filmId);
	}
}
