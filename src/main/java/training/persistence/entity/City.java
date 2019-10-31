package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Integer id;
    @Column(name = "city")
    private String name;
    @ManyToOne
    @JoinColumn(name = "country_id",referencedColumnName = "country_id")
    private Country country;

    public City() {
    }

    public City(training.generated.City city) {
        this.id = city.getCityId();
        this.name = city.getName();
        this.country = new Country(city.getCountry());
    }

    public training.generated.City makeGenerated() {
        training.generated.City city = new training.generated.City();

        city.setCityId(id);
        city.setName(name);
        city.setCountry(country.makeGenerated());

        return city;
    }
}
