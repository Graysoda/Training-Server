package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import training.service.FilmServiceImpl;

@RestController
public class FilmsController {
    @Autowired @Lazy private FilmServiceImpl filmService;

    @RequestMapping(value = "/films/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllFilms(){
        return new ResponseEntity<>(filmService.getAllFilms(), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/{filmId}", method = RequestMethod.GET)
    public ResponseEntity<?> getFilmById(@PathVariable(name = "filmId")long filmId){
        return new ResponseEntity<>(filmService.getFilmById(filmId), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/rating/{rating}", method = RequestMethod.GET)
    public ResponseEntity<?> getFilmsByRating(@PathVariable(name = "rating")String rating){
        return new ResponseEntity<>(filmService.getFilmByRating(rating), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/release_year/{release_year}", method = RequestMethod.GET)
    public ResponseEntity<?> getFilmsByReleaseYear(@PathVariable(name = "release_year")int releaseYear){
        return new ResponseEntity<>(filmService.getFilmByReleaseYear(releaseYear), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/title/{title}", method = RequestMethod.GET)
    public ResponseEntity<?> getFilmsByTitle(@PathVariable(name = "title")String title){
        return new ResponseEntity<>(filmService.getFilmByTitle(title), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/{filmId}/summary", method = RequestMethod.GET)
    public ResponseEntity<?> getSummary(@PathVariable(name = "filmId")long filmId){
        return new ResponseEntity<>(filmService.getFilmSummary(filmId), HttpStatus.OK);
    }

    @RequestMapping(value = "/films/{filmId}/actors/", method = RequestMethod.GET)
    public ResponseEntity<?> getFilmsActors(@PathVariable(name = "filmId")long filmId){
        return new ResponseEntity<>(filmService.getFilmsActors(filmId), HttpStatus.OK);
    }


}
