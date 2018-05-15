package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.*;
import training.service.FilmServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class FilmsController {
    @Autowired @Lazy private FilmServiceImpl filmService;

    @RequestMapping(value = "/films/", method = RequestMethod.GET)
    public ResponseEntity<List<Film>> getAllFilms(){
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/{filmId}", method = RequestMethod.GET)
    public ResponseEntity<Film> getFilmById(@PathVariable long filmId){
        return new ResponseEntity<>(filmService.getFilmById(filmId), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/rating/{rating}", method = RequestMethod.GET)
    public ResponseEntity<List<Film>> getFilmsByRating(@PathVariable String rating){
        return new ResponseEntity<>(filmService.getFilmByRating(rating), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/release_year/{release_year}", method = RequestMethod.GET)
    public ResponseEntity<List<Film>> getFilmsByReleaseYear(@PathVariable int releaseYear){
        return new ResponseEntity<>(filmService.getFilmByReleaseYear(releaseYear), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<List<Film>> getFilmsByTitle(@PathVariable String title){
        return new ResponseEntity<>(filmService.getFilmByTitle(title), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/{filmId}/summary", method = RequestMethod.GET)
    public ResponseEntity<Summary> getSummary(@PathVariable long filmId){
        return new ResponseEntity<>(filmService.getFilmSummary(filmId), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/{filmId}/actors/", method = RequestMethod.GET)
    public ResponseEntity<List<Actor>> getFilmsActors(@PathVariable long filmId){
        return new ResponseEntity<>(filmService.getFilmsActors(filmId), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/create", method = RequestMethod.PUT)
    public ResponseEntity<?> createFilm(@RequestBody CreateFilmRequest request){
        return filmService.createFilm(request);
    }

    @RequestMapping(value = "/films/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateFilm(@RequestBody UpdateFilmRequest request){
        return filmService.updateFilm(request);
    }

    @RequestMapping(value = "/films/{filmId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFilm(@PathVariable long filmId){
        return filmService.deleteFilm(filmId);
    }
}
