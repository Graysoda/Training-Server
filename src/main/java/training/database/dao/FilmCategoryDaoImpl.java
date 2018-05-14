package training.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.entity.FilmCategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FilmCategoryDaoImpl implements FilmCategoryDao{
	protected EntityManager em;
	private CategoryDaoImpl categoryDaoImpl;
	private static final String baseQuery = "SELECT fc FROM sakila.film_category fc";

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Autowired @Lazy
	public void setCategoryDaoImpl(CategoryDaoImpl categoryDaoImpl) {
		this.categoryDaoImpl = categoryDaoImpl;
	}

	public String getById(long film_id) {
        return categoryDaoImpl.getNameById(this.em.createQuery(baseQuery+" WHERE fc.film_id = '"+film_id+"'", FilmCategoryEntity.class).getSingleResult().getCategory_id());
	}
}
