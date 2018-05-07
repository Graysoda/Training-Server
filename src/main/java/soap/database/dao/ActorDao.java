//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.ActorEntity;
import soap.database.entity.FilmActorEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ActorDao {
	@PersistenceContext
	private EntityManager em;
	private FilmActorDao filmActorDao;
	private Connection connection;
	private String baseQuery = "SELECT a FROM sakila.actor a ";

	@Autowired
	public void setFilmActorDao(FilmActorDao filmActorDao) {
		this.filmActorDao = filmActorDao;
	}

	@Autowired
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void insert(CreateActorRequest request){
		String sql = "INSERT INTO sakila.actor (first_name, last_name) VALUES ('"+request.getFirstName()+"', '"+request.getLastName()+"');";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Actor> getAllActors() {
		ArrayList<Actor> actors = new ArrayList<>();
		TypedQuery<ActorEntity> query = this.em.createQuery(this.baseQuery, ActorEntity.class);

		for (ActorEntity actor : query.getResultList()) {
			actors.add(this.convertActorEntityToGenerated(actor));
		}

		return actors;
	}

	public Actor findById(long id) {
		ActorEntity actor = (ActorEntity)this.em.createQuery(this.baseQuery + "WHERE a.actor_id = " + id).getSingleResult();
		return this.convertActorEntityToGenerated(actor);
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
		List<ActorEntity> actorEntityList = this.em.createQuery(baseQuery+"WHERE a.first_name = "+actorFirstName,ActorEntity.class).getResultList();
		return convertActorEntitiesToGenerated(actorEntityList);
	}

	private List<Actor> convertActorEntitiesToGenerated(List<ActorEntity> actorEntityList) {
		List<Actor> actors = new ArrayList<>();

		for (ActorEntity actorEntity : actorEntityList){
			actors.add(convertActorEntityToGenerated(actorEntity));
		}

		return actors;
	}

	public void update(UpdateActorRequest request) {
		String sql = "UPDATE sakila.actor SET ";

		 if (request.getNewLastName() != null)
			sql += "last_name = '"+request.getNewLastName()+"' ";
		 if (request.getNewFirstName() != null)
			sql += "first_name = '"+request.getNewFirstName()+"' ";

		sql = sql.substring(0, sql.length()-3) + " WHERE actor_id='"+request.getActorId()+"';";
		try{
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(DeleteActorRequest request) {
		String sql = "DELETE FROM sakila.actor WHERE actor_id='"+request.getActorId()+"';";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	List<Actor> getAllActors(List<FilmActorEntity> resultList) {
		StringBuilder query = new StringBuilder(baseQuery);
		query.append("WHERE a.actor_id IN (");

		for (FilmActorEntity filmActorEntity : resultList) {
			query.append("'").append(filmActorEntity.getActor_id()).append("', ");
		}

		query.deleteCharAt(query.length()).deleteCharAt(query.length()).append(")");

		return convertActorEntitiesToGenerated(this.em.createQuery(query.toString(),ActorEntity.class).getResultList());
	}

	public List<Film> getFilms(long actorId) throws SQLException {
		return filmActorDao.getFilms(actorId);
	}
}
