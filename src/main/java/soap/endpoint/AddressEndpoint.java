package soap.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import soap.generated.*;
import soap.service.AddressServiceImpl;

@Endpoint
public class AddressEndpoint {
	private static final String NAMESPACE_URI = Constants.NAMESPACE_URI;
	@Autowired @Lazy private AddressServiceImpl addressService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAddressRequest")
	public void insertAddress(@RequestPayload CreateAddressRequest request){
		addressService.insertAddress(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAddressRequest")
	public void deleteAddress(@RequestPayload DeleteAddressRequest request){
		addressService.deleteAddress(request.getAddressId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAddressRequest")
	public void updateAddress(@RequestPayload UpdateAddressRequest request){
		addressService.updateAddress(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllAddressRequest")
	@ResponsePayload
	public GetAllAddressResponse getAllAddress(@RequestPayload GetAllAddressRequest request){
		GetAllAddressResponse response = new GetAllAddressResponse();
		response.setAddress(addressService.getAllAddresses());
		return response;
	}
}
