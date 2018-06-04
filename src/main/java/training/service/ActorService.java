package training.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;

import java.util.List;

@Service
public interface ActorService {
	@Transactional
	List<Actor> getAllActors();
	@Transactional
	Actor getActorById(long id);
	@Transactional
	List<Actor> getActorsByFirstName(String name);
	@Transactional
	ResponseEntity<?> insertActor(CreateActorRequest request);
	@Transactional
	ResponseEntity<?> updateActor(UpdateActorRequest request);
	@Transactional
	ResponseEntity<?> deleteActor(long ActorId);
	@Transactional
	List<Summary> getFilmsWithActor(long actorId);
}
