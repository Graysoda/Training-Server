package training.api.soap;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.*;
import training.persistence.dto.FilmDto;
import training.service.FilmService;

@Endpoint
public class FilmEndpoint {
    @Autowired
    private FilmService filmService;
    @Autowired
    private ValidationHelper validationHelper;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllFilmsRequest")
    @ResponsePayload
    public GetAllFilmsResponse getAllFilms(@RequestPayload GetAllFilmsRequest request){
        GetAllFilmsResponse response = new GetAllFilmsResponse();
        response.setFilm(filmService.getAllFilms());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getFilmByTitleRequest")
    @ResponsePayload
    public GetFilmByTitleResponse getFilmByTitle(@RequestPayload GetFilmByTitleRequest request){
        GetFilmByTitleResponse response = new GetFilmByTitleResponse();
        response.setFilm(filmService.getFilmsByTitle(request.getTitle()));
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getFilmByIdRequest")
    @ResponsePayload
    public GetFilmByIdResponse getFilmById(@RequestPayload GetFilmByIdRequest request){
        GetFilmByIdResponse response = new GetFilmByIdResponse();

        if (filmService.exists(request.getFilmId())){
            response.setFilm(filmService.getFilmById(request.getFilmId()));
        } else {
            response.setError("film " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getFilmByReleaseYearRequest")
    @ResponsePayload
    public GetFilmByReleaseYearResponse getFilmByReleaseYear(@RequestPayload GetFilmByReleaseYearRequest request){
        GetFilmByReleaseYearResponse response = new GetFilmByReleaseYearResponse();
        response.setFilm(filmService.getFilmsByReleaseYear(request.getReleaseYear()));
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getFilmByRatingRequest")
    @ResponsePayload
    public GetFilmByRatingResponse getFilmByRating(@RequestPayload GetFilmByRatingRequest request){
        GetFilmByRatingResponse response = new GetFilmByRatingResponse();

        if (Constants.RATINGS.contains(request.getRating())){
            response.setFilm(filmService.getFilmsByRating(request.getRating()));
        } else {
            response.setError("rating [" + request.getRating() + "] is invalid.");
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getFilmsActorsRequest")
    @ResponsePayload
    public GetFilmsActorsResponse getFilmsActors(@RequestPayload GetFilmsActorsRequest request){
        GetFilmsActorsResponse response = new GetFilmsActorsResponse();

        if (filmService.exists(request.getFilmId())){
            response.setActors(filmService.getFilmActors(request.getFilmId()));
        } else {
            response.setError("film " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "createFilmRequest")
    @ResponsePayload
    public CreateFilmResponse createFilm(@RequestPayload CreateFilmRequest request){
        CreateFilmResponse response = new CreateFilmResponse();
        FilmDto film = new FilmDto(request);
        String error = validationHelper.validateFilmCreation(film);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setFilm(filmService.save(film));
        } else {
            response.setError(error);
        }

        return response;
    }
    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "updateFilmRequest")
    @ResponsePayload
    public UpdateFilmResponse updateFilm(@RequestPayload UpdateFilmRequest request){
        UpdateFilmResponse response = new UpdateFilmResponse();
        FilmDto film = new FilmDto(request);
        String error = validationHelper.validateFilmUpdate(film);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setFilm(filmService.save(film));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "deleteFilmRequest")
    @ResponsePayload
    public DeleteFilmResponse deleteFilm(@RequestPayload DeleteFilmRequest request){
        DeleteFilmResponse response = new DeleteFilmResponse();

        if (request.getFilmId() <= 0 || !filmService.exists(request.getFilmId())){
            response.setResponse("film id invalid.");
        } else {
            filmService.delete(request.getFilmId());
            response.setResponse("film with id [" + request.getFilmId() + "] was deleted.");
        }

        return response;
    }
}
