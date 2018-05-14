package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;

import java.util.List;

public interface ActorService {
	List<Actor> getAllActors();
	Actor getActorById(long id);
	List<Actor> getActorsByFirstName(String name);
	ResponseEntity<?> insertActor(CreateActorRequest request);
	ResponseEntity<?> updateActor(UpdateActorRequest request);
	ResponseEntity<?> deleteActor(long ActorId);
	List<Summary> getFilmsWithActor(long actorId);
}
