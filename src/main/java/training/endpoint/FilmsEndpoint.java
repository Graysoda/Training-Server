package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.FilmServiceImpl;


@Endpoint
public class FilmsEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired private FilmServiceImpl filmService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createFilmRequest")
	@ResponsePayload
    public ResponseEntity<?> createFilm(@RequestPayload CreateFilmRequest request) {
		return filmService.createFilm(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteFilmRequest")
	@ResponsePayload
    public ResponseEntity<?> deleteFilm(@RequestPayload DeleteFilmRequest request) {
		return filmService.deleteFilm(request.getFilmId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateFilmRequest")
	@ResponsePayload
    public ResponseEntity<?> updateFilm(@RequestPayload UpdateFilmRequest request) {
		return filmService.updateFilm(request);
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

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmTextRequest")
	@ResponsePayload
	public GetFilmTextResponse getFilmText(@RequestPayload GetFilmTextRequest request){
		GetFilmTextResponse response = new GetFilmTextResponse();
		response.setFilmText(filmService.getFilmSummary(request.getFilmId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmsActorsRequest")
	@ResponsePayload
	public GetFilmsActorsResponse getFilmsActors(@RequestPayload GetFilmsActorsRequest request) {
		GetFilmsActorsResponse response = new GetFilmsActorsResponse();
		response.setActors(filmService.getFilmsActors(request.getFilmId()));
		return response;
	}
}
