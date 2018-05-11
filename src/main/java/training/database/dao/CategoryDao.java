package training.database.dao;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.entity.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CategoryDao {
	@PersistenceContext @Lazy
	private EntityManager em;
	private String baseQuery = "SELECT c FROM sakila.category c ";

	public String getNameById(long category_id) {
		return this.em.createQuery(baseQuery+"WHERE c.category_id = '"+category_id+"'", CategoryEntity.class).getSingleResult().getName();
	}
}
