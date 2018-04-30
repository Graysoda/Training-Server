package soap.database.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmEntity;
import soap.generated.CreateFilmRequest;
import soap.generated.Film;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Repository
public class FilmDaoImpl implements FilmDao{
	@PersistenceContext
	private EntityManager entityManager;
	private String baseQuery = "SELECT f FROM sakila.film f ";
	private SessionFactory sessionFactory;

	public FilmDaoImpl(){}

	@Autowired
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	private Film convertFilmEntityToGenerated(FilmEntity entity) {
		soap.generated.Film film = new soap.generated.Film();
		LanguageDao languageDao = new LanguageDao();

		film.setFilmId(BigInteger.valueOf(entity.getFilmId()));
		film.setTitle(entity.getTitle());
		film.setDescription(entity.getDescription());
		film.setReleaseYear(entity.getReleaseYear());
		film.setLanguage(languageDao.getLanguage(entity.getLanguageId()));
//		film.setOriginalLanguage(entity.getOriginal_language());
		film.setRentalDuration(entity.getRentalDuration());
		film.setRentalRate(entity.getRentalRate());
		film.setLength(entity.getLength());
		film.setReplacementCost(entity.getReplacementCost());
		film.setRating(entity.getRating());
		film.setSpecialFeatures(entity.getSpecialFeatures());
		film.setLastUpdate(entity.getLastUpdate());

		return film;
	}

	@Override
	public void addFilm(CreateFilmRequest request) {
		FilmEntity filmEntity = new FilmEntity(request);
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(filmEntity);
	}

	@Override
	public List<Film> listFilms() {
		System.out.println("dao list films");
		Session session = this.sessionFactory.getCurrentSession();
		System.out.println("got session connected="+session.isConnected());
		System.out.println("open="+session.isOpen());
		List<FilmEntity> filmEntityList = session.createQuery(baseQuery,FilmEntity.class).list();
		System.out.println("got ["+filmEntityList.size()+"] films from db");
		return convertFilmEntityToGenerated(filmEntityList);
	}

	private List<soap.generated.Film> convertFilmEntityToGenerated(List<FilmEntity> filmEntityEntityList) {
		List<soap.generated.Film> filmList = new ArrayList<>(filmEntityEntityList.size());

		for (FilmEntity filmEntity : filmEntityEntityList){
			filmList.add(convertFilmEntityToGenerated(filmEntity));
		}

		return filmList;
	}

	@Override
	public soap.generated.Film getFilmById(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return convertFilmEntityToGenerated(session.createQuery(baseQuery+"WHERE f.film_id = " + id,FilmEntity.class).getSingleResult());
	}

	@Override
	public void removeFilm(long id) {

	}

	@Override
	public void updateFilm(Film film) {

	}
}
