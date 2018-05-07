package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.FilmActorEntity;
import soap.database.entity.FilmEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDao extends Database{
	@PersistenceContext
	private EntityManager em;
	private LanguageDao languageDao;
	private SummaryDao summaryDao;
	private FilmActorDao filmActorDao;
	private FilmCategoryDao filmCategoryDao;

	@Autowired
	public void setLanguageDao(LanguageDao languageDao) {
		this.languageDao = languageDao;
	}

	@Autowired
	public void setSummaryDao(SummaryDao summaryDao) {
		this.summaryDao = summaryDao;
	}

	@Autowired
	public void setFilmCategoryDao(FilmCategoryDao filmCategoryDao) {
		this.filmCategoryDao = filmCategoryDao;
	}

	@Autowired
	public void setFilmActorDao(FilmActorDao filmActorDao) {
		this.filmActorDao = filmActorDao;
	}

	public void create(CreateFilmRequest request) {
		String sql = "INSERT INTO film " +
				"(film.title, " +
				"film.description, " +
				"film.release_year, " +
				"film.language_id, " +
				"film.original_language_id, " +
				"film.rental_duration, " +
				"film.rental_rate, " +
				"film.length, " +
				"film.replacement_cost, " +
				"film.rating, " +
				"film.special_features) " +
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
			sql += "film.title = '"+request.getTitle()+"', ";
		if (request.getDescription() != null)
			sql += "film.description = '"+request.getDescription()+"', ";
		if (request.getLanguage()!=null)
			sql += "film.language_id = '"+languageDao.getId(request.getLanguage())+"', ";
		if (request.getOriginalLanguage()!=null)
			sql += "film.original_language = '"+languageDao.getId(request.getOriginalLanguage())+"', ";
		if (request.getLength()!=null)
			sql += "film.length = '"+request.getLength()+"', ";
		if (request.getRating()!=null)
			sql += "film.rating = '"+request.getRating()+"', ";
		if (request.getReleaseYear()!=null)
			sql += "film.release_year = '"+request.getReleaseYear()+"', ";
		if (request.getRentalDuration()!=null)
			sql += "film.rental_duration ='"+request.getRentalDuration()+"', ";
		if (request.getRentalRate()!=null)
			sql += "film.rental_rate = '"+request.getRentalRate()+"', ";
		if (request.getReplacementCost()!=null)
			sql += "film.replacement_cost = '"+request.getReplacementCost()+"', ";
		if (request.getSpecialFeatures()!=null)
			sql += "film.special_features = '"+request.getSpecialFeatures()+"', ";

		sql += sql.subSequence(0,sql.length()-3) + " WHERE film.film_id = '"+request.getFilmId()+"';";

		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long filmId) {
		String sql = "DELETE FROM film WHERE film.film_id='"+filmId+"';";
		try {
			getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/****************************************
	 				Queries
	 ****************************************/
	public List<Film> getAll() throws SQLException {
		System.out.println("in get all films");
		return convertListToGenerated(this.em.createQuery(buildBaseQuery(null)).getResultList());
	}

	public Film getById(long id) {
		return convertSingleToGenerated(this.em.createQuery(buildBaseQuery(buildIdPredicate(id))).getSingleResult());
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
			filmList.add(convertSingleToGenerated(entity));
		}

		return filmList;
	}

	private Film convertSingleToGenerated(FilmEntity filmEntity) {
		Film film = new Film();

		System.out.println("converting single to generated");

		film.setFilmId(BigInteger.valueOf(filmEntity.getFilm_id()));
		film.setTitle(filmEntity.getTitle());
		film.setDescription(filmEntity.getDescription());
		film.setReleaseYear(filmEntity.getRelease_year());
		film.setLanguage(languageDao.getLanguage(filmEntity.getLanguage_id()));
		film.setOriginalLanguage(languageDao.getLanguage(filmEntity.getOriginal_language_id()));
		film.setRentalDuration(filmEntity.getRental_duration());
		film.setRentalRate(filmEntity.getRental_rate());
		film.setLength(filmEntity.getLength());
		film.setReplacementCost(filmEntity.getReplacement_cost());
		film.setRating(filmEntity.getRating());
		film.setSpecialFeatures(filmEntity.getSpecial_features());
		film.setLastUpdate(filmEntity.getLast_update());
		film.setCategory(filmCategoryDao.getById(filmEntity.getFilm_id()));
		film.setActors(filmActorDao.getActors(filmEntity.getFilm_id()));

		System.out.println("film_id = "+film.getFilmId());

		return film;
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

	public List<Actor> getActors(long filmId) {
		return filmActorDao.getActors(filmId);
	}

	private Predicate buildIdPredicate(long id) {
		return em.getCriteriaBuilder().equal(em.getCriteriaBuilder().createQuery(FilmEntity.class).from(FilmEntity.class).get("film_id"),id);
	}

	private CriteriaQuery<FilmEntity> buildBaseQuery(Predicate predicate){
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaBuilder.Coalesce coalesce = criteriaBuilder.coalesce();
		CriteriaQuery<FilmEntity> query = criteriaBuilder.createQuery(FilmEntity.class);
		Root<FilmEntity> from = query.from(FilmEntity.class);

		coalesce.value(from.get("original_language_id"));
		coalesce.value(Long.valueOf(-1));

		List<Selection> selections = new ArrayList<>();
		selections.add(from.get("film_id"));
		selections.add(from.get("title"));
		selections.add(from.get("description"));
		selections.add(from.get("release_year"));
		selections.add(from.get("langauge_id"));
		selections.add(coalesce);
		selections.add(from.get("rental_duration"));
		selections.add(from.get("rental_rate"));
		selections.add(from.get("length"));
		selections.add(from.get("replacement_cost"));
		selections.add(from.get("rating"));
		selections.add(from.get("special_features"));
		selections.add(from.get("last_update"));

		for (Selection selection : selections){
			query.select(selection);
		}

		if (predicate != null)
				query.where(predicate);

		return query;
	}
}
