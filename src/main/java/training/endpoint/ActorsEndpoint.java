package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.ActorServiceImpl;


@Endpoint
public class ActorsEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Lazy @Autowired
	private ActorServiceImpl actorService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createActorRequest")
	@ResponsePayload
	public CreateActorResponse createActor(@RequestPayload CreateActorRequest request) {
		CreateActorResponse response = new CreateActorResponse();
		if (request.getFirstName().isEmpty()){
			response.setError("firstName cannot be empty.");
			return response;
		}
		if (request.getLastName().isEmpty()){
			response.setError("lastName cannot be empty.");
			return response;
		}

		ResponseEntity entity =  actorService.insertActor(request);

		if (entity.getBody() instanceof Actor){
			response.setActor((Actor) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateActorRequest")
	@ResponsePayload
	public UpdateActorResponse updateActor(@RequestPayload UpdateActorRequest request) {
		UpdateActorResponse response = new UpdateActorResponse();
		if (request.getActorId() < 0){
			response.setError("actorId is invalid");
			return response;
		}

		ResponseEntity entity =  actorService.updateActor(request);

		if (entity.getBody() instanceof Actor){
			response.setActor(((Actor) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteActorRequest")
	@ResponsePayload
	public DeleteActorResponse deleteActor(@RequestPayload DeleteActorRequest request) {
		DeleteActorResponse response = new DeleteActorResponse();

		if (request.getActorId() < 0){
			response.setResponse("actorId is invalid");
			return response;
		}

		ResponseEntity entity = actorService.deleteActor(request.getActorId());

		response.setResponse(entity.getBody().toString());
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
		System.out.println("get specific actor");
		GetActorsByIdResponse response = new GetActorsByIdResponse();
		response.setActor(actorService.getActorById(request.getActorId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getActorsByFirstNameRequest")
	@ResponsePayload
	public GetActorsByFirstNameResponse getActorsByFirstName(@RequestPayload GetActorsByFirstNameRequest request){
		GetActorsByFirstNameResponse response = new GetActorsByFirstNameResponse();
		response.setActor(actorService.getActorsByFirstName(request.getActorFirstName()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getFilmsWithActorRequest")
	@ResponsePayload
	public GetFilmsWithActorResponse getFilmsWithActor(@RequestPayload GetFilmsWithActorRequest request) {
		GetFilmsWithActorResponse response = new GetFilmsWithActorResponse();
		response.setFilms(actorService.getFilmsWithActor(request.getActorId()));
		return response;
	}
}
