package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Customer;
import training.service.CustomerServiceImpl;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired @Lazy private CustomerServiceImpl customerService;

    @RequestMapping("/customers")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @RequestMapping("/customer")
    public Customer getCustomerById(@RequestParam(name = "customerId") long customerId){
        return customerService.getCustomerById(customerId);
    }

    @RequestMapping("/customers")
    public List<Customer> getCustomersbyStore(@RequestParam(name = "storeId")long storeId){
        return customerService.getCustomersByStore(storeId);
    }

    @RequestMapping("/activeCustomers")
    public List<Customer> getActiveCustomers(){
        return customerService.getActiveCustomers();//TODO get inactive customers
    }
}
