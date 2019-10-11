package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.ValidationHelper;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Film;
import training.generated.UpdateActorRequest;
import training.persistence.dto.ActorDto;
import training.service.ActorService;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ActorRestController {
    @Autowired
    private ActorService actorService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping(value = "/actors")
    public ResponseEntity<List<Actor>> getAllActors(){
        return new ResponseEntity<>(actorService.getAllActors(), HttpStatus.OK);
    }

    @GetMapping(value = "/actors/{actorId}")
    public ResponseEntity<?> getActorById(@PathVariable int actorId){
        Actor actor = actorService.getActorById(actorId);

        return actor != null ? new ResponseEntity<>(actor, HttpStatus.OK)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Actor with id ["+actorId+"]");
    }

    @GetMapping(value = "/actors/first_name/{firstName}")
    public ResponseEntity<?> getActorsByFirstName(@PathVariable String firstName){
        List<Actor> actors = actorService.getActorsByFirstName(firstName);

        return actors != null && actors.size() > 0 ? new ResponseEntity<>(actors, HttpStatus.OK)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Actors with first name [" + firstName + "]");
    }

    @GetMapping(value = "/actors/last_name/{lastName}")
    public ResponseEntity<?> getActorsByLastName(@PathVariable String lastName){
        List<Actor> actors = actorService.getActorsByLastName(lastName);

        return actors != null && actors.size() > 0 ? new ResponseEntity<>(actors, HttpStatus.OK)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Actors with last name [" + lastName + "]");
    }

    @GetMapping(value = "actors/{actorId}/films")
    public ResponseEntity<?> getFilmsWithActor(@PathVariable int actorId){
        List<Film> films = actorService.getFilmsWithActor(actorId);

        return films != null && films.size() > 0 ? new ResponseEntity<>(films, HttpStatus.OK)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body("No films associated with actor id [" + actorId + "]");
    }

    @PostMapping(value = "/actors", consumes = "application/json")
    public ResponseEntity<?> createActor(@RequestBody CreateActorRequest request){
        ActorDto actorDto = new ActorDto(request);
        String error = validationHelper.validateActorCreation(actorDto);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.status(HttpStatus.CREATED).body(actorService.save(actorDto)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/actors/{actorId}")
    public ResponseEntity<?> updateActor(@PathVariable int actorId, @RequestBody UpdateActorRequest request){
        ActorDto actorDto = new ActorDto(request);
        String error = validationHelper.validateActorUpdate(actorId, actorDto);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(actorService.save(actorDto)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/actors/")
    public ResponseEntity<?> updateActor(@RequestBody UpdateActorRequest request){
        ActorDto actorDto = new ActorDto(request);
        String error = validationHelper.validateActorUpdate(actorDto);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(actorService.save(actorDto)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping(value = "/actors/{actorId}")
    public ResponseEntity<?> deleteActor(@PathVariable int actorId){
        if(actorId <= 0 || !actorService.exists(actorId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("actor id is invalid");
        }
        actorService.delete(actorId);
        return ResponseEntity.status(HttpStatus.OK).body("Actor with id [" + actorId + "] was deleted.");
    }
}
