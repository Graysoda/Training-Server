package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.dao.FilmCategoryDao;
import training.database.entity.FilmCategoryEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class FilmCategoryDaoImpl implements FilmCategoryDao {
	protected EntityManager em;
	private CategoryDaoImpl categoryDaoImpl;
	private Connection connection;
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

	@Autowired @Lazy
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public String getById(long film_id) {
        try {
			return categoryDaoImpl.getNameById(this.em.createQuery(baseQuery+" WHERE fc.film_id = '"+film_id+"'", FilmCategoryEntity.class).getSingleResult().getCategory_id());
		} catch (NoResultException e){
        	return null;
		}
	}

	@Override
	public boolean insert(BigInteger filmId, String category) {
		long id = Long.valueOf(filmId.toString());
		String sql = "INSERT INTO film_category (film_id, category_id) VALUES ('"+id+"', '"+categoryDaoImpl.getIdByName(category)+"');";

		try{
			connection.createStatement().executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
