package soap.database.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CategoryDao {
	@PersistenceContext
	private EntityManager em;

}
