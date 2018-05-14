package training.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.Database;
import training.database.entity.ActorEntity;
import training.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@Transactional
public class ActorDao extends Database {
	@PersistenceContext @Lazy
	private EntityManager em;
	@Autowired @Lazy
	private FilmActorDao filmActorDao;
	private static final String baseQuery = "SELECT a FROM sakila.actor a";


    public List<Actor> getAllActors() {
		return convertActorEntitiesToGenerated(this.em.createQuery(baseQuery,ActorEntity.class).getResultList());
	}

	public Actor findById(long id) {
		return this.convertActorEntityToGenerated(this.em.createQuery(baseQuery+" WHERE a.actor_id = '"+id+"'", ActorEntity.class).getSingleResult());
	}

	public List<Actor> findByFirstName(String actorFirstName) {
		return convertActorEntitiesToGenerated(this.em.createQuery(baseQuery+" WHERE a.first_name = '"+actorFirstName.toUpperCase()+"'",ActorEntity.class).getResultList());
	}

	public List<Actor> getActorsById(List<Long> actorIds) {
    	if (actorIds.size()==0)
    		return new ArrayList<>();
    	if (actorIds.size()==1)
    		return Collections.singletonList(findById(actorIds.get(0)));

		StringBuilder where = new StringBuilder(" WHERE a.actor_id IN (");

		for (Long actorId : actorIds) {
			where.append("'").append(actorId).append("', ");
		}

		where = new StringBuilder(where.substring(0, where.length() - 2) + ")");

		return convertActorEntitiesToGenerated(this.em.createQuery(baseQuery+where.toString(),ActorEntity.class).getResultList());
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
			sql += "last_name = '"+request.getNewLastName()+"', ";
		 if (request.getNewFirstName() != null)
			sql += "first_name = '"+request.getNewFirstName()+"', ";

		sql = sql.substring(0, sql.length()-2) + " WHERE actor_id='"+request.getActorId()+"';";
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

	private Actor convertActorEntityToGenerated(ActorEntity entity) {
		Actor actor = new Actor();

		//System.out.println("actor id = ["+entity.getActor_id()+"]");
		actor.setActorId((int)entity.getActor_id());

		//System.out.println("first name = ["+entity.getFirst_name()+"]");
		actor.setFirstName(entity.getFirst_name());

		//System.out.println("last name = ["+entity.getLast_name()+"]");
		actor.setLastName(entity.getLast_name());

		//System.out.println("last_update = ["+entity.getLast_update()+"]");
		actor.setLastUpdate(entity.getLast_update());

		return actor;
	}
}
