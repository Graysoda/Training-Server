package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.AddressServiceImpl;

@Endpoint
public class AddressEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired @Lazy private AddressServiceImpl addressService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAddressRequest")
	@ResponsePayload
	public ResponseEntity<?> insertAddress(@RequestPayload CreateAddressRequest request){
		return addressService.insertAddress(request);
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAddressRequest")
	@ResponsePayload
	public ResponseEntity<?> deleteAddress(@RequestPayload DeleteAddressRequest request){
		return addressService.deleteAddress(request.getAddressId());
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAddressRequest")
	@ResponsePayload
	public ResponseEntity<?> updateAddress(@RequestPayload UpdateAddressRequest request){
		return addressService.updateAddress(request);
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
		response.setAddress(addressService.getAddressById(request.getAddressId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAddressByCityRequest")
	@ResponsePayload
	public GetAddressByCityResponse getAddressByCity(@RequestPayload GetAddressByCityRequest request){
		GetAddressByCityResponse response = new GetAddressByCityResponse();
		response.setAddress(addressService.getAddressByCity(request.getCityName()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAddressByPostalCodeRequest")
	@ResponsePayload
	public GetAddressByPostalCodeResponse getAddressByPostalCode(@RequestPayload GetAddresssByPostalCodeRequest request){
		GetAddressByPostalCodeResponse response = new GetAddressByPostalCodeResponse();
		response.setAddress(addressService.getAddressByPostalCode(request.getPostalCode()));
		return response;
	}
}
