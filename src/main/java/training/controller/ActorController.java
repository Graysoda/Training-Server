package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;
import training.service.impl.ActorServiceImpl;

import java.util.List;

@Controller
public class ActorController {
    private ActorServiceImpl actorService;

    @Autowired
    public void setActorService(ActorServiceImpl actorService) {
        this.actorService = actorService;
    }

    @RequestMapping(value = "/actors", method = RequestMethod.GET)
    public String showAll(Model model){
        model.addAttribute("actors", actorService.getAllActors());
        return "allActors";
    }

    @RequestMapping(value = "/actors/{actorId}", method = RequestMethod.GET)
    public String showById(Model model, @PathVariable long actorId){
        Actor actor = actorService.getActorById(actorId);

        model.addAttribute("actor", actor);

        return "actor";
    }

    @RequestMapping(value = "/actors/first_name/{firstName}", method = RequestMethod.GET)
    public String showByFirstName(Model model, @PathVariable String firstName){
        List<Actor> actors = actorService.getActorsByFirstName(firstName);

        model.addAttribute("actors", actors);

        return "allActors";
    }

    @RequestMapping(value = "/actors/last_name/{lastName}",method = RequestMethod.GET)
    public String showByLastName(Model model, @PathVariable String lastName){
        List<Actor> actors = actorService.getActorsByLastName(lastName);

        model.addAttribute("actors", actors);

        return "allActors";
    }

    @RequestMapping(value = "/actors/{actorId}/films", method = RequestMethod.GET)
    public String showAssociatedFilms(Model model, @PathVariable long actorId){
        List<Summary> summaries = actorService.getFilmsWithActor(actorId);

        model.addAttribute("summaries", summaries);

        return "summaries";
    }

    @RequestMapping(value = "/actors/create", method = RequestMethod.GET)
    public String createActorForm(Model model){

        model.addAttribute("actor", new Actor());

        return "createActor";
    }

    @RequestMapping(value = "/actor/create/result", method = RequestMethod.POST)
    public String createActorSubmit(@ModelAttribute Actor actor){
        CreateActorRequest request = new CreateActorRequest();

        request.setFirstName(actor.getFirstName());
        request.setLastName(actor.getLastName());

        ResponseEntity responseEntity = actorService.insertActor(request);

        if (responseEntity.getBody() instanceof Actor){
            actor.setActorId(((Actor) responseEntity.getBody()).getActorId());
            return "actorResult";
        } else {
            return "error";
        }
    }

    @RequestMapping(value = "/actors/{actorId}/update", method = RequestMethod.GET)
    public String updateActorForm(Model model, @PathVariable long actorId){
        Actor actor = actorService.getActorById(actorId);

        model.addAttribute("actor", actor);
        model.addAttribute("link", "/actor/"+actorId);

        return "updateActor";
    }

    @RequestMapping(value = "/actors/{actorId}", method = RequestMethod.PUT)
    public String updateActorSubmit(@ModelAttribute Actor actor, @PathVariable long actorId){
        UpdateActorRequest request = new UpdateActorRequest();

        request.setActorId(actorId);
        if (actor.getFirstName() != null && !actor.getFirstName().isEmpty())
            request.setNewFirstName(actor.getFirstName());
        if (actor.getLastName() != null && !actor.getLastName().isEmpty())
            request.setNewLastName(actor.getLastName());

        ResponseEntity responseEntity = actorService.updateActor(request);

        if (responseEntity.getBody() instanceof Actor){
            return "actor";
        } else {
            return "error";
        }
    }
}
