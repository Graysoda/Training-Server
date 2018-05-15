package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.Address;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;
import training.service.AddressServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class AddressController {
    @Autowired @Lazy private AddressServiceImpl addressService;

    @RequestMapping(value = "/address/", method = RequestMethod.GET)
    public ResponseEntity<List<Address>> getAllAddresses(){
        return new ResponseEntity<>(addressService.getAllAddresses(), HttpStatus.OK);
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
