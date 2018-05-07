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
import java.sql.ResultSet;
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
		String sql = "INSERT INTO actor (actor.first_name, actor.last_name) VALUES ('"+request.getFirstName()+"', '"+request.getLastName()+"');";
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
		String sql = "UPDATE actor SET ";

		 if (request.getNewLastName() != null)
			sql += "actor.last_name = '"+request.getNewLastName()+"' ";
		 if (request.getNewFirstName() != null)
			sql += "actor.first_name = '"+request.getNewFirstName()+"' ";

		sql = sql.substring(0, sql.length()-3) + " WHERE actor.actor_id='"+request.getActorId()+"';";
		try{
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(DeleteActorRequest request) {
		String sql = "DELETE FROM actor WHERE actor.actor_id='"+request.getActorId()+"';";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	List<Actor> getAllActors(List<FilmActorEntity> resultList) throws SQLException {
        System.out.println("actor dao get all actors");
		StringBuilder query = new StringBuilder("SELECT actor.actor_id, actor.first_name, actor.last_name, actor.last_update FROM actor WHERE actor.actor_id IN (");

		for (FilmActorEntity filmActorEntity : resultList) {
			query.append("'").append(filmActorEntity.getActor_id()).append("', ");
		}

		query.deleteCharAt(query.length()-1).deleteCharAt(query.length()-1).append(")");

        System.out.println("query = ["+ query.toString()+"]");
		return parseResultSetToList(connection.prepareStatement(query.toString()).executeQuery());
	}

    private List<Actor> parseResultSetToList(ResultSet resultSet) throws SQLException {
        List<Actor> actors = new ArrayList<>();
        System.out.println("parse result set to list");

        while (resultSet.next()){
            Actor actor = new Actor();

            actor.setActorId(resultSet.getLong("actor_id"));
            actor.setFirstName(resultSet.getString("first_name"));
            actor.setLastName(resultSet.getString("last_name"));
            actor.setLastUpdate(resultSet.getString("last_update"));

            actors.add(actor);
        }

        System.out.println("size of list = ["+actors.size()+"]");

        return actors;
    }

    public List<Film> getFilms(long actorId) throws SQLException {
		return filmActorDao.getFilms(actorId);
	}
}
