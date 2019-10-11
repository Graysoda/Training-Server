package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.CreateFilmRequest;
import training.generated.UpdateFilmRequest;
import training.persistence.dto.FilmDto;
import training.service.FilmService;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class FilmRestController {
    @Autowired
    private FilmService filmService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping("/films")
    public ResponseEntity<?> getAllFilms(){
        return ResponseEntity.ok(filmService.getAllFilms());
    }

    @GetMapping("/films/{filmId}")
    public ResponseEntity<?> getFilmById(@PathVariable int filmId){
        return ResponseEntity.ok(filmService.getFilmById(filmId));
    }

    @GetMapping("/films/rating/{rating}")
    public ResponseEntity<?> getFilmsByRating(@PathVariable String rating){
        if (!Constants.RATINGS.contains(rating)){
            return ResponseEntity.badRequest().body("rating not valid.");
        }
        return ResponseEntity.ok(filmService.getFilmsByRating(rating));
    }

    @GetMapping("/films/releaseYear/{releaseYear}")
    public ResponseEntity<?> getFilmsByReleaseYear(@PathVariable int releaseYear){
        return ResponseEntity.ok(filmService.getFilmsByReleaseYear(releaseYear));
    }

    @GetMapping("/films/title/{title}")
    public ResponseEntity<?> getFilmsByTitle(@PathVariable String title){
        return ResponseEntity.ok(filmService.getFilmsByTitle(title));
    }

    @GetMapping("/films/{filmId}/actors")
    public ResponseEntity<?> getFilmActors(@PathVariable int filmId){
        return ResponseEntity.ok(filmService.getFilmActors(filmId));
    }

    @PostMapping(value = "/films", consumes = "application/json")
    public ResponseEntity<?> createFilm(@RequestBody CreateFilmRequest request){
        FilmDto film = new FilmDto(request);
        String error = validationHelper.validateFilmCreation(film);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(filmService.save(film)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/films/{filmId}", consumes = "application/json")
    public ResponseEntity<?> updateFilm(@PathVariable int filmId, @RequestBody UpdateFilmRequest request){
        FilmDto film = new FilmDto(request);
        String error = validationHelper.validateFilmUpdate(filmId, film);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(filmService.save(film)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/films", consumes = "application/json")
    public ResponseEntity<?> updateFilm(@RequestBody UpdateFilmRequest request){
        FilmDto film = new FilmDto(request);
        String error = validationHelper.validateFilmUpdate(film);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(filmService.save(film)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping("/films/{filmId}")
    public ResponseEntity<?> deleteFilm(@PathVariable int filmId){
        if (filmId <= 0 || !filmService.exists(filmId)){
            return ResponseEntity.badRequest().body("film id is invalid");
        }
        filmService.delete(filmId);
        return ResponseEntity.ok("film with id [" + filmId + "] was deleted.");
    }
}
