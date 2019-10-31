package training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import training.generated.Actor;
import training.generated.Film;
import training.persistence.dto.ActorDto;
import training.service.ActorService;

import java.util.List;

@Controller
public class ActorController {
    @Autowired
    private ActorService actorService;

    @GetMapping("/actors")
    public String getAllActors(Model model){
        model.addAttribute("actors", actorService.getAllActors());
        return "allActors";
    }

    @GetMapping("/actor/{id}")
    public String getActor(@PathVariable int id, Model model){
        Actor actor = actorService.getActorById(id);
        List<Film> films = actorService.getFilmsWithActor(id);

        model.addAttribute("actor", actor);
        model.addAttribute("films", films);

        return "actor";
    }

    @GetMapping("/actor/{id}/update")
    public String getActorUpdateForm(@PathVariable int id, Model model){
        model.addAttribute("actor", actorService.getActorById(id));
        model.addAttribute("actorDto", new ActorDto());
        return "updateActor";
    }

    @PostMapping("/actor/{id}/update")
    public String updateActor(@PathVariable int id, @ModelAttribute ActorDto actorDto, BindingResult errors, Model model){
        if (!errors.hasErrors()){
            actorService.save(actorDto);
            model.addAttribute("actors", actorService.getAllActors());
        }
        return errors.hasErrors() ? "updateActor" : "allActors";
    }

    @GetMapping("/actor/create")
    public String getCreateActorForm(Model model){
        model.addAttribute("actorDto", new ActorDto());
        return "createActor";
    }

    @PostMapping("/actor/create")
    public String createActor(@ModelAttribute ActorDto actorDto, BindingResult errors, Model model){
        if (!errors.hasErrors()){
            actorService.save(actorDto);
            model.addAttribute("actors", actorService.getAllActors());
        }
        return errors.hasErrors() ? "createActor" : "allActors";
    }
}
