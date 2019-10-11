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
import training.persistence.dto.CustomerDto;
import training.service.CustomerService;
import training.service.StoreService;

@Endpoint
public class CustomerEndpoint {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ValidationHelper validationHelper;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getAllCustomersRequest")
    @ResponsePayload
    public GetAllCustomersResponse getAllCustomers(@RequestPayload GetAllCustomersRequest request){
        GetAllCustomersResponse response = new GetAllCustomersResponse();
        response.setCustomer(customerService.getAllCustomers());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getCustomersByStoreRequest")
    @ResponsePayload
    public GetCustomersByStoreResponse getCustomersByStore(@RequestPayload GetCustomersByStoreRequest request){
        GetCustomersByStoreResponse response = new GetCustomersByStoreResponse();

        if (storeService.exists(request.getStoreId())){
            response.setCustomer(customerService.getCustomersByStore(request.getStoreId()));
        } else {
            response.setError("store " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getCustomerByIdRequest")
    @ResponsePayload
    public GetCustomerByIdResponse getCustomerById(@RequestPayload GetCustomerByIdRequest request){
        GetCustomerByIdResponse response = new GetCustomerByIdResponse();

        if (customerService.exists(request.getCustomerId())){
            response.setCustomer(customerService.getCustomerById(request.getCustomerId()));
        } else {
            response.setError("customer " + Constants.DNE);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getInactiveCustomersRequest")
    @ResponsePayload
    public GetInactiveCustomersResponse getInactiveCustomers(@RequestPayload GetInactiveCustomersRequest request){
        GetInactiveCustomersResponse response = new GetInactiveCustomersResponse();
        response.setCustomer(customerService.getInactiveCustomers());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getActiveCustomersRequest")
    @ResponsePayload
    public GetActiveCustomersResponse getActiveCustomers(@RequestPayload GetActiveCustomersRequest request){
        GetActiveCustomersResponse response = new GetActiveCustomersResponse();
        response.setCustomer(customerService.getActiveCustomers());
        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "createCustomerRequest")
    @ResponsePayload
    public CreateCustomerResponse createCustomer(@RequestPayload CreateCustomerRequest request){
        CreateCustomerResponse response = new CreateCustomerResponse();
        CustomerDto customer = new CustomerDto(request);
        String error = validationHelper.validateCustomerCreation(customer);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setCustomer(customerService.save(customer));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "updateCustomerRequest")
    @ResponsePayload
    public UpdateCustomerResponse updateCustomer(@RequestPayload UpdateCustomerRequest request){
        UpdateCustomerResponse response = new UpdateCustomerResponse();
        CustomerDto customer = new CustomerDto(request);
        String error = validationHelper.validateCustomerUpdate(customer);

        if (StringHelper.isNullOrEmptyString(error)){
            response.setCustomer(customerService.save(customer));
        } else {
            response.setError(error);
        }

        return response;
    }

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "deleteCustomerRequest")
    @ResponsePayload
    public DeleteCustomerResponse deleteCustomer(@RequestPayload DeleteCustomerRequest request){
        DeleteCustomerResponse response = new DeleteCustomerResponse();

        if (request.getCustomerId() <= 0 || !customerService.exists(request.getCustomerId())){
            response.setResponse("customer id invalid.");
        } else {
            customerService.delete(request.getCustomerId());
            response.setResponse("Customer with id [" + request.getCustomerId() + "] was deleted.");
        }

        return response;
    }
}
