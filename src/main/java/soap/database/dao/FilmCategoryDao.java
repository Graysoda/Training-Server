package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.Category;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FilmCategoryDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT fc FROM sakila.film_category fc ";

	public String getById(long categoryId){
		return this.em.createQuery(baseQuery+"WHERE fc.category_id = '"+categoryId+"'",Category.class).getSingleResult().getName();
	}
}
