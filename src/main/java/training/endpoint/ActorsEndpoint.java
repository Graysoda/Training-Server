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
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	@Lazy @Autowired
	private ActorServiceImpl actorService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createActorRequest")
	public ResponseEntity<?> createActor(@RequestPayload CreateActorRequest request) {
		return actorService.insertActor(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateActorRequest")
	public ResponseEntity<?> updateActor(@RequestPayload UpdateActorRequest request) {
		return actorService.updateActor(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteActorRequest")
	public ResponseEntity<?> deleteActor(@RequestPayload DeleteActorRequest request) {
		return actorService.deleteActor(request.getActorId());
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
