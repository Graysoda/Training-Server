package soap.database.dao;

import org.springframework.stereotype.Repository;
import soap.database.entity.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CategoryDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT c FROM sakila.category c ";

	public String getNameById(long category_id) {
        System.out.println("category dao get name by id");
		return this.em.createQuery(baseQuery+"WHERE c.category_id = "+category_id,CategoryEntity.class).getSingleResult().getName();
	}
}
