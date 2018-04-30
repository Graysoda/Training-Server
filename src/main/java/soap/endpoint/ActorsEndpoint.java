package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import soap.service.FilmServiceImpl;


@Endpoint
public class ActorsEndpoint {
	private static final String NAMESPACE_URI = "my-namespace";
	@Autowired
	private FilmServiceImpl filmService;

	@Autowired
	public ActorsEndpoint(FilmServiceImpl filmService) {
		this.filmService = filmService;
	}

//	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getActorsRequest")
//	@ResponsePayload
//	public GetActorsResponse getActors(@RequestPayload GetActorsRequest request) {
//		System.out.println("getAllActors");
//		GetActorsResponse response = new GetActorsResponse();
//		response.setActors(actorDao.getActors());
//		return response;
//	}
//
//	@PayloadRoot(namespace = "my-namespace",localPart = "getActorRequest")
//	@ResponsePayload
//	public GetActorResponse getActor(@RequestPayload GetActorRequest request){
//		System.out.println("get specific actor");
//		GetActorResponse response = new GetActorResponse();
//		response.setActor(actorDao.findById(request.getActorId()));
//		return response;
//	}


}
