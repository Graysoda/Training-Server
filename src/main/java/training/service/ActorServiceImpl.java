package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import training.database.dao.ActorDaoImpl;
import training.generated.*;

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
		return actorDaoImpl.findById(id);
	}

	@Override
	@Transactional
	public List<Actor> getActorsByFirstName(String name) {
		return actorDaoImpl.findByFirstName(name);
	}

	@Override
	@Transactional
	public void insertActor(CreateActorRequest request) {
		actorDaoImpl.insert(request);
	}

	@Override
	@Transactional
	public void updateActor(UpdateActorRequest request) {
		actorDaoImpl.update(request);
	}

	@Override
	@Transactional
	public void deleteActor(DeleteActorRequest request) {
		actorDaoImpl.delete(request);
	}

	@Override
	@Transactional
	public List<Summary> getFilmsWithActor(long actorId) {
		return actorDaoImpl.getFilms(actorId);
	}
}
