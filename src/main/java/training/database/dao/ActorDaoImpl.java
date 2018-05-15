package training.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import training.database.entity.ActorEntity;
import training.generated.Actor;
import training.generated.CreateActorRequest;
import training.generated.Summary;
import training.generated.UpdateActorRequest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ActorDaoImpl implements ActorDao{
	protected EntityManager em;
	@Lazy @Autowired
	private FilmActorDaoImpl filmActorDao;
	@Lazy @Autowired
	private Connection connection;
	private static final String baseQuery = "SELECT a FROM sakila.actor a";

	public EntityManager getEm(){
		return em;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.em = entityManager;
	}

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

	public ResponseEntity<?> insert(CreateActorRequest request) {
		String sql = "INSERT INTO actor (first_name, last_name) VALUES ('"+request.getFirstName()+"', '"+request.getLastName()+"');";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.status(HttpStatus.CREATED).body("Actor was created successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Actor was not create.\n"+e.getLocalizedMessage());
		}
	}

	public ResponseEntity<?> update(UpdateActorRequest request) {
		if (request == null || request.getActorId() < 0 || ((request.getNewFirstName() == null || request.getNewFirstName().isEmpty()) && (request.getNewLastName() == null || request.getNewLastName().isEmpty())))
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("a parameter was invalid, please make sure at least one field is filled and that the id is valid");

		String sql = "UPDATE actor SET ";

		 if (request.getNewLastName() != null || !request.getNewLastName().isEmpty())
			sql += "last_name = '"+request.getNewLastName()+"', ";
		 if (request.getNewFirstName() != null || !request.getNewFirstName().isEmpty())
			sql += "first_name = '"+request.getNewFirstName()+"', ";

		sql = sql.substring(0, sql.length()-2) + " WHERE actor_id='"+request.getActorId()+"';";
		try{
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.status(HttpStatus.OK).body("Actor ["+request.getActorId()+"] was updated successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Actor ["+request.getActorId()+"] was not updated.\n"+e.getLocalizedMessage());
		}
	}

	public ResponseEntity<?> delete(long actorId) {
		String sql = "DELETE FROM actor WHERE actor_id='"+actorId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.status(HttpStatus.OK).body("Actor ["+actorId+"] was deleted successfully.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Actor ["+actorId+"] was not deleted.\n"+e.getLocalizedMessage());
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

		return actor;
	}
}
