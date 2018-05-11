package training.database.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import training.database.entity.CityEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Transactional
@Repository
class CityDao {
	@PersistenceContext
	private EntityManager em;
	private String baseQuery = "SELECT c FROM sakila.city c ";

	String getNameById(long id){
		return this.em.createQuery(baseQuery+"WHERE c.city_id='"+id+"'",CityEntity.class).getSingleResult().getCity();
	}

	long getIdByName(String city) {
		return this.em.createQuery(baseQuery+"WHERE c.city='"+city+"'",CityEntity.class).getSingleResult().getCity_id();
	}
}
