package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.impl.AddressServiceImpl;

@Endpoint
public class AddressEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired @Lazy private AddressServiceImpl addressService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createAddressRequest")
	@ResponsePayload
	public CreateAddressResponse insertAddress(@RequestPayload CreateAddressRequest request){
		CreateAddressResponse response = new CreateAddressResponse();

		if (request.getAddress().isEmpty()){
			response.setError("first address line cannot be empty.");
			return response;
		}
		if (request.getCity().isEmpty()){
			response.setError("city cannot be empty.");
			return response;
		}
		if (request.getDistrict().isEmpty()){
			response.setError("district cannot be empty.");
			return response;
		}
		if (request.getPhone().isEmpty()){
			response.setError("phone cannot be empty.");
			return response;
		}
		if (request.getPostalCode().isEmpty()){
			response.setError("postalCode cannot be empty.");
			return response;
		}

		ResponseEntity entity = addressService.insertAddress(request);

		if (entity.getBody() instanceof Address){
			response.setAddress(((Address) entity.getBody()));
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteAddressRequest")
	@ResponsePayload
	public DeleteAddressResponse deleteAddress(@RequestPayload DeleteAddressRequest request){
		DeleteAddressResponse response = new DeleteAddressResponse();

		if (request.getAddressId() < 0){
			response.setResponse("addressId is invalid");
			return response;
		}

		ResponseEntity entity = addressService.deleteAddress(request.getAddressId());

		response.setResponse(entity.getBody().toString());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateAddressRequest")
	@ResponsePayload
	public UpdateAddressResponse updateAddress(@RequestPayload UpdateAddressRequest request){
		UpdateAddressResponse response = new UpdateAddressResponse();

		if (request.getAddressId() < 0){
			response.setError("addressId is invalid");
			return response;
		}

		ResponseEntity entity = addressService.updateAddress(request);

		if (entity.getBody() instanceof Address){
			response.setAddress((Address) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
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
	public GetAddressByPostalCodeResponse getAddressByPostalCode(@RequestPayload GetAddressByPostalCodeRequest request){
		GetAddressByPostalCodeResponse response = new GetAddressByPostalCodeResponse();
		response.setAddress(addressService.getAddressByPostalCode(request.getPostalCode()));
		return response;
	}
}
