package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmCategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FilmCategoryDao {
	@PersistenceContext @Lazy
	@Autowired private EntityManager em;
	@Autowired @Lazy private CategoryDao categoryDao;
	private static final String baseQuery = "SELECT fc FROM sakila.film_category fc";

    public String getById(long film_id) {
        return categoryDao.getNameById(this.em.createQuery(baseQuery+" WHERE fc.film_id = '"+film_id+"'", FilmCategoryEntity.class).getSingleResult().getCategory_id());
	}
}
