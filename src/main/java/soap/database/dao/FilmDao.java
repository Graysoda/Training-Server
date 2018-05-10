package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.FilmEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDao extends Database{
	@PersistenceContext @Lazy private EntityManager em;
	@Autowired @Lazy private LanguageDao languageDao;
	@Autowired @Lazy private FilmCategoryDao filmCategoryDao;
	@Autowired @Lazy private FilmActorDao filmActorDao;
	private static final String baseFilmQuery = "SELECT f FROM sakila.film f";

//	@Autowired
//	public void setEm(@Lazy EntityManager em) {
//		this.em = em;
//	}
//
//	@Autowired
//	public void setFilmCategoryDao(@Lazy FilmCategoryDao filmCategoryDao) {
//		this.filmCategoryDao = filmCategoryDao;
//	}
//
//	@Autowired
//	public void setLanguageDao(@Lazy LanguageDao languageDao) {
//		this.languageDao = languageDao;
//	}

	/****************************************
	 				Queries
	 ****************************************/
	public List<Film> getAll() {
		return convertListToGenerated(this.em.createQuery(baseFilmQuery,FilmEntity.class).setMaxResults(100).getResultList());
	}

	public Film getById(long id) {
		return convertSingleToGenerated(this.em.createQuery(baseFilmQuery+" WHERE f.film_id='"+id+"'",FilmEntity.class).getSingleResult());
	}

	public List<Film> getByRating(String rating) {
		return convertListToGenerated(this.em.createQuery(baseFilmQuery+" WHERE f.rating = '"+rating+"'",FilmEntity.class).getResultList());
	}

	public List<Film> getByReleaseYear(int year) {
		return convertListToGenerated(this.em.createQuery(baseFilmQuery+" WHERE f.release_year = '"+year+"'",FilmEntity.class).setMaxResults(100).getResultList());
	}

	public List<Film> getByTitle(String title) {
		return convertListToGenerated(this.em.createQuery(baseFilmQuery+" where f.title = '"+title.toUpperCase()+"'",FilmEntity.class).getResultList());
	}

	public List<Summary> getFilmsById(List<Long> filmIds) {
		if (filmIds.size()==0)
			return new ArrayList<>();
		if (filmIds.size() == 1)
			return convertEntitiesToSummary(this.em.createQuery(baseFilmQuery+" WHERE f.film_id = '"+filmIds.get(0)+"'",FilmEntity.class).getResultList());

		String where = " WHERE f.film_id IN (";

		for (Long filmId : filmIds) {
			if (!where.contains(filmId.toString()))
				where += "'"+filmId+"', ";
		}

		where = where.substring(0,where.length()-2)+")";

		return convertEntitiesToSummary(this.em.createQuery(baseFilmQuery+where,FilmEntity.class).getResultList());
	}

	public List<Actor> getFilmsActors(long filmId) {
		return filmActorDao.getActorsFromFilm(filmId);
	}

	public Summary getSummary(long filmId) {
		CriteriaBuilder criteriaBuilder = this.em.getCriteriaBuilder();
		CriteriaQuery<FilmEntity> query = criteriaBuilder.createQuery(FilmEntity.class);
		Root<FilmEntity> from = query.from(FilmEntity.class);
		List<Selection<?>> selections = new ArrayList<>();


		selections.add(from.get("film_id"));
		selections.add(from.get("title"));
		selections.add(from.get("description"));

		query.multiselect(selections);
		query.where(criteriaBuilder.equal(from.get("film_id"),filmId));

		return convertEntityToSummary(this.em.createQuery(query).getSingleResult());
	}

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

		sql += sql.subSequence(0,sql.length()-2) + " WHERE film_id = '"+request.getFilmId()+"';";

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

	/******************************
			Conversions
	******************************
	 * @param entities*/
	private List<Film> convertListToGenerated(List<FilmEntity> entities) {
		List<Film> filmList = new ArrayList<>();
		//System.out.println("converting list to generated");

		for (FilmEntity entity : entities) {
			//System.out.println("Inside loop");
			filmList.add(convertSingleToGenerated(entity));
		}

		return filmList;
	}

	private Film convertSingleToGenerated(FilmEntity entity) {
		//System.out.println("Instantiate Film");
		Film film = new Film();

		//System.out.println("converting single to generated");

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
		System.out.println("original language id is null = ["+(entity.getOriginal_language_id() == null)+"]");
		if (entity.getOriginal_language_id() == null)
			film.setOriginalLanguage("");
		else
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
		film.setActors(filmActorDao.getActorsFromFilm(entity.getFilm_id()));

		//System.out.println("film_id = "+film.getFilmId());

		return film;
	}

	private List<Summary> convertEntitiesToSummary(List<FilmEntity> resultList) {
		List<Summary> summaries = new ArrayList<>();

		for (FilmEntity filmEntity : resultList) {
			summaries.add(convertEntityToSummary(filmEntity));
		}

		return summaries;
	}

	private Summary convertEntityToSummary(FilmEntity filmEntity) {
		Summary summary = new Summary();

		summary.setFilmId(filmEntity.getFilm_id());
		summary.setTitle(filmEntity.getTitle());
		summary.setDescription(filmEntity.getDescription());

		return summary;
	}

	private List<Selection<?>> makeSelections(CriteriaQuery<FilmEntity> query){
		CriteriaBuilder.Coalesce coalesce = em.getCriteriaBuilder().coalesce();
		Root<FilmEntity> from = query.from(FilmEntity.class);

		coalesce.value(from.get("original_language_id"));
		coalesce.value((long) -1);

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

		return selections;
	}
}
