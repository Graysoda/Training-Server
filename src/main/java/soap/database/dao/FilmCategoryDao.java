package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import soap.database.Database;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class FilmCategoryDao extends Database {
	@PersistenceContext
	private EntityManager em;
	private CategoryDao categoryDao;
	private String baseQuery = "SELECT film_category.film_id, film_category.category_id FROM film_category";

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

    public String getById(long film_id, Connection connection) throws SQLException {
        ResultSet resultSet = connection.prepareStatement(baseQuery+" WHERE film_category.film_id = '"+film_id+"'").executeQuery();
        resultSet.next();
        return categoryDao.getNameById(resultSet.getLong("category_id"));
	}
}
