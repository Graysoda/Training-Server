package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.Common;
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

        if (addressJson.getAddress() != null && !addressJson.getAddress().isEmpty() && Common.isStringSafe(addressJson.getAddress())){
            request.setAddress(addressJson.getAddress());
        } else {
            return ResponseEntity.badRequest().body("Address first line cannot be null or empty or contain \',\",\\,;.");
        }
        if (addressJson.getAddress2() != null && Common.isStringSafe(addressJson.getAddress2())){
            request.setAddress2(addressJson.getAddress2());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address address2"));
        }
        if (addressJson.getCity() != null){
            request.setCity(addressJson.getCity());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address city cannot be null."));
        }
        if (addressJson.getDistrict() != null && Common.isStringSafe(addressJson.getDistrict())){
            request.setDistrict(addressJson.getDistrict());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address district"));
        }
        if (addressJson.getPostalCode() != null && Common.isStringSafe(addressJson.getPostalCode())){
            request.setPostalCode(addressJson.getPostalCode());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address postalCode"));
        }
        if (addressJson.getPhone() != null && Common.isStringSafe(addressJson.getPhone())){
            request.setPhone(addressJson.getPhone());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address phone"));
        }

        return addressService.insertAddress(request);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateAddress(@PathVariable long addressId, @RequestBody AddressJson addressJson){
        UpdateAddressRequest request = new UpdateAddressRequest();

        request.setAddressId(addressId);

        if (addressJson.getAddress() != null && !addressJson.getAddress().isEmpty() && Common.isStringSafe(addressJson.getAddress())){
            request.setAddress(addressJson.getAddress());
        } else {
            return ResponseEntity.badRequest().body("Address first line cannot be null or empty or contain \',\",\\,;.");
        }
        if (addressJson.getAddress2() != null && Common.isStringSafe(addressJson.getAddress2())){
            request.setAddress2(addressJson.getAddress2());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address address2"));
        }
        if (addressJson.getCity() != null){
            request.setCity(addressJson.getCity());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address city cannot be null."));
        }
        if (addressJson.getDistrict() != null && Common.isStringSafe(addressJson.getDistrict())){
            request.setDistrict(addressJson.getDistrict());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address district"));
        }
        if (addressJson.getPostalCode() != null && Common.isStringSafe(addressJson.getPostalCode())){
            request.setPostalCode(addressJson.getPostalCode());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address postalCode"));
        }
        if (addressJson.getPhone() != null && Common.isStringSafe(addressJson.getPhone())){
            request.setPhone(addressJson.getPhone());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Address phone"));
        }

        return addressService.updateAddress(request);
    }

    @RequestMapping(value = "/address/{addressId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteAddress(@PathVariable long addressId){
        return addressService.deleteAddress(addressId);
    }
}
