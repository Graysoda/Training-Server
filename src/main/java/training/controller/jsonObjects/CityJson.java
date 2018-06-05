package training.controller.jsonObjects;

public class CityJson {
	private Long cityId;
	private CountryJson country;
	private String city;

	public CityJson() {
	}

	public CityJson(Long cityId, CountryJson country, String city) {
		this.cityId = cityId;
		this.country = country;
		this.city = city;
	}

	public Long getCityId() {
		return cityId;
	}

	public CountryJson getCountry() {
		return country;
	}

	public String getCity() {
		return city;
	}
}
