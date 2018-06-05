package training.database.dao;

import training.generated.City;
import training.generated.Country;

public interface CityDao {
    String getNameById(long id);
    long getIdByName(String city);
	City getById(long city_id);
	long insert(Country country, String name);
}
