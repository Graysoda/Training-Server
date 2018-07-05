package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.service.impl.LanguageServiceImpl;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class LanguageRestController {
	@Autowired @Lazy
	private LanguageServiceImpl languageService;

	@RequestMapping(value = "/languages", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllLanguages(){
		return new ResponseEntity<>(languageService.getAllLanguages(), HttpStatus.OK);
	}

	@RequestMapping(value = "/languages/{languageId}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getLanguageByValue(@PathVariable Long languageId){
		if (languageId != null){
			return new ResponseEntity<>(languageService.getLanguageById(languageId),HttpStatus.OK);
		} else {
			return ResponseEntity.badRequest().body("Request Body is null.");
		}
	}
}
