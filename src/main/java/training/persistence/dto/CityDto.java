package training.persistence.dto;

import lombok.Data;
import training.persistence.entity.City;
import training.persistence.entity.Country;

@Data
public class CityDto {
    private Integer id;
    private String name;
    private Integer country;

    public CityDto(){}

    public City makeEntity(Country country){
        City city = new City();
        city.setId(id);
        city.setName(name);
        city.setCountry(country);
        return city;
    }
}
