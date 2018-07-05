package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.rest.jsonObjects.AddressJson;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;
import training.service.impl.AddressServiceImpl;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class AddressRestController {
    @Autowired @Lazy private AddressServiceImpl addressService;

    @RequestMapping(value = "/address", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAddresses(){
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.GET)
    public ResponseEntity<?> getAddressById(@PathVariable long addressId){
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{city}/address", method = RequestMethod.GET)
    public ResponseEntity<?> getAddressByCity(@PathVariable String city){
        return new ResponseEntity<>(addressService.getAddressByCity(city),HttpStatus.OK);
    }

    @RequestMapping(value = "/address/postal/{postalCode}", method = RequestMethod.GET)
    public ResponseEntity<?> getAddressByPostalCode(@PathVariable String postalCode){
        return new ResponseEntity<>(addressService.getAddressByPostalCode(postalCode), HttpStatus.OK);
    }

    @RequestMapping(value = "/address", method = RequestMethod.POST)
    public ResponseEntity<?> createAddress(@RequestBody AddressJson addressJson){
        CreateAddressRequest request = new CreateAddressRequest();

        if (addressJson.getAddress() != null){
            request.setAddress(addressJson.getAddress());
        } else {
            return ResponseEntity.badRequest().body("Address first line cannot be null.");
        }
        if (addressJson.getAddress2() != null){
            request.setAddress2(addressJson.getAddress2());
        } else {
            return ResponseEntity.badRequest().body("Address second line cannot be null.");
        }
        if (addressJson.getCity() != null){
            request.setCity(addressJson.getCity());
        } else {
            return ResponseEntity.badRequest().body("Address city cannot be null.");
        }
        if (addressJson.getDistrict() != null){
            request.setDistrict(addressJson.getDistrict());
        } else {
            return ResponseEntity.badRequest().body("Address district cannot be null.");
        }
        if (addressJson.getPostalCode() != null){
            request.setPostalCode(addressJson.getPostalCode());
        } else {
            return ResponseEntity.badRequest().body("Address postal code cannot be null.");
        }
        if (addressJson.getPhone() != null){
            request.setPhone(addressJson.getPhone());
        } else {
            return ResponseEntity.badRequest().body("Address phone cannot be null.");
        }

        return addressService.insertAddress(request);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAddress(@PathVariable long addressId, @RequestBody AddressJson addressJson){
        UpdateAddressRequest request = new UpdateAddressRequest();

        request.setAddressId(addressId);

        if (addressJson.getAddress() != null)
            request.setAddress(addressJson.getAddress());
        if (addressJson.getAddress2() != null)
            request.setAddress2(addressJson.getAddress2());
        if (addressJson.getDistrict() != null)
            request.setDistrict(addressJson.getDistrict());
        if (addressJson.getCity() != null)
            request.setCity(addressJson.getCity());
        if (addressJson.getPostalCode() != null)
            request.setPostalCode(addressJson.getPostalCode());
        if (addressJson.getPhone() != null)
            request.setPhone(addressJson.getPhone());

        return addressService.updateAddress(request);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@PathVariable long addressId){
        return addressService.deleteAddress(addressId);
    }
}
