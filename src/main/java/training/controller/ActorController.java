package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;
import training.service.ActorServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class ActorController {
    @Autowired @Lazy
    private ActorServiceImpl actorService;

    @RequestMapping(value = "/actors/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Actor>> getAllActors(){
        return new ResponseEntity<>(actorService.getAllActors(),HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{actorId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getActorById(@PathVariable long actorId) {
        Actor actor = actorService.getActorById(actorId);
        if (actor == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Actor with id ["+actorId+"]");
        else
            return new ResponseEntity<>(actor,HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/first_name/{firstName}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getActorsByFirstName(@PathVariable String firstName){
        List<Actor> actors = actorService.getActorsByFirstName(firstName);
        if (actors == null || actors.size() == 0)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No Actors with that first name.");
        else
            return new ResponseEntity<>(actors, HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{actorId}/films", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFilmsWithActor(@PathVariable long actorId){
        List<Summary> summaries = actorService.getFilmsWithActor(actorId);
        if (summaries == null || summaries.size() == 0)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No films associated with actor ["+actorId+"]");
        else
            return new ResponseEntity<>(summaries, HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/create", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> createActor(@RequestBody String firstName, @RequestBody String lastName){
        CreateActorRequest request = new CreateActorRequest();

        request.setFirstName(firstName);
        request.setLastName(lastName);

        return actorService.insertActor(request);
    }

    @RequestMapping(value = "/actors/{actorId}/update", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateActor(@PathVariable long actorId, @RequestBody(required = false) String firstName, @RequestBody(required = false) String lastName){
        UpdateActorRequest request = new UpdateActorRequest();

        request.setActorId(actorId);
        request.setNewFirstName(firstName);
        request.setNewLastName(lastName);

        return actorService.updateActor(request);
    }

    @RequestMapping(value = "/actors/{actorId}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteActor(@PathVariable long actorId){
        return actorService.deleteActor(actorId);
    }
}
