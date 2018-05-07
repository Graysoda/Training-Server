package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.FilmServiceImpl;

import java.sql.SQLException;


@Endpoint
public class FilmsEndpoint {
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	private FilmServiceImpl filmService;

	@Autowired
	public void setFilmService(FilmServiceImpl filmService){
		this.filmService = filmService;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createFilmRequest")
	public void createFilm(@RequestPayload CreateFilmRequest request){
		filmService.createFilm(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteFilmRequest")
	public void deleteFilm(@RequestPayload DeleteFilmRequest request){
		filmService.deleteFilm(request.getFilmId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateFilmRequest")
	public void updateFilm(@RequestPayload UpdateFilmRequest request){
		filmService.updateFilm(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI,localPart = "getAllFilmsRequest")
	@ResponsePayload
	public GetAllFilmsResponse getAllFilms(@RequestPayload GetAllFilmsRequest request) throws SQLException {
		System.out.println("get all films");
		GetAllFilmsResponse response = new GetAllFilmsResponse();
		response.setFilm(filmService.listFilms());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmsByIdRequest")
	@ResponsePayload
	public GetFilmByIdResponse getFilmById(@RequestPayload GetFilmByIdRequest request) throws SQLException {
		System.out.println("get film by id");
		GetFilmByIdResponse response = new GetFilmByIdResponse();
		response.setFilm(filmService.getFilmById(request.getFilmId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getByRatingRequest")
	@ResponsePayload
	public GetFilmByRatingResponse getFilmByRating(@RequestPayload GetFilmByRatingRequest request) throws SQLException {
		GetFilmByRatingResponse response = new GetFilmByRatingResponse();
		response.setFilm(filmService.getFilmByRating(request.getRating()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getByReleaseYearRequest")
	@ResponsePayload
	public GetFilmByReleaseYearResponse getFilmByReleaseYear(@RequestPayload GetFilmByReleaseYearRequest request) throws SQLException {
		GetFilmByReleaseYearResponse response = new GetFilmByReleaseYearResponse();
		response.setFilm(filmService.getFilmByReleaseYear(request.getReleaseYear()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmByTitleRequest")
	@ResponsePayload
	public GetFilmByTitleResponse getFilmByTitle(@RequestPayload GetFilmByTitleRequest request) throws SQLException {
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
	public GetFilmsActorsResponse getFilmsActors(@RequestPayload GetFilmsActorsRequest request) throws SQLException {
		GetFilmsActorsResponse response = new GetFilmsActorsResponse();
		response.setActors(filmService.getFilmsActors(request.getFilmId()));
		return response;
	}
}
