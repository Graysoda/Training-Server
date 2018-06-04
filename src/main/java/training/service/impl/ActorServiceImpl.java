package training.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import training.database.dao.impl.ActorDaoImpl;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;
import training.service.ActorService;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
	@Lazy @Autowired
	private ActorDaoImpl actorDaoImpl;

    @Override
	public List<Actor> getAllActors() {
		return actorDaoImpl.getAllActors();
	}

	@Override
	public Actor getActorById(long id) {
		return actorDaoImpl.getById(id);
	}

	@Override
	public List<Actor> getActorsByFirstName(String name) {
		return actorDaoImpl.findByFirstName(name);
	}

	@Override
	public ResponseEntity<?> insertActor(CreateActorRequest request) {
		return actorDaoImpl.insert(request);
	}

	@Override
	public ResponseEntity<?> updateActor(UpdateActorRequest request) {
		return actorDaoImpl.update(request);
	}

	@Override
	public ResponseEntity<?> deleteActor(long actorId) {
		return actorDaoImpl.delete(actorId);
	}

	@Override
	public List<Summary> getFilmsWithActor(long actorId) {
		return actorDaoImpl.getFilms(actorId);
	}
}
