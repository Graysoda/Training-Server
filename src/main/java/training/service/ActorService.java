package training.service;

import org.springframework.http.ResponseEntity;
import training.generated.*;

import java.sql.SQLException;
import java.util.List;

public interface ActorService {
	List<Actor> getAllActors();
	Actor getActorById(long id);
	List<Actor> getActorsByFirstName(String name);
	ResponseEntity<?> insertActor(CreateActorRequest request) throws SQLException;
	ResponseEntity<?> updateActor(UpdateActorRequest request) throws SQLException;
	ResponseEntity<?> deleteActor(DeleteActorRequest request) throws SQLException;
	List<Summary> getFilmsWithActor(long actorId) throws SQLException;
}
