package training.persistence.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    private String address;
    private String address2;
    private String district;
    @ManyToOne
    @JoinColumn(name = "city_id",referencedColumnName = "city_id")
    private City city;
    @Column(name = "postal_code")
    private String postalCode;
    private String phone;

    public Address() {
    }

    public Address(training.generated.Address generated) {
        this.id = generated.getAddressId();
        this.address = generated.getAddress();
        this.address2 = generated.getAddress2();
        this.district = generated.getDistrict();
        this.phone = generated.getPhone();
        this.postalCode = generated.getPostalCode();
        this.city = new City(generated.getCity());
    }

    public training.generated.Address makeGenerated() {
        training.generated.Address generated = new training.generated.Address();

        generated.setAddressId(id);
        generated.setAddress(address);
        generated.setAddress2(address2);
        generated.setDistrict(district);
        generated.setPhone(phone);
        generated.setPostalCode(postalCode);
        generated.setCity(city.makeGenerated());

        return generated;
    }
}
