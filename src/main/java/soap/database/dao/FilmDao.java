package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.ActorEntity;
import soap.database.entity.FilmActorEntity;
import soap.database.entity.FilmEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDao extends Database{
	@PersistenceContext
	private EntityManager em;
	@Autowired private LanguageDao languageDao;
	@Autowired private SummaryDao summaryDao;
	@Autowired private FilmCategoryDao filmCategoryDao;

	public void create(CreateFilmRequest request) {
		String sql = "INSERT INTO film " +
				"(title, " +
				"description, " +
				"release_year, " +
				"language_id, " +
				"original_language_id, " +
				"rental_duration, " +
				"rental_rate, " +
				"length, " +
				"replacement_cost, " +
				"rating, " +
				"special_features) " +
				"VALUES " +
				"("+request.getTitle()+", " +
				request.getDescription()+", " +
				request.getReleaseYear()+", " +
				request.getLanguageId()+", " +
				request.getOriginalLanguage()+", "+
				request.getRentalDuration()+", "+
				request.getRentalRate()+", "+
				request.getLength()+", "+
				request.getReplacementCost()+", "+
				request.getRating()+", "+
				request.getSpecialFeatures()+");";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateFilmRequest request) {
		String sql = "UPDATE film SET ";
		if (request.getTitle() != null)
			sql += "title = '"+request.getTitle()+"', ";
		if (request.getDescription() != null)
			sql += "description = '"+request.getDescription()+"', ";
		if (request.getLanguage()!=null)
			sql += "language_id = '"+languageDao.getId(request.getLanguage())+"', ";
		if (request.getOriginalLanguage()!=null)
			sql += "original_language = '"+languageDao.getId(request.getOriginalLanguage())+"', ";
		if (request.getLength()!=null)
			sql += "length = '"+request.getLength()+"', ";
		if (request.getRating()!=null)
			sql += "rating = '"+request.getRating()+"', ";
		if (request.getReleaseYear()!=null)
			sql += "release_year = '"+request.getReleaseYear()+"', ";
		if (request.getRentalDuration()!=null)
			sql += "rental_duration ='"+request.getRentalDuration()+"', ";
		if (request.getRentalRate()!=null)
			sql += "rental_rate = '"+request.getRentalRate()+"', ";
		if (request.getReplacementCost()!=null)
			sql += "replacement_cost = '"+request.getReplacementCost()+"', ";
		if (request.getSpecialFeatures()!=null)
			sql += "special_features = '"+request.getSpecialFeatures()+"', ";

		sql += sql.subSequence(0,sql.length()-3) + " WHERE film_id = '"+request.getFilmId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long filmId) {
		String sql = "DELETE FROM film WHERE film_id='"+filmId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/****************************************
	 				Queries
	 ****************************************/
	public List<Film> getAll() {
		System.out.println("in get all films");
		TypedQuery str = this.em.createQuery(buildBaseQuery(null));
		System.out.println(str.toString());
		return convertListToGenerated(str.getResultList());
	}

	public Film getById(long id) {
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
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

		query.where(criteriaBuilder.equal(from.get("film_id"),id));
		return convertSingleToGenerated(this.em.createQuery(query).getSingleResult());
	}

	public List<Film> getByRating(String rating) {
		List<Film> films =  convertListToGenerated(this.em.createQuery(buildBaseQuery(buildRatingPredicate(rating))).getResultList());

		return films;
	}

	private Predicate buildRatingPredicate(String rating) {
		return em.getCriteriaBuilder().equal(em.getCriteriaBuilder().createQuery(FilmEntity.class).from(FilmEntity.class).get("rating"),rating);
	}

	public List<Film> getByReleaseYear(int year) {
		List<Film> films =  convertListToGenerated(this.em.createQuery(buildBaseQuery(buildReleaseYearPredicate(year))).getResultList());

		return films;
	}

	private Predicate buildReleaseYearPredicate(int year) {
		return em.getCriteriaBuilder().equal(em.getCriteriaBuilder().createQuery(FilmEntity.class).from(FilmEntity.class).get("release_year"),year);
	}

	public List<Film> getByTitle(String title) {
		return convertListToGenerated(this.em.createQuery(buildBaseQuery(buildTitlePredicate(title))).getResultList());
	}

	private Predicate buildTitlePredicate(String title) {
		return em.getCriteriaBuilder().equal(em.getCriteriaBuilder().createQuery(FilmEntity.class).from(FilmEntity.class).get("title"),title);
	}

	public Summary getSummary(long id){
		return summaryDao.getById(id);
	}

	/******************************
			Conversions
	******************************
	 * @param entities*/
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
		System.out.println("language id = ["+entity.getLanguage_id()+"]");
		film.setLanguage(languageDao.getLanguage(entity.getLanguage_id()));
		System.out.println("original language id = ["+entity.getOriginal_language_id()+"]");
		film.setOriginalLanguage(languageDao.getLanguage(entity.getOriginal_language_id()));
		System.out.println("rental duration = ["+entity.getRental_duration()+"]");
		film.setRentalDuration(entity.getRental_duration());
		System.out.println("rental rate = ["+entity.getRental_rate()+"]");
		film.setRentalRate(entity.getRental_rate());
		System.out.println("length = ["+entity.getLength()+"]");
		film.setLength(entity.getLength());
		System.out.println("replacement cost = ["+entity.getReplacement_cost()+"]");
		film.setReplacementCost(entity.getReplacement_cost());
		System.out.println("rating = ["+entity.getRating()+"]");
		film.setRating(entity.getRating());
		System.out.println("special features = ["+entity.getSpecial_features()+"]");
		film.setSpecialFeatures(entity.getSpecial_features());
		System.out.println("last update = ["+entity.getLast_update()+"]");
		film.setLastUpdate(entity.getLast_update());
		film.setCategory(filmCategoryDao.getById(entity.getFilm_id()));
		film.setActors(getActors(entity.getFilm_id()));

		System.out.println("film_id = "+film.getFilmId());

		return film;
	}



	public List<Actor> getActors(long filmId) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<FilmActorEntity> filmActorEntityCriteriaQuery = criteriaBuilder.createQuery(FilmActorEntity.class);
		Root<FilmActorEntity> filmActorEntityRoot = filmActorEntityCriteriaQuery.from(FilmActorEntity.class);

		List<Selection<?>> filmActorSelections = new ArrayList<>();
		filmActorSelections.add(filmActorEntityRoot.get("film_id"));
		filmActorSelections.add(filmActorEntityRoot.get("actor_id"));

		filmActorEntityCriteriaQuery.multiselect(filmActorSelections);
		filmActorEntityCriteriaQuery.where(criteriaBuilder.equal(filmActorEntityRoot.get("film_id"),filmId));

		List<FilmActorEntity> filmActorEntities =  this.em.createQuery(filmActorEntityCriteriaQuery).getResultList();

        if (filmActorEntities.size() == 0){
            return new ArrayList<>();
        }

        CriteriaQuery<ActorEntity> actorEntityCriteriaQuery = criteriaBuilder.createQuery(ActorEntity.class);
        Root<ActorEntity> actorEntityRoot = actorEntityCriteriaQuery.from(ActorEntity.class);

        List<Selection<?>> actorSelections = new ArrayList<>();
        actorSelections.add(actorEntityRoot.get("actor_id"));
        actorSelections.add(actorEntityRoot.get("first_name"));
        actorSelections.add(actorEntityRoot.get("last_name"));
        actorSelections.add(actorEntityRoot.get("last_update"));

        actorEntityCriteriaQuery.multiselect(actorSelections);

        if (filmActorEntities.size() == 1){
		    actorEntityCriteriaQuery.where(criteriaBuilder.equal(actorEntityRoot.get("actor_id"),filmActorEntities.get(0).getActor_id()));

		    return convertActorEntitiesToGenerated(this.em.createQuery(actorEntityCriteriaQuery).getResultList());
        }

        ArrayList<Long> actorIds = new ArrayList<>();

        for (FilmActorEntity filmActorEntity : filmActorEntities) {
            actorIds.add(filmActorEntity.getActor_id());
        }

        Expression<Long> actorIdComparison = actorEntityRoot.get("actor_id");
        Predicate predicate = actorIdComparison.in(actorIds);
        actorEntityCriteriaQuery.where(predicate);

        return convertActorEntitiesToGenerated(this.em.createQuery(actorEntityCriteriaQuery).getResultList());
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
}
