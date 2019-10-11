package training.api.rest;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training.api.ValidationHelper;
import training.generated.CreateCustomerRequest;
import training.generated.Customer;
import training.generated.UpdateCustomerRequest;
import training.persistence.dto.CustomerDto;
import training.service.CustomerService;
import training.service.StoreService;

import java.util.List;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CustomerRestController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ValidationHelper validationHelper;

    @GetMapping(value = "/customers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @GetMapping("/store/{storeId}/customers")
    public ResponseEntity<?> getCustomersByStore(@PathVariable int storeId){
        if (!storeService.exists(storeId)){
            return ResponseEntity.badRequest().body("store id invalid.");
        }
        return ResponseEntity.ok(customerService.getCustomersByStore(storeId));
    }

    @GetMapping("/customers/active")
    public ResponseEntity<List<Customer>> getActiveCustomers(){
        return ResponseEntity.ok(customerService.getActiveCustomers());
    }

    @GetMapping("/customers/inactive")
    public ResponseEntity<List<Customer>> getInactiveCustomers(){
        return ResponseEntity.ok(customerService.getInactiveCustomers());
    }

    @PostMapping(value = "/customers", consumes = "application/json")
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerRequest request){
        CustomerDto customer = new CustomerDto(request);
        String error = validationHelper.validateCustomerCreation(customer);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/customer/{customerId}", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@PathVariable int customerId, @RequestBody UpdateCustomerRequest request){
        CustomerDto customer = new CustomerDto(request);
        String error = validationHelper.validateCustomerUpdate(customerId, customer);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(customerService.save(customer)) :
                ResponseEntity.badRequest().body(error);
    }

    @PutMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> updateCustomer(@RequestBody UpdateCustomerRequest request){
        CustomerDto customer = new CustomerDto(request);
        String error = validationHelper.validateCustomerUpdate(customer);
        return StringHelper.isNullOrEmptyString(error) ? ResponseEntity.ok(customerService.save(customer)) :
                ResponseEntity.badRequest().body(error);
    }

    @DeleteMapping("/customer/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable int customerId){
        if (customerId <= 0 || !customerService.exists(customerId)){
            return ResponseEntity.badRequest().body("customer id is invalid");
        }
        customerService.delete(customerId);
        return ResponseEntity.ok("Customer with id [" + customerId + "] was deleted");
    }
}
