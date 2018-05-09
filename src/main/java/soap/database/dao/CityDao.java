package soap.database.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import soap.database.entity.CityEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
public class CityDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT c FROM sakila.city c ";

	@Autowired
	public void setEm(@Lazy EntityManager em) {
		this.em = em;
	}

	String getNameById(long id){
		return this.em.createQuery(baseQuery+"WHERE c.city_id='"+id+"'",CityEntity.class).getSingleResult().getCity();
	}

	public long getIdByName(String city) {
		return this.em.createQuery(baseQuery+"WHERE c.city='"+city+"'",CityEntity.class).getSingleResult().getCity_id();
	}
}
