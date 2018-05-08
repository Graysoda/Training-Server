package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.ActorEntity;
import soap.database.entity.FilmActorEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ActorDao extends Database {
	@PersistenceContext private EntityManager em;
	private String baseQuery = "SELECT a FROM sakila.actor a ";

	public void insert(CreateActorRequest request) {
		String sql = "INSERT INTO actor (actor.first_name, actor.last_name) VALUES ('"+request.getFirstName()+"', '"+request.getLastName()+"');";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Actor> getAllActors() {
		return convertActorEntitiesToGenerated(this.em.createQuery(this.baseQuery, ActorEntity.class).getResultList());
	}

	public Actor findById(long id) {
		return this.convertActorEntityToGenerated(this.em.createQuery(this.baseQuery + "WHERE a.actor_id = " + id, ActorEntity.class).getSingleResult());
	}

	private Actor convertActorEntityToGenerated(ActorEntity actorEntity) {
		Actor actor = new Actor();
		actor.setActorId((int)actorEntity.getActor_id());
		actor.setFirstName(actorEntity.getFirst_name());
		actor.setLastName(actorEntity.getLast_name());
		actor.setLastUpdate(actorEntity.getLast_update());
		return actor;
	}

	public List<Actor> findByFirstName(String actorFirstName) {
		return convertActorEntitiesToGenerated(this.em.createQuery(baseQuery+"WHERE a.first_name = "+actorFirstName,ActorEntity.class).getResultList());
	}

	private List<Actor> convertActorEntitiesToGenerated(List<ActorEntity> actorEntityList) {
		List<Actor> actors = new ArrayList<>();

		for (ActorEntity actorEntity : actorEntityList){
			actors.add(convertActorEntityToGenerated(actorEntity));
		}

		return actors;
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

	List<Actor> getAllActors(List<FilmActorEntity> resultList) {
        System.out.println("actor dao get all actors");
        if (resultList.size()==0)
            return new ArrayList<Actor>();

		StringBuilder query = new StringBuilder("WHERE a.actor_id IN (");

		for (FilmActorEntity filmActorEntity : resultList) {
			query.append("'").append(filmActorEntity.getActor_id()).append("', ");
		}

		query.deleteCharAt(query.length()-1).deleteCharAt(query.length()-1).append(")");

        System.out.println("query = ["+ query.toString()+"]");

		return convertActorEntitiesToGenerated(this.em.createQuery(query.toString(),ActorEntity.class).getResultList());
	}

    public List<Film> getFilms(long actorId) {
        FilmActorDao filmActorDao = new FilmActorDao();
		return filmActorDao.getFilms(actorId);
	}
}
