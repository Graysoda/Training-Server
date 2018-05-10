package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.ActorEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ActorDao extends Database {
	@PersistenceContext @Lazy private EntityManager em;
//	@Autowired private FilmDao filmDao;
	@Autowired @Lazy private FilmActorDao filmActorDao;

//	@Autowired
//	public void setEm(@Lazy EntityManager em) {
//		this.em = em;
//	}

//	@Autowired
//    public void setFilmDao(@Lazy FilmDao filmDao) {
//        this.filmDao = filmDao;
//    }

    public List<Actor> getAllActors() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ActorEntity> query = criteriaBuilder.createQuery(ActorEntity.class);
		query.multiselect(makeSelection(query.from(ActorEntity.class)));
		return convertActorEntitiesToGenerated(this.em.createQuery(query).setMaxResults(50).getResultList());
	}

	public Actor findById(long id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ActorEntity> query = criteriaBuilder.createQuery(ActorEntity.class);
		Root<ActorEntity> root = query.from(ActorEntity.class);
		query.multiselect(makeSelection(root));
		query.where(criteriaBuilder.equal(root.get("actor_id"),id));
		return this.convertActorEntityToGenerated(this.em.createQuery(query).getSingleResult());
	}

	public List<Actor> findByFirstName(String actorFirstName) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<ActorEntity> query = criteriaBuilder.createQuery(ActorEntity.class);
		Root<ActorEntity> root = query.from(ActorEntity.class);
		query.multiselect(makeSelection(root));
		query.where(criteriaBuilder.equal(root.get("first_name"),actorFirstName.toUpperCase()));
		return convertActorEntitiesToGenerated(this.em.createQuery(query).getResultList());
	}

	public List<Actor> getActorsById(List<Long> actorIds) {
    	if (actorIds.size()==0)
    		return new ArrayList<>();
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<ActorEntity> query = criteriaBuilder.createQuery(ActorEntity.class);
		Root<ActorEntity> from = query.from(ActorEntity.class);

		query.multiselect(makeSelection(from));
		query.where(from.get("actor_id").in(actorIds));

		return convertActorEntitiesToGenerated(this.em.createQuery(query).getResultList());
	}

	public List<Summary> getFilms(long actorId) {
		return filmActorDao.getFilmsWithActor(actorId);
	}

	public void insert(CreateActorRequest request) {
		String sql = "INSERT INTO actor (first_name, last_name) VALUES ('"+request.getFirstName()+"', '"+request.getLastName()+"');";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateActorRequest request) {
		String sql = "UPDATE actor SET ";

		 if (request.getNewLastName() != null)
			sql += "last_name = '"+request.getNewLastName()+"' ";
		 if (request.getNewFirstName() != null)
			sql += "actor.first_name = '"+request.getNewFirstName()+"' ";

		sql = sql.substring(0, sql.length()-3) + " WHERE actor_id='"+request.getActorId()+"';";
		try{
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(DeleteActorRequest request) {
		String sql = "DELETE FROM actor WHERE actor_id='"+request.getActorId()+"';";
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

	private List<Selection<?>> makeSelection(Root<ActorEntity> from) {
		List<Selection<?>> selections = new ArrayList<>();

		selections.add(from.get("actor_id"));
		selections.add(from.get("first_name"));
		selections.add(from.get("last_name"));
		selections.add(from.get("last_update"));

		return selections;
	}
}
