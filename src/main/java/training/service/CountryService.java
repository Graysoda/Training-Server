package training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import training.generated.Country;
import training.persistence.dao.CountryDao;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryService {
    @Autowired
    private CountryDao countryDao;

    public boolean exists(Integer countryId) {
        return countryDao.existsById(countryId);
    }

    public List<Country> getAllCountries() {
        return convert(countryDao.findAll());
    }

    private List<Country> convert(Iterable<training.persistence.entity.Country> all) {
        List<Country> countries = new ArrayList<>();
        all.forEach( c -> countries.add(c.makeGenerated()));
        return countries;
    }
}
