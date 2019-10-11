package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.persistence.dao.CityDao;

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
}
