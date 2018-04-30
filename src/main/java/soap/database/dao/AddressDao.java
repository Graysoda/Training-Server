package soap.database.dao;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Repository
@Transactional
public class AddressDao {
	@PersistenceContext private EntityManager em;
	private String baseQuery = "SELECT adr FROM sakila.address adr ";

	public AddressDao(){}
}
