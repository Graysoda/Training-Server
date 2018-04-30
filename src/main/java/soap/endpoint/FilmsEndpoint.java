package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.FilmServiceImpl;

import java.util.List;

@Controller
@Endpoint
public class FilmsEndpoint {
	private static final String NAMESPACE_URI = "my-namespace";

	private FilmServiceImpl filmService;

	@Autowired(required = true)
	public void setFilmService(FilmServiceImpl filmService){
		this.filmService = filmService;
	}



	@PayloadRoot(namespace = NAMESPACE_URI,localPart = "getAllFilmsRequest")
	@ResponsePayload
	public GetAllFilmsResponse getAllFilms(@RequestPayload GetAllFilmsRequest request){
		System.out.println("get all films");
		GetAllFilmsResponse response = new GetAllFilmsResponse();

		List<Film> films = filmService.listFilms();
		response.setFilm(films);
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmsById")
	@ResponsePayload
	public GetFilmByIdResponse getFilmById(@RequestPayload GetFilmByIdRequest request){
		System.out.println("get film by id");
		GetFilmByIdResponse response = new GetFilmByIdResponse();
		response.setFilm(filmService.getFilmById(request.getFilmId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createFilmRequest")
	public void createFilm(@RequestPayload CreateFilmRequest request){
		filmService.addFilm(request);
	}
}
