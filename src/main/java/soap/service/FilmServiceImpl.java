package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.FilmDaoImpl;
import soap.generated.CreateFilmRequest;
import soap.generated.Film;

import java.util.List;
@Service
public class FilmServiceImpl implements FilmService {
	private FilmDaoImpl filmDao;

	public FilmServiceImpl() {
		System.out.println("made new film service impl");
	}

	@Autowired
	public void setFilmDao(FilmDaoImpl filmDao) {
		System.out.println("film dao is set");
		this.filmDao = filmDao;
	}

	@Override
	@Transactional
	public void addFilm(CreateFilmRequest film) {
		this.filmDao.addFilm(film);
	}

	@Override
	@Transactional
	public void updateFilm(Film film) {
		this.filmDao.updateFilm(film);
	}

	@Override
	@Transactional
	public Film getFilmById(long id) {
		return this.filmDao.getFilmById(id);
	}

	@Override
	@Transactional
	public List<Film> listFilms() {
		System.out.println("service impl list films");
		return this.filmDao.listFilms();
	}

	@Override
	@Transactional
	public void removeFilm(long id) {
		this.filmDao.removeFilm(id);
	}
}
