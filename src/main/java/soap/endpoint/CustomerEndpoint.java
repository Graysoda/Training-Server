package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.CustomerServiceImpl;

@Endpoint
public class CustomerEndpoint {
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	@Autowired @Lazy private CustomerServiceImpl customerService;

//	@Autowired
//	public void setCustomerService(@Lazy CustomerServiceImpl customerService) {
//		this.customerService = customerService;
//	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createCustomerRequest")
	public void insertCustomer(@RequestPayload CreateCustomerRequest request){
		customerService.insertCustomer(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteCustomerRequest")
	public void deleteCustomer(@RequestPayload DeleteCustomerRequest request){
		customerService.deleteCustomer(request.getCustomerId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateCustomerRequest")
	public void updateCustomer(@RequestPayload UpdateCustomerRequest request){
		customerService.updateCustomer(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI,localPart = "getActiveCustomersRequest")
	@ResponsePayload
	public GetActiveCustomersResponse getActiveCustomers(@RequestPayload GetActiveCustomersRequest request){
		GetActiveCustomersResponse response = new GetActiveCustomersResponse();
		response.setCustomer(customerService.getActiveCustomers());
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
