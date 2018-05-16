package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;
import training.service.AddressServiceImpl;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class AddressController {
    @Autowired @Lazy private AddressServiceImpl addressService;

    @RequestMapping(value = "/address/", method = RequestMethod.GET)
    public ResponseEntity<?> getAllAddresses(){
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
    }

    @RequestMapping(value = "/address/{addressId}/", method = RequestMethod.GET)
    public ResponseEntity<?> getAddressById(@PathVariable long addressId){
        return new ResponseEntity<>(addressService.getAddressById(addressId), HttpStatus.OK);
    }

    @RequestMapping(value = "/{city}/address/", method = RequestMethod.GET)
    public ResponseEntity<?> getAddressByCity(@PathVariable String city){
        return new ResponseEntity<>(addressService.getAddressByCity(city),HttpStatus.OK);
    }

    @RequestMapping(value = "/address/postal/{postalCode}/", method = RequestMethod.GET)
    public ResponseEntity<?> getAddressByPostalCode(@PathVariable String postalCode){
        return new ResponseEntity<>(addressService.getAddressByPostalCode(postalCode), HttpStatus.OK);
    }

    @RequestMapping(value = "/address/create", method = RequestMethod.PUT)
    public ResponseEntity<?> createAddress(@RequestBody CreateAddressRequest request){
        return addressService.insertAddress(request);
    }

    @RequestMapping(value = "/address/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateAddress(@RequestBody UpdateAddressRequest request){
        return addressService.updateAddress(request);
    }

    @RequestMapping(value = "/address/{addressId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@PathVariable long addressId){
        return addressService.deleteAddress(addressId);
    }
}
