package training.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.Common;
import training.api.rest.jsonObjects.CustomerJson;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;
import training.service.impl.CustomerServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class CustomerRestController {
    @Autowired @Lazy private CustomerServiceImpl customerService;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/store/{storeId}/customers", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomersbyStore(@PathVariable long storeId){
        return new ResponseEntity<>(customerService.getCustomersByStore(storeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/active", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getActiveCustomers(){
        return new ResponseEntity<>(customerService.getActiveCustomers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/inactive", method = RequestMethod.GET)
    public ResponseEntity<?> getInactiveCustomers(){
        return new ResponseEntity<>(customerService.getInactiveCustomers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@RequestBody CustomerJson customerJson){
        CreateCustomerRequest request = new CreateCustomerRequest();

        if (customerJson.isActive() != null){
            request.setActive(customerJson.isActive());
        } else {
            return ResponseEntity.badRequest().body("Customer isActive cannot be null.");
        }

        if (customerJson.getStoreId() != null && customerJson.getStoreId() > 0){
            request.setStoreId(customerJson.getStoreId());
        } else {
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Customer storeId"));
        }

        if (customerJson.getAddressId() != null && customerJson.getAddressId() > 0){
            request.setAddressId(customerJson.getAddressId());
        } else {
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Customer addressId"));
        }

        if (customerJson.getEmail() != null && Common.isStringSafe(customerJson.getEmail())){
            request.setEmail(customerJson.getEmail());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Customer email"));
        }

        if (customerJson.getFirstName() != null && Common.isStringSafe(customerJson.getFirstName())){
            request.setFirstName(customerJson.getFirstName());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Customer firstName"));
        }

        if (customerJson.getLastName() != null && Common.isStringSafe(customerJson.getLastName())){
            request.setLastName(customerJson.getLastName());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Customer lastName"));
        }

        return customerService.insertCustomer(request);
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateCustomer(@PathVariable long customerId, @RequestBody CustomerJson customerJson){
        UpdateCustomerRequest request = new UpdateCustomerRequest();

        request.setCustomerId(customerId);

        if (customerJson.isActive() != null){
            request.setActive(customerJson.isActive());
        } else {
            return ResponseEntity.badRequest().body("Customer isActive cannot be null.");
        }

        if (customerJson.getStoreId() != null && customerJson.getStoreId() > 0){
            request.setStoreId(customerJson.getStoreId());
        } else {
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Customer storeId"));
        }

        if (customerJson.getAddressId() != null && customerJson.getAddressId() > 0){
            request.setAddressId(customerJson.getAddressId());
        } else {
            return ResponseEntity.badRequest().body(Common.idFailureMessage("Customer addressId"));
        }

        if (customerJson.getEmail() != null && Common.isStringSafe(customerJson.getEmail())){
            request.setEmail(customerJson.getEmail());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Customer email"));
        }

        if (customerJson.getFirstName() != null && Common.isStringSafe(customerJson.getFirstName())){
            request.setFirstName(customerJson.getFirstName());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Customer firstName"));
        }

        if (customerJson.getLastName() != null && Common.isStringSafe(customerJson.getLastName())){
            request.setLastName(customerJson.getLastName());
        } else {
            return ResponseEntity.badRequest().body(Common.stringFailureMessage("Customer lastName"));
        }

        return customerService.updateCustomer(request);
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCustomer(@PathVariable long customerId){
        return customerService.deleteCustomer(customerId);
    }
}
