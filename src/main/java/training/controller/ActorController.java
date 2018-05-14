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
import training.generated.Summary;
import training.service.ActorServiceImpl;

import java.util.List;

@RestController
public class ActorController {
    @Autowired @Lazy
    private ActorServiceImpl actorService;

    @RequestMapping(value = "/actor/", method = RequestMethod.GET, produces = "text/json")
    public ResponseEntity<List<Actor>> getAllActors(){
        return new ResponseEntity<>(actorService.getAllActors(),HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{actorId}", method = RequestMethod.GET, produces = "text/json")
    public ResponseEntity<Actor> getActorById(@PathVariable long actorId) {
        return new ResponseEntity<>(actorService.getActorById(actorId),HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{firstName}", method = RequestMethod.GET, produces = "text/json")
    public ResponseEntity<List<Actor>> getActorsByFirstName(@PathVariable String firstName){
        return new ResponseEntity<>(actorService.getActorsByFirstName(firstName), HttpStatus.OK);
    }

    @RequestMapping(value = "/actors/{actorId}/films", method = RequestMethod.GET, produces = "text/json")
    public ResponseEntity<List<Summary>> getFilmsWithActor(@PathVariable long actorId){
        return new ResponseEntity<>(actorService.getFilmsWithActor(actorId), HttpStatus.OK);
    }


}
