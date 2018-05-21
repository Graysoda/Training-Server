package training.controller.jsonObjects;

public class AddressJson {
    private String address;
    private String address2;
    private String district;
    private String city;
    private String postalCode;
    private String phone;

    public AddressJson() {
    }

    public AddressJson(String address, String address2, String district, String city, String postalCode, String phone) {
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

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }
}
