package training.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import training.database.entity.FilmEntity;
import training.generated.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDaoImpl implements FilmDao{
    protected EntityManager em;
	private LanguageDaoImpl languageDaoImpl;
	private FilmCategoryDaoImpl filmCategoryDaoImpl;
	private FilmActorDaoImpl filmActorDaoImpl;
	private Connection connection;
	private static final String baseFilmQuery = "SELECT f FROM sakila.film f";

    public EntityManager getEm() {
        return em;
    }

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Autowired @Lazy
    public void setFilmActorDaoImpl(FilmActorDaoImpl filmActorDaoImpl) {
        this.filmActorDaoImpl = filmActorDaoImpl;
    }

    @Autowired @Lazy
    public void setFilmCategoryDaoImpl(FilmCategoryDaoImpl filmCategoryDaoImpl) {
        this.filmCategoryDaoImpl = filmCategoryDaoImpl;
    }

    @Autowired @Lazy
    public void setLanguageDaoImpl(LanguageDaoImpl languageDaoImpl) {
        this.languageDaoImpl = languageDaoImpl;
    }

    @Autowired @Lazy
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

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

		StringBuilder where = new StringBuilder(" WHERE f.film_id IN (");

		for (Long filmId : filmIds) {
			if (!where.toString().contains(filmId.toString()))
				where.append("'").append(filmId).append("', ");
		}

		where = new StringBuilder(where.substring(0, where.length() - 2) + ")");

		return convertEntitiesToSummary(this.em.createQuery(baseFilmQuery+where,FilmEntity.class).getResultList());
	}

	public List<Actor> getFilmsActors(long filmId) {
		return filmActorDaoImpl.getActorsFromFilm(filmId);
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

	public ResponseEntity<?> insert(CreateFilmRequest request) {
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
				request.getLanguage()+", " +
				request.getOriginalLanguage()+", "+
				request.getRentalDuration()+", "+
				request.getRentalRate()+", "+
				request.getLength()+", "+
				request.getReplacementCost()+", "+
				request.getRating()+", "+
				request.getSpecialFeatures()+");";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Film was created.");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Film was not created.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	public ResponseEntity<?> update(UpdateFilmRequest request) {
		String sql = "UPDATE film SET ";
		if (request.getTitle() != null && !request.getTitle().isEmpty())
			sql += "title = '"+request.getTitle()+"', ";
		if (request.getDescription() != null && !request.getDescription().isEmpty())
			sql += "description = '"+request.getDescription()+"', ";
		if (request.getLanguage()!=null && !request.getLanguage().isEmpty())
			sql += "language_id = '"+ languageDaoImpl.getId(request.getLanguage())+"', ";
		if (request.getOriginalLanguage()!=null && !request.getOriginalLanguage().isEmpty())
			sql += "original_language = '"+ languageDaoImpl.getId(request.getOriginalLanguage())+"', ";
		if (request.getLength()!=null && request.getLength() > 0)
			sql += "length = '"+request.getLength()+"', ";
		if (request.getRating()!=null && !request.getRating().isEmpty())
			sql += "rating = '"+request.getRating()+"', ";
		if (request.getReleaseYear()!=null && request.getReleaseYear() > 0)
			sql += "release_year = '"+request.getReleaseYear()+"', ";
		if (request.getRentalDuration()!=null && request.getRentalDuration() > 0)
			sql += "rental_duration ='"+request.getRentalDuration()+"', ";
		if (request.getRentalRate()!=null && !request.getRentalRate().isNaN())
			sql += "rental_rate = '"+request.getRentalRate()+"', ";
		if (request.getReplacementCost()!=null && !request.getReplacementCost().isNaN())
			sql += "replacement_cost = '"+request.getReplacementCost()+"', ";
		if (request.getSpecialFeatures()!=null && !request.getSpecialFeatures().isEmpty())
			sql += "special_features = '"+request.getSpecialFeatures()+"', ";

		sql += sql.subSequence(0,sql.length()-2) + " WHERE film_id = '"+request.getFilmId()+"';";

		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Film ["+request.getFilmId()+"] was updated successfully");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Film ["+request.getFilmId()+"] was not updated.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	public ResponseEntity<?> delete(long filmId) {
		String sql = "DELETE FROM film WHERE film_id='"+filmId+"';";
		try {
			connection.createStatement().executeUpdate(sql);
			return ResponseEntity.ok("Film ["+filmId+"] was deleted");
		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Film ["+filmId+"] was not deleted.\n"+e.getSQLState()+"\n"+e.getLocalizedMessage());
		}
	}

	/******************************
			Conversions
	******************************/
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

		//System.out.println("film_id = ["+entity.getFilm_id()+"]");
		film.setFilmId(BigInteger.valueOf(entity.getFilm_id()));

		//System.out.println("title = ["+entity.getTitle()+"]");
		film.setTitle(entity.getTitle());

		//System.out.println("description = ["+entity.getDescription()+"]");
		film.setDescription(entity.getDescription());

		//System.out.println("release year = ["+entity.getRelease_year()+"]");
		film.setReleaseYear(entity.getRelease_year());

		//System.out.println("language id = ["+entity.getLanguage_id()+"]");
		film.setLanguage(languageDaoImpl.getLanguage(entity.getLanguage_id()));

		//System.out.println("original language id is null = ["+(entity.getOriginal_language_id() == null)+"]");
		if (entity.getOriginal_language_id() == null)
			film.setOriginalLanguage("");
		else
			film.setOriginalLanguage(languageDaoImpl.getLanguage(entity.getOriginal_language_id()));

		//System.out.println("rental duration = ["+entity.getRental_duration()+"]");
		film.setRentalDuration(entity.getRental_duration());

		//System.out.println("rental rate = ["+entity.getRental_rate()+"]");
		film.setRentalRate(entity.getRental_rate());

		//System.out.println("length = ["+entity.getLength()+"]");
		film.setLength(entity.getLength());

		//System.out.println("replacement cost = ["+entity.getReplacement_cost()+"]");
		film.setReplacementCost(entity.getReplacement_cost());

		//System.out.println("rating = ["+entity.getRating()+"]");
		film.setRating(entity.getRating());

		//System.out.println("special features = ["+entity.getSpecial_features()+"]");
		film.setSpecialFeatures(entity.getSpecial_features());

		film.setCategory(filmCategoryDaoImpl.getById(entity.getFilm_id()));
		film.setActors(filmActorDaoImpl.getActorsFromFilm(entity.getFilm_id()));

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
		summary.setLength(filmEntity.getLength());
		summary.setRating(filmEntity.getRating());

		return summary;
	}
}
