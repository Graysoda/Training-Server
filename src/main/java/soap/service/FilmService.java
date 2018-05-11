package soap.service;

import soap.generated.*;

import java.sql.SQLException;
import java.util.List;

public interface FilmService {
	void createFilm(CreateFilmRequest film) throws SQLException;
	void updateFilm(UpdateFilmRequest film) throws SQLException;
	Film getFilmById(long id) throws SQLException;
	List<Film> listFilms() throws SQLException;
	List<Film> getFilmByRating(String rating) throws SQLException;
	List<Film> getFilmByReleaseYear(int year) throws SQLException;
	List<Film> getFilmByTitle(String title) throws SQLException;
	void deleteFilm(long filmId) throws SQLException;
	Summary getFilmSummary(long filmId);
	List<Actor> getFilmsActors(long filmId) throws SQLException;
}
