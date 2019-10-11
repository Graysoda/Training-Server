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
import training.persistence.dto.ActorDto;
import training.service.ActorService;

@Endpoint
public class ActorsEndpoint {
    private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
    @Autowired
    private ActorService actorService;
    @Autowired
    private ValidationHelper validationHelper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createActorRequest")
    @ResponsePayload
    public CreateActorResponse createActor(@RequestPayload CreateActorRequest request){
        CreateActorResponse response = new CreateActorResponse();
        ActorDto actor = new ActorDto(request);
        String error = validationHelper.validateActorCreation(actor);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setActor(actorService.save(actor));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateActorRequest")
    @ResponsePayload
    public UpdateActorResponse updateActor(@RequestPayload UpdateActorRequest request) {
        UpdateActorResponse response = new UpdateActorResponse();

        ActorDto actor = new ActorDto(request);
        String error = validationHelper.validateActorUpdate(actor);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setActor(actorService.save(actor));
        } else {
            response.setError(error);
        }
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteActorRequest")
    @ResponsePayload
    public DeleteActorResponse deleteActor(@RequestPayload DeleteActorRequest request) {
        DeleteActorResponse response = new DeleteActorResponse();

        if (request.getActorId() <= 0 || !actorService.exists(request.getActorId())){
            response.setResponse("actorId is invalid");
        } else {
            actorService.delete(request.getActorId());
            response.setResponse("Actor [" + request.getActorId() + "] was deleted successfully.");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllActorsRequest")
    @ResponsePayload
    public GetAllActorsResponse getAllActors(@RequestPayload GetAllActorsRequest request) {
        GetAllActorsResponse response = new GetAllActorsResponse();
        response.setActor(actorService.getAllActors());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI,localPart = "getActorsByIdRequest")
    @ResponsePayload
    public GetActorsByIdResponse getActorById(@RequestPayload GetActorsByIdRequest request){
        GetActorsByIdResponse response = new GetActorsByIdResponse();

        if (actorService.exists(request.getActorId())){
            System.out.println("actor with id ["+ +request.getActorId() + "] exists");
            response.setActor(actorService.getActorById(request.getActorId()));
        } else {
            System.out.println("actor with id [" + request.getActorId() + "] DNE");
            response.setError("actor " + Constants.DNE);
        }

        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getActorsByFirstNameRequest")
    @ResponsePayload
    public GetActorsByFirstNameResponse getActorsByFirstName(@RequestPayload GetActorsByFirstNameRequest request){
        GetActorsByFirstNameResponse response = new GetActorsByFirstNameResponse();
        response.setActor(actorService.getActorsByFirstName(request.getActorFirstName()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getActorsByLastNameRequest")
    @ResponsePayload
    public GetActorsByLastNameResponse getActorsByLastName(@RequestPayload GetActorsByLastNameRequest request){
        GetActorsByLastNameResponse response = new GetActorsByLastNameResponse();
        response.setActor(actorService.getActorsByLastName(request.getActorLastName()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmsWithActorRequest")
    @ResponsePayload
    public GetFilmsWithActorResponse getFilmsWithActor(@RequestPayload GetFilmsWithActorRequest request) {
        GetFilmsWithActorResponse response = new GetFilmsWithActorResponse();

        if (actorService.exists(request.getActorId())){
            response.setFilms(actorService.getFilmsWithActor(request.getActorId()));
        } else {
            response.setError("actor " + Constants.DNE);
        }

        return response;
    }
}
