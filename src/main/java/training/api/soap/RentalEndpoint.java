package training.api.soap;

import org.hibernate.validator.internal.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.Constants;
import training.api.ValidationHelper;
import training.generated.*;
import training.persistence.dto.RentalDto;
import training.service.CustomerService;
import training.service.RentalService;
import training.service.StaffService;

import java.util.Objects;

@Endpoint
public class RentalEndpoint {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private ValidationHelper validationHelper;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StaffService staffService;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllRentalsRequest")
    @ResponsePayload
    public GetAllRentalsResponse getAllRentals(@RequestPayload GetAllRentalsRequest request){
        GetAllRentalsResponse response = new GetAllRentalsResponse();
        response.setRental(rentalService.getAllRentals());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getRentalByCustomerIdRequest")
    @ResponsePayload
    public GetRentalByCustomerIdResponse getRentalByCustomerId(@RequestPayload GetRentalByCustomerIdRequest request){
        GetRentalByCustomerIdResponse response = new GetRentalByCustomerIdResponse();

        if (customerService.exists(request.getCustomerId())){
            response.setRental(rentalService.getCustomerRentals(request.getCustomerId()));
        } else {
            response.setError("customer " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getRentalByStartDateRequest")
    @ResponsePayload
    public GetRentalByStartDateResponse getRentalByStartDate(@RequestPayload GetRentalByStartDateRequest request){
        GetRentalByStartDateResponse response = new GetRentalByStartDateResponse();

        if (Objects.nonNull(Constants.formatString(request.getRentalStartDate()))){
            response.setRental(rentalService.getRentalsByStartDate(Constants.formatString(request.getRentalStartDate())));
        } else {
            response.setError("date is invalid.");
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getRentalByIdRequest")
    @ResponsePayload
    public GetRentalByIdResponse getRentalById(@RequestPayload GetRentalByIdRequest request){
        GetRentalByIdResponse response = new GetRentalByIdResponse();

        if (rentalService.exists(request.getRentalId())){
            response.setRental(rentalService.getRentalById(request.getRentalId()));
        } else {
            response.setError("rental " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getRentalByReturnDateRequest")
    @ResponsePayload
    public GetRentalByReturnDateResponse getRentalByReturnDate(@RequestPayload GetRentalByReturnDateRequest request){
        GetRentalByReturnDateResponse response = new GetRentalByReturnDateResponse();

        if (Objects.nonNull(Constants.formatString(request.getReturnDate()))){
            response.setRental(rentalService.getRentalsByReturnDate(Constants.formatString(request.getReturnDate())));
        } else {
            response.setError("date is invalid.");
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getRentalByStaffIdRequest")
    @ResponsePayload
    public GetRentalByStaffIdResponse getRentalByStaffId(@RequestPayload GetRentalByStaffIdRequest request){
        GetRentalByStaffIdResponse response = new GetRentalByStaffIdResponse();

        if (staffService.exists(request.getStaffId())){
            response.setRental(rentalService.getStaffRentals(request.getStaffId()));
        } else {
            response.setError("staff " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "createRentalRequest")
    @ResponsePayload
    public CreateRentalResponse createRental(@RequestPayload CreateRentalRequest request){
        CreateRentalResponse response = new CreateRentalResponse();
        RentalDto rental = new RentalDto(request);
        String error = validationHelper.validateRentalCreation(rental);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setRental(rentalService.save(rental));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "updateRentalRequest")
    @ResponsePayload
    public UpdateRentalResponse updateRental(@RequestPayload UpdateRentalRequest request){
        UpdateRentalResponse response = new UpdateRentalResponse();
        RentalDto rental = new RentalDto(request);
        String error = validationHelper.validateRentalUpdate(rental);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setRental(rentalService.save(rental));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "deleteRentalRequest")
    @ResponsePayload
    public DeleteRentalResponse deleteRental(@RequestPayload DeleteRentalRequest request){
        DeleteRentalResponse response = new DeleteRentalResponse();

        if (request.getRentalId() <= 0 || !rentalService.exists(request.getRentalId())){
            response.setResponse("rental id is invalid");
        } else {
            rentalService.delete(request.getRentalId());
            response.setResponse("rental with id [" + request.getRentalId() + "] was deleted.");
        }

        return response;
    }
}
