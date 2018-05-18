package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.ActorDaoImpl;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
	@Lazy @Autowired
	private ActorDaoImpl actorDaoImpl;

    @Override
	@Transactional
	public List<Actor> getAllActors() {
		return actorDaoImpl.getAllActors();
	}

	@Override
	@Transactional
	public Actor getActorById(long id) {
		return actorDaoImpl.getById(id);
	}

	@Override
	@Transactional
	public List<Actor> getActorsByFirstName(String name) {
		return actorDaoImpl.findByFirstName(name);
	}

	@Override
	@Transactional
	public ResponseEntity<?> insertActor(CreateActorRequest request) {
		return actorDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public ResponseEntity<?> updateActor(UpdateActorRequest request) {
		return actorDaoImpl.update(request);
	}

	@Override
	@Transactional
	public ResponseEntity<?> deleteActor(long actorId) {
		return actorDaoImpl.delete(actorId);
	}

	@Override
	@Transactional
	public List<Summary> getFilmsWithActor(long actorId) {
		return actorDaoImpl.getFilms(actorId);
	}
}
