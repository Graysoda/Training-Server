package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.ActorDao;
import soap.generated.*;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
	@Autowired private ActorDao actorDao;

	@Override
	@Transactional
	public List<Actor> getAllActors() {
		return actorDao.getAllActors();
	}

	@Override
	@Transactional
	public Actor getActorById(long id) {
		return actorDao.findById(id);
	}

	@Override
	@Transactional
	public List<Actor> getActorsByFirstName(String name) {
		return actorDao.findByFirstName(name);
	}

	@Override
	@Transactional
	public void insertActor(CreateActorRequest request) {
		actorDao.insert(request);
	}

	@Override
	@Transactional
	public void updateActor(UpdateActorRequest request) {
		actorDao.update(request);
	}

	@Override
	@Transactional
	public void deleteActor(DeleteActorRequest request) {
		actorDao.delete(request);
	}

	@Override
	@Transactional
	public List<Summary> getFilmsWithActor(long actorId) {
		return actorDao.getFilms(actorId);
	}
}
