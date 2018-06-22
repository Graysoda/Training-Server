package training.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import training.generated.*;
import training.service.impl.StaffServiceImpl;

@Endpoint
public class StaffEndpoint {
	private static final String NAMESPACE_URI = SoapConstants.NAMESPACE_URI;
	@Autowired @Lazy private StaffServiceImpl staffService;

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllStaffRequest")
	@ResponsePayload
	public GetAllStaffResponse getAllStaff(@RequestPayload GetAllStaffRequest request){
		GetAllStaffResponse response = new GetAllStaffResponse();
		response.setStaff(staffService.getAllStaff());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getStaffByIdRequest")
	@ResponsePayload
	public GetStaffByIdResponse getStaffById(@RequestPayload GetStaffByIdRequest request){
		GetStaffByIdResponse response = new GetStaffByIdResponse();
		response.setStaff(staffService.getStaffById(request.getStaffId()));
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "createStaffRequest")
	@ResponsePayload
	public CreateStaffResponse insertStaff(@RequestPayload CreateStaffRequest request){
		CreateStaffResponse response = new CreateStaffResponse();

		if (request.getFirstName().isEmpty()){
			response.setError("firstName cannot be empty");
			return response;
		}
		if (request.getLastName().isEmpty()){
			response.setError("lastName cannot be empty");
			return response;
		}
		if (request.getPassword().isEmpty()){
			response.setError("password cannot be empty");
			return response;
		}
		if (request.getUsername().isEmpty()){
			response.setError("username cannot be empty");
			return response;
		}
		if (request.getEmail().isEmpty()){
			response.setError("email cannot be empty");
			return response;
		}
		if (request.getAddressId() < 0){
			response.setError("addressId is invalid");
			return response;
		}
		if (request.getStoreId() < 0){
			response.setError("storeId is invalid");
			return response;
		}

		ResponseEntity entity = staffService.insertStaff(request);

		if (entity.getBody() instanceof Staff){
			response.setStaff((Staff) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteStaffRequest")
	@ResponsePayload
	public DeleteStaffResponse deleteStaff(@RequestPayload DeleteStaffRequest request){
		DeleteStaffResponse response = new DeleteStaffResponse();

		if (request.getStaffId() < 0){
			response.setResponse("staffId is invalid");
			return response;
		}

		ResponseEntity entity = staffService.deleteStaff(request.getStaffId());

		response.setResponse(entity.getBody().toString());
		return response;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateStaffRequest")
	@ResponsePayload
	public UpdateStaffResponse updateStaff(@RequestPayload UpdateStaffRequest request){
		UpdateStaffResponse response = new UpdateStaffResponse();

		if (request.getStaffId() < 0){
			response.setError("staffId is invalid");
			return response;
		}

		ResponseEntity entity = staffService.updateStaff(request);

		if (entity.getBody() instanceof Staff){
			response.setStaff((Staff) entity.getBody());
			return response;
		} else {
			response.setError(entity.getBody().toString());
			return response;
		}
	}
}
