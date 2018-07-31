package training.api.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.api.Common;
import training.generated.*;
import training.service.impl.CustomerServiceImpl;

@Endpoint
public class CustomerEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired @Lazy private CustomerServiceImpl customerService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCustomerRequest")
	@ResponsePayload
	public CreateCustomerResponse insertCustomer(@RequestPayload CreateCustomerRequest request){
		CreateCustomerResponse response = new CreateCustomerResponse();

		if (request.getFirstName().isEmpty()){
			response.setError("Customer firstName cannot be empty.");
			return response;
		} else if (!Common.isStringSafe(request.getFirstName())){
			response.setError(Common.stringFailureMessage("Customer firstName"));
			return response;
		}
		if (request.getLastName().isEmpty()){
			response.setError("Customer lastName cannot be empty.");
			return response;
		} else if (!Common.isStringSafe(request.getLastName())){
			response.setError(Common.stringFailureMessage("Customer lastName"));
			return response;
		}
		if (request.getEmail().isEmpty()){
			response.setError("Customer email cannot be empty.");
			return response;
		} else if (!Common.isStringSafe(request.getEmail())){
			response.setError(Common.stringFailureMessage("Customer email"));
			return response;
		}
		if (request.getAddressId() < 0){
			response.setError("Customer addressId is invalid");
			return response;
		}
		if (request.getStoreId() < 0){
			response.setError("Customer storeId is invalid");
			return response;
		}

		ResponseEntity entity = customerService.insertCustomer(request);

		if (entity.getBody() instanceof Customer){
			response.setCustomer(((Customer) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCustomerRequest")
	@ResponsePayload
	public DeleteCustomerResponse deleteCustomer(@RequestPayload DeleteCustomerRequest request){
		DeleteCustomerResponse response = new DeleteCustomerResponse();

		if (request.getCustomerId() < 0){
			response.setResponse("customerId is invalid.");
			return response;
		}

		ResponseEntity entity = customerService.deleteCustomer(request.getCustomerId());

		response.setResponse(entity.getBody().toString());

		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCustomerRequest")
	@ResponsePayload
	public UpdateCustomerResponse updateCustomer(@RequestPayload UpdateCustomerRequest request){
		UpdateCustomerResponse response = new UpdateCustomerResponse();

		if (request.getCustomerId() < 0){
			response.setError("customerId is invalid.");
			return response;
		}

		ResponseEntity entity = customerService.updateCustomer(request);

		if (entity.getBody() instanceof Customer){
			response.setCustomer(((Customer) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI,localPart = "getActiveCustomersRequest")
	@ResponsePayload
	public GetActiveCustomersResponse getActiveCustomers(@RequestPayload GetActiveCustomersRequest request){
		GetActiveCustomersResponse response = new GetActiveCustomersResponse();
		response.setCustomer(customerService.getActiveCustomers());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInactiveCustomersRequest")
	@ResponsePayload
	public GetInactiveCustomersResponse getInactiveCustomer(@RequestPayload GetInactiveCustomersRequest request){
		GetInactiveCustomersResponse response = new GetInactiveCustomersResponse();
		response.setCustomer(customerService.getInactiveCustomers());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomerByIdRequest")
	@ResponsePayload
	public GetCustomerByIdResponse getCustomerById(@RequestPayload GetCustomerByIdRequest request){
		GetCustomerByIdResponse response = new GetCustomerByIdResponse();
		response.setCustomer(customerService.getCustomerById(request.getCustomerId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCustomersByStoreRequest")
	@ResponsePayload
	public GetCustomersByStoreResponse getCustomersByStore(@RequestPayload GetCustomersByStoreRequest request){
		GetCustomersByStoreResponse response = new GetCustomersByStoreResponse();
		response.setCustomer(customerService.getCustomersByStore(request.getStoreId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllCustomersRequest")
	@ResponsePayload
	public GetAllCustomersResponse getAllCustomers(@RequestPayload GetAllCustomersRequest request){
		GetAllCustomersResponse response = new GetAllCustomersResponse();
		response.setCustomer(customerService.getAllCustomers());
		return response;
	}
}
