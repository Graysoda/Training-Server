package soap.service;

import soap.generated.CreateFilmRequest;
import soap.generated.Film;

import java.util.List;

public interface FilmService {
	public void addFilm(CreateFilmRequest film);
	public void updateFilm(Film film);
	public Film getFilmById(long id);
	public List<Film> listFilms();
	public void removeFilm(long id);
}
