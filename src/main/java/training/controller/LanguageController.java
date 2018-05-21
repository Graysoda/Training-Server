package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.controller.jsonObjects.LanguageJson;
import training.service.LanguageServiceImpl;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class LanguageController {
	@Autowired @Lazy
	private LanguageServiceImpl languageService;

	@RequestMapping(value = "/langauges/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllLanguages(){
		return new ResponseEntity<>(languageService.getAllLanguages(), HttpStatus.OK);
	}

	@RequestMapping(value = "/languages/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getLanguageByValue(@RequestBody LanguageJson languageJson){
		if (languageJson.getLanguageId() != null){
			return new ResponseEntity<>(languageService.getLanguageById(languageJson.getLanguageId()),HttpStatus.OK);
		} else if (languageJson.getName() != null){
			return new ResponseEntity<>(languageService.getLanguageByName(languageJson.getName()),HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body("Request Body is null.");
		}
	}
}
