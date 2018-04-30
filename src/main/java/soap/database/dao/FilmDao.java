package soap.database.dao;

import soap.generated.CreateFilmRequest;
import soap.generated.Film;

import java.util.List;

public interface FilmDao {
	public void addFilm(CreateFilmRequest film);
	public List<Film> listFilms();
	public Film getFilmById(long id);
	public void removeFilm(long id);
	public void updateFilm(Film film);
}
