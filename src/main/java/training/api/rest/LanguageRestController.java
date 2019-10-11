package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Language;
import training.service.LanguageService;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class LanguageRestController {
    @Autowired
    LanguageService languageService;

    @GetMapping("/languages")
    public ResponseEntity<List<Language>> getAllLanguages(){
        return ResponseEntity.ok(languageService.getAllLanguages());
    }

    @GetMapping("/languages/{languageId}")
    public ResponseEntity<Language> getLanguageById(@PathVariable int languageId){
        return ResponseEntity.ok(languageService.getLanguageById(languageId));
    }
}
