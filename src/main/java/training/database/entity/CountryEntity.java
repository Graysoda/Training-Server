package training.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "sakila.country")
@Table(name = "country")
public class CountryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long country_id;

	@NotNull
	private String country;

	public CountryEntity() {
	}

	public CountryEntity(Long country_id, @NotNull String country) {
		this.country_id = country_id;
		this.country = country;
	}

	public Long getCountry_id() {
		return country_id;
	}

	public void setCountry_id(Long country_id) {
		this.country_id = country_id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
