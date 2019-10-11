package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.City;

@Repository
public interface CityDao extends CrudRepository<City, Integer> {

    boolean existsByName(String cityName);
}
