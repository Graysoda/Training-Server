package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id")
    private int id;

    private String country;

    public Country() {
    }

    public Country(training.generated.Country country) {
        this.id = Math.toIntExact(country.getCountryId());
        this.country = country.getName();
    }

    public training.generated.Country makeGenerated() {
        training.generated.Country generated = new training.generated.Country();

        generated.setCountryId(id);
        generated.setName(country);

        return generated;
    }
}
