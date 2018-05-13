package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import training.service.ActorServiceImpl;

@RestController
public class ActorController {
    @Autowired @Lazy
    private ActorServiceImpl actorService;

    @RequestMapping(value = "/actor/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllActors(){
        return new ResponseEntity<>(actorService.getAllActors(),HttpStatus.OK);
    }

    @RequestMapping(value = "/actor/{actorId}", method = RequestMethod.GET)
    public ResponseEntity<?> getActorById(@RequestParam(value = "actorId", defaultValue = "1") long actorId) {
        return new ResponseEntity<>(actorService.getActorById(actorId),HttpStatus.OK);
    }

    @RequestMapping(value = "/actor/{firstName}", method = RequestMethod.GET)
    public ResponseEntity<?> getActorsByFirstName(@RequestParam(value = "firstName")String firstName){
        return new ResponseEntity<>(actorService.getActorsByFirstName(firstName), HttpStatus.OK);
    }

    @RequestMapping(value = "/actor/{actorId}/films", method = RequestMethod.GET)
    public ResponseEntity<?> getFilmsWithActor(@RequestParam(value = "actorId")long actorId){
        return new ResponseEntity<>(actorService.getFilmsWithActor(actorId), HttpStatus.OK);
    }


}
