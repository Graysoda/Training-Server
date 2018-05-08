package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.ActorEntity;
import soap.database.entity.FilmActorEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ActorDao extends Database {
	@PersistenceContext private EntityManager em;
	private String baseQuery = "SELECT a FROM sakila.actor a ";
	@Autowired private SummaryDao summaryDao;

	public List<Actor> getAllActors() {
		return convertActorEntitiesToGenerated(this.em.createQuery(this.baseQuery, ActorEntity.class).getResultList());
	}

	public Actor findById(long id) {
		return this.convertActorEntityToGenerated(this.em.createQuery(this.baseQuery + "WHERE a.actor_id = " + id, ActorEntity.class).getSingleResult());
	}

	public List<Actor> findByFirstName(String actorFirstName) {
		return convertActorEntitiesToGenerated(this.em.createQuery(baseQuery+"WHERE a.first_name = "+actorFirstName,ActorEntity.class).getResultList());
	}

	public List<Summary> getFilms(long actorId) {
		return getAllFilms(actorId);
	}

	private List<Summary> getAllFilms(long actorId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<FilmActorEntity> query = criteriaBuilder.createQuery(FilmActorEntity.class);
		Root<FilmActorEntity> root = query.from(FilmActorEntity.class);

		query.where(criteriaBuilder.equal(root.get("actor_id"),actorId));

		List<FilmActorEntity> resultList = this.em.createQuery(query).getResultList();

		long[] ids = new long[resultList.size()];

		for (int i = 0; i < resultList.size(); i++) {
			ids[i] = resultList.get(i).getFilm_id();
		}

		List<Summary> films = summaryDao.getByIds(ids);

		return films;
	}

	public void insert(CreateActorRequest request) {
		String sql = "INSERT INTO actor (actor.first_name, actor.last_name) VALUES ('"+request.getFirstName()+"', '"+request.getLastName()+"');";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateActorRequest request) {
		String sql = "UPDATE actor SET ";

		 if (request.getNewLastName() != null)
			sql += "actor.last_name = '"+request.getNewLastName()+"' ";
		 if (request.getNewFirstName() != null)
			sql += "actor.first_name = '"+request.getNewFirstName()+"' ";

		sql = sql.substring(0, sql.length()-3) + " WHERE actor.actor_id='"+request.getActorId()+"';";
		try{
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(DeleteActorRequest request) {
		String sql = "DELETE FROM actor WHERE actor.actor_id='"+request.getActorId()+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<Actor> convertActorEntitiesToGenerated(List<ActorEntity> actorEntityList) {
		List<Actor> actors = new ArrayList<>();

		for (ActorEntity actorEntity : actorEntityList){
			actors.add(convertActorEntityToGenerated(actorEntity));
		}

		return actors;
	}

	private Actor convertActorEntityToGenerated(ActorEntity actorEntity) {
		Actor actor = new Actor();
		actor.setActorId((int)actorEntity.getActor_id());
		actor.setFirstName(actorEntity.getFirst_name());
		actor.setLastName(actorEntity.getLast_name());
		actor.setLastUpdate(actorEntity.getLast_update());
		return actor;
	}
}
