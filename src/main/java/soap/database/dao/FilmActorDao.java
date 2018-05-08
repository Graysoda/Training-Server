package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.FilmActorEntity;
import soap.generated.Actor;
import soap.generated.Film;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmActorDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT fa from sakila.film_actor fa ";

	List<Actor> getActors(long film_id) {
		System.out.println("film actor dao get actors");
		ActorDao actorDao = new ActorDao();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<FilmActorEntity> query = criteriaBuilder.createQuery(FilmActorEntity.class);
		Root<FilmActorEntity> root = query.from(FilmActorEntity.class);

		List<Selection<?>> selections = new ArrayList<>();
		selections.add(root.get("film_id"));
		selections.add(root.get("actor_id"));

		query.multiselect(selections);
		query.where(criteriaBuilder.equal(root.get("film_id"),film_id));

		return actorDao.getAllActors(this.em.createQuery(query).getResultList());
	}

	List<Film> getFilms(long actor_id) {
		FilmDao filmDao = new FilmDao();
		return filmDao.getAllFilms(this.em.createQuery(baseQuery+"WHERE fa.actor_id = "+actor_id, FilmActorEntity.class).getResultList());
	}
}
