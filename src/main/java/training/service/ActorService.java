package training.service;

import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.DeleteActorRequest;
import training.generated.UpdateActorRequest;

import java.sql.SQLException;
import java.util.List;

public interface ActorService {
	List<Actor> getAllActors();
	Actor getActorById(long id);
	List<Actor> getActorsByFirstName(String name);
	void insertActor(CreateActorRequest request) throws SQLException;
	void updateActor(UpdateActorRequest request) throws SQLException;
	void deleteActor(DeleteActorRequest request) throws SQLException;
//	List<Summary> getFilmsWithActor(long actorId) throws SQLException;
}
