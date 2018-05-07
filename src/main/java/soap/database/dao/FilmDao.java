package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;
import soap.database.entity.FilmActorEntity;
import soap.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDao extends Database {
	@PersistenceContext
	private EntityManager em;
	private Connection connection = null;
	private LanguageDao languageDao;
	private SummaryDao summaryDao;
	private FilmActorDao filmActorDao;
	private FilmCategoryDao filmCategoryDao;
	private static final String baseQuery = "SELECT " +
			"film.film_id, " +
			"film.title, " +
			"film.description, " +
			"film.release_year, " +
			"film.language_id, " +
			"COALESCE(film.original_language_id, '-1'), " +
			"film.rental_duration, " +
			"film.rental_rate, " +
			"film.length, " +
			"film.replacement_cost, " +
			"film.rating, " +
			"film.special_features, " +
            "film.last_update " +
			"FROM film";

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

	public void create(CreateFilmRequest request) throws SQLException {
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
			connection = getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (isConnectionOpen(connection))
				connection.close();
		}
	}

	public void update(UpdateFilmRequest request) throws SQLException {
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
			connection = getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (isConnectionOpen(connection))
				connection.close();
		}
	}

	public void delete(long filmId) throws SQLException {
		String sql = "DELETE FROM sakila.film WHERE film_id='"+filmId+"';";
		try {
			connection = getConnection();
			connection.createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if (isConnectionOpen(connection))
				connection.close();
		}
	}


	/****************************************
	 				Queries
	 ****************************************/
	public List<Film> getAll() throws SQLException {
		System.out.println("in get all films");
		connection = getConnection();
		List<Film> films = convertListToGenerated(connection.prepareStatement(baseQuery+";").executeQuery(), connection);
		if (!connection.isClosed())
			connection.close();
		return films;
	}

	public Film getById(long id) throws SQLException {
		connection = getConnection();
		Film film =  convertSingleToGenerated(connection.prepareStatement(baseQuery+"WHERE film.film_id = '" + id+"'").executeQuery(), connection);
		if (isConnectionOpen(connection))
			connection.close();
		return film;
	}

	private boolean isConnectionOpen(Connection connection) throws SQLException {
		return connection!=null && !connection.isClosed();
	}

	public List<Film> getByRating(String rating) throws SQLException {
		connection = getConnection();
		List<Film> films =  convertListToGenerated(connection.prepareStatement(baseQuery+"WHERE film.rating='"+rating+"';").executeQuery(), connection);

		if (isConnectionOpen(connection))
			connection.close();

		return films;
	}

	public List<Film> getByReleaseYear(int year) throws SQLException {
		connection = getConnection();
		List<Film> films =  convertListToGenerated(connection.prepareStatement(baseQuery+"WHERE film.release_year='"+year+"';").executeQuery(), connection);

		if (isConnectionOpen(connection))
			connection.close();

		return films;
	}

	public List<Film> getByTitle(String title) throws SQLException {
		connection = getConnection();
		List<Film> films =  convertListToGenerated(connection.prepareStatement(baseQuery+"WHERE film.title='"+title+"';").executeQuery(), connection);

		if (isConnectionOpen(connection))
			connection.close();

		return films;
	}

	public Summary getSummary(long id){
		return summaryDao.getById(id);
	}

	/******************************
			Conversions
	******************************
	 * @param resultSet*/
	private List<Film> convertListToGenerated(ResultSet resultSet, Connection connection) throws SQLException {
		List<Film> filmList = new ArrayList<>();
		System.out.println("converting list to generated");

		while (resultSet.next()){
			filmList.add(convertSingleToGenerated(resultSet, connection));
		}

		return filmList;
	}

	private Film convertSingleToGenerated(ResultSet resultSet, Connection connection) throws SQLException {
		Film film = new Film();

		System.out.println("converting single to generated");

		film.setFilmId(BigInteger.valueOf(resultSet.getLong("film_id")));
		film.setTitle(resultSet.getString("title"));
		film.setDescription(resultSet.getString("description"));
		film.setReleaseYear(resultSet.getInt("release_year"));
		film.setLanguage(languageDao.getLanguage(resultSet.getLong("language_id")));
		film.setOriginalLanguage(languageDao.getLanguage(resultSet.getLong(5)));
		film.setRentalDuration(resultSet.getInt("rental_duration"));
		film.setRentalRate(resultSet.getFloat("rental_rate"));
		film.setLength(resultSet.getInt("length"));
		film.setReplacementCost(resultSet.getFloat("replacement_cost"));
		film.setRating(resultSet.getString("rating"));
		film.setSpecialFeatures(resultSet.getString("special_features"));
		film.setLastUpdate(resultSet.getString("last_update"));
		film.setCategory(filmCategoryDao.getById(resultSet.getLong("film_id"), connection));
		film.setActors(filmActorDao.getActors(resultSet.getLong("film_id"), connection));

		System.out.println("film_id = "+film.getFilmId());

		return film;
	}

	List<Film> getAllFilms(List<FilmActorEntity> resultList) throws SQLException {
		StringBuilder query = new StringBuilder(baseQuery + "WHERE film.film_id IN (");

		for (FilmActorEntity filmActorEntity : resultList) {
			query.append("'").append(filmActorEntity.getFilm_id()).append("', ");
		}

		query.deleteCharAt(query.length()-1).deleteCharAt(query.length()-1).append(");");

		connection = getConnection();

		List<Film> films = convertListToGenerated(connection.prepareStatement(query.toString()).getResultSet(),connection);

		if (isConnectionOpen(connection))
			connection.close();

		return films;
	}

	public List<Actor> getActors(long filmId) throws SQLException {
		return filmActorDao.getActors(filmId, getConnection());
	}
}
