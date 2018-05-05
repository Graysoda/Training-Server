package soap.service;

import soap.generated.*;

import java.sql.SQLException;
import java.util.List;

public interface ActorService {
	public List<Actor> getAllActors();
	public Actor getActorById(long id);
	public List<Actor> getActorsByFirstName(String name);
	public void insertActor(CreateActorRequest request);
	void updateActor(UpdateActorRequest request);
	void deleteActor(DeleteActorRequest request);
	List<Film> getFilmsWithActor(long actorId) throws SQLException;
}
