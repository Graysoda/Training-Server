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
	@Autowired private FilmDao filmDao;

	@Override
	@Transactional
	public void createFilm(CreateFilmRequest film) throws SQLException {
		this.filmDao.create(film);
	}

	@Override
	@Transactional
	public void updateFilm(UpdateFilmRequest film) throws SQLException {
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
	@Transactional
	public void deleteFilm(long filmId) throws SQLException {
		filmDao.delete(filmId);
	}

	@Override
	@Transactional
	public Summary getFilmSummary(long filmId) {
		return filmDao.getSummary(filmId);
	}

	@Override
	@Transactional
	public List<Actor> getFilmsActors(long filmId) throws SQLException {
		return filmDao.getActors(filmId);
	}
}
