package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.ActorEntity;
import soap.database.entity.FilmActorEntity;
import soap.database.entity.FilmEntity;
import soap.generated.Actor;
import soap.generated.Film;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmActorDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT fa from sakila.film_actor fa ";
	@Autowired private LanguageDao languageDao;
	@Autowired private FilmCategoryDao filmCategoryDao;

	List<Actor> getActors(long film_id) {
		System.out.println("film actor dao get actors");
		//ActorDao actorDao = new ActorDao();

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<FilmActorEntity> query = criteriaBuilder.createQuery(FilmActorEntity.class);
		Root<FilmActorEntity> root = query.from(FilmActorEntity.class);

		List<Selection<?>> selections = new ArrayList<>();
		selections.add(root.get("film_id"));
		selections.add(root.get("actor_id"));

		query.multiselect(selections);
		query.where(criteriaBuilder.equal(root.get("film_id"),film_id));

		return getAllActors(this.em.createQuery(query).getResultList());
	}

	private List<Actor> getAllActors(List<FilmActorEntity> resultList) {
		String query = ("SELECT a FROM sakila.actor a WHERE a.actor_id IN (");

		for (FilmActorEntity filmActorEntity : resultList) {
            System.out.println("actor_id = ["+filmActorEntity.getActor_id()+"]");
			if (!query.contains(String.valueOf(filmActorEntity.getActor_id())))
				query += "'"+filmActorEntity.getActor_id()+"', ";
		}

		query = query.substring(0,query.length()-3);
		query += "')";

		System.out.println("query = ["+ query+"]");

		return convertActorEntitiesToGenerated(this.em.createQuery(query,ActorEntity.class).getResultList());
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

	List<Film> getFilms(long actor_id) {

		return null;//filmDao.getAllFilms(this.em.createQuery(baseQuery+"WHERE fa.actor_id = "+actor_id, FilmActorEntity.class).getResultList());
	}

	List<Film> getAllFilms(List<FilmActorEntity> resultList) {
		long[] ids = new long[resultList.size()];

		for (int i = 0; i < resultList.size(); i++) {
			ids[i] = resultList.get(i).getFilm_id();
		}

		List<Film> films = convertListToGenerated(this.em.createQuery(buildBaseQuery(buildIdPredicate(ids))).getResultList());

		return films;
	}

	private Predicate buildIdPredicate(long[] ids) {
		Expression<Long> expression = em.getCriteriaBuilder().createQuery(FilmEntity.class).from(FilmEntity.class).get("film_id");
		return expression.in(ids);
	}

	private CriteriaQuery<FilmEntity> buildBaseQuery(Predicate predicate){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaBuilder.Coalesce coalesce = criteriaBuilder.coalesce();
		CriteriaQuery<FilmEntity> query = criteriaBuilder.createQuery(FilmEntity.class);
		Root<FilmEntity> from = query.from(FilmEntity.class);

		coalesce.value(from.get("original_language_id"));
		coalesce.value(Long.valueOf(-1));

		List<Selection<?>> selections = new ArrayList<>();
		selections.add(from.get("film_id"));
		selections.add(from.get("title"));
		selections.add(from.get("description"));
		selections.add(from.get("release_year"));
		selections.add(from.get("language_id"));
		selections.add(coalesce);
		selections.add(from.get("rental_duration"));
		selections.add(from.get("rental_rate"));
		selections.add(from.get("length"));
		selections.add(from.get("replacement_cost"));
		selections.add(from.get("rating"));
		selections.add(from.get("special_features"));
		selections.add(from.get("last_update"));


		query.multiselect(selections);


		if (predicate != null)
			query.where(predicate);
		return query;
	}

	private List<Film> convertListToGenerated(List<FilmEntity> entities) {
		List<Film> filmList = new ArrayList<>();
		System.out.println("converting list to generated");

		for (FilmEntity entity : entities) {
			System.out.println("Inside loop");
			filmList.add(convertSingleToGenerated(entity));
		}

		return filmList;
	}

	private Film convertSingleToGenerated(FilmEntity entity) {
		System.out.println("Instantiate Film");
		Film film = new Film();

		System.out.println("converting single to generated");

		System.out.println("film_id = ["+entity.getFilm_id()+"]");
		film.setFilmId(BigInteger.valueOf(entity.getFilm_id()));
		System.out.println("title = ["+entity.getTitle()+"]");
		film.setTitle(entity.getTitle());
		System.out.println("description = ["+entity.getDescription()+"]");
		film.setDescription(entity.getDescription());
		System.out.println("release year = ["+entity.getRelease_year()+"]");
		film.setReleaseYear(entity.getRelease_year());
		System.out.println();
		film.setLanguage(languageDao.getLanguage(entity.getLanguage_id()));
		film.setOriginalLanguage(languageDao.getLanguage(entity.getOriginal_language_id()));
		film.setRentalDuration(entity.getRental_duration());
		film.setRentalRate(entity.getRental_rate());
		film.setLength(entity.getLength());
		film.setReplacementCost(entity.getReplacement_cost());
		film.setRating(entity.getRating());
		film.setSpecialFeatures(entity.getSpecial_features());
		film.setLastUpdate(entity.getLast_update());
		film.setCategory(filmCategoryDao.getById(entity.getFilm_id()));
		film.setActors(getActors(entity.getFilm_id()));

		System.out.println("film_id = "+film.getFilmId());

		return film;
	}

}
