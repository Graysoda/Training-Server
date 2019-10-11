package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.CreateAddressRequest;
import training.generated.UpdateAddressRequest;
import training.persistence.dto.AddressDto;
import training.service.AddressService;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class AddressRestController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping("/address")
    public ResponseEntity<?> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAllAddresses());
    }

    @GetMapping("/address/{addressId}")
    public ResponseEntity<?> getAddressById(@PathVariable int addressId) {
        return ResponseEntity.ok(addressService.getAddressById(addressId));
    }

    @GetMapping("/{city}/address")
    public ResponseEntity<?> getAddressByCity(@PathVariable String city) {
        return ResponseEntity.ok(addressService.getAddressByCity(city));
    }

    @GetMapping("/address/postal/{postalCode}")
    public ResponseEntity<?> getAddressByPostalCode(@PathVariable String postalCode) {
        return ResponseEntity.ok(addressService.getAddressByPostalCode(postalCode));
    }

    @PostMapping("/address")
    public ResponseEntity<?> createAddress(@RequestBody CreateAddressRequest request) {
        AddressDto address = new AddressDto(request);
        String error = validationHelper.validateAddressCreation(address);

        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.status(HttpStatus.CREATED).body(addressService.save(address)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<?> updateAddress(@PathVariable int addressId, @RequestBody UpdateAddressRequest request) {
        AddressDto address = new AddressDto(request);
        String error = validationHelper.validateAddressUpdate(addressId, address);

        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(addressService.save(address)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping("/address/")
    public ResponseEntity<?> updateAddress(@RequestBody UpdateAddressRequest request) {
        AddressDto address = new AddressDto(request);
        String error = validationHelper.validateAddressUpdate(address);

        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(addressService.save(address)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping("/address/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable int addressId){
        if (addressId <= 0 || !addressService.exists(addressId)){
            return ResponseEntity.badRequest().body("address id " + Constants.DNE);
        }
        addressService.deleteById(addressId);
        return ResponseEntity.ok("Address with id [" + addressId + "] was successfully deleted.");
    }
}
