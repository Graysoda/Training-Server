package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Actor;
import training.service.ActorServiceImpl;

import java.util.List;

@RestController(value = "actor")
public class ActorController {
    @Autowired @Lazy
    private ActorServiceImpl actorService;

    @RequestMapping("/actors")
    public List<Actor> getAllActors(){
        return actorService.getAllActors();
    }
}
