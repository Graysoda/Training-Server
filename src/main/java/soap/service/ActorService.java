package soap.service;

import soap.generated.*;

import java.sql.SQLException;
import java.util.List;

public interface ActorService {
	public List<Actor> getAllActors();
	public Actor getActorById(long id);
	public List<Actor> getActorsByFirstName(String name);
	public void insertActor(CreateActorRequest request) throws SQLException;
	void updateActor(UpdateActorRequest request) throws SQLException;
	void deleteActor(DeleteActorRequest request) throws SQLException;
	List<Film> getFilmsWithActor(long actorId) throws SQLException;
}
