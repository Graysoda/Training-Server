package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmActorEntity;
import soap.generated.Actor;
import soap.generated.Film;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmActorDao {
	@PersistenceContext
	private EntityManager em;
	private Connection connection;
	private String baseQuery = "SELECT fa from film_actor fa ";

	@Autowired
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	List<Actor> getActors(long film_id) throws SQLException {
		ActorDao actorDao = new ActorDao();
		actorDao.setConnection(DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL")));
		System.out.println("film actor dao get actors");
		return actorDao.getAllActors(parseResultSetToList(connection.prepareStatement(
		        "SELECT film_actor.actor_id, film_actor.film_id, film_actor.last_update " +
                "FROM film_actor " +
                "WHERE film_actor.film_id = '"+film_id+"'")
                .executeQuery()));
	}

	private List<FilmActorEntity> parseResultSetToList(ResultSet resultSet) throws SQLException {
		List<FilmActorEntity> entityList = new ArrayList<>();

		System.out.println("parse result set to list");

		while(resultSet.next()){
			FilmActorEntity entity = new FilmActorEntity();

			entity.setActor_id(resultSet.getLong("actor_id"));
			entity.setFilm_id(resultSet.getLong("film_id"));
			entity.setLast_update(resultSet.getString("last_update"));

			entityList.add(entity);
		}

		System.out.println("size of list = ["+entityList.size()+"]");

		return entityList;
	}

	List<Film> getFilms(long actor_id) throws SQLException {
		FilmDao filmDao = new FilmDao();
		filmDao.setConnection(DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL")));
		return filmDao.getAllFilms(this.em.createQuery(baseQuery+"WHERE fa.actor_id = '"+actor_id+"'", FilmActorEntity.class).getResultList());
	}
}
