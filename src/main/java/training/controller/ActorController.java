package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;
import training.service.ActorServiceImpl;

import java.util.List;

@RestController
public class ActorController {
    @Autowired @Lazy
    private ActorServiceImpl actorService;

    @RequestMapping(value = "/actors/", method = RequestMethod.GET)
    public ResponseEntity<List<Actor>> getAllActors(){
        return new ResponseEntity<>(actorService.getAllActors(),HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{actorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getActorById(@PathVariable long actorId) {
        Actor actor = actorService.getActorById(actorId);
        if (actor == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Actor with id ["+actorId+"]");
        else
            return new ResponseEntity<>(actor,HttpStatus.OK);
    }

    @RequestMapping(value = "/actors_first_name/{firstName}", method = RequestMethod.GET)
    public ResponseEntity<?> getActorsByFirstName(@PathVariable String firstName){
        List<Actor> actors = actorService.getActorsByFirstName(firstName);
        if (actors == null || actors.size() == 0)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Actors with that first name.");
        else
            return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{actorId}/films", method = RequestMethod.GET)
    public ResponseEntity<?> getFilmsWithActor(@PathVariable long actorId){
        List<Summary> summaries = actorService.getFilmsWithActor(actorId);
        if (summaries == null || summaries.size() == 0)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No films associated with actor ["+actorId+"]");
        else
            return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/create/",method = RequestMethod.PUT)
    public ResponseEntity<?> createActor(CreateActorRequest request){
        return actorService.insertActor(request);
    }

    @RequestMapping(value = "actors/{actorId}/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateActor(@PathVariable long actorId, UpdateActorRequest request){
        if (request == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please provide a non-null Actor value!");
//        UpdateActorRequest request = new UpdateActorRequest();

        return actorService.updateActor(request);
    }
}
