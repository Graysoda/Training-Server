package training.api.rest.jsonObjects;

import training.generated.City;
import training.generated.Country;

public class AddressJson {
    private String address;
    private String address2;
    private String district;
    private CityJson city;
    private String postalCode;
    private String phone;

    public AddressJson() {
    }

    public AddressJson(String address, String address2, String district, CityJson city, String postalCode, String phone) {
        this.address = address;
        this.address2 = address2;
        this.district = district;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getDistrict() {
        return district;
    }

    public City getCity() {
        City city = new City();

        city.setName(this.city.getCity());
        city.setCityId(this.city.getCityId());

        Country country = new Country();

        country.setName(this.city.getCountry().getCountry());
        country.setCountryId(this.city.getCountry().getCountryId());

        city.setCountry(country);

        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }
}
