package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmActorEntity;
import soap.database.entity.FilmEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDao {
	@PersistenceContext
	private EntityManager em;
	private Connection connection;
	private LanguageDao languageDao;
	private SummaryDao summaryDao;
	private FilmActorDao filmActorDao;
	private FilmCategoryDao filmCategoryDao;
	private String baseQuery = "SELECT f FROM sakila.film f ";

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

	@Autowired
	private void setConnection(Connection connection){
		this.connection = connection;
	}

	public void create(CreateFilmRequest request) {
		String sql = "INSERT INTO sakila.film " +
				"(title, " +
				"description, " +
				"release_year, " +
				"language_id, " +
				"original_language, " +
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
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(UpdateFilmRequest request) {
		String sql = "UPDATE sakila.film SET ";
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
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(long filmId) {
		String sql = "DELETE FROM sakila.film WHERE film_id='"+filmId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	/****************************************
	 				Queries
	 ****************************************/
	public List<Film> getAll() {
		return convertFilmEntityToGenerated(em.createQuery(baseQuery,FilmEntity.class).getResultList());
	}

	public Film getById(long id) {
		return convertFilmEntityToGenerated(em.createQuery(baseQuery+"WHERE f.film_id = '" + id+"'",FilmEntity.class).getSingleResult());
	}

	public List<Film> getByRating(String rating) {
		return convertFilmEntityToGenerated(this.em.createQuery(baseQuery+"WHERE f.rating='"+rating+"'",FilmEntity.class).getResultList());
	}

	public List<Film> getByReleaseYear(int year) {
		return convertFilmEntityToGenerated(this.em.createQuery(baseQuery+"WHERE f.release_year='"+year+"'",FilmEntity.class).getResultList());
	}

	public List<Film> getByTitle(String title) {
		return convertFilmEntityToGenerated(this.em.createQuery(baseQuery+"WHERE f.title='"+title+"'",FilmEntity.class).getResultList());
	}

	public Summary getSummary(long id){
		return summaryDao.getById(id);
	}

	/******************************
			Conversions
	*******************************/
	private List<Film> convertFilmEntityToGenerated(List<FilmEntity> filmEntityEntityList) {
		List<Film> filmList = new ArrayList<>(filmEntityEntityList.size());

		for (FilmEntity filmEntity : filmEntityEntityList){
			filmList.add(convertFilmEntityToGenerated(filmEntity));
		}

		return filmList;
	}

	private Film convertFilmEntityToGenerated(FilmEntity entity) {
		Film film = new Film();

		film.setFilmId(BigInteger.valueOf(entity.getFilm_id()));
		film.setTitle(entity.getTitle());
		film.setDescription(entity.getDescription());
		film.setReleaseYear(entity.getRelease_year());
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
		film.setActors(filmActorDao.getActors(entity.getFilm_id()));

		return film;
	}

	List<Film> getAllFilms(List<FilmActorEntity> resultList) {
		StringBuilder query = new StringBuilder(baseQuery + "WHERE film_id IN (");

		for (FilmActorEntity filmActorEntity : resultList) {
			query.append("'").append(filmActorEntity.getFilm_id()).append("', ");
		}

		query.deleteCharAt(query.length()).deleteCharAt(query.length()).append(")");

		return convertFilmEntityToGenerated(this.em.createQuery(query.toString(),FilmEntity.class).getResultList());
	}

	public List<Actor> getActors(long filmId) {
		return filmActorDao.getActors(filmId);
	}
}
