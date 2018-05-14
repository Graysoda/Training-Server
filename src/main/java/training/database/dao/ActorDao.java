package training.database.dao;

import org.springframework.http.ResponseEntity;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;

import java.util.List;

public interface ActorDao {
    List<Actor> getAllActors();
    Actor findById(long actorId);
    List<Actor> findByFirstName(String actorFirstName);
    List<Actor> getActorsById(List<Long> actorIds);
    List<Summary> getFilms(long actorId);
    ResponseEntity<?> insert(CreateActorRequest request);
    ResponseEntity<?> update(UpdateActorRequest request);
    ResponseEntity<?> delete(long actorId);
}
