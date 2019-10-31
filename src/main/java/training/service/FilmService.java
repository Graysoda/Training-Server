package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Actor;
import training.generated.Film;
import training.persistence.dao.ActorDao;
import training.persistence.dao.CategoryDao;
import training.persistence.dao.FilmDao;
import training.persistence.dao.LanguageDao;
import training.persistence.dto.FilmDto;
import training.persistence.entity.Category;
import training.persistence.entity.Language;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class FilmService {
    @Autowired
    private FilmDao filmDao;
    @Autowired
    private LanguageDao languageDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private ActorDao actorDao;

    public List<Film> getAllFilms() {
        return convert(filmDao.findAll());
    }

    private List<Film> convert(Iterable<training.persistence.entity.Film> all) {
        List<Film> films = new ArrayList<>();
        all.forEach( f -> films.add(f.makeGenerated()));
        return films;
    }

    public Film getFilmById(int filmId) {
        return filmDao.findById(filmId)
                .orElseThrow(() -> new EntityNotFoundException(String.valueOf(filmId))).makeGenerated();
    }

    public List<Film> getFilmsByRating(String rating) {
        return convert(filmDao.findByRating(rating));
    }

    public List<Film> getFilmsByReleaseYear(int releaseYear) {
        return convert(filmDao.findByReleaseYear(releaseYear));
    }

    public List<Film> getFilmsByTitle(String title) {
        return convert(filmDao.findByTitle(title));
    }

    public List<Actor> getFilmActors(int filmId) {
        List<Actor> actors = new ArrayList<>();
        filmDao.findActorsInFilm(filmId).forEach(a -> actors.add(a.makeGenerated()));
        return actors;
    }

    public Film save(FilmDto film) {
        training.persistence.entity.Film entity;

        if (Objects.nonNull(film.getId())){
            training.persistence.entity.Film f = filmDao.findById(film.getId())
                    .orElseThrow(() -> new EntityNotFoundException(film.getId().toString()));

            if (Objects.nonNull(film.getLanguage())){
                f.setLanguage(languageDao.findByName(film.getLanguage())
                        .orElseThrow(() -> new EntityNotFoundException(film.getLanguage())));
            }
            if (Objects.nonNull(film.getCategory())){
                f.setCategory(categoryDao.findByName(film.getCategory()));
            }
            if (Objects.nonNull(film.getOriginalLanguage())){
                f.setOriginalLanguage(languageDao.findByName(film.getOriginalLanguage())
                        .orElseThrow(() -> new EntityNotFoundException(film.getOriginalLanguage())));
            }
            if (Objects.nonNull(film.getActors())){
                f.setActors(new ArrayList<>());
                actorDao.findAllById(film.getActors()).forEach(f.getActors()::add);
            }

            entity = film.makeEntity(f);
        } else {
            Language language = languageDao.findByName(film.getLanguage())
                    .orElseThrow(() -> new EntityNotFoundException(film.getLanguage()));
            List<training.persistence.entity.Actor> actors = new ArrayList<>();
            actorDao.findAllById(film.getActors()).forEach(actors::add);
            Category category = categoryDao.findByName(film.getCategory());
            Language originalLanguage = null;
            if (Objects.nonNull(film.getOriginalLanguage())) {
                originalLanguage = languageDao.findByName(film.getOriginalLanguage())
                        .orElse(null);
            }

            entity = film.makeEntity(language, actors, category, originalLanguage);
        }

        return filmDao.save(entity).makeGenerated();
    }

    public void delete(int filmId) {
        filmDao.deleteById(filmId);
    }

    public boolean exists(int filmId) {
        return filmDao.existsById(filmId);
    }

    public List<Film> getFilmsByCategory(String category) {
        return convert(filmDao.findAllByCategory(category));
    }
}
