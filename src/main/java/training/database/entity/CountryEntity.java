package training.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.country")
@Table(name = "country")
public class CountryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long countryId;

	@NotNull
	private String country;

	public CountryEntity() {
	}

	public CountryEntity(Long countryId, @NotNull String country) {
		this.countryId = countryId;
		this.country = country;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
