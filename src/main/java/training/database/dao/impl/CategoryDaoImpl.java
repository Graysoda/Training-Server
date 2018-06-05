package training.database.dao.impl;

import org.springframework.stereotype.Repository;
import training.database.dao.CategoryDao;
import training.database.entity.CategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Repository
public class CategoryDaoImpl implements CategoryDao {
	protected EntityManager em;
	private final String baseQuery = "SELECT c FROM sakila.category c ";

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.em = entityManager;
	}

	@Override
	public String getNameById(long category_id) {
		try {
			return this.em.createQuery(baseQuery+"WHERE c.category_id = '"+category_id+"'", CategoryEntity.class).getSingleResult().getName();
		} catch (NoResultException e){
			return null;
		}
	}

	@Override
	public long getIdByName(String category) {
		return this.em.createQuery(baseQuery+"WHERE c.name = '"+category+"'", CategoryEntity.class).getSingleResult().getCategory_id();
	}
}
