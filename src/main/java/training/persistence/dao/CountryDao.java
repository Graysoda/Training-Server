package training.persistence.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.persistence.entity.Country;

@Repository
public interface CountryDao extends CrudRepository<Country, Integer> {

}
