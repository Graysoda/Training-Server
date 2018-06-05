package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.controller.jsonObjects.FilmJson;
import training.generated.*;
import training.service.impl.FilmServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class FilmsController {
    @Autowired @Lazy private FilmServiceImpl filmService;

    @RequestMapping(value = "/films", method = RequestMethod.GET)
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

    @RequestMapping(value = "/films/releaseYear/{releaseYear}", method = RequestMethod.GET)
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

    @RequestMapping(value = "/films/{filmId}/actors", method = RequestMethod.GET)
    public ResponseEntity<List<Actor>> getFilmsActors(@PathVariable long filmId){
        return new ResponseEntity<>(filmService.getFilmsActors(filmId), HttpStatus.OK);
    }

    @RequestMapping(value = "/films", method = RequestMethod.POST)
    public ResponseEntity<?> createFilm(@RequestBody FilmJson filmJson){
        CreateFilmRequest request = new CreateFilmRequest();

        if (filmJson.getTitle() != null)
            request.setTitle(filmJson.getTitle());
        else
            return ResponseEntity.badRequest().body("Film title cannot be null.");

        if (filmJson.getDescription() != null)
            request.setDescription(filmJson.getDescription());
        else
            return ResponseEntity.badRequest().body("Film description cannot be null.");

        if (filmJson.getLanguage() != null)
            request.setLanguage(filmJson.getLanguage());
        else
            return ResponseEntity.badRequest().body("Film language cannot be null.");

        if (filmJson.getLength() != null)
            request.setLength(filmJson.getLength());
        else
            return ResponseEntity.badRequest().body("Film length cannot be null.");

        if (filmJson.getOriginalLanguage() != null)
            request.setOriginalLanguage(filmJson.getOriginalLanguage());
        else
            return ResponseEntity.badRequest().body("Film originalLanguage cannot be null");

		System.out.println("rating = ["+filmJson.getRating()+"]");

        if (filmJson.getRating() != null)
            request.setRating(filmJson.getRating());
        else
            return ResponseEntity.badRequest().body("Film rating cannot be null.");

        if (filmJson.getReleaseYear() != null)
            request.setReleaseYear(filmJson.getReleaseYear());
        else
            return ResponseEntity.badRequest().body("Film releaseYear cannot be null.");

        if (filmJson.getRentalDuration() != null)
            request.setRentalDuration(filmJson.getRentalDuration());
        else
            return ResponseEntity.badRequest().body("Film rentalDuration cannot be null.");

        if (filmJson.getRentalRate() != null)
            request.setRentalRate(filmJson.getRentalRate());
        else
            return ResponseEntity.badRequest().body("Film rentalRate cannot be null.");

        if (filmJson.getReplacementCost() != null)
            request.setReplacementCost(filmJson.getReplacementCost());
        else
            return ResponseEntity.badRequest().body("Film replacementCost cannot be null.");

        if (filmJson.getSpecialFeatures() != null)
            request.setSpecialFeatures(filmJson.getSpecialFeatures());
        else
            return ResponseEntity.badRequest().body("Film specialFeatures cannot be null.");

        return filmService.createFilm(request);
    }

    @RequestMapping(value = "/films/{filmId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateFilm(@PathVariable long filmId, @RequestBody FilmJson filmJson){
        UpdateFilmRequest request = new UpdateFilmRequest();

        request.setFilmId(filmId);

        if (filmJson.getTitle() != null)
            request.setTitle(filmJson.getTitle());
        if (filmJson.getDescription() != null)
            request.setDescription(filmJson.getDescription());
        if (filmJson.getLanguage() != null)
            request.setLanguage(filmJson.getLanguage());
        if (filmJson.getLength() != null)
            request.setLength(filmJson.getLength());
        if (filmJson.getOriginalLanguage() != null)
            request.setOriginalLanguage(filmJson.getOriginalLanguage());
        if (filmJson.getRating() != null)
            request.setRating(filmJson.getRating());
        if (filmJson.getReleaseYear() != null)
            request.setReleaseYear(filmJson.getReleaseYear());
        if (filmJson.getRentalDuration() != null)
            request.setRentalDuration(filmJson.getRentalDuration());
        if (filmJson.getRentalRate() != null)
            request.setRentalRate(filmJson.getRentalRate());
        if (filmJson.getReplacementCost() != null)
            request.setReplacementCost(filmJson.getReplacementCost());
        if (filmJson.getSpecialFeatures() != null)
            request.setSpecialFeatures(filmJson.getSpecialFeatures());

        return filmService.updateFilm(request);
    }

    @RequestMapping(value = "/films/{filmId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFilm(@PathVariable long filmId){
        return filmService.deleteFilm(filmId);
    }
}
