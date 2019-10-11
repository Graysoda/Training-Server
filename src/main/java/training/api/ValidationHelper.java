package training.api;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import training.Constants;
import training.persistence.dto.*;
import training.service.*;

import java.util.Objects;

@Component
public class ValidationHelper {
    @Autowired
    private StoreService storeService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CityService cityService;
    @Autowired
    private LanguageService languageService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private FilmService filmService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RentalService rentalService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private InventoryService inventoryService;

    public String validateAddressCreation(AddressDto address){
        if (StringHelper.isNullOrEmptyString(address.getAddress())) {
            return "address " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(address.getDistrict())) {
            return "district " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(address.getPhone())) {
            return "phone " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(address.getPostalCode())) {
            return "postal code " + Constants.STRING_ERROR_MSG;
        }
        if (Objects.isNull(address.getCity()) || address.getCity() <= 0){
            return "city id " + Constants.ID_ERROR_MSG;
        }
        if (!cityService.exists(address.getCity())) {
            return "city " + Constants.DNE;
        }
        return "";
    }

    public String validateAddressUpdate(int addressId, AddressDto address){
        if (Objects.isNull(address.getId()) || address.getId() <= 0){
            return "addressId " + Constants.ID_ERROR_MSG;
        }
        if (addressId != address.getId()){
            return "addressId provided in request body and url must be the same";
        }
        if (!addressService.exists(address.getId())){
            return "address " + Constants.DNECU;
        }
        if (Objects.nonNull(address.getCity()) && address.getCity() <= 0){
            return "city id " + Constants.ID_ERROR_MSG;
        }
        if (!cityService.exists(address.getCity())) {
            return "city " + Constants.DNE;
        }
        return "";
    }

    public String validateAddressUpdate(AddressDto address){
        if (Objects.isNull(address.getId()) || address.getId() <= 0){
            return "addressId " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(address.getId())){
            return "address " + Constants.DNECU;
        }
        if (Objects.nonNull(address.getCity()) && address.getCity() <= 0){
            return "city id " + Constants.ID_ERROR_MSG;
        }
        if (!cityService.exists(address.getCity())) {
            return "city " + Constants.DNE;
        }
        return "";
    }

    public String validateActorCreation(ActorDto actor){
        if (StringHelper.isNullOrEmptyString(actor.getFirstName())){
            return "first name " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(actor.getLastName())){
            return "last name " + Constants.STRING_ERROR_MSG;
        }
        return "";
    }

    public String validateActorUpdate(ActorDto actor){
        if(Objects.isNull(actor.getId()) || actor.getId() <= 0){
            return "actor id " + Constants.ID_ERROR_MSG;
        }
        if (!actorService.exists(actor.getId())){
            return "actor " + Constants.DNECU;
        }
        return "";
    }

    public String validateActorUpdate(int actorId, ActorDto actor){
        if(Objects.isNull(actor.getId()) || actor.getId() <= 0){
            return "actor id " + Constants.ID_ERROR_MSG;
        }
        if (actorId != actor.getId()){
            return "actor id provided in request body and url must be the same";
        }
        if (!actorService.exists(actor.getId())){
            return "actor " + Constants.DNECU;
        }
        return "";
    }

    public String validateCustomerCreation(CustomerDto customer){
        if (StringHelper.isNullOrEmptyString(customer.getFirstName())){
            return "first name " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(customer.getLastName())){
            return "last name " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(customer.getEmail())){
            return "email " + Constants.STRING_ERROR_MSG;
        }
        if (Objects.isNull(customer.getStoreId()) || customer.getStoreId() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(customer.getStoreId())){
            return "store " + Constants.DNE;
        }
        if (Objects.isNull(customer.getAddressId()) || customer.getAddressId() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(customer.getAddressId())){
            return "address " + Constants.DNE;
        }
        if (Objects.isNull(customer.getActive())){
            return "active cannot be null.";
        }
        return "";
    }

