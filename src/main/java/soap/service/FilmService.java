package soap.service;

import soap.generated.*;

import java.sql.SQLException;
import java.util.List;

public interface FilmService {
	public void createFilm(CreateFilmRequest film) throws SQLException;
	public void updateFilm(UpdateFilmRequest film) throws SQLException;
	public Film getFilmById(long id) throws SQLException;
	public List<Film> listFilms() throws SQLException;
	public List<Film> getFilmByRating(String rating) throws SQLException;
	public List<Film> getFilmByReleaseYear(int year) throws SQLException;
	public List<Film> getFilmByTitle(String title) throws SQLException;
	void deleteFilm(long filmId) throws SQLException;
	Summary getFilmSummary(long filmId);
	List<Actor> getFilmsActors(long filmId) throws SQLException;
}
