package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Customer;
import training.service.CustomerServiceImpl;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired @Lazy private CustomerServiceImpl customerService;

    @RequestMapping("/customers/")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @RequestMapping("/customers/{customerId}/")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK);
    }

    @RequestMapping("/store/{storeId}/customers/")
    public ResponseEntity<List<Customer>> getCustomersbyStore(@PathVariable long storeId){
        return new ResponseEntity<>(customerService.getCustomersByStore(storeId), HttpStatus.OK);
    }

    @RequestMapping("/customers/active/")
    public ResponseEntity<List<Customer>> getActiveCustomers(){
        return new ResponseEntity<>(customerService.getActiveCustomers(), HttpStatus.OK);//TODO get inactive customers
    }
}
