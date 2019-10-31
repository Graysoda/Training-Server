package training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import training.generated.Film;
import training.service.FilmService;

import java.util.List;

@Controller
public class FilmController {
    @Autowired
    private FilmService filmService;

    @GetMapping("/film/{id}")
    public String getFilm(@PathVariable int id, Model model){
        Film film = filmService.getFilmById(id);

        film.setSpecialFeatures(
                film.getSpecialFeatures()
                .replace("{","")
                .replace("}","")
                .replace("\"", "").trim()
        );

        model.addAttribute("f", film);
        return "film";
    }

    @GetMapping("/films/category/{category}")
    public String getFilmsByCategory(@PathVariable String category, Model model){
        List<Film> films = filmService.getFilmsByCategory(category);

        films.forEach( f -> f.setSpecialFeatures(
                f.getSpecialFeatures()
                        .replace("{","")
                        .replace("}","")
                        .replace("\"", "").trim()
        ));

        model.addAttribute("films", films);

        return "index";
    }

    @GetMapping("/films/rating/{rating}")
    public String getFilmsByRating(@PathVariable String rating, Model model){
        List<Film> films = filmService.getFilmsByRating(rating);

        films.forEach( f -> f.setSpecialFeatures(
                f.getSpecialFeatures()
                        .replace("{","")
                        .replace("}","")
                        .replace("\"", "").trim()
        ));

        model.addAttribute("films", films);

        return "index";
    }
}
