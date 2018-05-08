package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.entity.FilmCategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FilmCategoryDao {
	@PersistenceContext
	private EntityManager em;
	@Autowired private CategoryDao categoryDao;
	private String baseQuery = "SELECT fc FROM sakila.film_category ";

    public String getById(long film_id) {
        FilmCategoryEntity fce = this.em.createQuery(baseQuery+"WHERE fc.film_id = :film_id",FilmCategoryEntity.class).setParameter("film_id",film_id).getSingleResult();
        return categoryDao.getNameById(fce.getCategory_id());
	}
}
