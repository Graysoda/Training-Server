package soap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soap.database.dao.ActorDao;
import soap.generated.*;

import java.sql.SQLException;
import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
	@Autowired private ActorDao actorDao;

//	@Autowired
//	public void setActorDao(ActorDao actorDao){
//		this.actorDao = actorDao;
//	}

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
	public void insertActor(CreateActorRequest request) throws SQLException {
		actorDao.insert(request);
	}

	@Override
	@Transactional
	public void updateActor(UpdateActorRequest request) throws SQLException {
		actorDao.update(request);
	}

	@Override
	public void deleteActor(DeleteActorRequest request) throws SQLException {
		actorDao.delete(request);
	}

	@Override
	public List<Film> getFilmsWithActor(long actorId) throws SQLException {
		return actorDao.getFilms(actorId);
	}
}
