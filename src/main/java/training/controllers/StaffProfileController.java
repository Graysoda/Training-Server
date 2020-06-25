package training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import training.generated.City;
import training.generated.Country;
import training.generated.Staff;
import training.generated.Store;
import training.persistence.dto.AddressDto;
import training.persistence.dto.StaffDto;
import training.persistence.dto.StoreDto;
import training.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class StaffProfileController {
    @Autowired
    private StaffService staffService;
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private StoreService storeService;


    @GetMapping("/profile")
    public String getStaffProfile(Model model, HttpServletRequest request){
        Staff staff = staffService.getStaffById(((training.persistence.entity.Staff) request.getSession().getAttribute("user")).getId());
        List<City> cities = cityService.getAllCities();
        List<Country> countries = countryService.getAllCountries();
        List<Store> stores = storeService.getAll();

        model.addAttribute("staff", staff);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("stores", stores);

        return "profile";
    }

    @PostMapping("/update/staff/{id}")
    public String updateStaff(@PathVariable int id, @ModelAttribute StaffDto staffDto, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(((training.persistence.entity.Staff) request.getSession().getAttribute("user")).getId() != id){
            //redirectStrategy.sendRedirect(request, response, "/"); //TODO make an unauthorized page
        }
        Staff staff = staffService.save(staffDto);
        List<City> cities = cityService.getAllCities();
        List<Country> countries = countryService.getAllCountries();
        List<Store> stores = storeService.getAll();

        model.addAttribute("staff", staff);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("stores", stores);

        return "profile";
    }

    @PostMapping("/update/staff/{id}/address")
    public String updateStaffAddress(@PathVariable int id, @ModelAttribute AddressDto addressDto, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(((training.persistence.entity.Staff) request.getSession().getAttribute("user")).getId() != id){
            //redirectStrategy.sendRedirect(request, response, "/"); //TODO make an unauthorized page
        }
        Staff staff = ((training.persistence.entity.Staff) request.getSession().getAttribute("user")).makeGenerated();
        staff.setAddress(addressService.save(addressDto));

        List<City> cities = cityService.getAllCities();
        List<Country> countries = countryService.getAllCountries();
        List<Store> stores = storeService.getAll();

        model.addAttribute("staff", staff);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("stores", stores);

        return "profile";
    }

    @PostMapping("/update/staff/{id}/store")
    public String updateStaffStore(@PathVariable int id, @ModelAttribute StoreDto storeDto, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(((training.persistence.entity.Staff) request.getSession().getAttribute("user")).getId() != id){
            //redirectStrategy.sendRedirect(request, response, "/"); //TODO make an unauthorized page
        }
        Staff staff = ((training.persistence.entity.Staff) request.getSession().getAttribute("user")).makeGenerated();
        staff.setStoreId(storeService.save(storeDto).getStoreId());

        List<City> cities = cityService.getAllCities();
        List<Country> countries = countryService.getAllCountries();
        List<Store> stores = storeService.getAll();

        model.addAttribute("staff", staff);
        model.addAttribute("cities", cities);
        model.addAttribute("countries", countries);
        model.addAttribute("stores", stores);

        return "profile";
    }
}
