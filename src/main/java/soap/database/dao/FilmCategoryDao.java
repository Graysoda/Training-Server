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
	private CategoryDao categoryDao;
	private String baseQuery = "SELECT fc FROM sakila.film_category fc ";

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public String getById(long film_id){
		return categoryDao.getById(this.em.createQuery(baseQuery+"WHERE fc.film_id = '"+film_id+"'",FilmCategoryEntity.class).getSingleResult().getCategory_id()).getName();
	}
}