    public String validateCustomerUpdate(int customerId, CustomerDto customer){
        if (Objects.isNull(customer.getId()) || customer.getId() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (customerId != customer.getId()){
            return "customer id provided in request body and url must be the same";
        }
        if (!customerService.exists(customer.getId())){
            return "customer " + Constants.DNECU;
        }
        if (Objects.nonNull(customer.getStoreId()) && customer.getStoreId() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(customer.getStoreId())){
            return "store " + Constants.DNE;
        }
        if (Objects.nonNull(customer.getAddressId()) && customer.getAddressId() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(customer.getAddressId())){
            return "address " + Constants.DNE;
        }
        return "";
    }

    public String validateCustomerUpdate(CustomerDto customer){
        if (Objects.isNull(customer.getId()) || customer.getId() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (!customerService.exists(customer.getId())){
            return "customer " + Constants.DNECU;
        }
        if (Objects.nonNull(customer.getStoreId()) && customer.getStoreId() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(customer.getStoreId())){
            return "store " + Constants.DNE;
        }
        if (Objects.nonNull(customer.getAddressId()) && customer.getAddressId() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(customer.getAddressId())){
            return "address " + Constants.DNE;
        }
        return "";
    }

    public String validateFilmCreation(FilmDto film) {
        if (StringHelper.isNullOrEmptyString(film.getTitle())){
            return "title " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(film.getDescription())){
            return "description " + Constants.STRING_ERROR_MSG;
        }
        if (Objects.isNull(film.getReleaseYear()) || film.getReleaseYear().toString().length()!=4){
            return "release is invalid.";
        }
        if (!languageService.exists(film.getLanguage())){
            return "language " + Constants.DNE;
        }
        if (Objects.nonNull(film.getOriginalLanguage()) && !languageService.exists(film.getOriginalLanguage())){
            return "original language " + Constants.DNE;
        }
        if (!categoryService.exists(film.getCategory())){
            return "category " + Constants.DNE;
        }
        if (Objects.nonNull(film.getActors()) && film.getActors().size() != 0){
            for (int i = 0; i < film.getActors().size()-1; i++) {
                if (!actorService.exists(film.getActors().get(i))){
                    return "actor [" + i + "] " + Constants.STRING_ERROR_MSG;
                }
            }
        } else {
            return "actors " + Constants.STRING_ERROR_MSG;
        }
        if (!Constants.RATINGS.contains(film.getRating())){
            return "rating is invalid.";
        }
        if (Objects.isNull(film.getRentalDuration()) || film.getRentalDuration() <= 0){
            return "rental duration " + Constants.ID_ERROR_MSG;
        }
        if (Objects.isNull(film.getRentalRate()) || film.getRentalRate() <= 0){
            return "rental rate " + Constants.ID_ERROR_MSG;
        }
        if (Objects.isNull(film.getReplacementCost()) || film.getReplacementCost() <= 0){
            return "replacement cost " + Constants.ID_ERROR_MSG;
        }
        return "";
    }

    public String validateFilmUpdate(FilmDto film) {
        if (Objects.isNull(film.getId()) || film.getId() <= 0){
            return "film id " + Constants.ID_ERROR_MSG;
        }
        if (!filmService.exists(film.getId())){
            return "film " + Constants.DNECU;
        }
        if (Objects.nonNull(film.getReleaseYear()) && film.getReleaseYear().toString().length()!=4){
            return "release is invalid.";
        }
        if (!languageService.exists(film.getLanguage())){
            return "language " + Constants.DNE;
        }
        if (Objects.nonNull(film.getOriginalLanguage()) && !languageService.exists(film.getOriginalLanguage())){
            return "original language " + Constants.DNE;
        }
        if (StringHelper.isNullOrEmptyString(film.getCategory()) && !categoryService.exists(film.getCategory())){
            return "category " + Constants.DNE;
        }
        if (Objects.nonNull(film.getActors()) && film.getActors().size() != 0){
            for (int i = 0; i < film.getActors().size()-1; i++) {
                if (!actorService.exists(film.getActors().get(i))){
                    return "actor [" + i + "] " + Constants.DNE;
                }
            }
        }
        if (!Constants.RATINGS.contains(film.getRating())){
            return "rating is invalid.";
        }
        if (Objects.nonNull(film.getRentalDuration()) && film.getRentalDuration() <= 0){
            return "rental duration " + Constants.ID_ERROR_MSG;
        }
        if (Objects.nonNull(film.getRentalRate()) && film.getRentalRate() <= 0){
            return "rental rate " + Constants.ID_ERROR_MSG;
        }
        if (Objects.nonNull(film.getReplacementCost()) && film.getReplacementCost() <= 0){
            return "replacement cost " + Constants.ID_ERROR_MSG;
        }
        return "";
    }

    public String validateFilmUpdate(int filmId, FilmDto film) {
        if (Objects.isNull(film.getId()) || film.getId() <= 0){
            return "film id " + Constants.ID_ERROR_MSG;
        }
        if (filmId != film.getId()){
            return "film id provided in the request body and url must be the same.";
        }
        if (!filmService.exists(film.getId())){
            return "film " + Constants.DNECU;
        }
        return "";
    }

    public String validateInventoryCreation(InventoryDto inventory) {
        if (Objects.isNull(inventory.getFilm()) || inventory.getFilm() <= 0){
            return "film id " + Constants.ID_ERROR_MSG;
        }
        if (!filmService.exists(inventory.getFilm())){
            return "film " + Constants.DNE;
        }
        if (Objects.isNull(inventory.getStore()) || inventory.getStore() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(inventory.getStore())){
            return "store " + Constants.DNE;
        }
        return "";
    }

    public String validateInventoryUpdate(int inventoryId, InventoryDto inventory) {
        if (Objects.isNull(inventory.getId()) || inventory.getId() <= 0){
            return "inventory id " + Constants.ID_ERROR_MSG;
        }
        if(inventoryId != inventory.getId()){
            return "inventory id in the request body and url must be the same.";
        }
        if (!inventoryService.exists(inventoryId)){
            return "inventory " + Constants.DNECU;
        }
        if (Objects.nonNull(inventory.getFilm()) && inventory.getFilm() <= 0){
            return "film id " + Constants.ID_ERROR_MSG;
        }
        if (!filmService.exists(inventory.getFilm())){
            return "film " + Constants.DNE;
        }
        if (Objects.nonNull(inventory.getStore()) && inventory.getStore() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(inventory.getStore())){
            return "store " + Constants.DNE;
        }
        return "";
    }

    public String validateInventoryUpdate(InventoryDto inventory) {
        if (Objects.isNull(inventory.getId()) || inventory.getId() <= 0){
            return "inventory id " + Constants.ID_ERROR_MSG;
        }
        if (!inventoryService.exists(inventory.getId())){
            return "inventory " + Constants.DNECU;
        }
        if (Objects.nonNull(inventory.getFilm()) && inventory.getFilm() <= 0){
            return "film id " + Constants.ID_ERROR_MSG;
        }
        if (!filmService.exists(inventory.getFilm())){
            return "film " + Constants.DNE;
        }
        if (Objects.nonNull(inventory.getStore()) && inventory.getStore() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(inventory.getStore())){
            return "store " + Constants.DNE;
        }
        return "";
    }

    public String validatePaymentCreation(PaymentDto payment) {
        if (Objects.isNull(payment.getAmount()) || payment.getAmount() <= 0){
            return "payment amount " + Constants.ID_ERROR_MSG;
        }
        if (Objects.isNull(payment.getCustomer()) || payment.getCustomer() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (!customerService.exists(payment.getCustomer())){
            return "customer " + Constants.DNE;
        }
        if (Objects.isNull(payment.getRental()) || payment.getRental() <= 0){
            return "rental id " + Constants.ID_ERROR_MSG;
        }
        if (!rentalService.exists(payment.getRental())){
            return "rental " + Constants.DNE;
        }
        if (Objects.isNull(payment.getStaff()) || payment.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(payment.getStaff())){
            return "staff " + Constants.DNE;
        }
        if (StringHelper.isNullOrEmptyString(payment.getPaymentDate()) || Objects.isNull(Constants.formatString(payment.getPaymentDate()))){
            return "payment date is invalid.";
        }
        return "";
    }

    public String validatePaymentUpdate(int paymentId, PaymentDto payment) {
        if (Objects.isNull(payment.getId()) || payment.getId() <= 0){
            return "payment id " + Constants.ID_ERROR_MSG;
        }
        if (paymentId != payment.getId()){
            return "payment id in the request and url must be the same.";
        }
        if (!paymentService.exists(paymentId)){
            return "payment " + Constants.DNECU;
        }
        if (Objects.nonNull(payment.getAmount()) && payment.getAmount() <= 0){
            return "payment amount " + Constants.ID_ERROR_MSG;
        }
        if (Objects.nonNull(payment.getCustomer()) && payment.getCustomer() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (!customerService.exists(payment.getCustomer())){
            return "customer " + Constants.DNE;
        }
        if (Objects.nonNull(payment.getRental()) && payment.getRental() <= 0){
            return "rental id " + Constants.ID_ERROR_MSG;
        }
        if (!rentalService.exists(payment.getRental())){
            return "rental " + Constants.DNE;
        }
        if (Objects.nonNull(payment.getStaff()) && payment.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(payment.getStaff())){
            return "staff " + Constants.DNE;
        }
        if (!StringHelper.isNullOrEmptyString(payment.getPaymentDate()) && Objects.isNull(Constants.formatString(payment.getPaymentDate()))){
            return "payment date is invalid.";
        }
        return "";
    }

    public String validatePaymentUpdate(PaymentDto payment) {
        if (Objects.isNull(payment.getId()) || payment.getId() <= 0){
            return "payment id " + Constants.ID_ERROR_MSG;
        }
        if (!paymentService.exists(payment.getId())){
            return "payment " + Constants.DNECU;
        }
        if (Objects.nonNull(payment.getAmount()) && payment.getAmount() <= 0){
            return "payment amount " + Constants.ID_ERROR_MSG;
        }
        if (Objects.nonNull(payment.getCustomer()) && payment.getCustomer() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (!customerService.exists(payment.getCustomer())){
            return "customer " + Constants.DNE;
        }
        if (Objects.nonNull(payment.getRental()) && payment.getRental() <= 0){
            return "rental id " + Constants.ID_ERROR_MSG;
        }
        if (!rentalService.exists(payment.getRental())){
            return "rental " + Constants.DNE;
        }
        if (Objects.nonNull(payment.getStaff()) && payment.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(payment.getStaff())){
            return "staff " + Constants.DNE;
        }
        if (!StringHelper.isNullOrEmptyString(payment.getPaymentDate()) && Objects.isNull(Constants.formatString(payment.getPaymentDate()))){
            return "payment date is invalid.";
        }
        return "";
    }

    public String validateRentalCreation(RentalDto rental) {
        if (Objects.isNull(rental.getCustomer()) || rental.getCustomer() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (!customerService.exists(rental.getCustomer())){
            return "customer " + Constants.DNE;
        }
        if (Objects.isNull(rental.getInventory()) || rental.getInventory() <= 0){
            return "inventory id " + Constants.ID_ERROR_MSG;
        }
        if (!inventoryService.exists(rental.getInventory())){
            return "inventory " + Constants.DNE;
        }
        if (Objects.isNull(rental.getStaff()) || rental.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(rental.getStaff())){
            return "staff " + Constants.DNE;
        }
        if (Objects.isNull(rental.getRentalDate()) || Objects.isNull(Constants.formatString(rental.getRentalDate()))){
            return "rental date invalid, format should be [" + Constants.DATE_FORMAT + "]";
        }
        if (Objects.isNull(rental.getReturnDate()) || Objects.isNull(Constants.formatString(rental.getReturnDate()))){
            return "return date invalid, format should be [" + Constants.DATE_FORMAT + "]";
        }
        return "";
    }

    public String validateRentalUpdate(int rentalId, RentalDto rental) {
        if (Objects.isNull(rental.getId()) || rental.getId() <= 0){
            return "rental id " + Constants.ID_ERROR_MSG;
        }
        if (rentalId != rental.getId()){
            return "rental id in the request and url must be the same.";
        }
        if (!rentalService.exists(rental.getId())){
            return "rental " + Constants.DNECU;
        }
        if (Objects.nonNull(rental.getCustomer()) && rental.getCustomer() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (!customerService.exists(rental.getCustomer())){
            return "customer " + Constants.DNE;
        }
        if (Objects.nonNull(rental.getInventory()) && rental.getInventory() <= 0){
            return "inventory id " + Constants.ID_ERROR_MSG;
        }
        if (!inventoryService.exists(rental.getInventory())){
            return "inventory " + Constants.DNE;
        }
        if (Objects.nonNull(rental.getStaff()) && rental.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(rental.getStaff())){
            return "staff " + Constants.DNE;
        }
        return validateRentalCreation(rental);
    }

    public String validateRentalUpdate(RentalDto rental) {
        if (Objects.isNull(rental.getId()) || rental.getId() <= 0){
            return "rental id " + Constants.ID_ERROR_MSG;
        }
        if (!rentalService.exists(rental.getId())){
            return "rental " + Constants.DNECU;
        }
        if (Objects.nonNull(rental.getCustomer()) && rental.getCustomer() <= 0){
            return "customer id " + Constants.ID_ERROR_MSG;
        }
        if (!customerService.exists(rental.getCustomer())){
            return "customer " + Constants.DNE;
        }
        if (Objects.nonNull(rental.getInventory()) && rental.getInventory() <= 0){
            return "inventory id " + Constants.ID_ERROR_MSG;
        }
        if (!inventoryService.exists(rental.getInventory())){
            return "inventory " + Constants.DNE;
        }
        if (Objects.nonNull(rental.getStaff()) && rental.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(rental.getStaff())){
            return "staff " + Constants.DNE;
        }
        return "";
    }

    public String validateStaffCreation(StaffDto staff) {
        if (StringHelper.isNullOrEmptyString(staff.getFirstName())){
            return "first name " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(staff.getLastName())){
            return "last name " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(staff.getEmail())){
            return "email " + Constants.STRING_ERROR_MSG;
        }
        if (Objects.isNull(staff.getAddress()) || staff.getAddress() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(staff.getAddress())){
            return "address " + Constants.DNE;
        }
        if (Objects.isNull(staff.getStore()) || staff.getStore() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(staff.getStore())){
            return "store " + Constants.DNE;
        }
        if (Objects.isNull(staff.getActive())){
            return "active cannot be null.";
        }
        if (StringHelper.isNullOrEmptyString(staff.getUsername())){
            return "username " + Constants.STRING_ERROR_MSG;
        }
        if (StringHelper.isNullOrEmptyString(staff.getPassword())){
            return "password " + Constants.STRING_ERROR_MSG;
        }
        return "";
    }

    public String validateStaffUpdate(int staffId, StaffDto staff) {
        if (Objects.isNull(staff.getId()) || staff.getId() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (staffId != staff.getId()){
            return "staff id in the request and url must be the same.";
        }
        if (!staffService.exists(staff.getId())){
            return "staff " + Constants.DNECU;
        }
        if (Objects.nonNull(staff.getAddress()) && staff.getAddress() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(staff.getAddress())){
            return "address " + Constants.DNE;
        }
        if (Objects.nonNull(staff.getStore()) && staff.getStore() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(staff.getStore())){
            return "store " + Constants.DNE;
        }
        return "";
    }

    public String validateStaffUpdate(StaffDto staff) {
        if (Objects.isNull(staff.getId()) || staff.getId() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(staff.getId())){
            return "staff " + Constants.DNECU;
        }
        if (Objects.nonNull(staff.getAddress()) && staff.getAddress() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(staff.getAddress())){
            return "address " + Constants.DNE;
        }
        if (Objects.nonNull(staff.getStore()) && staff.getStore() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(staff.getStore())){
            return "store " + Constants.DNE;
        }
        return "";
    }

    public String validateStoreCreation(StoreDto store) {
        if (Objects.isNull(store.getStaff()) || store.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(store.getStaff())){
            return "staff " + Constants.DNE;
        }
        if (Objects.isNull(store.getAddress()) || store.getAddress() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(store.getAddress())){
            return "address " + Constants.DNE;
        }
        return "";
    }

    public String validateStoreUpdate(int storeId, StoreDto store) {
        if (Objects.isNull(store.getId()) || store.getId() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (storeId != store.getId()){
            return "store id in the request and url must be the same.";
        }
        if (!storeService.exists(store.getId())){
            return "store " + Constants.DNECU;
        }
        if (Objects.nonNull(store.getAddress()) && store.getAddress() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(store.getAddress())){
            return "address " + Constants.DNE;
        }
        if (Objects.nonNull(store.getStaff()) && store.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(store.getStaff())){
            return "staff " + Constants.DNE;
        }
        return "";
    }

    public String validateStoreUpdate(StoreDto store) {
        if (Objects.isNull(store.getId()) || store.getId() <= 0){
            return "store id " + Constants.ID_ERROR_MSG;
        }
        if (!storeService.exists(store.getId())){
            return "store " + Constants.DNECU;
        }
        if (Objects.nonNull(store.getAddress()) && store.getAddress() <= 0){
            return "address id " + Constants.ID_ERROR_MSG;
        }
        if (!addressService.exists(store.getAddress())){
            return "address " + Constants.DNE;
        }
        if (Objects.nonNull(store.getStaff()) && store.getStaff() <= 0){
            return "staff id " + Constants.ID_ERROR_MSG;
        }
        if (!staffService.exists(store.getStaff())){
            return "staff " + Constants.DNE;
        }
        return "";
    }
}
