package training.database.dao;

import training.generated.*;

import java.util.List;

public interface ActorDao {
    List<Actor> getAllActors();
    Actor findById(long actorId);
    List<Actor> findByFirstName(String actorFirstName);
    List<Actor> getActorsById(List<Long> actorIds);
    List<Summary> getFilms(long actorId);
    void insert(CreateActorRequest request);
    void update(UpdateActorRequest request);
    void delete(DeleteActorRequest request);
}
