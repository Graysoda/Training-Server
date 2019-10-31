package training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import training.service.FilmService;

@Controller
public class HomepageController {
    @Autowired
    private FilmService filmService;

    @GetMapping("/")
    public String homepage(Model model){
        model.addAttribute("films", filmService.getAllFilms());
        return "index";
    }

}
