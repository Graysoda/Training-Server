package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;
import training.service.CustomerServiceImpl;

import java.util.List;

@RestController
@RequestMapping(value = RestConstants.REST_SERVICES_LOCATION, produces = RestConstants.JSON)
public class CustomerController {
    @Autowired @Lazy private CustomerServiceImpl customerService;

    @RequestMapping(value = "/customers/", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/{customerId}/", method = RequestMethod.GET)
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @RequestMapping(value = "/store/{storeId}/customers/", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getCustomersbyStore(@PathVariable long storeId){
        return new ResponseEntity<>(customerService.getCustomersByStore(storeId), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/active/", method = RequestMethod.GET)
    public ResponseEntity<List<Customer>> getActiveCustomers(){
        return new ResponseEntity<>(customerService.getActiveCustomers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customers/inactive/", method = RequestMethod.GET)
    public ResponseEntity<?> getInactiveCustomers(){
        return new ResponseEntity<>(customerService.getInactiveCustomers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/customer/create", method = RequestMethod.PUT)
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest request){
        return customerService.insertCustomer(request);
    }

    @RequestMapping(value = "/customer/update", method = RequestMethod.POST)
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request){
        return customerService.updateCustomer(request);
    }

    @RequestMapping(value = "/customer/{customerId}/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteCustomer(@PathVariable long customerId){
        return customerService.deleteCustomer(customerId);
    }
}
