package training.database.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;

import java.util.List;

@Repository
@Transactional
public interface ActorDao {
    List<Actor> getAllActors();

    @Query("select a from Actor a where a.id = ?1")
    Actor getById(long actorId);

    List<Actor> findByFirstName(String actorFirstName);

    List<Actor> getActorsById(List<Long> actorIds);

    List<Summary> getFilms(long actorId);

    ResponseEntity<?> insert(CreateActorRequest request);

    ResponseEntity<?> update(UpdateActorRequest request);

    ResponseEntity<?> delete(long actorId);

    boolean exists(Long actorId);
}
