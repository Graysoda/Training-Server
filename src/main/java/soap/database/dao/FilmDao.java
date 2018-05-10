package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.Database;
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
	@PersistenceContext @Lazy private EntityManager em;
	@Autowired @Lazy private LanguageDao languageDao;
	@Autowired @Lazy private FilmCategoryDao filmCategoryDao;
	@Autowired @Lazy private FilmActorDao filmActorDao;
	private static final String baseFilmQuery = "SELECT * FROM film";

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
		//System.out.println("in get all films");
		CriteriaQuery<FilmEntity> query = this.em.getCriteriaBuilder().createQuery(FilmEntity.class);

		query.distinct(true);
		query.multiselect(makeSelections(query));

		TypedQuery<FilmEntity> str = this.em.createQuery(query).setMaxResults(50);
		//System.out.println(str.toString());
		return convertListToGenerated(this.em.createNativeQuery(baseFilmQuery+";",FilmEntity.class).getResultList());
	}

	public Film getById(long id) {
		CriteriaQuery<FilmEntity> query = this.em.getCriteriaBuilder().createQuery(FilmEntity.class);
		Root<FilmEntity> from = query.from(FilmEntity.class);

		//query.distinct(true);
		query.multiselect(makeSelections(query));
		query.where(this.em.getCriteriaBuilder().equal(from.get("film_id"),id));

		return convertSingleToGenerated(this.em.createQuery(baseFilmQuery+" WHERE film_id='"+id+"'",FilmEntity.class).getSingleResult());
	}

	public List<Film> getByRating(String rating) {
		CriteriaQuery<FilmEntity> query = this.em.getCriteriaBuilder().createQuery(FilmEntity.class);
		Root<FilmEntity> from = query.from(FilmEntity.class);
		Expression<String> ratingField = from.get("rating").as(String.class);

		query.distinct(true);
		query.multiselect(makeSelections(query));
		query.where(this.em.getCriteriaBuilder().equal(ratingField.as(String.class),rating));

		return convertListToGenerated(this.em.createQuery(query).setMaxResults(50).getResultList());
	}

	public List<Film> getByReleaseYear(int year) {
		CriteriaQuery<FilmEntity> query = em.getCriteriaBuilder().createQuery(FilmEntity.class);
		Root<FilmEntity> from  = query.from(FilmEntity.class);

		query.distinct(true);
		query.multiselect(makeSelections(query));
		query.where(this.em.getCriteriaBuilder().equal(from.get("release_year"),year));

		return convertListToGenerated(this.em.createQuery(query).setMaxResults(50).getResultList());
	}

	public List<Film> getByTitle(String title) {

		CriteriaQuery<FilmEntity> query = em.getCriteriaBuilder().createQuery(FilmEntity.class);
		Root<FilmEntity> from  = query.from(FilmEntity.class);

		query.distinct(true);
		query.multiselect(makeSelections(query));
		query.where(this.em.getCriteriaBuilder().equal(from.get("title"),title.toUpperCase()));

		return convertListToGenerated(this.em.createNativeQuery("select * from film where title = '"+title.toUpperCase()+"'",FilmEntity.class).setMaxResults(50).getResultList());//this.em.createQuery(query).setMaxResults(50).getResultList());
	}

	public List<Summary> getFilmsById(List<Long> filmIds) {
		CriteriaQuery<FilmEntity> query = em.getCriteriaBuilder().createQuery(FilmEntity.class);
		Root<FilmEntity> from  = query.from(FilmEntity.class);

		query.distinct(true);
		query.multiselect(makeSelections(query));
		query.where(this.em.getCriteriaBuilder().in(from.get("film_id").in(filmIds)));

		return convertEntitiesToSummary(this.em.createQuery(query).setMaxResults(50).getResultList());
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
