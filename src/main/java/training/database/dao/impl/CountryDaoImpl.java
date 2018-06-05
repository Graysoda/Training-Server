package training.database.dao.impl;

import org.springframework.stereotype.Repository;
import training.database.entity.CountryEntity;
import training.generated.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class CountryDaoImpl {
	protected EntityManager em;
	private final String baseQuery = "SELECT c FROM sakila.country c ";

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEm(EntityManager em) {
		this.em = em;
	}


	public Country getById(long country_id) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE c.country_id = '"+country_id+"'",CountryEntity.class).getSingleResult());
	}

	private Country convertEntityToGenerated(CountryEntity entity) {
		Country country = new Country();

		country.setCountryId(entity.getCountryId());
		country.setName(entity.getCountry());

		return country;
	}
}
