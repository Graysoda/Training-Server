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
import training.persistence.dto.AddressDto;
import training.service.AddressService;
import training.service.CityService;

import java.util.Objects;

@Endpoint
public class AddressEndpoint {
    public static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
    @Autowired
    private AddressService addressService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ValidationHelper validationHelper;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAddressRequest")
    @ResponsePayload
    public CreateAddressResponse insertAddress(@RequestPayload CreateAddressRequest request) {
        CreateAddressResponse response = new CreateAddressResponse();
        AddressDto address = new AddressDto(request);
        String error = validationHelper.validateAddressCreation(address);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setAddress(addressService.save(address));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAddressRequest")
    @ResponsePayload
    public DeleteAddressResponse deleteAddress(@RequestPayload DeleteAddressRequest request) {
        DeleteAddressResponse response = new DeleteAddressResponse();

        if (request.getAddressId() <= 0 || !addressService.exists(request.getAddressId())){
            response.setResponse("address id is invalid");
        } else {
            addressService.deleteById(request.getAddressId());
            response.setResponse("Address with id [" + request.getAddressId() + "] was successfully deleted");
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAddressRequest")
    @ResponsePayload
    public UpdateAddressResponse updateAddress(@RequestPayload UpdateAddressRequest request) {
        UpdateAddressResponse response = new UpdateAddressResponse();
        AddressDto address = new AddressDto(request);
        String error = validationHelper.validateAddressUpdate(address);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setAddress(addressService.save(address));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllAddressRequest")
    @ResponsePayload
    public GetAllAddressResponse getAllAddress(@RequestPayload GetAllAddressRequest request){
        GetAllAddressResponse response = new GetAllAddressResponse();
        response.setAddress(addressService.getAllAddresses());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAddressByIdRequest")
    @ResponsePayload
    public GetAddressByIdResponse getAddressById(@RequestPayload GetAddressByIdRequest request){
        GetAddressByIdResponse response = new GetAddressByIdResponse();

        if (addressService.exists(request.getAddressId())){
            response.setAddress(addressService.getAddressById(request.getAddressId()));
        } else {
            response.setError("address " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAddressByCityRequest")
    @ResponsePayload
    public GetAddressByCityResponse getAddressByCity(@RequestPayload GetAddressByCityRequest request){
        GetAddressByCityResponse response = new GetAddressByCityResponse();

        if (Objects.nonNull(request.getCityName()) && cityService.exists(request.getCityName()))
        {
            response.setAddress(addressService.getAddressByCity(request.getCityName()));
        }
        else if (Objects.nonNull(request.getCityId()) && cityService.exists(request.getCityId()))
        {
            response.setAddress(addressService.getAddressByCityId(request.getCityId()));
        }
        else if (Objects.isNull(request.getCityId()) && Objects.isNull(request.getCityName()))
        {
            response.setError("city id and city name cannot both be null.");
        }
        else
        {
            response.setError("city " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAddressByPostalCodeRequest")
    @ResponsePayload
    public GetAddressByPostalCodeResponse getAddressByPostalCode(@RequestPayload GetAddressByPostalCodeRequest request){
        GetAddressByPostalCodeResponse response = new GetAddressByPostalCodeResponse();
        response.setAddress(addressService.getAddressByPostalCode(request.getPostalCode()));
        return response;
    }
}
