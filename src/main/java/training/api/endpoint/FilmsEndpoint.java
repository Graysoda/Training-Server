package training.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.api.Common;
import training.generated.*;
import training.service.impl.FilmServiceImpl;

import java.util.ArrayList;


@Endpoint
public class FilmsEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired private FilmServiceImpl filmService;
	private static final ArrayList<String> ratings = new ArrayList<String>(){
		{
			add("R");
			add("G");
			add("PG");
			add("PG-13");
			add("NC-17");
		}
	};

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createFilmRequest")
	@ResponsePayload
    public CreateFilmResponse createFilm(@RequestPayload CreateFilmRequest request) {
		CreateFilmResponse response = new CreateFilmResponse();

		if (request.getTitle().isEmpty()){
			response.setError("Film title cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getTitle())){
			response.setError(Common.stringFailureMessage("Film title"));
			return response;
		}

		if (request.getDescription().isEmpty()){
			response.setError("description cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getDescription())){
			response.setError(Common.stringFailureMessage("Film description"));
			return response;
		}

		if (request.getLanguage().isEmpty()){
			response.setError("language cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getLanguage())){
			response.setError(Common.stringFailureMessage("Film language"));
			return response;
		}

		if (!Common.isStringSafe(request.getOriginalLanguage())){
			response.setError(Common.stringFailureMessage("Film originalLanguage"));
			return response;
		}

		if (request.getLength() < 0){
			response.setError("length is invalid");
			return response;
		}

		if (request.getRating().isEmpty()){
			response.setError("rating cannot be empty");
			return response;
		} else if (!isValidRating(request.getRating())){
			response.setError("rating is invalid");
			return response;
		}

		if (request.getReleaseYear() < 0){
			response.setError("releaseYear is invalid");
			return response;
		}

		if (request.getRentalDuration() < 0){
			response.setError("rentalDuration is invalid");
			return response;
		}

		if (request.getRentalRate() < 0){
			response.setError("rentalRate is invalid");
			return response;
		}

		if (request.getReplacementCost() < 0){
			response.setError("replacementCost is invalid");
			return response;
		}

		ResponseEntity entity = filmService.createFilm(request);

		if (entity.getBody() instanceof Film){
			response.setFilm(((Film) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteFilmRequest")
	@ResponsePayload
    public DeleteFilmResponse deleteFilm(@RequestPayload DeleteFilmRequest request) {
		DeleteFilmResponse response = new DeleteFilmResponse();

		if (request.getFilmId() < 0){
			response.setResponse("filmId is invalid");
			return response;
		}

		ResponseEntity entity = filmService.deleteFilm(request.getFilmId());

		response.setResponse(entity.getBody().toString());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateFilmRequest")
	@ResponsePayload
    public UpdateFilmResponse updateFilm(@RequestPayload UpdateFilmRequest request) {
		UpdateFilmResponse response = new UpdateFilmResponse();

		if (request.getFilmId() < 0){
			response.setError("filmId is invalid");
			return response;
		}

		if (request.getTitle().isEmpty()){
			response.setError("Film title cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getTitle())){
			response.setError(Common.stringFailureMessage("Film title"));
			return response;
		}

		if (request.getDescription().isEmpty()){
			response.setError("description cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getDescription())){
			response.setError(Common.stringFailureMessage("Film description"));
			return response;
		}

		if (request.getLanguage().isEmpty()){
			response.setError("language cannot be empty");
			return response;
		} else if (!Common.isStringSafe(request.getLanguage())){
			response.setError(Common.stringFailureMessage("Film language"));
			return response;
		}

		if (!Common.isStringSafe(request.getOriginalLanguage())){
			response.setError(Common.stringFailureMessage("Film originalLanguage"));
			return response;
		}

		if (request.getLength() < 0){
			response.setError("length is invalid");
			return response;
		}

		if (request.getRating().isEmpty()){
			response.setError("rating cannot be empty");
			return response;
		} else if (!isValidRating(request.getRating())){
			response.setError("rating is invalid");
			return response;
		}

		if (request.getReleaseYear() < 0){
			response.setError("releaseYear is invalid");
			return response;
		}

		if (request.getRentalDuration() < 0){
			response.setError("rentalDuration is invalid");
			return response;
		}

		if (request.getRentalRate() < 0){
			response.setError("rentalRate is invalid");
			return response;
		}

		if (request.getReplacementCost() < 0){
			response.setError("replacementCost is invalid");
			return response;
		}

		ResponseEntity entity = filmService.updateFilm(request);

		if (entity.getBody() instanceof Film){
			response.setFilm(((Film) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI,localPart = "getAllFilmsRequest")
	@ResponsePayload
	public GetAllFilmsResponse getAllFilms(@RequestPayload GetAllFilmsRequest request) {
		System.out.println("get all films");
		GetAllFilmsResponse response = new GetAllFilmsResponse();
		response.setFilm(filmService.getAllFilms());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmByIdRequest")
	@ResponsePayload
	public GetFilmByIdResponse getFilmById(@RequestPayload GetFilmByIdRequest request) {
		System.out.println("get film by id");
		GetFilmByIdResponse response = new GetFilmByIdResponse();
		response.setFilm(filmService.getFilmById(request.getFilmId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmByRatingRequest")
	@ResponsePayload
	public GetFilmByRatingResponse getFilmByRating(@RequestPayload GetFilmByRatingRequest request) {
		GetFilmByRatingResponse response = new GetFilmByRatingResponse();
		response.setFilm(filmService.getFilmByRating(request.getRating()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmByReleaseYearRequest")
	@ResponsePayload
	public GetFilmByReleaseYearResponse getFilmByReleaseYear(@RequestPayload GetFilmByReleaseYearRequest request) {
		GetFilmByReleaseYearResponse response = new GetFilmByReleaseYearResponse();
		response.setFilm(filmService.getFilmByReleaseYear(request.getReleaseYear()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmByTitleRequest")
	@ResponsePayload
	public GetFilmByTitleResponse getFilmByTitle(@RequestPayload GetFilmByTitleRequest request) {
		GetFilmByTitleResponse response = new GetFilmByTitleResponse();
		response.setFilm(filmService.getFilmByTitle(request.getTitle()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmSummaryRequest")
	@ResponsePayload
	public GetFilmSummaryResponse getFilmText(@RequestPayload GetFilmSummaryRequest request){
		GetFilmSummaryResponse response = new GetFilmSummaryResponse();
		response.setSummary(filmService.getFilmSummary(request.getFilmId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmsActorsRequest")
	@ResponsePayload
	public GetFilmsActorsResponse getFilmsActors(@RequestPayload GetFilmsActorsRequest request) {
		GetFilmsActorsResponse response = new GetFilmsActorsResponse();
		response.setActors(filmService.getFilmsActors(request.getFilmId()));
		return response;
	}

	private boolean isValidRating(String rating) {
		boolean isValid = false;
		for (int i = 0; i < ratings.size(); i++) {
			if (rating.equals(ratings.get(i))){
				isValid = true;
				break;
			}
		}
		return isValid;
	}
}
