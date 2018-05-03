package soap.service;

import soap.generated.*;

import java.util.List;

public interface FilmService {
	public void createFilm(CreateFilmRequest film);
	public void updateFilm(UpdateFilmRequest film);
	public Film getFilmById(long id);
	public List<Film> listFilms();
	public List<Film> getFilmByRating(String rating);
	public List<Film> getFilmByReleaseYear(int year);
	public List<Film> getFilmByTitle(String title);
	void deleteFilm(long filmId);
	Summary getFilmSummary(long filmId);
	List<Actor> getFilmsActors(long filmId);
}
