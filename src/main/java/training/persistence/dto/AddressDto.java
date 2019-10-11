package training.persistence.dto;

import lombok.Data;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;
import training.persistence.entity.Address;
import training.persistence.entity.City;

import java.util.Objects;

@Data
public class AddressDto {
    private Integer id;
    private String address;
    private String address2;
    private String district;
    private Integer city;
    private String postalCode;
    private String phone;

    public AddressDto(){}

    public AddressDto(CreateAddressRequest request) {
        address = request.getAddress();
        address2 = request.getAddress2();
        district = request.getDistrict();
        city = request.getCity();
        postalCode = request.getPostalCode();
        phone = request.getPhone();
    }

    public AddressDto(UpdateAddressRequest request) {
        id = request.getAddressId();
        address = request.getAddress();
        address2 = request.getAddress2();
        district = request.getDistrict();
        city = request.getCity();
        postalCode = request.getPostalCode();
        phone = request.getPhone();
    }

    public Address makeEntity(City city){
        Address address = new Address();
        address.setId(id);
        address.setAddress(this.address);
        address.setAddress2(address2);
        address.setCity(city);
        address.setDistrict(district);
        address.setPhone(phone);
        address.setPostalCode(postalCode);
        return address;
    }

    public Address makeEntity(Address entity) {
        if (Objects.isNull(this.address)){
            address = entity.getAddress();
        }
        if (Objects.isNull(address2) && Objects.nonNull(entity.getAddress2())){
            address2 = entity.getAddress2();
        }
        if (Objects.isNull(district)){
            district = entity.getDistrict();
        }
        if (Objects.isNull(phone)){
            phone = entity.getPhone();
        }
        if (Objects.isNull(postalCode)){
            postalCode = entity.getPostalCode();
        }
        return makeEntity(entity.getCity());
    }
}
