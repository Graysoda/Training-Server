package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.City;
import training.persistence.dao.CityDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class CityService {
    @Autowired
    private CityDao cityDao;

    public boolean exists(int cityId){
        return cityDao.existsById(cityId);
    }

    public boolean exists(String cityName) {
        return cityDao.existsByName(cityName);
    }

    public List<City> getAllCities() {
        return convert(cityDao.findAll());
    }

    private List<City> convert(Iterable<training.persistence.entity.City> all) {
        List<City> cities = new ArrayList<>();
        all.forEach(c -> cities.add(c.makeGenerated()));
        return cities;
    }
}
