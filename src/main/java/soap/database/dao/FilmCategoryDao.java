package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class FilmCategoryDao {
	@PersistenceContext
	private EntityManager em;
	private Connection connection;
	private CategoryDao categoryDao;
	private String baseQuery = "SELECT film_category.film_id, film_category.category_id FROM film_category";

	@Autowired
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Autowired
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getById(long film_id) throws SQLException {
        return categoryDao.getNameById(connection.prepareStatement(baseQuery+" WHERE film_category.film_id = '"+film_id+"'").executeQuery().getLong("category_id"));
	}
}
