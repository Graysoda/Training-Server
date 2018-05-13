package training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import training.generated.Address;
import training.service.AddressServiceImpl;

import java.util.List;

@RestController
public class AddressController {
    @Autowired @Lazy private AddressServiceImpl addressService;

    @RequestMapping(value = "/address/", method = RequestMethod.GET)
    public List<Address> getAllAddresses(){
        return addressService.getAllAddresses();
    }

//    @RequestMapping(value = "/address/", method = RequestMethod.POST)
//    public void createAddress()
}
