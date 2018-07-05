package training.api.rest.jsonObjects;

public class CountryJson {
	private Long countryId;
	private String country;

	public CountryJson() {
	}

	public CountryJson(Long countryId, String country) {
		this.countryId = countryId;
		this.country = country;
	}

	public Long getCountryId() {
		return countryId;
	}

	public String getCountry() {
		return country;
	}
}
