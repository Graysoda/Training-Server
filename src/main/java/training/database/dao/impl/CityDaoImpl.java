package training.database.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import training.database.dao.CityDao;
import training.database.entity.CityEntity;
import training.generated.City;
import training.generated.Country;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
class CityDaoImpl implements CityDao {
	protected EntityManager em;
	private CountryDaoImpl countryDao;
	private Connection connection;
	private final String baseQuery = "SELECT c FROM sakila.city c ";

	@Autowired @Lazy
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public EntityManager getEm() {
		return em;
	}

	@PersistenceContext
	public void setEntityManager(EntityManager entityManager){
		this.em = entityManager;
	}

	@Autowired @Lazy
	public void setCountryDao(CountryDaoImpl countryDao) {
		this.countryDao = countryDao;
	}

	@Override
	public String getNameById(long id){
		return this.em.createQuery(baseQuery+"WHERE c.city_id='"+id+"'",CityEntity.class).getSingleResult().getCity();
	}

	@Override
	public long getIdByName(String city) {
		return this.em.createQuery(baseQuery+"WHERE c.city='"+city+"'",CityEntity.class).getSingleResult().getCity_id();
	}

	@Override
	public City getById(long cityId) {
		return convertEntityToGenerated(this.em.createQuery(baseQuery+"WHERE c.city_id = '"+cityId+"'",CityEntity.class).getSingleResult());
	}

	@Override
	public long insert(Country country, String name) {
		String sql = "INSERT INTO city (city, country_id) VALUES ('"+name+", '"+country.getCountryId()+"')";

		try {
			connection.createStatement().executeUpdate(sql);
			return newestCity();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private long newestCity() throws SQLException {
		String sql = "SELECT city_id from city ORDER BY last_update DESC LIMIT 1";

		ResultSet resultSet = connection.createStatement().executeQuery(sql);
		resultSet.next();

		return resultSet.getLong("city_id");
	}

	private City convertEntityToGenerated(CityEntity entity) {
		City city = new City();

		city.setCityId(entity.getCity_id());
		city.setName(entity.getCity());
		city.setCountry(countryDao.getById(entity.getCountry_id()));

		return city;
	}
}
