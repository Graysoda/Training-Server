package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Actor;
import training.generated.Film;
import training.generated.Summary;
import training.service.FilmServiceImpl;

import java.util.List;

@RestController
public class FilmsController {
    @Autowired @Lazy private FilmServiceImpl filmService;

    @RequestMapping(value = "/films/", method = RequestMethod.GET)
    public List<Film> getAllFilms(){
        return filmService.getAllFilms();
    }

    @RequestMapping(value = "/film/{filmId}", method = RequestMethod.GET)
    public Film getFilmById(@RequestParam(name = "filmId")long filmId){
        return filmService.getFilmById(filmId);
    }

    @RequestMapping(value = "/films/{rating}", method = RequestMethod.GET)
    public List<Film> getFilmsByRating(@RequestParam(name = "rating")String rating){
        return filmService.getFilmByRating(rating);
    }

    @RequestMapping(value = "/films/{release_year}", method = RequestMethod.GET)
    public List<Film> getFilmsByReleaseYear(@RequestParam(name = "release_year")int releaseYear){
        return filmService.getFilmByReleaseYear(releaseYear);
    }

    @RequestMapping(value = "/films/{title}", method = RequestMethod.GET)
    public List<Film> getFilmsByTitle(@RequestParam(name = "title")String title){
        return filmService.getFilmByTitle(title);
    }

    @RequestMapping(value = "/summary/{filmId}", method = RequestMethod.GET)
    public Summary getSummary(@RequestParam(name = "filmId")long filmId){
        return filmService.getFilmSummary(filmId);
    }

    @RequestMapping(value = "/film/{filmId}/actors/", method = RequestMethod.GET)
    public List<Actor> getFilmsActors(@RequestParam(name = "filmId")long filmId){
        return filmService.getFilmsActors(filmId);
    }


}
