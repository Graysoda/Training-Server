package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmActorEntity;
import soap.generated.Actor;
import soap.generated.Film;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FilmActorDao {
	@PersistenceContext
	private EntityManager em;
	@Autowired private ActorDao actorDao;
	@Autowired private FilmDao filmDao;
	private String baseQuery = "SELECT fa from film_actor fa ";

	List<Actor> getActors(long film_id) {
//		ActorDao actorDao = new ActorDao();
		System.out.println("film actor dao get actors");
		return actorDao.getAllActors(this.em.createQuery(baseQuery+"WHERE film_actor.film_id = "+film_id,FilmActorEntity.class).getResultList());
	}

	List<Film> getFilms(long actor_id) {
//		FilmDao filmDao = new FilmDao();
		return filmDao.getAllFilms(this.em.createQuery(baseQuery+"WHERE fa.actor_id = "+actor_id, FilmActorEntity.class).getResultList());
	}
}
