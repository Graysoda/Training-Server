package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.FilmActorEntity;
import soap.generated.Actor;
import soap.generated.Film;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FilmActorDao {
	@PersistenceContext
	private EntityManager em;
	String baseQuery = "SELECT fa from sakila.film_actor fa ";

	List<Actor> getActors(long film_id) {
		ActorDao actorDao = new ActorDao();
		return actorDao.getAllActors(this.em.createQuery(baseQuery + "WHERE fa.film_id = '" + film_id + "'", FilmActorEntity.class).getResultList());
	}

	List<Film> getFilms(long actor_id) throws SQLException {
		FilmDao filmDao = new FilmDao();
		return filmDao.getAllFilms(this.em.createQuery(baseQuery+"WHERE fa.actor_id = '"+actor_id+"'", FilmActorEntity.class).getResultList());
	}
}
