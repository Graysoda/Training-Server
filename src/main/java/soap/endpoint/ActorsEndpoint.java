package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.database.dao.ActorDao;
import soap.generated.GetActorRequest;
import soap.generated.GetActorResponse;
import soap.generated.GetActorsRequest;
import soap.generated.GetActorsResponse;


@Endpoint
public class ActorsEndpoint {
	private static final String NAMESPACE_URI = "my-namespace";
	@Autowired
	private ActorDao actorDao;

	@Autowired
	public ActorsEndpoint(ActorDao actorDao) {
		this.actorDao = actorDao;
	}

	@PayloadRoot(namespace = "my-namespace", localPart = "getActorsRequest")
	@ResponsePayload
	public GetActorsResponse getActors(@RequestPayload GetActorsRequest request) {
		System.out.println("getAllActors");
		GetActorsResponse response = new GetActorsResponse();
		response.setActors(actorDao.getActors());
		return response;
	}

	@PayloadRoot(namespace = "my-namespace",localPart = "getActorRequest")
	@ResponsePayload
	public GetActorResponse getActor(@RequestPayload GetActorRequest request){
		System.out.println("get specific actor");
		GetActorResponse response = new GetActorResponse();
		response.setActor(actorDao.findById(request.getActorId()));
		return response;
	}


}
