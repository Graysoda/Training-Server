package training.database.dao;

import org.springframework.stereotype.Repository;
import training.database.entity.CityEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
class CityDaoImpl implements CityDao{
	protected EntityManager em;
	private final String baseQuery = "SELECT c FROM sakila.city c ";

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.em = entityManager;
	}

	@Override
	public String getNameById(long id){
		return this.em.createQuery(baseQuery+"WHERE c.city_id='"+id+"'",CityEntity.class).getSingleResult().getCity();
	}

	@Override
	public long getIdByName(String city) {
		return this.em.createQuery(baseQuery+"WHERE c.city='"+city+"'",CityEntity.class).getSingleResult().getCity_id();
	}
}
