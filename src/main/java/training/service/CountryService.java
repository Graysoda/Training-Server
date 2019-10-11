package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.persistence.dao.CountryDao;

@Service
public class CountryService {
    @Autowired
    private CountryDao countryDao;

    public boolean exists(Integer countryId) {
        return countryDao.existsById(countryId);
    }
}
